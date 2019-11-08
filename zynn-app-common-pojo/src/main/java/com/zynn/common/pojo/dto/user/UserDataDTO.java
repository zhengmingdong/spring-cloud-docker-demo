package com.zynn.common.pojo.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author 刘天元
 * @description 用户资料
 * @create 2018-07-29 10:20
 **/
@Data
public class UserDataDTO {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户昵称
     */
    private String nickName;


    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 用户性别
     */
    private Integer sex;

    /**
     * 用户头像
     */
    private String headPic;

    /**
     * 用户生日
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthday;

    /**
     *  微信号
     */
    private String wechatNumber;

    /**
     * 手机号
     */
    private String telPhone;

    /**
     * 城市
     */
    private String city;

    /**
     * 省
     */
    private String province;

    /**
     * 星座名称
     */
    private String constellationValue;

    /**
     * 星座Id
     */
    private String constellationId;

    /**
     * 职业名称
     */
    private String professionValue;

    /**
     * 职业id
     */
    private String professionId;

    /**
     * 学校id
     */
    private Long schoolId;

    /**
     * 学校名称
     */
    private String schoolName;

    /**
     * 院校Id
     */
    private Long departmentId;

    /**
     * 院校Id
     */
    private String departmentName;

    /**
     * 年级id
     */
    private Long gradeId;

    /**
     * 用户所用设备的机器码，用于推送
     */
    private String deviceId;

    /**
     * 记录用户登录的设备  值为iOS或Android
     */
    private String device;

}