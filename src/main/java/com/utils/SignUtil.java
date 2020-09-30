package com.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

/**
 * @author Hope
 * Date： 2020/09/30  10:34
 * 描述：
 */

@Service
public class SignUtil {
    private static final Logger log = LoggerFactory.getLogger(SignUtil.class);

    //证书算法信息
    private static String sigAlgName;
    //证书私钥
    private static PrivateKey privateKey;

    /**
     * 加载证书配置
     */
    @PostConstruct
    private void loadPrivateCertConfig() throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException {
        //conf/ifp.keystore
        String keyStorePath = "";
        //ifp
        String alias = "";
        //123456
        String keyStorePwd = "";
        //123456
        String certPwd = "";
        //conf/fitp.cer
        String certPath = "";

        //实例化密钥库
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        //获取密钥库文件流
        FileInputStream is = new FileInputStream(keyStorePath);
        //加载密钥库
        ks.load(is,keyStorePwd.toCharArray());
        //关闭密钥库文件流
        is.close();

        //获取证书
        X509Certificate x509Certificate = (X509Certificate) ks.getCertificate(alias);
        Enumeration<String> en = ks.aliases();
        while (en.hasMoreElements()) { }
        //由证书指定签名算法
        sigAlgName = x509Certificate.getSigAlgName();
        //获取私钥
        privateKey  = (PrivateKey) ks.getKey(alias, certPwd.toCharArray());
    }


    public static String sign(byte[] msg) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        //初始化签名，由私钥构建
        Signature signature = Signature.getInstance(sigAlgName);
        signature.initSign(privateKey);
        signature.update(msg);
        //获取签名
        byte[] sign = signature.sign();
        return (new BASE64Encoder().encode(sign));
    }
}
