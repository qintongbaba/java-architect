package org.wuqinghua.server;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * Created by wuqinghua on 18/2/24.
 */
public class SomeDistributedServer {


    private ZooKeeper zk;

    public SomeDistributedServer() throws IOException {
        //连接zk创建znode
        zk = new ZooKeeper("172.16.88.137:2181", 2000, null);
    }

    //注册服务
    public void registerServer(String serverName, String port) throws IOException, KeeperException,
            InterruptedException {

        //如果父节点不存在，则创建
        if (zk.exists("/servers", false) == null) {
            zk.create("/servers", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }

        //注册服务节点
        zk.create("/servers/", (serverName + ":" + port).getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("server:" + serverName + " port:" + port + " is online!");

    }


    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        SomeDistributedServer server = new SomeDistributedServer();

        //启动的时候注册服务  服务名和服务端口
        server.registerServer(args[0], args[1]);

        //处理业务
        System.out.println("server:" + args[0] + " start...");
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
