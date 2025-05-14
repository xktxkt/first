package cn.xkt.rpc.Server.Service;

import cn.xkt.rpc.Common.User;


/**
 * @author xkt
 * @varsion 1.0
 * userService
 */
public interface UserService {
    User getUserByUserId(Integer id);

    Integer insertUserId(User user);
}
