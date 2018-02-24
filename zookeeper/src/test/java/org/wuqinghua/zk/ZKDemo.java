package org.wuqinghua.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * Created by wuqinghua on 18/2/24.
 */
public class ZKDemo {

    private ZooKeeper zk;

    @Before
    public void setUp() throws IOException {
        zk = new ZooKeeper("172.16.88.137:2181", 2000, watchedEvent -> {
            if (watchedEvent.getType() != Watcher.Event.EventType.None) {
                System.out.println(watchedEvent.getType());
                System.out.println(watchedEvent.getPath());
            }
        });
    }

    /**
     * 向zookeeper中创建数据
     */
    @Test
    public void testCreateZNode() throws KeeperException, InterruptedException {
        zk.create("/wqh", "数据".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zk.close();
    }

    /**
     * 删除znode
     */
    @Test
    public void testDeleteZNode() throws KeeperException, InterruptedException {
        zk.delete("/wqh", -1);  //－1表示匹配所有的版本
        Stat exists = zk.exists("/wqh", null);
        System.out.println(exists);
        zk.close();
    }


    @Test
    public void testUpdateZNode() throws Exception {
        zk.setData("/wqh", "数据2".getBytes(), -1);
        byte[] data = zk.getData("/wqh", false, null);
        System.out.println(new String(data, "utf-8"));
        zk.close();
    }

    @Test
    public void testGetChildren() throws KeeperException, InterruptedException {
        List<String> children = zk.getChildren("/", false);
        for (String child : children) {
            System.out.println(child);
        }
        zk.close();
    }


    @Test
    public void testWatch() throws Exception {
        byte[] data = zk.getData("/wqh", true, null);
        zk.close();
    }
}
