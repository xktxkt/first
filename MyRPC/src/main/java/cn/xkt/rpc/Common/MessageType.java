package cn.xkt.rpc.Common;

/**
 * @author xkt
 * @varsion 1.0
 */
public enum MessageType {
    //0 request，1response

     REQUEST(0),
    RESPONSE(1);

    private int code;
    public  int getCode(){
        return this.code;
    }
    private MessageType(int code)
    {
        this.code=code;
    }
}
