package org.wuqinghua.thread;

/**
 * Created by wuqinghua on 18/2/11.
 */
public class SyncException1 {
    private int i = 0;

    public synchronized void operation() {
        while (true) {
            try {
                i++;
                Thread.sleep(200);
                System.out.println(Thread.currentThread().getName() + ",i = " + i);
                if (i == 10) {
                    Integer.parseInt("a");
//                    throw new RuntimeException();
                }
            } catch (Exception e) { //InterruptedException
                e.printStackTrace();
                System.out.println(" log info i = " + i);
//                throw new RuntimeException();
//                continue;
            }
        }
    }

    public static void main(String[] args) {
        final SyncException1 se = new SyncException1();
        Thread t1 = new Thread(() -> se.operation(), "t1");
        t1.start();
    }
}
