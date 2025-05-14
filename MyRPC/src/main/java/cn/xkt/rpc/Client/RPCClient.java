package cn.xkt.rpc.Client;

import cn.xkt.rpc.Common.RPCRequest;
import cn.xkt.rpc.Common.Response;

/**
 * @author xkt
 * @varsion 1.0
 */
public interface RPCClient {
    Response sendRequest(RPCRequest request);
}
