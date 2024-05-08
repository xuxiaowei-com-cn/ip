package cn.com.xuxiaowei.ip.service.impl;

import cn.com.xuxiaowei.ip.properties.IpProperties;
import cn.com.xuxiaowei.ip.service.ConfigService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 配置 服务接口实现
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Service
@WebService(serviceName = "configService", targetNamespace = "https://ip.xuxiaowei.com.cn")
public class ConfigServiceImpl implements ConfigService {

	@Autowired
	private IpProperties ipProperties;

	@Override
	@WebMethod
	@WebResult(name = "response")
	public IpProperties config() {
		return ipProperties;
	}

}
