package com.zynn.common.core.constant;

/**
 * 七牛云相关的常量
 *
 * @author yu_chen
 * @date 2018/3/29 13:27
 */
public class QiNiuConstant {
    /**
     * 七牛云公钥
     */
    public static final String ACCESS_KEY = "2caeYdL9QSwjhpJc2v05LLgaOrk_Mc_HterAtD28";
    /**
     * 七牛云密钥
     */
    public static final String SECRET_KEY = "XQYY6AE3rhhp-ep9-xwOOUc2noRyAvXu8uLjkTMT";
    /**
     * 七牛云公开空间名
     */
    public static final String OPEN_BUCKET = "yinian-app-public";
    /**
     * 七牛云私有空间名
     */
    public static final String PRIVATE_BUCKET = "yinian-app";
    /**
     * 七牛云公开空间地址
     */
    public static final String QI_NIU_OPEN_ADDRESS = "https://app.public.zhuiyinanian.com/";
    /**
     * 七牛云私有空间地址
     */
    public static final String QI_NIU_PRIVATE_ADDRESS = "https://app.zhuiyinanian.com/";
    /**
     * 图片缩略图参数
     */
    public static final String PICTURE_THUMBNAIL_PARA = "?imageView2/2/w/300";
    /**
     * 持久化操作前缀
     */
    public static final String PERSIST_PREFIX = "persist_";
    /**
     * 生成图片出错时返回的图片地址
     */
    public static final String ERROR_PIC_URL = QI_NIU_OPEN_ADDRESS + "QRCodeError.png";

    /**
     * 七牛云视频封面地址后缀
     */
    public final static String VIDEO_COVER_SUFFIX = "?vframe/jpg/offset/0.03/w/750";

    /**
     * 七牛云视频封面地址后缀,较小图
     */
    public final static String VIDEO_COVER_SMALL_IMAGE = "?vframe/jpg/offset/0.03/w/250";

    /**
     * 七牛云视频封面地址后缀,中等图
     */
    public final static String VIDEO_COVER_MEDIUM_IMAGE = "?vframe/jpg/offset/0.03/w/500";

    /**
     * 七牛云视频封面地址后缀,较大图
     */
    public final static String VIDEO_COVER_LARGE_IMAGE = "?vframe/jpg/offset/1/w/1000";

    /**
     * 七牛云图片尺寸调整,较小图
     */
    public final static String PIC_SMALL = "?imageView2/2/w/250";

    /**
     * 七牛云图片尺寸调整,中等图
     */
    public final static String PIC_MEDIUM = "?imageView2/2/w/500";

    /**
     * 七牛云图片尺寸调整,较大图
     */
    public final static String PIC_LARGE = "?imageView2/2/w/1000";

    /**
     * 图片尺寸调整
     */
    public final static String PIC_BEAUTIFUL_THUMBNAIL = "?imageView2/2/w/600";

    /**
     * 图片尺寸调整
     */
    public final static String PIC_BEAUTIFUL_THUMBNAIL_400 = "?imageView2/2/w/400";

    /**
     * 图片尺寸调整
     */
    public final static String PIC_BEAUTIFUL_THUMBNAIL_800 = "?imageView2/2/w/800";

    /**
     * 图片尺寸调整
     */
    public final static String PIC_BEAUTIFUL_THUMBNAIL_200 = "?imageView2/2/w/200";

    /**
     * webp格式调整
     */
    public final static String PIC_BEAUTIFUL_THUMBNAIL_WEBP= "?imageMogr2/format/webp";

    public final static String PIC_BIG_THUMBNAIL = "?imageView2/2/w/1000";

    public final static String COMPRESS_FOR_PICTURE = "new_";

}
