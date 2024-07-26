package cn.sakka.wechat.transit;

import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Resource(name = "weChatReloadableResourceBundleMessageSource")
	private ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource;


	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String message = reloadableResourceBundleMessageSource.getMessage("test.name", null, Locale.CHINA);
		System.out.println(message);
	}
}
