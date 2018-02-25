package org.wuqinghua.jvm;

/**
 * Created by wuqinghua on 18/2/25.
 */
public class Test04 {


    //-Xss1m
    private static int count;

    //栈调用深度
    public static void recursion() {
        count++;
        recursion();
    }

    public static void main(String[] args) {

        try {
            recursion();
        } catch (Throwable t) {
            System.out.println("调用的最大深度:" + count);
            t.printStackTrace();
        }


    }
}
