package cn.xkt.rpc.register;

import java.net.InetSocketAddress;

/**
 * @author xkt
 * @varsion 1.0
 * 服务注册接口
 * 注册（存ip和服务名），与发现（从名字找ip）
 *
 */
public interface ServiceRegister {
    void register(String serviceName, InetSocketAddress serverAddress);

    InetSocketAddress severDiscovery(String ServiceName);

}
