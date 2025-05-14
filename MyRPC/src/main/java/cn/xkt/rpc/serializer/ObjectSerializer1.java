package cn.xkt.rpc.serializer;

import java.io.*;

/**
 * @author xkt
 * @varsion 1.0
 * java原生序列化器
 */
public class ObjectSerializer1 implements Serializer{
    //对象->byte[]
    @Override
    public byte[] serialize(Object obj) {
        byte[] bytes = null;
        //输出流，将object序列化为byte[]
        ByteArrayOutputStream bos = new ByteArrayOutputStream();//创建一个缓存区
        try {
            ObjectOutputStream oos  = new ObjectOutputStream(bos);
            oos.writeObject(obj);//写入内存中一个缓冲区，不会写入网络
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return bytes;
    }

    @Override
    public Object deserialize(byte[] bytes, int messageType) {
        Object obj =null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (IOException|ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    @Override
    public int getType() {
        return 0;
    }
}
