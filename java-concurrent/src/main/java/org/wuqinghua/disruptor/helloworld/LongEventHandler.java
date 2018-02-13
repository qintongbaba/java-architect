package org.wuqinghua.disruptor.helloworld;

import com.lmax.disruptor.EventHandler;

/**
 * Created by wuqinghua on 18/2/12.
 */
public class LongEventHandler implements EventHandler<LongEvent> {
    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println(event.getValue());
    }
}
