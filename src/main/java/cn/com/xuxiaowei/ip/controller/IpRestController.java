package cn.com.xuxiaowei.ip.controller;

import cn.com.xuxiaowei.ip.service.GeoIp2Service;
import cn.com.xuxiaowei.ip.vo.ResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * IP
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Tag(name = "IP", description = "IP 获取地理信息接口")
@Slf4j
@RestController
@RequestMapping("/ip")
public class IpRestController {

	private GeoIp2Service geoIp2Service;

	@Autowired
	public void setGeoIp2Service(GeoIp2Service geoIp2Service) {
		this.geoIp2Service = geoIp2Service;
	}

	/**
	 * 查询 IP 地理信息
	 * @param request 请求
	 * @param response 响应
	 * @param host 要查询的 IP 或 域名，如果为空时则查询当前请求的IP地理信息
	 * @return 返回 IP 地理信息
	 */
	@Operation(summary = "查询 IP 地理信息", description = "根据用户参数中的 IP、域名 或 用户当前 IP 获取 大陆、国家、省、市、网络运营商、网段、是否为欧盟国家等")
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ResponseVo ip(HttpServletRequest request, HttpServletResponse response,
			@Parameter(example = "119.165.180.222",
					description = "IP或域名，如果为空时则查询当前请求的IP地理信息") @RequestParam(required = false) String host) {

		if (!StringUtils.hasText(host)) {
			host = request.getRemoteHost();
		}

		return geoIp2Service.ip(host);
	}

}
