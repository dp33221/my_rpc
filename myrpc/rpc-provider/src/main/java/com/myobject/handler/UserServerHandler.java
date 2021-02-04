package com.myobject.handler;

import com.myobject.help.SpringBootHelper;
import com.myobject.pojo.RpcRequest;
import com.myobject.pojo.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.Method;

/**
 * @author dingpei
 * @Description: todo
 * @date 2021/1/16 4:03 下午
 */
public class UserServerHandler extends ChannelInboundHandlerAdapter {
    //当客户端读取数据时,该方法会被调用
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        //1.判断当前的请求是否符合规则
        if (msg instanceof RpcRequest) {
            //2.如果符合规则,调用实现类货到一个result
            RpcRequest rpcRequest = (RpcRequest) msg;

            // 3.根据RpcRequest寻找对应的接口和方法
            Class<?> aClass = Thread.currentThread().getContextClassLoader().loadClass(rpcRequest.getClassName());
            if (aClass == null) {
                writeAndFlushResponse(ctx, rpcRequest.getRequestId(), "not support class:" + rpcRequest.getClassName());
                return;
            }

            Object bean = SpringBootHelper.getBean(aClass);
            if (bean == null) {
                writeAndFlushResponse(ctx, rpcRequest.getRequestId(), "not support class:" + rpcRequest.getClassName());
                return;
            }
            Method declaredMethod = bean.getClass().getDeclaredMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
            if (declaredMethod == null) {
                writeAndFlushResponse(ctx, rpcRequest.getRequestId(), "not support method:" + rpcRequest.getMethodName());
                return;
            }
            // 4.并执行它
            Object invoke = declaredMethod.invoke(bean, rpcRequest.getParameters());

            // 5.把调用实现类的方法获得的结果写到客户端
            writeAndFlushResponse(ctx, rpcRequest.getRequestId(), invoke);
        } else {
            System.out.println("请求了数据：" + msg.toString());
            writeAndFlushResponse(ctx, null, "fail");
        }
    }

    private void writeAndFlushResponse(ChannelHandlerContext ctx, String requestId, Object msg) {
        RpcResponse rpcReponse = new RpcResponse(requestId, msg);
        ctx.writeAndFlush(rpcReponse);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 忽略掉远方主机下线异常
        if ("远程主机强迫关闭了一个现有的连接。".equals(cause.getMessage())) {
            return;
        }
        super.exceptionCaught(ctx, cause);
    }
}
