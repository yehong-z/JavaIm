package com.zyh.javaim.comet.netty;


import com.alibaba.fastjson2.JSON;
import com.zyh.javaim.LogicService;
import com.zyh.javaim.Message;
import com.zyh.javaim.MessageType;
import com.zyh.javaim.comet.common.context.UserDetail;
import com.zyh.javaim.comet.common.convention.redis.Key;
import com.zyh.javaim.comet.common.jwt.JwtGenerator;
import com.zyh.javaim.comet.common.util.TimingWheel;
import io.netty.channel.ChannelHandler;
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
@ChannelHandler.Sharable
public class SocketHandler extends ChannelInboundHandlerAdapter {
    public static final ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private final RedisTemplate<String, ChannelId> redis;
    private final RedisTemplate<String, Integer> redis2;
    private final TimingWheel timingWheel;
    private final SendMessage sendMessage;
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
        try {
            byte[] data = (byte[]) msg;

            Message message = JSON.parseObject(data, Message.class);
            log.info("client msg:"+message.toString());
            if (message.type == MessageType.Auth) {
                String token = message.content;
                UserDetail userDetail = JwtGenerator.parseJwtToken(token);
                var UserId = userDetail.getUser().getId();
                log.info("user" + UserId + "连接");
                // 解析传过来的token，并设置redis 用户id-> channelID的映射
                redis.opsForValue().set(Key.UserChannelKey(UserId), ctx.channel().id());
                ctx.channel().writeAndFlush("验证成功\n");

                // 先拉取离线消息，再注册状态server，否则容易导致消息乱序
                var offlineMessage = logicService.getOfflineMessage(UserId);
                for (Message m : offlineMessage) {
                    ctx.channel().writeAndFlush(m.toString());
                }

                redis2.opsForValue().set(Key.UserServerKey(userDetail.getUser().getId()), 1);
            } else if (message.type == MessageType.Ack) {
                // 收到ACK取消任务
                timingWheel.cancelTimeout(
                        timingWheel.EventKey(
                                message.getToUserId(),
                                message.getMsgSeq()));
                // 告知已读 TODO： 发现本机没有连接通过rpc调用转发ack消息
                Message ackMsg = new Message();
                ackMsg.setType(MessageType.Ack)
                        .setMsgSeq(message.getMsgSeq())
                        .setFromUserId(message.getToUserId())
                        .setToUserId(message.getFromUserId());
                sendMessage.Send(ackMsg);

            } else if (message.type == MessageType.HeartBeat) {
                log.info("receive heartbeat");
                ctx.writeAndFlush("收到心跳");
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
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
