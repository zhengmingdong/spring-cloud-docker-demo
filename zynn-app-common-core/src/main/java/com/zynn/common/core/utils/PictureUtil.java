package com.zynn.common.core.utils;

import com.zynn.common.core.constant.QiNiuConstant;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

/**
 * @author yu_chen
 * @date 2018/5/29 16:59
 */
public class PictureUtil {

    /**
     * 可以将合成后的图片上传图片到公有云
     *
     * @param bufferedImage 图片对象
     * @param fileName      要生成的文件名
     * @return 合成后的图片的地址
     */
    public static String uploadPicture(BufferedImage bufferedImage, String fileName) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "jpg", out);
            byte[] b = out.toByteArray();
            QiniuOperateUtil.uploadByteToOpenSpace(b, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return QiNiuConstant.QI_NIU_OPEN_ADDRESS + fileName;
    }

    /**
     *
     * @param belowImgHttpUtl   要合成图片的底图
     * @param coverImageHttpUrl 要合成的图片的URL
     * @param x                 要合成图片的X偏移
     * @param y                 要合成图片的Y坐标
     * @param width             合成图片显示的宽度
     * @param height            合成图片显示的高度
     * @return 合成后的图片的image对象
     */
    public static String mergePicture(String belowImgHttpUtl, String coverImageHttpUrl, int x, int y, int width, int height) {
        BufferedImage belowBufferedImage = null;
        String fileName = SequenceUtil.getId()+ ".jpg";
        try {
            belowBufferedImage = ImageIO.read(new URL(belowImgHttpUtl));
            BufferedImage coverBufferedImage = ImageIO.read(new URL(coverImageHttpUrl));
            Graphics2D graphics = belowBufferedImage.createGraphics();
            graphics.drawImage(coverBufferedImage, x, y, width, height, null);
            // 结束绘制
            graphics.dispose();

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(belowBufferedImage, "jpg", out);
            byte[] bytes = out.toByteArray();
            QiniuOperateUtil.uploadByteToOpenSpace(bytes, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return QiNiuConstant.QI_NIU_OPEN_ADDRESS + fileName;
    }

    /**
     * 合并图片和文本
     *
     * @param picHttpUrl   线上图片地址
     * @param picHttpWidth 线上图片的宽度 可以保持文字居中
     * @param y            文字显示的纵坐标
     * @param fontSize     生成的文字大小
     * @param showContent  合并的文字内容
     * @param fileName     七牛云上图片的名称
     * @return 七牛云的地址
     */
    public static String mergePictureAndText(String picHttpUrl, int picHttpWidth, int y, int fontSize, String showContent, String fileName) {

        try {
            BufferedImage bufferedImage = ImageIO.read(new URL(picHttpUrl));
            Graphics2D graphics = bufferedImage.createGraphics();
            insertText(graphics, picHttpWidth, y, fontSize, showContent);
            // 结束绘制
            graphics.dispose();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", out);
            byte[] b = out.toByteArray();
            QiniuOperateUtil.uploadByteToOpenSpace(b, fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return picHttpUrl;
        }
        return QiNiuConstant.QI_NIU_OPEN_ADDRESS + fileName;
    }

    public static void insertText(Graphics2D gs, int width, int height, int fontSize, String showContent) {
        gs.setColor(new Color(255, 255, 255));
        Font font = new Font(null, Font.PLAIN, fontSize);
        FontMetrics metrics = gs.getFontMetrics(font);
        //得到文本的长度
        int strLen = metrics.stringWidth(showContent);
        int height1 = metrics.getHeight();
        //如果文本过长需要换行显示
        int startX = (width - strLen) / 2;
        //设置字体
        gs.setFont(font);
        //画字符串
        gs.drawString(showContent, startX, height + height1);
    }

    /**
     * 插入文字,用户名
     *
     * @param showContent
     * @return
     */
    public static void insertTextNameForCircle(Graphics2D gs, int width, int height, String showContent,int size,String color) {
        gs.setColor(new Color(Integer.parseInt(color.substring(1),16)));
        Font font = new Font(null, Font.TRUETYPE_FONT, size);
        FontMetrics metrics = gs.getFontMetrics(font);
        if(null == showContent || "".equals(showContent)){
            return;
        }
        //将半角英文转为全角
        String s = showContent;
        //得到文本的长度
        int height1 = metrics.getHeight();
        //设置字体
        gs.setFont(font);
        //如果文本过长需要换行显示

        int count=(s.length()/11)+1;

        int startX = 0;
        for(int i=0;i<count;i++){
            int sizes=startX+11;
            if(sizes>s.length()){
                sizes=s.length();
            }
            gs.drawString(s.substring(startX,sizes), width, height+height1);
            height=height+height1;
            startX=startX+11;
        }
    }

    /**
     * 对资源图片进行拉伸
     * @param src
     * @param fileName
     * @param w
     * @param h
     * @return
     * @throws Exception
     */
    public static String zoomImage(String src, String fileName, int w, int h) throws Exception {
        double wr = 0, hr = 0;
        BufferedImage bufImg = ImageIO.read(new URL(src));

        Image itemp = bufImg.getScaledInstance(w, h, Image.SCALE_SMOOTH);

        //获得缩放比例值
        wr = w * 1.0 / bufImg.getWidth();
        hr = h * 1.0 / bufImg.getHeight();

        AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);

        itemp = ato.filter(bufImg, null);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write((RenderedImage) itemp, "png", out);
        byte[] b = out.toByteArray();
        QiniuOperateUtil.uploadFileToOpenSpace(b, fileName);
        return QiNiuConstant.QI_NIU_OPEN_ADDRESS + fileName;
    }

    /**
     * 对资源图片进行截取
     * @param src
     * @param fileName
     * @return
     * @throws Exception
     */
    public static String subImage(String src, String fileName,int width,int height) throws Exception {

        Iterator iterator = ImageIO.getImageReadersByFormatName("png");
        ImageReader reader = (ImageReader)iterator.next();

        //利用HttpURLConnection对象,我们可以从网络中获取网页数据
        HttpURLConnection conn = (HttpURLConnection) (new URL(src)).openConnection();
        conn.setDoInput(true);
        conn.connect();
        ImageInputStream iis = ImageIO.createImageInputStream(conn.getInputStream());
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
        //输入起始点,宽,高,截取图片
        Rectangle rect = new Rectangle(0, 0, width,height);
        param.setSourceRegion(rect);
        BufferedImage bi = reader.read(0,param);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(bi, "png", out);
        byte[] b = out.toByteArray();
        QiniuOperateUtil.uploadFileToOpenSpace(b, fileName);
        return QiNiuConstant.QI_NIU_OPEN_ADDRESS + fileName;
    }

    /**
     * 在图片中心合成图片
     * @param belowImgHttpUtl   要合成图片的底图
     * @param coverImageHttpUrl 要合成的图片的URL
     * @return 合成后的图片的image对象
     */
    public static String mergePictureInCenter(String belowImgHttpUtl, String coverImageHttpUrl) {
        BufferedImage belowBufferedImage = null;
        String fileName = SequenceUtil.getId()+ ".png";
        try {
            belowBufferedImage = ImageIO.read(new URL(belowImgHttpUtl));
            BufferedImage coverBufferedImage = ImageIO.read(new URL(coverImageHttpUrl));
            Graphics2D graphics = belowBufferedImage.createGraphics();

            int x = (belowBufferedImage.getWidth()-coverBufferedImage.getWidth())/2;
            int y = (belowBufferedImage.getHeight()-coverBufferedImage.getHeight())/2;
            graphics.drawImage(coverBufferedImage, x, y, coverBufferedImage.getWidth(), coverBufferedImage.getHeight(), null);
            // 结束绘制
            graphics.dispose();

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(belowBufferedImage, "png", out);
            byte[] bytes = out.toByteArray();
            QiniuOperateUtil.uploadByteToOpenSpace(bytes, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return QiNiuConstant.QI_NIU_OPEN_ADDRESS + fileName;
    }

}
