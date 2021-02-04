package com.myobject.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author dingpei
 * @Description: todo
 * @date 2021/1/17 9:26 上午
 */
public class RpcEncoder extends MessageToByteEncoder {
    private Class<?> clazz;

    private Serializer serializer;

    public RpcEncoder(Class<?> clazz, Serializer serializer) {
        this.clazz = clazz;
        this.serializer = serializer;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object msg, ByteBuf byteBuf) throws Exception {
        if (clazz != null && clazz.isInstance(msg)) {
            byte[] bytes = serializer.serialize(msg);
            // 写入消息体的字节长度
            byteBuf.writeInt(bytes.length);
            // 写入消息的二进制数据
            byteBuf.writeBytes(bytes);
        } else {
            System.out.println("无法加码：" + msg.toString());
        }
    }
}
