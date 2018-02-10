package org.wuqinghua.thread;

/**
 * 需要使用完整的synchronized，保证业务的原子性
 * Created by wuqinghua on 18/2/10.
 */
public class DirtyRead {

    private String username = "admin";
    private String password = "123";


    public synchronized void setValue(String username, String password) {
        this.username = username;

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.password = password;

        System.out.println("setValue最终结果：username ＝ " + this.username + " ,password = " +
                this.password);
    }

    /** synchronized **/
    public synchronized void getValue() {
        System.out.println("getValue最终结果：username ＝ " + this.username + " ,password = " +
                this.password);
    }


    public static void main(String[] args) {
        final  DirtyRead dr = new DirtyRead();
        Thread t1 = new Thread(()->dr.setValue("root","456"));
        t1.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        dr.getValue();
    }
}
