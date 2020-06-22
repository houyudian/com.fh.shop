package com.fh.shop.api.util;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import org.junit.Test;

public class TestRSA {
    @Test
    public void setRsa() {
        RSA rsa = new RSA();
        String privateKeyBase64 = rsa.getPrivateKeyBase64();
        String publicKeyBase64 = rsa.getPublicKeyBase64();
        System.out.println("私钥：" + privateKeyBase64);
        System.out.println("公钥：" + publicKeyBase64);
    }

    @Test
    public void test() {
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCeWPDpAGUZ1DK5k5/t22GuiS8Quv1LigF7Z6qC+QSnmh17avkR4uiU5T5o1+4hpkhJkUy95uoplAjQtUvktbUd1nZoEy3rHqYBRgRXfMxOJTyLGFdgjLH2eLmWSR/xTmgITU0z1ASrn6dSgzvAGXEzf3munhRz6ITImuyXgIup3QIDAQAB";
        String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJ5Y8OkAZRnUMrmTn+3bYa6JLxC6/UuKAXtnqoL5BKeaHXtq+RHi6JTlPmjX7iGmSEmRTL3m6imUCNC1S+S1tR3WdmgTLesepgFGBFd8zE4lPIsYV2CMsfZ4uZZJH/FOaAhNTTPUBKufp1KDO8AZcTN/ea6eFHPohMia7JeAi6ndAgMBAAECgYEAni7hcxH27WeCyMpFgLqgiVf30XHK6XRS6LF1r+MNmFSn0QvbBtgzWdLslpXnCGxxWDSzgh20nJaKe9wwlahD5Y+jSzYZTj+7DH9IyPCvzryRtCL0pjT6x8optGggMEhdqxl8YOYnGcj4zCMAGqHPhWSFS4507HAVmfBuiuyn9uECQQD7G9zEy2dRLF7Qjkz31HeAFZzt5QtZ72a5Hqu0EV9qaEYAChXA/j1fxcQrTjkDPzKCBW2cUYVUqRJje5jC4ilZAkEAoW6HsAuOsko40nsanrXT8hGe4gmYn8cbKyaRj+2h3J/gvhMqI6bIR17vDHCHvXOf9gSetojDdZC+kphesJMwJQJAGAUhSB3g4y3oX/DMwgQr7i+jFEqNnGu/GsQpTo8+3lHdKFHOpOhs/IVRb4cLAxl/flqAkqxloaSOor+G7FVv+QJAXO9F3Dp7FvcVyU6nn5DsCGLOhjCiFfHJGUll6WcTlPDRsHtV+hpNGJF1q0KpUo7bGwXD3S/U3rzKiZiAgsTppQJAeLyMKj4CY/wXTkPqFSQzhlW3W5T4qFJlsRcunqNxiP5484Jo8jSsO31vopr0ktqOLweAPQGb4xDOwYu0qdTBYw==";
        RSA rsa = new RSA(privateKey, publicKey);
        String res = rsa.encryptBase64("huahua", KeyType.PublicKey);
        System.out.println(rsa);
        byte[] bytes = rsa.decryptFromBase64(res, KeyType.PrivateKey);
        String string = new String(bytes);
        System.out.println("解密后：" + string);
    }

}
