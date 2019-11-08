package com.zynn.common.pojo.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.zynn.common.pojo.vo.LabelVO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author wangyulin
 * @date 2019年3月8日10:54:44
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 创建时间
     */
    @JsonIgnore
    private Timestamp createTime;

    /**
     * 修改时间
     */
    @JsonIgnore
    private Timestamp updateTime;

    /**
     * 用户头像
     */
    private String userPic;

    /**
     * 用户昵称
     */
    private String userNickName;

    /**
     * 用户性别:男-1;女-2
     */
    private Integer userSex;

    /**
     * 是否有效:有效-1;无效-0
     */
    private Integer isValid;

    /**
     * 是否绑定了手机号
     */
    private Integer isSkipCellphone;

    /**
     * 手机号
     */
    private String cellphone;


    /**
     * 环信的用户名称
     */
    private String huanxinUserName;


    /**
     * 用户类型:真实用户-1;马甲用户-2;游客-3
     */
    private Integer userType;

    /**
     * 和该用户的推荐信息
     */
    private String recommendInfo;

    /**
     * 关注标志
     */
    private Integer attentionBlank;

    /**
     * 占位文本
     */
    private String mutualFriend;

    /**
     * 用户的学校名称
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long schoolId;

    /**
     * 用户的学校全名
     */
    private String schoolName;

    /**
     * 用户的学校别名
     */
    private String schoolAlias;

    /**
     * 学院id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long departmentId;

    /**
     * 学院名称
     */
    private String departmentName;

    /**
     * 入学年份id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long gradeId;

    /**
     * 入学年份
     */
    private String gradeName;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 备注
     */
    private String remarkName;

    /**
     * kol名称
     */
    private String kolTypeName;

    /**
     * 职位/兴趣特长
     */
    private String postInterest;

    /**
     * 校园说匿名昵称
     */
    private String schoolAnonymityName;

    /**
     * 校园说展示昵称
     */
    private String anonymityShowName;

    /**
     * 用户标签
     */
    private List<LabelVO> labels;
}
