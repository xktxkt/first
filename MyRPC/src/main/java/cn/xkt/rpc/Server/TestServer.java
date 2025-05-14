package cn.xkt.rpc.Server;

import cn.xkt.rpc.Server.RPCServer.NettyServer;
import cn.xkt.rpc.Server.Service.*;
import cn.xkt.rpc.Server.simpleServer.SimpleRPCServer;

/**
 * @author xkt
 * @varsion 1.0
 */
public class TestServer {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        BlogService blogService = new BlogServiceImpl();
        //将服务端的接口和实现类放在一个map中
        String host = "127.0.0.1";
        int port = 8899;
        ServiceProvider serviceProvider = new ServiceProvider(host,port);
        serviceProvider.provideServiceInterface(userService);
        serviceProvider.provideServiceInterface(blogService);

        //开启一个服务端
        NettyServer nettyServer = new NettyServer(serviceProvider);
        nettyServer.start(8899);


    }
}
