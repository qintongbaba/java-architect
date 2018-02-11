package org.wuqinghua.thread;

/**
 * Created by wuqinghua on 18/2/11.
 */
public class ChangeLock {
    private String lock = "lock";

    private void method() {
        synchronized (lock) {
            try {
                while (true) {
                    System.out.println("当前线程：" + Thread.currentThread().getName() + " 开始");
                    lock = "change lock";
                    Thread.sleep(2000);
                    System.out.println("当前线程：" + Thread.currentThread().getName() + " 结束");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
        }
    }

    public static void main(String[] args) {
        final ChangeLock cl = new ChangeLock();
        Thread t1 = new Thread(() -> cl.method(), "t1");
        Thread t2 = new Thread(() -> cl.method(), "t2");

        t1.start();
        t2.start();
    }
}
