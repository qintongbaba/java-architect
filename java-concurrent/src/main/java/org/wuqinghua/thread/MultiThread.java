package org.wuqinghua.thread;

/**
 * 关键子synchronized取得的锁都是对象锁，而不是把一段代码（方法）当作锁。
 * 所以代码中哪个线程先执行synchronized关键子的方法，哪个线程就持有该方法所属对象的锁（Lock）,
 * 在静态方法上添加synchronized关键字，表示锁定.class类，类级别的锁（独占.class类）
 * Created by wuqinghua on 18/2/10.
 */
public class MultiThread {

    private static int num = 0;

    /**
     * 注意static
     *
     * @param tag
     */
    public static synchronized void printNum(String tag) {
        try {
            switch (tag) {
                case "a":
                    num = 100;
                    System.out.println("tag a,set num over!");
                    Thread.sleep(1000);
                    break;
                case "b":
                    num = 200;
                    System.out.println("tag b,set num over!");
                    break;
                default:
                    break;
            }

            System.out.println("tag " + tag + " num=" + num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 注意观察run方法输出的顺序
     *
     * @param args
     */
    public static void main(String[] args) {
        final MultiThread m1 = new MultiThread();
        final MultiThread m2 = new MultiThread();


        Thread t1 = new Thread(() -> m1.printNum("a"));

        Thread t2 = new Thread(() -> m2.printNum("b"));

        t1.start();
        t2.start();

    }
}
