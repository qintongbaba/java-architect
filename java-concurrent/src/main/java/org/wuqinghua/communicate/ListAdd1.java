package org.wuqinghua.communicate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuqinghua on 18/2/11.
 */
public class ListAdd1 {
    //注意使用了volatile保证线程间可见
    private volatile static List<String> list = new ArrayList<>();

    public static void add() {
        list.add("root");
    }

    public static int size() {
        return list.size();
    }


    public static void main(String[] args) {
        ListAdd1 listAdd1 = new ListAdd1();
        Thread t1 = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    listAdd1.add();
                    System.out.println("当前线程:" + Thread.currentThread().getName() + " 添加了一个元素...");
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            while (true) {
                if (listAdd1.size() == 5) {
                    System.out.println("当前线程:" + Thread.currentThread().getName() + "收到通知 list size =" +
                            " 5 线程停止！");
                    throw new RuntimeException();
                }
            }
        }, "t2");
        t2.start();
        t1.start();
    }
}
