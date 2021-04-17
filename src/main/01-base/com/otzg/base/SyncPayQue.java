package com.otzg.base;

import com.otzg.log.util.LogUtil;
import org.springframework.stereotype.Service;

@Service
public class SyncPayQue extends SyncQue {
    private final static String channel="sync_pay_channel";

    public SyncPayQue() {
        super.addChannelMap(channel,this);
    }

    @Override
    public final void send(String ids){
        LogUtil.print("send auth change message");
        super.sendPublish(channel,ids);
        super.sendProduce(channel,ids);
    }

    @Override
    protected void receiver(String message){
        LogUtil.print("receiver auth change message="+message);
    }

}
