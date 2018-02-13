package org.wuqinghua.disruptor.helloworld;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * Created by wuqinghua on 18/2/12.
 */
public class LongEventProducer {

    private final RingBuffer<LongEvent> ringBuffer;

    public LongEventProducer(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }


    public void onData(ByteBuffer byteBuffer) {
        long sequence = ringBuffer.next();

        try {
            LongEvent longEvent = ringBuffer.get(sequence);
            longEvent.setValue(byteBuffer.getLong(0));
        } finally {
            ringBuffer.publish(sequence);
        }


    }
}
