package com.zyh.javaim.logic.service;

import com.zyh.javaim.LogicService;
import com.zyh.javaim.Message;
import com.zyh.javaim.logic.dao.entity.OfflineMessage;
import com.zyh.javaim.logic.dao.entity.OfflineMessageExample;
import com.zyh.javaim.logic.dao.mapper.OfflineMessageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.ArrayList;
import java.util.List;

@DubboService
@Slf4j
@RequiredArgsConstructor
public class LogicServiceImpl implements LogicService {
    private final OfflineMessageMapper offlineMessageMapper;
    @Override
    public List<Message> getOfflineMessage(Long userId) {
        OfflineMessageExample offlineMessageExample = new OfflineMessageExample();
        offlineMessageExample.createCriteria().andToUserIdEqualTo(userId);
        var offlineMessages = offlineMessageMapper.selectByExample(offlineMessageExample);
        var res = new ArrayList<Message>();
        for (OfflineMessage msg : offlineMessages) {
            Message m = new Message();
            m.content = msg.getContent();
            m.toUserId = msg.getToUserId();
            res.add(m);
        }
        return res;
    }
}
