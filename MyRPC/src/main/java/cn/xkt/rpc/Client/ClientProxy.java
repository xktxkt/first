package cn.xkt.rpc.Client;

import cn.xkt.rpc.Common.RPCRequest;
import cn.xkt.rpc.Common.Response;
import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.SplittableRandom;

/**
 * @author xkt
 * @varsion 1.0
 * 动态代理封装request对象
 */
@AllArgsConstructor
public class ClientProxy implements InvocationHandler {

    private RPCClient rpcClient;

    //jdk动态代理，每一次代理对象调用方法，会通过此方法得到增强(反射获取request对象，socket发到客户端)
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //构建request
        RPCRequest request = RPCRequest.builder().interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .params(args).paramsTypes(method.getParameterTypes()).build();
        Response response = this.rpcClient.sendRequest(request);
        return response.getData();
    }

    <T>T getProxy(Class<T> clazz)
    {
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(),new Class[]{clazz},this);
        //clazz 代理对象实现的接口
        //此处的this，当前对象（this），即所有方法调用会被转发到 invoke 方法。
        return (T)o;
    }
}
