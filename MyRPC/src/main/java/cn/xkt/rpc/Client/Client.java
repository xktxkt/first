package cn.xkt.rpc.Client;

import cn.xkt.rpc.Common.Blog;
import cn.xkt.rpc.Common.User;
import cn.xkt.rpc.Server.Service.BlogService;
import cn.xkt.rpc.Server.Service.UserService;


/**
 * @author xkt
 * @varsion 1.0
 */
public class Client {
    public static void main(String[] args) {

        NettyClient nettyClient = new NettyClient();
        ClientProxy proxy = new ClientProxy(nettyClient);
        UserService userService = proxy.getProxy(UserService.class);
        User userByUserId = userService.getUserByUserId(10);
        System.out.println("从服务端得到的user为：" + userByUserId);
        User user = User.builder().userName("张三").id(100).sex(true).build();
        Integer integer = userService.insertUserId(user);
        System.out.println("向服务端插入数据：" + integer);
        BlogService blogService = (BlogService)proxy.getProxy(BlogService.class);
        Blog blogById = blogService.getBlogById(10000);
        System.out.println("从服务端得到的blog为：" + blogById);
    }
}
