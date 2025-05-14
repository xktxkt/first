package cn.xkt.rpc.Server.RPCServer;

import cn.xkt.rpc.Server.Service.ServiceProvider;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.AllArgsConstructor;

/**
 * @author xkt
 * @varsion 1.0
 */
@AllArgsConstructor
public class NettyServer implements RPCServer{
    private ServiceProvider serviceProvider;
    @Override
    public void start(int port) {
        //负责连接的线程池
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        //负责执行工作的线程池
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        System.out.println("Netty 服务端开始启动");

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //初始化服务端
            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new NettyServerInitializer(serviceProvider));
            //同步阻塞
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            //阻塞当前线程，知道通道关闭事件发生
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }


    @Override
    public void stop() {

    }
}
