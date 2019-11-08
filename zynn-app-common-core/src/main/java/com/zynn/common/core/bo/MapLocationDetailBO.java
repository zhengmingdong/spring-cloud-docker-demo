package com.zynn.common.core.bo;

import lombok.Data;

@Data
public class MapLocationDetailBO {

    private String formattedAddress;

    private LocationReturnBO addressComponent;
}
