package cn.xkt.rpc.Common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xkt
 * @varsion 1.0
 */


@Builder
@Data
@NoArgsConstructor  // 无参构造方法
@AllArgsConstructor // 全参构造方法（Lombok自动生成）
public class RPCRequest implements Serializable {
    //接口名,可以通过反射调用
    private String interfaceName;
    //方法名
    private String methodName;
    //参数列表
    private Object[] params;
    //参数类型
    private Class<?>[] paramsTypes;



}
