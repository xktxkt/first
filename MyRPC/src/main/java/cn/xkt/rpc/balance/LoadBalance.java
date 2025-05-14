package cn.xkt.rpc.balance;

import java.util.List;

/**
 * @author xkt
 * @varsion 1.0
 * 参数：服务器地址列表
 * 根据负载均衡策略选择一个server
 */
public interface LoadBalance {
    String balance(List<String> addressList);
}
