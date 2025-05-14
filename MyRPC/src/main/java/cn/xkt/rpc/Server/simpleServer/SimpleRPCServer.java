package cn.xkt.rpc.Server.simpleServer;

import cn.xkt.rpc.Server.RPCServer.RPCServer;
import cn.xkt.rpc.Server.Service.ServiceProvider;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author xkt
 * @varsion 1.0
 * 服务端解析request和返回response类
 * 简单实现还是使用Bio模式监听，来一个任务就new一个线程
 */
public class SimpleRPCServer implements RPCServer {
    private ServiceProvider serviceProvide;
    private final ThreadPoolExecutor threadPool;

    public SimpleRPCServer(ServiceProvider serviceProvide)
    {
        this.serviceProvide = serviceProvide;
        //核心线程数设置为处理器的数量
        threadPool = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),1000,60,
                TimeUnit.SECONDS,new ArrayBlockingQueue<>(100));
    }

    @Override
    public void start(int port) {
        try {
            ServerSocket socket = new ServerSocket(port);
            System.out.println("服务端启动了");
            //以Bio的方式进行监听
            while (true)
            {
                Socket socket1 = socket.accept();
                threadPool.execute(new WorkThread(socket1,serviceProvide));
            }
        } catch (Exception e) {
           e.printStackTrace();
            System.out.println("服务器启动失败");

        }


    }

    @Override
    public void stop() {

    }
}
