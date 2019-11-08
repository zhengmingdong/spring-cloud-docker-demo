package com.zynn.service.module.user.im.params.huanxin;

import lombok.Data;

/**
 * 创建IM群Params
 * @author wangyulin
 * @date 2019年7月2日14:36:50
 **/
@Data
public class CreateClusterParams extends HuanxinBaseParams{


    // 群名称
    private String groupName;

    //群组描述
    private String desc;

    /**
     *群组成员最大数,默认值200，最大值2000 选填
     */
    private int maxUsers;

    /**
     *加入群是否需要群主或者群管理员审批，默认是false
     */
    private boolean membersOnly;

    /**
     *是否允许群成员邀请别人加入此群。 true：允许群成员邀请人加入此群，false：只有群主或者管理员才可以往群里加人。
     */
    private boolean allowInvites;

    /**
     *群组的管理员，此属性为必须的
     */
    private String owner;

    /**
     * 是否是公开群
     */
    private boolean publicBlank;

}
