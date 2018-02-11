package org.wuqinghua.communicate;

import org.wuqinghua.thread.ObjectLock;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by wuqinghua on 18/2/11.
 */
public class MyQueue<T> {

    //1.需要一个承装元素的集合
    private final LinkedList<T> list = new LinkedList<>();

    //2.需要一个计数器
    private AtomicInteger count = new AtomicInteger(0);

    //3.需要指定上限和下限
    private final int minSize = 0;
    private final int maxSize;

    public MyQueue() {
        this(5);
    }

    public MyQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    //4.需要一个对象来进行加锁
    private final Object lock = new Object();


    public void put(T t) {
        synchronized (lock) {
            while (this.count.get() == maxSize) { //容器已满
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //添加元素
            this.list.add(t);
            count.incrementAndGet();
            System.out.println("添加了一个元素:" + t);
            //唤醒其他线程
            this.lock.notify();
        }
    }


    public T take() {
        T ret = null;
        synchronized (lock) {
            while (this.count.get() == this.minSize) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //获取数据
            ret = list.removeFirst();
            System.out.println("获取了一个元素:" + ret);
            count.decrementAndGet();
            this.lock.notify();
        }
        return ret;
    }


    public int size() {
        return this.count.get();
    }


    public static void main(String[] args) {

        MyQueue<String> queue = new MyQueue<>();
        queue.put("a");
        queue.put("b");
        queue.put("c");
        queue.put("d");
        queue.put("e");
        System.out.println(queue.size());


        Thread t1 = new Thread(()->{
            queue.put("f");
            queue.put("g");
        });

        t1.start();


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread t2 = new Thread(()->{
            queue.take();
            queue.take();
        });

        t2.start();
    }

}
