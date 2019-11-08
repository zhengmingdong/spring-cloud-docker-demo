package com.zynn.common.core.bo;

import lombok.Data;

import java.util.List;

@Data
public class MapResultBO {

    private String status;
    private String info;
    private String infoCode;
    private List<MapLocationDetailBO> regeocodes;

}
