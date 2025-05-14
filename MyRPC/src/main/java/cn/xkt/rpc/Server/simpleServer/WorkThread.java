package cn.xkt.rpc.Server.simpleServer;

import cn.xkt.rpc.Common.RPCRequest;
import cn.xkt.rpc.Common.Response;
import cn.xkt.rpc.Server.Service.ServiceProvider;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @author xkt
 * @varsion 1.0
 */
public class WorkThread implements Runnable {
    Socket socket;
    ServiceProvider serviceProvide;

    public WorkThread(Socket socket, ServiceProvider serviceProvide )
    {
        this.serviceProvide = serviceProvide;
        this.socket  =socket;

    }

    @Override
    public void run() {
        //读取socket的request
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            RPCRequest request = (RPCRequest) ois.readObject();
            Response response = getResponse(request);
            oos.writeObject(response);
            oos.flush();


        } catch (IOException|ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("从IO中读取数据错误");
        }


    }

    private Response getResponse(RPCRequest request) {
        String interfaceName = request.getInterfaceName();
        //方法的实现类
        Object service = serviceProvide.getService(interfaceName);
        Method method = null;
        try {
            method = service.getClass().getMethod(request.getMethodName(), request.getParamsTypes());
            Object invoke = method.invoke(service, request.getParams());
            return Response.success(invoke,Response.class);

        } catch (NoSuchMethodException|InvocationTargetException|IllegalAccessException e) {
            e.printStackTrace();
            System.out.println("方法执行错误");
            return Response.fail();
        }


    }
}
