package cn.sakka.wechat.transit.cn.sakka.wechat.transit;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.shaded.json.JSONObject;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class JweDemo {

    public static String encrypt(JSONObject payload, String publicKey) throws Exception {
        // 解析公钥
        JWK jwk = JWK.parse(publicKey);

        // 创建加密器
        JWEHeader header = new JWEHeader.Builder(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A256GCM).keyID("hsbcTocm").contentType("jwt").build();
        JWEEncrypter encrypter = new RSAEncrypter(jwk.toRSAKey());

        // 加密JSON数据
        Payload jwePayload = new Payload(payload.toString());
        JWEObject jweObject = new JWEObject(header, jwePayload);
        jweObject.encrypt(encrypter);

        // 将JWE对象转换为JWE字符串
        String jwe = jweObject.serialize();
        return jwe;
    }

    public static JSONObject decrypt(String jwe, String privateKey) throws Exception {
        // 解析私钥
        JWK jwk = JWK.parse(privateKey);

        // 创建解密器
        JWEDecrypter decrypter = new RSADecrypter(jwk.toRSAKey());

        // 解密JWE字符串
        JWEObject jweObject = JWEObject.parse(jwe);
        jweObject.decrypt(decrypter);

        // 将解密后的JSON数据转换为JSONObject对象
        Payload payload = jweObject.getPayload();
        JSONObject decryptedPayload = new JSONObject(payload.toJSONObject());
        return decryptedPayload;
    }

    public static void main(String[] args) throws Exception {
        // 创建一个JSON对象
        JSONObject payload = new JSONObject();
        payload.put("name", "Alice");
        payload.put("age", 30);

        // 生成RSA密钥对
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey publicKeyRsaJWK = new RSAKey.Builder(publicKey).build();
        JWK publicKeyJwk = publicKeyRsaJWK.toPublicJWK();
        RSAKey privateKeyRsaJWK = new RSAKey.Builder(publicKey).privateKey(privateKey).build();
        JWK privateKeyJwk = privateKeyRsaJWK.toRSAKey();
        // 加密JSON对象
        String jwe = encrypt(payload, publicKeyJwk.toJSONString());
        System.out.println("JWE: " + jwe);
        // 解密JWE字符串
        JSONObject decryptedPayload = decrypt(jwe, privateKeyJwk.toJSONString());
        System.out.println("Decrypted payload: " + decryptedPayload.toString());
    }
}
