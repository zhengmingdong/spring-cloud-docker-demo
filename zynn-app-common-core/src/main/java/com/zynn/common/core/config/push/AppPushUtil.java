package com.zynn.common.core.config.push;

import com.alibaba.fastjson.JSON;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.base.payload.MultiMedia;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.zynn.common.core.config.push.enums.PushTypeEnum;
import com.zynn.common.pojo.enums.TriggerPushModuleTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 袁毅雄
 * @date 2019/3/19
 */
@Slf4j
@Component
public class AppPushUtil {

    @Autowired
    private AppPushProperties appPushProperties;

    /**
     * 个推-推送
     *
     * @param triggerModule 触发推送的模块
     * @param pushId        推送的id
     * @param pushType      推送的类型
     * @param title         推送的标题
     * @param titleContent  推送的标题内容
     * @param pushContent   透传的参数
     * @param image         推送的图片
     * @param clientIds     接收推送的目标cid
     * @return
     */
    public ImmutablePair<Long, String> push(TriggerPushModuleTypeEnum triggerModule, Long pushId, PushTypeEnum pushType, String title, String titleContent, Map<String, Object> pushContent, String image, List<String> clientIds) {
        log.info("AppPushUtil-push-appPushProperties:{}", appPushProperties);
        Assert.notNull(Objects.nonNull(appPushProperties), "app push properties not null");

        PushObject pushObject = new PushObject();
        pushObject.setTriggerModule(triggerModule);
        pushObject.setPushId(pushId);
        pushObject.setPushType(pushType);
        pushObject.setPushTitle(title);
        pushObject.setPushTitleContent(titleContent);
        pushObject.setPushImage(image);
        pushObject.setPushContent(pushContent);

        System.err.println("========================================================================================================================");
        System.err.println(pushObject.toString());
        System.err.println("========================================================================================================================");
        return push(pushObject, clientIds);
    }

    /**
     * 个推-推送
     *
     * @param pushObject 推送参数
     * @param clientIds  接收推送的目标cid
     * @return
     */
    private ImmutablePair<Long, String> push(PushObject pushObject, List<String> clientIds) {

        /**获取字典提示信息**/
        APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg()
                .setBody(pushObject.getPushTitleContent())
                .setActionLocKey("查看")
                .setLocKey(pushObject.getPushTitleContent())
                .addLocArg("loc-args")
                .setLaunchImage("launch-image")
                .setTitle(pushObject.getPushTitle())
                .setTitleLocKey(pushObject.getPushTitle())
                .addTitleLocArg("TitleLocArg");


        /**透传消息模板**/
        APNPayload payload = new APNPayload()
                .setAutoBadge("+1")
                .setContentAvailable(1)
                .setSound("default")
                .setCategory("$由客户端定义")
                .addCustomMsg("pushContent", pushObject.toString());
        if (pushObject.getPushImage() != null) {
            payload.addMultiMedia(
                    new MultiMedia()
                            .setResType(MultiMedia.MediaType.pic)
                            .setOnlyWifi(true)
                            .setResUrl(pushObject.getPushImage())
            );
        }
        payload.setAlertMsg(alertMsg);

        /**设置透传的应用信息**/
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(appPushProperties.getAppId());
        template.setAppkey(appPushProperties.getAppKey());
        template.setTransmissionType(2);
        template.setTransmissionContent(pushObject.toString());
        template.setAPNInfo(payload);

        /**推送**/
        ListMessage message = new ListMessage();
        message.setOffline(true);
        message.setOfflineExpireTime(24 * 3600 * 1000L);
        message.setData(template);
        message.setPushNetWorkType(0);

        IGtPush push = new IGtPush(appPushProperties.getAppKey(), appPushProperties.getMasterSecret());
        String contentId = push.getContentId(message);

        /**接收方**/
        List<Target> targets = clientIds.stream().map(clientId -> {
            Target target = new Target();
            target.setAppId(appPushProperties.getAppId());
            target.setClientId(clientId);
            return target;
        }).collect(Collectors.toList());

        long start = System.currentTimeMillis();
        IPushResult ret = push.pushMessageToList(contentId, targets);
        long diff = System.currentTimeMillis() - start;
        return new ImmutablePair<Long, String>(diff, JSON.toJSONString(ret.getResponse()));
    }
}
