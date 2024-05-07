package cn.com.xuxiaowei.ip.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author xuxiaowei
 * @since 0.0.1
 * @see <a href=
 * "https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-config/content-negotiation.html">Content
 * Types</a>
 */
@Slf4j
@Configuration
@EnableWebMvc
@ConditionalOnProperty(name = { "ip.content-negotiation" })
public class WebMvcConfigurerContentNegotiationConfig implements WebMvcConfigurer {

	/**
	 * @see <a href=
	 * "https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-config/content-negotiation.html">Content
	 * Types</a>
	 */
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

		log.warn("警告：已开启内容协商");
		log.warn("根据请求头中的 Accept 匹配到 application/json，将返回 json 类型的数据");
		log.warn("根据请求头中的 Accept 匹配到 application/xml，将返回 xml 类型的数据");
		log.warn("根据请求头中的 Accept 未匹配到时，将返回默认 json 类型的数据");

		configurer.defaultContentType(MediaType.APPLICATION_JSON);
		configurer.mediaType("json", MediaType.APPLICATION_JSON);
		configurer.mediaType("xml", MediaType.APPLICATION_XML);
	}

}
