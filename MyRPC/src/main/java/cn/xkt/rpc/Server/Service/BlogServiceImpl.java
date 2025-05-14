package cn.xkt.rpc.Server.Service;

import cn.xkt.rpc.Common.Blog;

/**
 * @author xkt
 * @varsion 1.0
 */
public class BlogServiceImpl implements BlogService{
    @Override
    public Blog getBlogById(Integer id) {
        Blog blog = Blog.builder().title("我的博客").id(id).userId(22).build();
        System.out.println("客户端查询了"+id+"博客");
        return blog;

    }
}
