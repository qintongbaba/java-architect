package org.wuqinghua.jvm;

/**
 * Created by wuqinghua on 18/2/25.
 */
public class Test02 {
    public static void main(String[] args) {

        //  -Xms20m -Xmx20m -Xmn1m -XX:SurvivorRatio=2 -XX:+PrintGCDetails -XX:+UseSerialGC



        byte[] b = null;

        //连续向系统申请10M的空间
        for (int i = 0; i < 10; i++) {
            b = new byte[1 * 1024 * 1024];
        }
    }
}
