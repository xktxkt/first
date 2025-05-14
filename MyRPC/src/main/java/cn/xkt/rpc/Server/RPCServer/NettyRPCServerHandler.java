package cn.xkt.rpc.Server.RPCServer;

import cn.xkt.rpc.Common.RPCRequest;
import cn.xkt.rpc.Common.Response;
import cn.xkt.rpc.Server.Service.ServiceProvider;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author xkt
 * @varsion 1.0
 */
public class NettyRPCServerHandler extends SimpleChannelInboundHandler<RPCRequest>{
    private ServiceProvider serviceProvider;

    public NettyRPCServerHandler(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RPCRequest request) throws Exception {
        Response response = this.getResponse(request);
        channelHandlerContext.writeAndFlush(response);
        channelHandlerContext.close();
    }

    Response getResponse(RPCRequest request)
    {
        String interfaceName = request.getInterfaceName();
        Object service = serviceProvider.getService(interfaceName);
        Method method =null;

        try {
            method = service.getClass().getMethod(request.getMethodName(),request.getParamsTypes());
            Object invoke = method.invoke(service,request.getParams());
            return Response.success(invoke,invoke.getClass());
        } catch (NoSuchMethodException|IllegalAccessException|InvocationTargetException e) {
            e.printStackTrace();
            System.out.println("服务端：方法执行错误");
            return Response.fail();
        }

    }
}
