package org.wuqinghua.socket.nio.buffer;

import java.nio.IntBuffer;

/**
 * Created by wuqinghua on 18/2/12.
 */
public class TestBuffer {
    public static void main(String[] args) {
        //1.基本操作
        IntBuffer buffer = IntBuffer.allocate(10); //指定长度的缓冲区
        buffer.put(12);   //position位置: 0 -> 1
        buffer.put(new int[]{13, 14});//position位置: 0 -> 3
        //进行复位
        buffer.flip();

        System.out.println("使用flip复位:" + buffer);
        System.out.println("容量:" + buffer.capacity());
        System.out.println("限制:" + buffer.limit());  //可以读取和操作的值


        System.out.println("获取下标为1的元素:" + buffer.get(1)); //position的位置不会发生变化
        buffer.put(1, 4);
        System.out.println("使用buffer.put(index,val)不会改变position:" + buffer);


        for (int i = 0; i < buffer.limit(); i++) {
            System.out.print(buffer.get() + "\t");  //get方法会每次position递增一
        }

        System.out.println("buffer循环完后:" + buffer);
    }
}
