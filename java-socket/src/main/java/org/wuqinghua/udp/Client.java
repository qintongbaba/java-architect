package org.wuqinghua.udp;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * Created by wuqinghua on 18/2/24.
 */
public class Client {
    public static void main(String[] args) throws IOException {
        DatagramSocket client = new DatagramSocket(new InetSocketAddress("127.0.0.1",6666));

        String msg = "udp编程";

        DatagramPacket packet = new DatagramPacket(msg.getBytes(),msg.getBytes().length,new
                InetSocketAddress("127.0.0.1",8888));

        //发送
        client.send(packet);

        client.close();


    }
}
