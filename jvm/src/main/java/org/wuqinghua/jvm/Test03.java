package org.wuqinghua.jvm;

import java.util.Vector;

/**
 * Created by wuqinghua on 18/2/25.
 */
public class Test03 {
    public static void main(String[] args) {
//        -Xms2m -Xmx2m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/Users/wuqinghua/test03.dump

        //堆内存溢出
        Vector v = new Vector();
        for (int i = 0; i < 5; i++) {
            v.add(new Byte[1 * 1024 * 1024]);
        }
    }
}
