package com.myobject.encoder;

import java.io.IOException;

/**
 * @author dingpei
 * @Description: todo
 * @date 2021/1/17 9:32 上午
 */
public interface Serializer {
    /**
     * java对象转换为二进制
     */
    byte[] serialize(Object object) throws IOException;

    /**
     * 二进制转换成java对象
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes) throws IOException;
}
