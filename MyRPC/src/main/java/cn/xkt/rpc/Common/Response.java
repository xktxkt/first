package cn.xkt.rpc.Common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xkt
 * @varsion 1.0
 * 将响应抽象成object对象
 * rpc 引入状态码和信息表示服务调用成功还是失败
 */


@Builder
@Data
@NoArgsConstructor  // 无参构造方法
@AllArgsConstructor // 全参构造方法（Lombok自动生成）
public class Response implements Serializable {
    //状态信息
    private int code;
    private String message;
    //返回的具体数据
    //DataType data的数据类型
    private  Class<?> DataType;
    private Object data;




    public static Response success(Object data,Class<?> DataType)
    {

        return Response.builder().code(200).data(data).DataType(DataType).build();

    }

    public static Response fail()
    {
        return Response.builder().code(500).message("服务器发生错误").build();
    }
}
