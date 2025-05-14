package cn.xkt.rpc.Server.Service;

import cn.xkt.rpc.register.ServiceRegister;
import cn.xkt.rpc.register.ZkServiceRegister;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xkt
 * @varsion 1.0
 * 服务端需要把自己注册到zookeeper上
 */
public class ServiceProvider {
    //接口名字 实现实例
    private Map<String,Object> interfaceProvider;
    //zk客户端
    private ServiceRegister register ;
    //当前服务器的ip
    private String host;
    private int port;
    public ServiceProvider(String host,int port)
    {
        //服务器自己的地址
        this.host=host;
        this.port=port;
        this.interfaceProvider = new HashMap<>();
        this.register = new ZkServiceRegister();
    }


    //在interfaceProvider中添加<String,object>
    public void provideServiceInterface(Object service)
    {
        //类名和接口名
        String serviceName = service.getClass().getName();
        Class<?>[] interfaces = service.getClass().getInterfaces();

        for(Class clazz :interfaces)
        {
            //本机注册到zookeeper
            register.register(clazz.getName(),new InetSocketAddress(host,port));
            //在当前的服务器上添加接口名和实例
            interfaceProvider.put(clazz.getName(),service);
        }
    }

    //在interfaceProvider中通过接口名查找实现类
    public  Object getService(String InterfaceName)
    {
        return interfaceProvider.get(InterfaceName);
    }
}
