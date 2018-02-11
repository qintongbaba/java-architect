package org.wuqinghua.thread;

/**
 * Created by wuqinghua on 18/2/11.
 * synchronized可以在任意的Object对象上添加，用法比较灵活
 */
public class ObjectLock {


    public void method01() {
        synchronized (this) {         //对象锁
            try {
                System.out.println("do method1...");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void method02() {
        synchronized (ObjectLock.class) {      //类锁
            try {
                System.out.println("do method2...");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private Object lock = new Object();

    public void method03() {
        synchronized (lock) {       //任意对象锁
            try {
                System.out.println("do method3...");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        ObjectLock ol = new ObjectLock();
        Thread t1 = new Thread(() -> ol.method01());
        Thread t2 = new Thread(() -> ol.method02());
        Thread t3 = new Thread(() -> ol.method03());


        t1.start();
        t2.start();
        t3.start();
    }
}
