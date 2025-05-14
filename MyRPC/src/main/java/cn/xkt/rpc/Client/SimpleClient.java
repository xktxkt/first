package cn.xkt.rpc.Client;

import cn.xkt.rpc.Common.RPCRequest;
import cn.xkt.rpc.Common.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author xkt
 * @varsion 1.0
 * 客户端根据不同的service进行动态代理
 * 代理对象增强公共行为
 * 把不同的请求封装成统一的request对象
 */

public class SimpleClient implements RPCClient{
   String host ;
   int port;

   public SimpleClient(String host,int port)
   {
       this.host=host;
       this.port = port;
   }

   //客户端发起一次请求调用，socket建立连接，发起request请求，得到响应response
    //这里的request是proxy意已经封装好了的

    public Response sendRequest (RPCRequest request)
    {
        try {
            Socket socket = new Socket(host,port);

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            System.out.println(request);
            oos.writeObject(request);
            oos.flush();

            Response response = (Response) ois.readObject();

            return response;
        } catch (IOException|ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}
