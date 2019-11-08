package com.zynn.service.module.user.im;

import com.google.common.collect.Maps;
import com.zynn.service.module.user.config.HuanxinConfiguration;
import com.zynn.service.module.user.enums.IMServerEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author zhanghao
 * @date 2019/3/22 15:56
 **/
@Component
public class IMServiceFactory  implements InitializingBean {

    private static Map<String,IMService> IM_SERVICE_MAP ;

    @Autowired
    private  List<AbstractIMService> imServiceList;

    @Override
    public void afterPropertiesSet() throws Exception {
        IM_SERVICE_MAP = Optional.ofNullable(imServiceList.stream().collect(Collectors.toMap(AbstractIMService::getServiceName, e -> (IMService)e))).orElse(null);
    }


    public static IMService getDefaultIMService(){
        final IMService imService = Optional.ofNullable(IM_SERVICE_MAP).map(e -> e.get(IMServerEnum.huanxin.name())).orElseThrow(() -> new RuntimeException("IM服务实现列表为空"));
        return  Optional.of(imService).orElseThrow(() -> new RuntimeException("获取默认im服务实现失败"));
    }


    public static IMService getIMService(IMServerEnum imServerEnum){
        final IMService imService = Optional.ofNullable(IM_SERVICE_MAP).map(e -> e.get(imServerEnum.name())).orElseThrow(() -> new RuntimeException("IM服务实现列表为空"));
        return  Optional.of(imService).orElseThrow(() -> new RuntimeException("获取im服务实现失败 , im 名称为: "+imServerEnum.name()));
    }
}
