package org.wuqinghua.thread;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuqinghua on 18/2/11.
 */
public class SyncException2 {

    public synchronized void operation(int tag) {
        try {
            System.out.println(Thread.currentThread().getName() + " , tag = " + tag);
            Thread.sleep(1000);
            if (tag == 10) {
                Integer.parseInt("a");
            }
        } catch (Exception e) {
            System.out.println(" log info tag = " + tag);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SyncException2 se = new SyncException2();
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            int finalI = i;
            threadList.add(new Thread(() -> se.operation(finalI), "t" + i));
        }

        for (int i = 0; i < threadList.size(); i++) {
            threadList.get(i).start();
        }
    }
}
