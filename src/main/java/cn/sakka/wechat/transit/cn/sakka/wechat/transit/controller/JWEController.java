package cn.sakka.wechat.transit.cn.sakka.wechat.transit.controller;

import cn.hutool.core.codec.Base64;
import com.nimbusds.jose.JWEDecrypter;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSADecrypter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

@RestController
public class JWEController {

    private static final Logger LOGGER = LoggerFactory.getLogger(JWEController.class);

    private final PrivateKey privateKey;

    /**
     * 在构造方法里初始化刚刚生成的私钥
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public JWEController() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String privateKeyBase64 = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCyoFNg6oFTmt2l1Ff3+jDGLScezGzIav/w9ZBnwa09CKVd825AQVY7TWjRlTwCCf7DNq2kYI7AiBJoQXinq4ji3KELwGWeRBk38ZyNuvKWPMitGW7w0qwu7jRJA7cJhr6nsMDfql1f+cxD1Bx5J4Zb17sTlha6IyFQOCpZUR2ZHFf5vF+g1ikG02W/LF4vQwoQhRWWKRgp75TEqGI9FzCMEiIcp+xjY+zM/iHjzlSNwha97ImMG7ycSlSAdSpQd0DWebfZaZQxw6FNTjW1fpmhgrqpQF41fjGd84buOcTvpXPZByxVOedOpND/3efEQitmxJ9D+93OLwuvJjbFLQSZAgMBAAECggEAJHg4XcazPeMWEufuR/pkX+nTHWYmZar283bnk0+HM7lirfJoFaVhWj09Q+EgvdfVlHzC6hcuvh9qBrArVqxeh9b86H3RIYWM0o+5Y3SCV+s0G6dgL7oLno9SzH9+LOs+XNVpI6FQbCp/qm+RmqjXtUOv9dlEbZ+DizHUb6TwkpRPMo3BHPUKGpajmP0pgSkQz8x4MWJ0a6SVST+/E7ZbwF8PobzOQCxND2yZQah/TY1EvCCyOM6chGm0loCyC4HGTBmDm/0LcWRqgiI8GiglEijGu2Iha6UR11JaEStHoc1Sy38Orj377bhndm2l19HNOQIslp9m0VP4WUSG3GJDKwKBgQC55+HY29F0H2jy7p94+snPCKVG0uJHu0IKhFN09fChP/1q0PLJuz3Vj07YUjGneSL3RHM1D+HbMYAGnZ+7GFmwIi8FLKBVZDx4L7ENhcPMUM2q0rsBphZuHfD14Hf9I0xUiNrpyTLuVPJfK3kzx2NYQmRGo6tY+INDuI5L2HEELwKBgQD1+c4ilEQtTRvoIa+BfZ/oOBeFaYsQGlLL+8yVbsPsfH+0MMoiIY++my5rDhT//8QXNhnOI1cs7CFKELA/99Mk7y9G/4o+cMb1kAyZJU6zNoSe9Eitdyo0qQQ20NVAW+YWX0zAuAm5bttk1RvVGz/wiuOotVw6oCSR0uUYUWCptwKBgB2psy6f/G6z6FIC4y0xjuva7Ew9r99UMLhu3sYly+xewne9uU+Y8cfWovT/QG8BdCPSJzPLQfVwk4X6tpbqzry855XCxh557PAcY/rNYi2Cox5jm3Uq5B9T5bPFyj9412ARqiRtdxPyN+4ZiLBLWz2k8k0XJmr+1CsFEqdldLr/AoGAEjyqLuAlSeKMriJJO+WPhI0cGVUg7Vm2R89sdKvYtODqKvbvFaa9XJlu0JsjrXNOG5Z0RVdTcE41jaM9HhEGw5dEPxRVMJn19mDuvjAI7LqfDJX6CXprU6owWMwU84ecwI3iR+udNPVmKMywGpXBoNj7VhfUNbiH3ZPwTmRCMXMCgYBi5I1KJ7kyaHKTilJEAhiYv6XBwsJScJkdAXWuA/SdG3aWQAEc4SOrRwqmqbHWYOm827tb20kG09rHnVS+tVSShvCkv4OcAr2X0L04IX9OfvUqI5pPWiQd/VzCQrTPcelixgkUkG4Sc2dRr6gvvFOKFAAVTQrePh9clk8kd3bg+g==";
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decode(privateKeyBase64));
        KeyFactory keyFactory;
        keyFactory = KeyFactory.getInstance("RSA");
        privateKey = keyFactory.generatePrivate(keySpec);
    }


    @GetMapping("/jwe")
    public void jwe(@RequestParam String encrypt) throws Exception {
        LOGGER.info("接受到的密文：{}", encrypt);
        String decrypt = decrypt(encrypt, (RSAPrivateKey) privateKey);
        LOGGER.info("解密后的内容：{}", decrypt);
    }


    public String decrypt(String jwe, RSAPrivateKey privateKey) throws Exception {
        // 创建解密器
        JWEDecrypter jweDecrypter = new RSADecrypter(privateKey);
        // 解密JWE字符串
        JWEObject jweObject = JWEObject.parse(jwe);
        jweObject.decrypt(jweDecrypter);
        // 将解密后的JSON数据转换为JSONObject对象
        Payload payload = jweObject.getPayload();
        return payload.toString();
    }

}
