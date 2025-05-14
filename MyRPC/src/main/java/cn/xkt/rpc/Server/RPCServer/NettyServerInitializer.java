package cn.xkt.rpc.Server.RPCServer;

import cn.xkt.rpc.Common.MyDecoder;
import cn.xkt.rpc.Common.MyEncoder;
import cn.xkt.rpc.Server.Service.ServiceProvider;
import cn.xkt.rpc.serializer.JsonSerializer;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * @author xkt
 * @varsion 1.0
 */
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {

    //提供服务类的接口和参数实现
    private ServiceProvider serviceProvider;

    public NettyServerInitializer(ServiceProvider serviceProvider)
    {
        this.serviceProvider =serviceProvider;
    }
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
//        //入站处理的
//        pipeline.addLast(new ChannelHandler[]{new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4)});
//        //出站处理的
//        pipeline.addLast(new ChannelHandler[]{new LengthFieldPrepender(4)});
        //加入自定义的编解码器
        pipeline.addLast(new ChannelHandler[]{new MyEncoder(new JsonSerializer())});
        pipeline.addLast(new ChannelHandler[]{new MyDecoder()});
        pipeline.addLast(new ChannelHandler[]{new NettyRPCServerHandler(this.serviceProvider)});

    }
}
