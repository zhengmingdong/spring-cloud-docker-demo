package com.zynn.common.pojo.dto.user;

import lombok.*;

/**
 * @author 袁毅雄
 * @description
 * @date 2019/6/13
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public class UniversityDTO {

    /**
     * 别名
     */
    @NonNull
    private String aliasName;

    /**
     * 全名
     */
    @NonNull
    private String fullName;
}
