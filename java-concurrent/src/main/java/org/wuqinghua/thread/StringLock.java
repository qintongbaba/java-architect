package org.wuqinghua.thread;

/**
 * Created by wuqinghua on 18/2/11.
 * 尽量不要String常量作为加锁对象
 */
public class StringLock {

    public void method() {
        //new String("字符串常量")
        synchronized ("字符串常量") {
            try {
                while (true) {
                    System.out.println("当前线程：" + Thread.currentThread().getName() + " 开始");
                    Thread.sleep(1000);
                    System.out.println("当前线程：" + Thread.currentThread().getName() + " 结束");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
        }
    }


    public static void main(String[] args) {
        StringLock sl = new StringLock();
        Thread t1 = new Thread(() -> sl.method(), "t1");
        Thread t2 = new Thread(() -> sl.method(), "t2");

        t1.start();
        t2.start();
    }
}
