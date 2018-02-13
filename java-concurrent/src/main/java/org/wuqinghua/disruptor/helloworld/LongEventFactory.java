package org.wuqinghua.disruptor.helloworld;

import com.lmax.disruptor.EventFactory;

/**
 * Created by wuqinghua on 18/2/12.
 */
public class LongEventFactory implements EventFactory<LongEvent> {

    @Override
    public LongEvent newInstance() {
        return new LongEvent();
    }
}
