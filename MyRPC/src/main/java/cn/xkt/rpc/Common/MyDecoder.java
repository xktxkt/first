package cn.xkt.rpc.Common;

import cn.xkt.rpc.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xkt
 * @varsion 1.0
 * 反序列化，byte变成对象
 * 调用deserialize(bytes[],int messageType)
 * 1/0（response或者request）0/1(序列化器的类型) 四个字节消息长度int 消息byte[]
 */
@NoArgsConstructor
public class MyDecoder extends ByteToMessageDecoder {


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        short messageType = in.readShort();
        if(messageType!=MessageType.REQUEST.getCode()&&messageType!=MessageType.RESPONSE.getCode())
        {
            System.out.println("不支持这种消息类型");
        } else
        {
            short serializeType = in.readShort();
            Serializer serializer = Serializer.getSerializerByCode(serializeType);
            if(serializer==null)
            {
                throw new RuntimeException("不存在对应的序列化器");
            }else
            {
                int length = in.readInt();
                byte[] bytes = new byte[length];
                in.readBytes(bytes);
                Object deserialize = serializer.deserialize(bytes,messageType);
                if (deserialize == null) {
                    throw new RuntimeException("反序列化失败: 结果为空");
                }
                out.add(deserialize);


            }
        }

    }
}
