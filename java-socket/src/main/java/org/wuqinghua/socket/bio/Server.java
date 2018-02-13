package org.wuqinghua.socket.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by wuqinghua on 18/2/12.
 */
public class Server {

    private final static int port = 8765;


    public static void main(String[] args) {

        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("server start...");

            //阻塞
            Socket socket = server.accept();

            //处理业务
            new Thread(new SocketHandler(socket)).start();


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            server = null;
        }
    }
}
