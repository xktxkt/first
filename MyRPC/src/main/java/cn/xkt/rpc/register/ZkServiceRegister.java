package cn.xkt.rpc.register;


import cn.xkt.rpc.balance.RoundLoadBalance;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author xkt
 * @varsion 1.0
 */
public class ZkServiceRegister implements ServiceRegister{
    //zookeeper客户端
    private CuratorFramework client;
    //zookpeer 根路径节点
    private static final String ROOT_PATH = "MyRPC";

    //初始化客户端，与服务器端建立连接
    public ZkServiceRegister()
    {
        //指数时间重试
        RetryPolicy policy = new ExponentialBackoffRetry(1000,3);
        //zookeeper的地址固定，服务的消费者和提供者都要建立连接
        this.client = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181")
                .sessionTimeoutMs(40000).retryPolicy(policy).namespace(ROOT_PATH).build();
        this.client.start();
        System.out.println("ZOOKEEPER 连接成功");

    }

    @Override
    public void register(String serviceName, InetSocketAddress serverAddress) {
        //检查节点是否已经存在
        try {
            if(client.checkExists().forPath("/"+serviceName)==null)
            {
                    //创建成永久节点，当服务提供者下线，删除地址，但是不删除服务名
                    client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/"+serviceName);
            }
            //服务实例节点格式：/userService/192.168.11.1:8080
            String path = "/"+serviceName+"/"+this.getServiceAddress(serverAddress);
            //创建临时节点
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);
        } catch (Exception e) {
            System.out.println("此服务已经存在");
        }

    }

    @Override
    public InetSocketAddress severDiscovery(String ServiceName) {
        //从节点名取实例,/serviceName 的子节点
        try {
            List<String> strings = (List<String>) this.client.getChildren().forPath("/"+ServiceName);
//            String string = strings.get(0);//第一个实例
            RoundLoadBalance roundLoadBalance = new RoundLoadBalance();
            String string = roundLoadBalance.balance(strings);
            return this.parseAddress(string);
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }

    private String getServiceAddress(InetSocketAddress serverAddress)
    {
        String hostName = serverAddress.getHostName();
        return hostName + ":" + serverAddress.getPort();
    }

    public InetSocketAddress parseAddress(String string)
    {

        String[] split = string.split(":");
        String server = split[0];
        String port = split[1];
        return new InetSocketAddress(server,Integer.parseInt(port));

    }
}
