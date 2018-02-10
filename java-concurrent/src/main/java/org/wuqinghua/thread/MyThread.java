package org.wuqinghua.thread;

/**
 * Created by wuqinghua on 18/2/10.
 */
public class MyThread extends Thread {


    private int count = 5;


    @Override
    public synchronized void run() {
        count--;
        System.out.println(this.currentThread().getName() + " count = " + count);
    }


    public static void main(String[] args) {
        MyThread myThread = new MyThread();

        /**
         * 分析:  如果run方法没有添加synchronized修饰的时候，那么这个MyThread不是一个线程安全的类
         *       如果添加了synchronized修饰,当多个线程访问MyThread的run方法时候，以排队的方式进行处理（这里的排队是按照cpu分配的
         *       先后而定的）。
         *       一个线程想要执行synchronized修饰的方法里的代码：
         *          1.尝试获取锁
         *          2.如果获取到锁，那么就开始执行synchronized中的代码，如果获取不到，这个线程就会不断的尝试获取锁，直到拿到为止
         *            而且是多个线程同时去竞争这把锁。（也就会出现锁竞争问题）
         */
        Thread t1 = new Thread(myThread, "t1");
        Thread t2 = new Thread(myThread, "t2");
        Thread t3 = new Thread(myThread, "t3");
        Thread t4 = new Thread(myThread, "t4");
        Thread t5 = new Thread(myThread, "t5");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
    }
}
