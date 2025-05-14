package cn.xkt.rpc.balance;

import java.util.List;
import java.util.Random;

/**
 * @author xkt
 * @varsion 1.0
 * 随机负载均衡
 */
public class RandomLoadBalance implements LoadBalance {

    @Override
    public String balance(List<String> addressList) {
        Random random = new Random();
        int choose = random.nextInt(addressList.size());
        System.out.println("随机负载均衡选择了"+choose+"服务器");
        return addressList.get(choose);
    }
}
