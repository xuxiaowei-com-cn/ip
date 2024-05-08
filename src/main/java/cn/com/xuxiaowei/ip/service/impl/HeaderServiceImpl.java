package cn.com.xuxiaowei.ip.service.impl;

import cn.com.xuxiaowei.ip.filter.RequestContextFilter;
import cn.com.xuxiaowei.ip.service.HeaderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jws.WebMethod;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.*;

/**
 * Header 服务接口实现
 *
 * @author xuxiaowei
 * @since 0.0.1
 * @see RequestContextFilter
 */
@Service
@WebService(serviceName = "headerService", targetNamespace = "https://ip.xuxiaowei.com.cn")
public class HeaderServiceImpl implements HeaderService {

	@SneakyThrows
	@Override
	@WebMethod
	@WebResult(name = "response")
	public Map<String, String> header() {
		Map<String, String> map = new HashMap<>(32);

		// 从请求线程储存中获取请求
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		// 清理
		RequestContextHolder.resetRequestAttributes();

		// 转换对象
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
		// 获取 HTTP 请求
		HttpServletRequest request = servletRequestAttributes.getRequest();

		Enumeration<String> headerNames = request.getHeaderNames();

		ObjectMapper objectMapper = new ObjectMapper();

		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			Enumeration<String> headerValues = request.getHeaders(headerName);
			List<String> list = Collections.list(headerValues);
			map.put(headerName, objectMapper.writeValueAsString(list));
		}

		return map;
	}

}
