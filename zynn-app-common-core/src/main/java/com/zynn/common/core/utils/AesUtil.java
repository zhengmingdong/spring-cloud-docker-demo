package com.zynn.common.core.utils;

import com.zynn.common.core.bo.LoginAndRegisterBO;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.Security;

/**
 * 解密工具类
 * @author 刘凯
 * @date 2018-07-01
 */
public class AesUtil {

	private static boolean initialized = false;

    /**
     * AES解密
     * @param content
     * @param keyByte
     * @param ivByte
     * @return
     * @throws Exception
     */
    public byte[] decrypt(byte[] content, byte[] keyByte, byte[] ivByte)
            throws Exception {
        initialize();
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        Key sKeySpec = new SecretKeySpec(keyByte, "AES");
        // 初始化
        cipher.init(Cipher.DECRYPT_MODE, sKeySpec, generateIV(ivByte));
        byte[] result = cipher.doFinal(content);
        return result;

    }

	public static void initialize() {
		if (initialized) {
			return;
		}
		Security.addProvider(new BouncyCastleProvider());
		initialized = true;
	}

    /**
     * 生成iv
     * @param iv
     * @return
     * @throws Exception
     */
	public static AlgorithmParameters generateIV(byte[] iv) throws Exception {
		AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
		params.init(new IvParameterSpec(iv));
		return params;
	}

	/**
	 * 解析微信数据
	 * @param loginAndRegisterBO 用户登录信息项
	 * @return 微信解析数据
	 * @throws Exception
	 */
	public String decryptWeChatData(LoginAndRegisterBO loginAndRegisterBO) throws Exception{
		String infoJson = "";
		byte[] resultByte = decrypt(Base64.decode(loginAndRegisterBO.getEncodeData()), Base64.decode(loginAndRegisterBO.getSessionKey()), Base64.decode(loginAndRegisterBO.getIv()));
		if (null != resultByte && resultByte.length > 0) {
			infoJson = new String(resultByte, "UTF-8");
		}
		if(StringUtils.isBlank(infoJson)) {
			throw new RuntimeException("解密内容为空");
		}
		return infoJson;
	}
}
