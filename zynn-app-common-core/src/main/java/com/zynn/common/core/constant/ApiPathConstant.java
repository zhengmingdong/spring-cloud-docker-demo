package com.zynn.common.core.constant;

public class ApiPathConstant {


    public static final String VERSION_V1 = "/v1";

    public static final String VERSION_V2 = "/v2";


    public static final String PREFIX = "/api";


    public static final String PATH_PREFIX_V1 = PREFIX + VERSION_V1;

    public static final String PATH_PREFIX_V2 = PREFIX + VERSION_V2;


    public static final String PATH_V1 = VERSION_V1;

    public static final String PATH_V2 = VERSION_V2;

    public static enum VersionEnum {
        VERSION_V1,
        VERSION_V2
    }
}
