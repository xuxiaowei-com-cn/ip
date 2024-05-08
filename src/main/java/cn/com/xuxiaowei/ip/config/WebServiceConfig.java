package cn.com.xuxiaowei.ip.config;

import cn.com.xuxiaowei.ip.service.HeaderService;
import cn.com.xuxiaowei.ip.service.IpService;
import jakarta.xml.ws.Endpoint;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.ext.logging.LoggingInInterceptor;
import org.apache.cxf.ext.logging.LoggingOutInterceptor;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * WebService 配置
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Configuration
public class WebServiceConfig {

	private IpService ipService;

	private HeaderService headerService;

	@Autowired
	public void setIpService(IpService ipService) {
		this.ipService = ipService;
	}

	@Autowired
	public void setHeaderService(HeaderService headerService) {
		this.headerService = headerService;
	}

	/**
	 * CXF
	 * @return 返回 CXF {@link Bean}
	 */
	@Bean(name = Bus.DEFAULT_BUS_ID)
	public SpringBus springBus() {
		return new SpringBus();
	}

	/**
	 * 注册 CXF 前缀 Servlet
	 * @return 返回 CXF {@link ServletRegistrationBean}
	 */
	@Bean
	public ServletRegistrationBean<CXFServlet> ws() {
		return new ServletRegistrationBean<>(new CXFServlet(), "/ws/*");
	}

	/**
	 * IP WebService 接口
	 * @return 返回 公共 WebService 桶 {@link Endpoint}
	 */
	@Bean
	public Endpoint ipServiceEndpoint() {
		EndpointImpl ipServiceEndpointImpl = new EndpointImpl(ipService);
		ipServiceEndpointImpl.publish("/ipService");

		ipServiceEndpointImpl.getInInterceptors().add(new LoggingInInterceptor());
		ipServiceEndpointImpl.getOutInterceptors().add(new LoggingOutInterceptor());

		return ipServiceEndpointImpl;
	}

	/**
	 * Header WebService 接口
	 * @return 返回 公共 WebService 桶 {@link Endpoint}
	 */
	@Bean
	public Endpoint headerServiceEndpoint() {
		EndpointImpl headerServiceEndpointImpl = new EndpointImpl(headerService);
		headerServiceEndpointImpl.publish("/headerService");

		headerServiceEndpointImpl.getInInterceptors().add(new LoggingInInterceptor());
		headerServiceEndpointImpl.getOutInterceptors().add(new LoggingOutInterceptor());

		return headerServiceEndpointImpl;
	}

}
