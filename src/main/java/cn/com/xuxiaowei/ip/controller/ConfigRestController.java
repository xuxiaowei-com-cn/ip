package cn.com.xuxiaowei.ip.controller;

import cn.com.xuxiaowei.ip.properties.IpProperties;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 配置
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Tag(name = "配置", description = "配置接口")
@RestController
@RequestMapping("/config")
public class ConfigRestController {

	private IpProperties ipProperties;

	@Autowired
	public void setIpProperties(IpProperties ipProperties) {
		this.ipProperties = ipProperties;
	}

	/**
	 * 获取配置属性
	 * @param request 请求
	 * @param response 响应
	 * @return 返回配置属性
	 */
	@GetMapping
	public IpProperties getIpProperties(HttpServletRequest request, HttpServletResponse response) {
		return ipProperties;
	}

}
