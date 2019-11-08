package com.zynn.common.pojo.serializer;/**
 * @Auther: 刘猛
 * @Date: 2019/2/19 11:13
 * @Description:
 */

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 *@ClassName ToJSONSerializer
 *@Description TODO
 *@Author 刘猛
 *@Date 2019/2/19 11:13
 **/
public class ToJSONSerializer extends JsonSerializer<String> {
    @Override
    public void serialize(String jsonString, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeObject(JSONObject.parseObject(jsonString));
    }
}
