package com.zynn.service.module.user.im.result.huanxin;

import com.zynn.service.module.user.im.result.IMBaseResult;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Author zhanghao
 * @date 2019/3/21 18:20
 **/
@Data
public class ClusterDetailResult implements IMBaseResult {


    private Long id;

    private String name;

    private String  description;

    private boolean  membersonly;

    private boolean  allowinvites;

    private Integer  maxusers;

    private Integer  affiliations_count;

    private List<String> affiliations;


}
