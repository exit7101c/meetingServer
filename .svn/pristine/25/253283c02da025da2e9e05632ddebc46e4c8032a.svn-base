package com.nse.config;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.ResourceBundle;
import select.spring.util.ApplicationProperty;

/**
 * @author Jay Lee
 * Input 암호화해주는 간략한 utility belt.
 * Refer to https://crackstation.net/hashing-security.htm
 * for more information on hashing security.
 * <p>
 * 해슁보안에 대한 정보를 참조하세요.
 * <p>
 * 나중에 필요에 따라 다양한 Hashing algorithms 추가
 * 예: SHA-256, SHA-512, 기타 등 등
 *
 * AES 128 Algorithm 추가. 나중에 AES 256 지원 가능해야하면, method 추가 / 수정하면됨.
 */
public class CryptoUtil {

    public static final Logger log = LoggerFactory.getLogger(CryptoUtil.class);
    private final int saltRounds;
    private String algorithm;
    private String configScretKey = ApplicationProperty.get("cmmn.encryption.secretKeyAES128S");
    private String configInitVector = ApplicationProperty.get("cmmn.encryption.initVectorAES128S");

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String hash(String input) {
        return BCrypt.hashpw(input, BCrypt.gensalt(this.saltRounds));
    }

    public boolean equals(String input, String hashedPw) {
        return BCrypt.checkpw(input, hashedPw);
    }

    public CryptoUtil() {
        this.saltRounds = 12;
        this.algorithm = "AES";
    }

    public CryptoUtil(int rounds) {
        this.saltRounds = rounds;
    }


    /**
     * @param input 암호화할 Input
     * Default AES 128
     * */
    public String encrypt(String input) {

    	return encrypt(configScretKey, configInitVector, input);
    	
    }
    /**
     * @param secretKey secretKey 값
     * @param initVector initVector 값
     * @param input 암호화할 string
     * */
    public String encrypt(String secretKey, String initVector, String input) {
        try {
            // Key 생성
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), this.algorithm);

            // 암호화
            Cipher cipher = Cipher.getInstance(this.algorithm + "/CBC/PKCS5PADDING");

            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);
            byte[] encrypted = cipher.doFinal(input.getBytes());

            // 결과
            return Base64.encodeBase64String(encrypted);
        }  catch (NoSuchAlgorithmException | InvalidKeyException
                | InvalidAlgorithmParameterException | NoSuchPaddingException
                | IllegalBlockSizeException | BadPaddingException e) {
            log.error(e.getMessage());
        }

        return null;
    }

    /**
     * AES 128 암호 풀기
     * @param encrypted 암호화된 string
     * */
    public String decrypt(String encrypted) {
        ResourceBundle rb = Objects.requireNonNull(Common.getApplicationProperties());
//        return encrypt(configScretKey, configInitVector, encrypted);
        return decrypt(rb.getString("cmmn.encryption.secretKeyAES128"),
                rb.getString("cmmn.encryption.initVectorAES128"), encrypted);
    }

    /**
     * @param secretKey secretKey 값
     * @param initVector initVector 값
     * @param encrypted 암호화된 string
     * */
    public String decrypt(String secretKey, String initVector, String encrypted) {
        try {
            // Key 생성
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), this.algorithm);

            // 암호화된 String 복구
            Cipher cipher = Cipher.getInstance(this.algorithm + "/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);
            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

            // 결과
            return new String(original);
        } catch (NoSuchAlgorithmException | InvalidKeyException
                | InvalidAlgorithmParameterException | NoSuchPaddingException
                | IllegalBlockSizeException | BadPaddingException e) {
            log.error(e.getMessage());
        }
        return null;
    }

}