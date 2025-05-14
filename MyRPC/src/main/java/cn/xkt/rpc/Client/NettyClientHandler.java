package cn.xkt.rpc.Client;

import cn.xkt.rpc.Common.Response;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;

/**
 * @author xkt
 * @varsion 1.0
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<Response> {
    public NettyClientHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Response msg) throws Exception {
        // 在channel的属性中存储Response对象，通过泛型直接明确指定数据类型
        AttributeKey<Response> key = AttributeKey.valueOf("Response");
        //将接收到的msg，存储在channel的属性中
        ctx.channel().attr(key).set(msg);
//        System.out.println(msg.toString());
        ctx.channel().close();
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
