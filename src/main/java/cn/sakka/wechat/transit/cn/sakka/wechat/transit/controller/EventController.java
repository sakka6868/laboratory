package cn.sakka.wechat.transit.cn.sakka.wechat.transit.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ByteUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.crypto.SecureUtil;
import cn.sakka.wechat.transit.cn.sakka.wechat.transit.WeChatEventData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Document;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author lwh
 * @version 1.0
 * @description: 配置微信回调
 * @date 2023/1/16
 */
@RestController
@RequestMapping("/event")
public class EventController {

    protected static final Logger LOGGER = LoggerFactory.getLogger(EventController.class);

    @GetMapping(value = "/hello")
    public String hello(String signature, String timestamp, String nonce, String echostr) {
        return "hello";
    }


    @GetMapping(value = "/wechat/wxgzh")
    public String checkSignature(String signature, String timestamp, String nonce, String echostr) {
        if (CharSequenceUtil.isEmpty(signature) || CharSequenceUtil.isEmpty(timestamp) || CharSequenceUtil.isEmpty(nonce) || CharSequenceUtil.isEmpty(echostr)) {
            throw new IllegalArgumentException("缺少微信参数验证");
        }
        List<String> list = new ArrayList<>();
        list.add(nonce);
        list.add(timestamp);
        //这是第5步中你设置的Token
        list.add("QjquGic0chZvECyQ9uF2L3nsVL06Z7jX");
        Collections.sort(list);
        String sha1Signature = SecureUtil.sha1(list.get(0) + list.get(1) + list.get(2));
        if (sha1Signature.equals(signature)) {
            LOGGER.info("收到事件：{}", echostr);
            return echostr;
        }
        throw new IllegalArgumentException("微信参数验证不匹配");
    }


    @PostMapping(value = "/wechat/wxgzh", produces = "application/xml; charset=UTF-8")
    public void onWeChatEvent(@RequestBody WeChatEventData weChatEventData) throws IOException {
        LOGGER.info("收到事件：{}", weChatEventData);
    }


}
