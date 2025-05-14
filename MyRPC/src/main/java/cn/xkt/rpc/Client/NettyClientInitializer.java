package cn.xkt.rpc.Client;

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
import lombok.NoArgsConstructor;


/**
 * @author xkt
 * @varsion 1.0
 * 初始化通道的类，每一个新建立的SocketChannel,都会被初始化
 */

@NoArgsConstructor
public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
//        //读包的类，保证正确分帧
//        pipeline.addLast(new ChannelHandler[]{new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4)});
//        //发送数据前添加的数据，自动加上4个字节的长度信息
//        pipeline.addLast(new ChannelHandler[]{new LengthFieldPrepender(4)});
        //序列化
        pipeline.addLast(new ChannelHandler[]{new MyEncoder(new JsonSerializer())});
        //反序列化
        pipeline.addLast(new ChannelHandler[]{new MyDecoder()});
        pipeline.addLast(new ChannelHandler[]{new NettyClientHandler()});

    }
}
