package cn.xkt.rpc.Common;

import cn.xkt.rpc.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author xkt
 * @varsion 1.0
 * 序列化，对象转byte,输出
 * serializer
 * 写出： 1/0（response或者request）0/1(序列化器的类型) 四个字节消息长度int 消息byte[]
 */
public class MyEncoder extends MessageToByteEncoder {
    private Serializer serializer;
    public MyEncoder(Serializer serializer)
    {
        this.serializer=serializer;
    }
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        //对象变byte
        if(msg instanceof RPCRequest)
        {
            out.writeShort(MessageType.REQUEST.getCode());
        } else if (msg instanceof Response) {
            out.writeShort(MessageType.RESPONSE.getCode());
        }

        out.writeShort(this.serializer.getType());
        byte[] serialize = this.serializer.serialize(msg);
        out.writeInt(serialize.length);
        out.writeBytes(serialize);

    }

}
