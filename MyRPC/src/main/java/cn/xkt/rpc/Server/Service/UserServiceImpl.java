package cn.xkt.rpc.Server.Service;

import cn.xkt.rpc.Common.User;

import java.util.Random;
import java.util.UUID;

/**
 * @author xkt
 * @varsion 1.0
 */
public class UserServiceImpl implements UserService{

    @Override
    public User getUserByUserId(Integer id) {
        System.out.println("客户端查询了id为"+id+"的用户");
        //模拟从数据库中取用户的行为
        Random random = new Random();
        User user = User.builder().userName(UUID.randomUUID().toString())
                                    .id(id)
                                    .sex(random.nextBoolean()).
                                    build();
        return user;
    }

    @Override
    public Integer insertUserId(User user) {
        System.out.println("数据插入成功"+user);
        return 1;
    }
}
