package org.wuqinghua.socket.bio2;

import org.wuqinghua.socket.bio.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
            Socket socket = null;
            ExecutorService executorService = Executors.newFixedThreadPool(50);

            //处理业务
            while (true) {
                socket = server.accept();
                executorService.submit(new SocketHandler(socket));
            }

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
