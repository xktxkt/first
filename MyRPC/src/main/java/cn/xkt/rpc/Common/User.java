package cn.xkt.rpc.Common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xkt
 * @varsion 1.0
 * User 类 被客户端和服务端共有
 * @Builder注解：建造者模式，可以使类显示调用构造
 * User.builder().id(1).build();
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User implements Serializable {
    private  Integer id;
    private  String userName;
    private Boolean sex;
}
