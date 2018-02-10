package org.wuqinghua.thread;

/**
 * synchronized锁重入
 * Created by wuqinghua on 18/2/10.
 */
public class SyncDubbo1 {

    public void method1() {
        System.out.println("method1...");
        method2();
    }

    public void method2() {
        System.out.println("method2...");
        method3();
    }

    public void method3() {
        System.out.println("method3...");
    }

    public static void main(String[] args) {
        final SyncDubbo1 sd = new SyncDubbo1();

        Thread t1 = new Thread(() -> sd.method1());

        t1.start();
    }
}
