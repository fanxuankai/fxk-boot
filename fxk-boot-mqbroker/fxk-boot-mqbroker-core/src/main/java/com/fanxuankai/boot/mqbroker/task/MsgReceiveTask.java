package com.fanxuankai.boot.mqbroker.task;

import com.fanxuankai.boot.mqbroker.domain.MsgReceive;
import com.fanxuankai.boot.mqbroker.service.MsgReceiveService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author fanxuankai
 */
@Component
public class MsgReceiveTask implements Runnable {

    @Resource
    private MsgReceiveService msgReceiveService;

    @Override
    public void run() {
        while (true) {
            List<MsgReceive> records = msgReceiveService.pullData();
            if (records.isEmpty()) {
                return;
            }
            for (MsgReceive msg : records) {
                if (!msgReceiveService.lock(msg.getId())) {
                    continue;
                }
                msgReceiveService.consume(msg, true, false);
            }
        }
    }
}
