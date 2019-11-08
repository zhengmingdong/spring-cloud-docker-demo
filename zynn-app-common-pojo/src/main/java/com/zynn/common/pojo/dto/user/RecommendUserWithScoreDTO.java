package com.zynn.common.pojo.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liqi
 *
 * 推荐计算的用户ID和分数
 */
@Data
public class RecommendUserWithScoreDTO implements Serializable {

    /** 用户ID*/
    private Long userId;

    /** 推荐值分数 */
    private double score;

    public RecommendUserWithScoreDTO() {

    }

    public RecommendUserWithScoreDTO(Long userId, double score) {
        this.userId = userId;
        this.score = score;
    }
}
