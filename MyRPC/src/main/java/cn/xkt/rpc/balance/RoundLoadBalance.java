package cn.xkt.rpc.balance;

import java.util.List;
import java.util.Random;

/**
 * @author xkt
 * @varsion 1.0
 * 随机负载均衡
 */
public class RoundLoadBalance implements LoadBalance {
    private int choose =-1;

    @Override
    public String balance(List<String> addressList) {
       choose++;
       choose=choose%addressList.size();
        System.out.println("轮询负载均衡选择了"+choose+"服务器");
        return addressList.get(choose);
    }
}
