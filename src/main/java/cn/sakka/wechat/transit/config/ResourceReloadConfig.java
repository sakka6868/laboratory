package cn.sakka.wechat.transit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.nio.charset.StandardCharsets;

@Configuration
public class ResourceReloadConfig {

    @Bean(name = "weChatReloadableResourceBundleMessageSource")
    public ReloadableResourceBundleMessageSource weChatReloadableResourceBundleMessageSource(){
        ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
        reloadableResourceBundleMessageSource.setBasenames("classpath:setting-uat");
        reloadableResourceBundleMessageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        reloadableResourceBundleMessageSource.setCacheSeconds(5);
        reloadableResourceBundleMessageSource.setUseCodeAsDefaultMessage(true);
        return reloadableResourceBundleMessageSource;
    }


}
