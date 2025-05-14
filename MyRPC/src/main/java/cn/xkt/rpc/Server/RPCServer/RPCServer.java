package cn.xkt.rpc.Server.RPCServer;

/**
 * @author xkt
 * @varsion 1.0
 * 把服务端抽象成接口，将监听和处理逻辑分开
 */
public interface RPCServer {
    void start(int port);//服务端的端口号
    void stop();
}
