package cn.xkt.rpc.Client;

import cn.xkt.rpc.Common.RPCRequest;
import cn.xkt.rpc.Common.Response;
import cn.xkt.rpc.register.ServiceRegister;
import cn.xkt.rpc.register.ZkServiceRegister;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.net.InetSocketAddress;

/**
 * @author xkt
 * @varsion 1.0
 *  客户端从zookeeper中获取服务器的地址，能确定服务名称的是在request中
 */
 public class NettyClient implements RPCClient{

    private static final Bootstrap bootstrap = new Bootstrap();
    private static final EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

    private ServiceRegister register;
    static {
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new NettyClientInitializer());
    }
    public NettyClient()
    {
        //连接到zookeeper
        this.register= new ZkServiceRegister();
    }




    @Override
    public Response sendRequest(RPCRequest request) {

        try {
            //连接服务器
            //从zookeeper中得到服务的IP地址
            InetSocketAddress address = register.severDiscovery(request.getInterfaceName());
            String host = address.getHostName();
            Integer port = address.getPort();
            //异步操作连接的结果
            ChannelFuture channelFuture = bootstrap.connect(host,port).sync();
            //连接成功的channel对象
            Channel channel = channelFuture.channel();
            channel.writeAndFlush(request);
            System.out.println("客户端已经发送");
            //等待通道关闭
            channel.closeFuture().sync();
            //取回数据后，关闭通道，到达这一步，此时可以得到response
            //提取响应数据，AttributeKey用来给channel绑定自定义属性，线程安全，名称作用是全局的
            AttributeKey<Response> key = AttributeKey.valueOf("Response");
            Response response =(Response) channel.attr(key).get();
            System.out.println(response);
            return response;

        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }

    }
}
