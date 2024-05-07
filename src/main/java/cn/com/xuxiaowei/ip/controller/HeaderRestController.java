package cn.com.xuxiaowei.ip.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author xuxiaowei
 * @since 0.0.1
 */
@RestController
@RequestMapping("/header")
public class HeaderRestController {

	@RequestMapping
	public Map<String, Object> header(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<>(16);

		Enumeration<String> headerNames = request.getHeaderNames();

		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			Enumeration<String> headerValues = request.getHeaders(headerName);
			map.put(headerName, Collections.list(headerValues));
		}

		return map;
	}

}
