package cn.xkt.rpc.serializer;

import com.alibaba.fastjson.serializer.ObjectSerializer;

/**
 * @author xkt
 * @varsion 1.0
 * 序列化器的接口
 */
public interface Serializer {
    //把对象序列化成数组
    byte[] serialize(Object obj);
    //把数组字节反序列化成消息(MessageType传输对象的类型)
    Object deserialize(byte[] bytes,int messageType);
    //使用的序列化器,0 自带序列化器,1 json序列化方式
    int getType();
    // 根据序号取出序列化器，暂时有两种实现方式，需要其它方式，实现这个接口即可
    static  Serializer getSerializerByCode(int code)
    {
        switch (code){
            case 0:
                return new ObjectSerializer1();
            case 1:
                return new JsonSerializer();
            default:
                return null;
        }

    }
}

