package cn.com.xuxiaowei.ip.filter;

import cn.com.xuxiaowei.ip.service.impl.GeoIp2ServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;

/**
 * 请求内容 过滤器
 *
 * @author xuxiaowei
 * @since 0.0.1
 * @see GeoIp2ServiceImpl
 */
@Slf4j
@Setter
@Component
public class RequestContextFilter extends HttpFilter implements Ordered {

	public static final int ORDERED = Ordered.HIGHEST_PRECEDENCE + 10000;

	private int order = ORDERED;

	@Override
	public int getOrder() {
		return order;
	}

	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// 创建 请求属性
		ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(request);
		// 在请求线程中储存：请求属性
		RequestContextHolder.setRequestAttributes(servletRequestAttributes);

		super.doFilter(request, response, chain);
	}

}
