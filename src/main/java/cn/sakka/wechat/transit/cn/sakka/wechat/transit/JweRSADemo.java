package cn.sakka.wechat.transit.cn.sakka.wechat.transit;

import cn.hutool.core.codec.Base64;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jose.shaded.json.JSONObject;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;


public class JweRSADemo {

    public static String encrypt(String payload, PublicKey publicKey) throws Exception {
        // 创建加密器
        JWEHeader header = new JWEHeader.Builder(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A256GCM).build();
        JWEEncrypter jweEncrypter = new RSAEncrypter((RSAPublicKey) publicKey);
        // 加密JSON数据
        Payload jwePayload = new Payload(payload);
        JWEObject jweObject = new JWEObject(header, jwePayload);
        jweObject.encrypt(jweEncrypter);
        // 将JWE对象转换为JWE字符串
        return jweObject.serialize();
    }

    public static String decrypt(String jwe, PrivateKey privateKey) throws Exception {
        // 创建解密器
        JWEDecrypter jweDecrypter = new RSADecrypter(privateKey);
        // 解密JWE字符串
        JWEObject jweObject = JWEObject.parse(jwe);
        jweObject.decrypt(jweDecrypter);
        // 将解密后的JSON数据转换为JSONObject对象
        Payload payload = jweObject.getPayload();
        return payload.toString();
    }

    public static void main(String[] args) throws Exception {
/*        // 创建一个JSON对象
        JSONObject payload = new JSONObject();
        payload.put("name", "Alice");
        payload.put("age", 30);
        String jsonString = payload.toJSONString();
        // 生成RSA密钥对*/

        // 生成RSA密钥对
//        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
//        keyPairGenerator.initialize(2048);
//        KeyPair keyPair = keyPairGenerator.generateKeyPair();
//        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
//        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
//
//        System.out.println("Base64.encode(publicKey.getEncoded()) = " + Base64.encode(publicKey.getEncoded()));
//        System.out.println("Base64.encode(privateKey.getEncoded()) = " + Base64.encode(privateKey.getEncoded()));


/*        // 加密JSON对象
        String jwe = encrypt(jsonString, publicKey);
        System.out.println("JWE: " + jwe);
        // 解密JWE字符串
        String decryptedPayload = decrypt(jwe, privateKey);
        System.out.println("Decrypted payload: " + decryptedPayload);*/

        String publicKeyStr = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsqBTYOqBU5rdpdRX9/owxi0nHsxsyGr/8PWQZ8GtPQilXfNuQEFWO01o0ZU8Agn+wzatpGCOwIgSaEF4p6uI4tyhC8BlnkQZN/GcjbryljzIrRlu8NKsLu40SQO3CYa+p7DA36pdX/nMQ9QceSeGW9e7E5YWuiMhUDgqWVEdmRxX+bxfoNYpBtNlvyxeL0MKEIUVlikYKe+UxKhiPRcwjBIiHKfsY2PszP4h485UjcIWveyJjBu8nEpUgHUqUHdA1nm32WmUMcOhTU41tX6ZoYK6qUBeNX4xnfOG7jnE76Vz2QcsVTnnTqTQ/93nxEIrZsSfQ/vdzi8LryY2xS0EmQIDAQAB";
        PublicKey publicKey = getPublicKey(publicKeyStr);
        //playload 可以是json串，也可以是普通字符串
        String encrypt = encrypt("jwe 测试", publicKey);
        System.out.println(encrypt);
    }

    /**
     * String转公钥PublicKey
     * @param key
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes = Base64.decode(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

}
