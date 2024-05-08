package cn.com.xuxiaowei.ip.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求头
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Tag(name = "请求头", description = "请求头接口")
@RestController
@RequestMapping("/header")
public class HeaderRestController {

	/**
	 * 请求头接口
	 * @param request 请求
	 * @param response 响应
	 * @return 返回用户请求头，返回数据可能由于使用了代理而携带额外请求头：x-forwarded-*、g-*（个人网关代理附加的请求头）
	 */
	@Operation(summary = "请求头接口", description = "返回用户请求头，返回数据可能由于使用了代理而携带额外请求头：x-forwarded-*、g-*（个人网关代理附加的请求头）")
	@RequestMapping
	public Map<String, Object> header(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<>(32);

		Enumeration<String> headerNames = request.getHeaderNames();

		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			Enumeration<String> headerValues = request.getHeaders(headerName);
			map.put(headerName, Collections.list(headerValues));
		}

		return map;
	}

}
