package com.zynn.common.core.config.push;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.google.common.collect.Lists;
import com.zynn.common.core.config.push.enums.PushTypeEnum;
import com.zynn.common.pojo.dto.user.UserDTO;
import com.zynn.common.pojo.enums.TriggerPushModuleTypeEnum;
import com.zynn.common.pojo.vo.EventVO;
import lombok.Data;
import lombok.experimental.PackagePrivate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * @author 袁毅雄
 * @date 2019/3/19
 */
@Data
@PackagePrivate
public class PushObject {

    /**
     * 推送Id
     */
    Long pushId;

    /**
     * 推送类型
     */
    PushTypeEnum pushType;

    /**
     * 触发的模块
     */
    TriggerPushModuleTypeEnum triggerModule;

    /**
     * 推送时间
     */
    String pushTime;

    /**
     * 推送的图片
     */
    String pushImage;

    /**
     * 推送的标题
     */
    String pushTitle;

    /**
     * 推送的标题内容
     */
    String pushTitleContent;

    /**
     * app 跳转的路径
     */
    String url;

    /**
     * 推送内容
     */
    Map<String, Object> pushContent;

    public PushObject() {
        this.pushTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public String toString() {

        List<String> userProperties = Lists.newArrayList(
                "id",
                "userPic",
                "userNickName",
                "userSex",
                "cellphone",
                //"huanxinUserName",
                //"userType",
                "attentionBlank",
                "schoolName",
                "realName",
                "remarkName"
        );
        List<String> eventProperties = Lists.newArrayList(
                "eventId",
                "url",
                "content",
                "sourceType",
                "location"
        );

        //数据过滤
        PropertyFilter filter = (source, name, value) -> {
            if (source instanceof UserDTO) {
                // UserDTO 不被过滤的属性
                return userProperties.contains(name.trim());
            } else if (source instanceof EventVO) {
                // EventVO 不被过滤的属性
                return eventProperties.contains(name.trim());
            }
            //默认不过滤
            return true;
        };

        //数据类型转换
        SerializeConfig serializeConfig = new SerializeConfig();
        serializeConfig.put(Long.class, ToStringSerializer.instance);
        serializeConfig.put(Long.TYPE, ToStringSerializer.instance);

        //减少透传的流量消耗
        return JSON.toJSONString(this, serializeConfig, filter, SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullStringAsEmpty);
    }
}
