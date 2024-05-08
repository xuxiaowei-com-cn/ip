package cn.com.xuxiaowei.ip.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

/**
 * SpringDoc 配置
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Configuration
@OpenAPIDefinition(
		info = @Info(title = "IP 地理信息", description = "根据 IP 获取：大陆、国家、省、市、网络运营商、网段、是否为欧盟国家等", version = "v0.0.1"))
public class SpringdocConfig {

}
