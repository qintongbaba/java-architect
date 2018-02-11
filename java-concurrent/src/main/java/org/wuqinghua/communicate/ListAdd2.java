package org.wuqinghua.communicate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuqinghua on 18/2/11.
 * 使用wait和notify来进行通信
 */
public class ListAdd2 {
    private volatile static List<String> list = new ArrayList<>();

    public static void add() {
        list.add("root");
    }

    public static int size() {
        return list.size();
    }


    public static void main(String[] args) {


        ListAdd2 list = new ListAdd2();

        Object lock = new Object();

        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                try {
                    for (int i = 0; i < 10; i++) {
                        list.add();
                        System.out.println("当前线程:" + Thread.currentThread().getName() + " 添加了一个元素...");
                        Thread.sleep(500);
                        if (list.size() == 5) {
                            System.out.println("当前线程:" + Thread.currentThread().getName() + " 发出通知..");
                            lock.notify();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1");


        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                if (list.size() != 5) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("当前线程:" + Thread.currentThread().getName() + "收到通知,线程停止...");
                throw new RuntimeException();
            }
        }, "t2");

        t2.start();
        t1.start();
    }
}
