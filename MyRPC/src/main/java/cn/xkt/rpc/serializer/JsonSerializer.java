package cn.xkt.rpc.serializer;

import cn.xkt.rpc.Common.RPCRequest;
import cn.xkt.rpc.Common.Response;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


/**
 * @author xkt
 * @varsion 1.0
 */
public class JsonSerializer implements Serializer{
    //对象转换成byte[]
    @Override
    public byte[] serialize(Object obj) {
        byte[] bytes= JSONObject.toJSONBytes(obj);
        return bytes;
    }

    //byte[]转换成对象
    @Override
    public Object deserialize(byte[] bytes, int messageType) {
        Object obj=null;
        //消息中data的类型可以是response 1也可以是request 0，进行区分
        switch (messageType)
        {
            //request,数组或者自定义类的时候，Fastjson解析时可能将其转换为JSONObject
            case 0:
                RPCRequest request = JSON.parseObject(bytes,RPCRequest.class);
                Object[] objects = new Object[request.getParams().length];
                for (int i = 0; i < objects.length; i++) {
                    //取得参数的类型
                    Class<?> paramsType = request.getParamsTypes()[i];
                    Object param = request.getParams()[i];
                    //比较参数类型和实际的解析得到的参数类型是否一致，不一致的话更改类型，改成request.getParamsTypes()[i]类型
                    if(param instanceof JSONObject &&!paramsType.isAssignableFrom(param.getClass()))
                    {
                        objects[i]= JSONObject.toJavaObject((JSONObject) param,request.getParamsTypes()[i]);

                    }else {
                        objects[i] = param;
                    }

                }
                request.setParams(objects);
                obj=request;
                break;
            case 1:
                Response response = JSON.parseObject(bytes, Response.class);
                Class<?> dataType = response.getDataType();
                if(!dataType.isAssignableFrom(response.getData().getClass()))
                {
                    response.setData(JSONObject.toJavaObject((JSONObject) response.getData(),dataType));
                }
                obj = response;
                break;
            default:
                System.out.println("暂时不支持此种消息");
                throw new RuntimeException();
        }
        return obj;
    }

    @Override
    public int getType() {
        return 1;
    }
}
