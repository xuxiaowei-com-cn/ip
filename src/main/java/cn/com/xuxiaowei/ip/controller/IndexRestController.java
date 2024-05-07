package cn.com.xuxiaowei.ip.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xuxiaowei
 * @since 0.0.1
 */
@RestController
public class IndexRestController {

	@RequestMapping("/ip")
	public Map<String, Object> ip(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<>(8);

		String remoteHost = request.getRemoteHost();

		map.put("remoteHost", remoteHost);

		return map;
	}

}
