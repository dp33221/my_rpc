package com.myobject.encoder;


import com.alibaba.fastjson.JSON;

/**
 * @author dingpei
 * @Description: todo
 * @date 2021/1/17 9:34 上午
 */
public class JSONSerializer implements Serializer {
    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
