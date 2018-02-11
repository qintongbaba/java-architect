package org.wuqinghua.thread;

/**
 * Created by wuqinghua on 18/2/11.
 * volatile关键子
 */
public class RunThread extends Thread {


    //volatile
    private volatile boolean isRunning = true;

    public void setRunning(boolean running) {
        this.isRunning = running;
    }

    @Override
    public void run() {
        System.out.println("进入run方法...");
        while (isRunning) {
            //..
        }

        System.out.println("线程停止");
    }


    public static void main(String[] args) throws InterruptedException {
        RunThread rt = new RunThread();
        rt.start();

        Thread.sleep(3000);
        rt.setRunning(false);
        System.out.println("isRunning的值已经被设置为false.");
        Thread.sleep(1000);
        System.out.println(rt.isRunning);
    }
}
