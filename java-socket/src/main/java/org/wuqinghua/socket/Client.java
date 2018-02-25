package org.wuqinghua.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by wuqinghua on 18/2/24.
 */
public class Client {
    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("127.0.0.1",8888);

        //接收数据
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String msg = br.readLine();
        System.out.println(msg);

    }
}
