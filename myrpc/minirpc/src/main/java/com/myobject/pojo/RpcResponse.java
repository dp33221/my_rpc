package com.myobject.pojo;
/**
 * @author dingpei
 * @Description: todo
 * @date 2021/1/17 9:44 上午
 */
public class RpcResponse {
    public RpcResponse(String requestId, Object result) {
        this.requestId = requestId;
        this.result = result;
    }

    /**
     * 请求对象的ID
     */
    private String requestId;

    private Object result;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
