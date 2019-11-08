package com.zynn.common.core.utils;

import com.qiniu.common.QiniuException;
import com.zynn.common.core.constant.QiNiuConstant;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * @author wangyulin
 * @date 2019年6月4日17:59:17
 */
public class QrCodePictureUtil {

    public  static final String CREATE_QRCODE_URL = "http://qr.topscan.com/api.php?text=";

    public static String getQrCodeUrl(String jumpUrl) {

        Long data = SequenceUtil.getId();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedImage picBi = null;
        try {
            picBi = ImageIO.read(new URL(CREATE_QRCODE_URL+jumpUrl));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ImageIO.write(picBi, "png", byteArrayOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = byteArrayOutputStream.toByteArray();

        QiniuOperateUtil.uploadFileToOpenSpace(bytes, "QrCodePicture_" + data + ".png");

        return QiNiuConstant.QI_NIU_OPEN_ADDRESS+"QrCodePicture_" + data + ".png";
    }

}
