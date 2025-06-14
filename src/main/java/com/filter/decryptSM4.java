package com.filter;

import org.bouncycastle.crypto.engines.SM4Engine;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import java.util.Base64;

public class decryptSM4 {
    public String decryptSM4(String encryptedBase64) {
        try {
            // 固定密钥（16字节）
            byte[] keyBytes = "0123456789abcdef".getBytes("UTF-8");

            // 初始化 SM4 解密器
            PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new SM4Engine());
            cipher.init(false, new KeyParameter(keyBytes)); // false 表示解密模式

            // Base64 解码
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedBase64);

            // 执行解密
            byte[] output = new byte[cipher.getOutputSize(encryptedBytes.length)];
            int len1 = cipher.processBytes(encryptedBytes, 0, encryptedBytes.length, output, 0);
            int len2 = cipher.doFinal(output, len1);
            int totalLen = len1 + len2;

            // 提取有效数据并转为字符串
            byte[] result = new byte[totalLen];
            System.arraycopy(output, 0, result, 0, totalLen);
            return new String(result, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException("SM4 解密失败", e);
        }
    }
}