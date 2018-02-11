package org.wuqinghua.thread;

/**
 * Created by wuqinghua on 18/2/11.
 */
public class ModifyLock {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public synchronized void setName(String username) {
        System.out.println("当前线程：" + Thread.currentThread().getName() + " 开始");
        try {
            this.setUsername(username);
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("当前线程：" + Thread.currentThread().getName() + " 结束");
    }

    public static void main(String[] args) {
        ModifyLock ml = new ModifyLock();
        Thread t1 = new Thread(() -> ml.setName("张三"), "t1");
        Thread t2 = new Thread(() -> ml.setName("李四"), "t2");

        t1.start();
        t2.start();
    }
}
