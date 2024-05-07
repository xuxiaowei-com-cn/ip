package cn.com.xuxiaowei.ip.filter;

import cn.com.xuxiaowei.ip.constant.LogConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 日志 过滤器
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@Setter
@Component
public class LogWebFilter extends HttpFilter implements Ordered {

	public static final int ORDERED = Ordered.HIGHEST_PRECEDENCE;

	private int order = ORDERED;

	@Override
	public int getOrder() {
		return order;
	}

	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String id = RandomStringUtils.randomAlphanumeric(8);
		String uri = request.getRequestURI();

		String remoteHost = request.getRemoteHost();

		MDC.put(LogConstants.P_REQUEST_ID, id);
		MDC.put(LogConstants.P_HOST_NAME, remoteHost);

		log.debug("URI: {}, {}: {}", uri, LogConstants.P_HOST_NAME, remoteHost);

		super.doFilter(request, response, chain);
	}

}
