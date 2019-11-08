package com.zynn.common.core.bo;

import lombok.Data;

@Data
public class LocationReturnBO {

    private Long id;
    private String country;
    private String province;
    private String city;
    private int cityCode;
    private String district;
}
