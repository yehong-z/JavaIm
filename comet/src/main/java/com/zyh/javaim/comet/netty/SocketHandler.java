package com.zyh.javaim.comet.netty;


import com.zyh.javaim.LogicService;
import com.zyh.javaim.Message;
import com.zyh.javaim.comet.common.context.UserDetail;
import com.zyh.javaim.comet.common.convention.redis.Key;
import com.zyh.javaim.comet.common.jwt.JwtGenerator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;




@Slf4j
@RequiredArgsConstructor
@Component
public class SocketHandler extends ChannelInboundHandlerAdapter {
    public static final ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private final RedisTemplate<String, ChannelId> redis;
    private final RedisTemplate<String, Integer> redis2;

    @DubboReference
    LogicService logicService;
    /**
     * 读取到客户端发来的消息
     *
     * @param ctx ChannelHandlerContext
     * @param msg msg
     * @throws Exception e
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 由于我们配置的是 字节数组 编解码器，所以这里取到的用户发来的数据是 byte数组
        byte[] data = (byte[]) msg;
        String token = new String(data);
        log.info("收到消息: " + token);

        // 解析传过来的token，并设置redis 用户id-> channelID的映射
        UserDetail userDetail = JwtGenerator.parseJwtToken(token);
        var UserId = userDetail.getUser().getId();
        log.info("user" + UserId + "连接");
        redis.opsForValue().set(Key.UserChannelKey(UserId), ctx.channel().id());
        ctx.channel().writeAndFlush("验证成功\n");
        // 先拉取离线消息，再注册状态server，否则容易导致消息乱序
        var offlineMessage = logicService.getOfflineMessage(UserId);
        for (Message m : offlineMessage) {
            ctx.channel().writeAndFlush(m.toString());
        }

        redis2.opsForValue().set(Key.UserServerKey(userDetail.getUser().getId()), 1);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        if (evt instanceof IdleStateEvent idleStateEvent) {
            if (idleStateEvent.state() == IdleState.WRITER_IDLE) {
                log.info("断开连接");
                ctx.writeAndFlush("超时断开连接");
                clients.remove(ctx.channel());
                // TODO: 删除redis中连接信息，删除clients中连接
            }

        }
    }


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("新的客户端链接：" + ctx.channel().id().asShortText());
        // 握手，校验身份
        ctx.channel().writeAndFlush("连接成功\n");
        clients.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        clients.remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
        clients.remove(ctx.channel());
    }


}
