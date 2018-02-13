package org.wuqinghua.disruptor.helloworld;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by wuqinghua on 18/2/12.
 */
public class LongEventMain {
    public static void main(String[] args) {
        //创建线程池
//        ExecutorService executor = Executors.newCachedThreadPool();
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        //创建工厂
        LongEventFactory factory = new LongEventFactory();

        //创建bufferSiz，也就是RingBuffer大小，必须为2的n次方
        int ringBufferSize = 1024 * 1024;

        //创建disruptor实例
        Disruptor<LongEvent> disruptor = new Disruptor<>(factory, ringBufferSize,
                threadFactory, ProducerType.SINGLE, new YieldingWaitStrategy());

        // 连接消费事件方法
        disruptor.handleEventsWith(new LongEventHandler());

        // 启动
        disruptor.start();


        //Disruptor 的事件发布过程是一个两个阶段提交过程
        //发布事件
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        LongEventProducer producer = new LongEventProducer(ringBuffer);

        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        for (long i = 0; i < 100; i++) {
            byteBuffer.putLong(0,i);
            producer.onData(byteBuffer);
        }

        disruptor.shutdown();
    }
}
