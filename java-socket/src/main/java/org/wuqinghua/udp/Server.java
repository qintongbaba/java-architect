package org.wuqinghua.udp;


import java.io.IOException;
import java.net.*;

/**
 * Created by wuqinghua on 18/2/24.
 */
public class Server {
    public static void main(String[] args) throws IOException {
        //服务端＋端口
        DatagramSocket server = new DatagramSocket(new InetSocketAddress("127.0.0.1", 8888));
        //创建数据容器
        byte[] bytes = new byte[1024];
        //封装为包
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length);

        //接收数据
        server.receive(packet);

        //分析数据
        byte[] data = packet.getData();
        int length = packet.getLength();
        System.out.println(new String(data, 0, length));

        server.close();
    }
}
