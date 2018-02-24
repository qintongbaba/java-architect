package org.wuqinghua.client;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuqinghua on 18/2/24.
 */
public class SomeDistributedClient {


    private volatile List<String> servers = null;
    private ZooKeeper zk;

    public SomeDistributedClient() throws IOException {
        //连接zk创建znode
        zk = new ZooKeeper("172.16.88.137:2181", 2000, watchedEvent -> {

            if (watchedEvent.getType() == Watcher.Event.EventType.None) return;
            //获取新的服务器列表
            try {
                getServers();
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }


    public void getServers() throws KeeperException, InterruptedException {
        //获取服务器列表，注册监听
        List<String> children = zk.getChildren("/servers", true);
        List<String> serverList = new ArrayList<>();
        for (String child : children) {
            byte[] data = zk.getData("/servers/" + child, false, null);
            serverList.add(new String(data));
        }

        servers = serverList;

        for (String server : serverList) {
            System.out.println(server);
        }
        System.out.println("-------------------------------");
    }


    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {


        //获取服务器列表
        SomeDistributedClient client = new SomeDistributedClient();
        client.getServers();


        //发送请求
        Thread.sleep(Long.MAX_VALUE);

    }
}
