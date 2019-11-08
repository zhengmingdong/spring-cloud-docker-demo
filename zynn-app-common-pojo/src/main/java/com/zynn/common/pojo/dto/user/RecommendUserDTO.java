package com.zynn.common.pojo.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.zynn.common.pojo.enums.BaseEnum;
import lombok.Data;

import java.util.Date;
import java.util.Random;


/**
 * @author liqi
 *
 * 可能认识的人
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecommendUserDTO {

    /**
     *  用户id
     */
    @JsonSerialize(using=ToStringSerializer.class)
    private Long userId;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 用户头像
     */
    private String headPic;

    /**
     * 性别，0-女  1-男
     */
    private Integer sex;

    /**
     * 是否关注
     */
    private Integer isAttention;

    /**
     * 共同好友数
     */
    private Integer commonFriendNum;

    /**
     * 权重分值
     */
    private Double score;

    /**
     * 备注 显示推荐备注文案
     */
    private String remark;

    /**
     * 和登录者(a)的关系:0-[a、b毫无关系]、
     * 1-[a用户单向关注b用户]、
     * 2-[b用户单向关注a用户]、
     * 3-[a、b用户相互关注】
     */
    private Integer relation;

    /**
     *  用户手机号
     */
    private Long cellphone;

    /**
     * 是否注册APP 1：已注册 0：未注册
     */
    private Integer isRegister;


    /**
     * 弹不弹窗(0表示不弹窗 1表示弹窗)
     * (弹出：必须是一度关系，且有真实姓名)
     */
    private Integer relationLevel;

    /**
     * 学校昵称
     */
    private String schoolName;
    /**
     * 职业/兴趣和特长
     */
    private String postInterest;
    /**
     * 生日
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthday;
    /**
     *情感状态0单身，1密码，2恋爱
     */
    private Integer affectiveState;
//    /**
//     *情感状态0单身，1密码，2恋爱
//     */
//    private String affectiveStateName;
//    private Long departmentId;
    /**
     * 院校名称
     */
    private String departmentName;
//    private Integer gradeId;
    /**
     * 年级名称
     */
    private String gradeName;

    /**
     * 粉丝数
     */
    private Integer fansNum;
    /**
     * KOL标签
     */
    private String kolTypeName;
    public RecommendUserDTO() {
        this.isAttention = BaseEnum.IS_VALID_NO.getKey();
        this.relation = 0;
        this.score = 0d;
        this.relationLevel = 0;
        this.isRegister = 1;
    }

    // 随机数
    private int returnRandomInt() {
        return Math.random()>0.5?1:0;
    }
}
