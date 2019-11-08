package com.zynn.common.core.utils;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 图片处理工具类
 *
 * @author 杨岳
 * @date 2018/7/5 16:31
 */
@Slf4j
public class ImageUtils {
    /**
     * 转换BufferedImage 数据为byte数组
     *
     * @param format image格式字符串.如"gif","png"
     * @return byte数组
     */
    public static byte[] imageToBytes(BufferedImage bImage, String format) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(bImage, format, out);
        } catch (IOException e) {
            log.error("字节转换图片异常", e);
        }
        return out.toByteArray();
    }

    /**
     * 转换byte数组为BufferedImage
     *
     * @param bytes
     * @return Image
     */
    public static BufferedImage bytesToBufferedImage(byte[] bytes) {
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        BufferedImage image = null;
        try {
            image = ImageIO.read(in);
        } catch (IOException e) {
            log.error("byte数组为BufferedImage失败", e);
        }
        return image;
    }

    /**
     * 转换byte数组为Image
     *
     * @param bytes
     * @return Image
     */
    public static Image bytesToImage(byte[] bytes) {
        Image image = Toolkit.getDefaultToolkit().createImage(bytes);
        try {
            MediaTracker mt = new MediaTracker(new Label());
            mt.addImage(image, 0);
            mt.waitForAll();
        } catch (InterruptedException e) {
            log.error("字节转化图片异常", e);
        }
        return image;
    }
}
