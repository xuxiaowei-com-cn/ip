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
// @formatter:off
@OpenAPIDefinition(info = @Info(title = "IP 地理信息",
		description = "<a target=\"_blank\" href=\"https://docs.xuxiaowei.cloud/spring-cloud-xuxiaowei/reference/maxmind-geoip.html\">MaxMind GeoIP 文档</a><br><br>" +
				"根据 IP 获取：大陆、国家、省、市、网络运营商、网段、是否为欧盟国家等。<br><br>" +
				"项目地址：<br>" +
				"<a target=\"_blank\" href=\"https://jihulab.com/xuxiaowei-jihu/xuxiaowei-com-cn/ip\">极狐 GitLab</a><br>" +
				"<a target=\"_blank\" href=\"https://gitee.com/xuxiaowei-com-cn/ip\">Gitee</a><br>" +
				"<a target=\"_blank\" href=\"https://github.com/xuxiaowei-com-cn/ip\">GitHub</a><br><br>" +
				"接口支持 WebService：<a target=\"_blank\" href=\"/ws\">CXF WebService 列表</a><br><br>" +
				"注意：<br>" + "本项目如果开启了内容协商，返回数据将遵守以下滚则。<br>" +
				"根据请求头中的 Accept 匹配到 application/json，将返回 json 类型的数据。<br>" +
				"根据请求头中的 Accept 匹配到 application/xml，将返回 xml 类型的数据。<br>" +
				"根据请求头中的 Accept 未匹配到时，将返回默认 json 类型的数据。",
		version = "v0.0.1"))
// @formatter:on
public class SpringdocConfig {

}
