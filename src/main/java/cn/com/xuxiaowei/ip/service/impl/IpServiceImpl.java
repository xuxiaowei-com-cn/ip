package cn.com.xuxiaowei.ip.service.impl;

import cn.com.xuxiaowei.ip.service.GeoIp2Service;
import cn.com.xuxiaowei.ip.service.IpService;
import cn.com.xuxiaowei.ip.vo.ResponseVo;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * IP 服务接口实现
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Service
@WebService(serviceName = "ipService", targetNamespace = "https://ip.xuxiaowei.com.cn")
public class IpServiceImpl implements IpService {

	@Autowired
	private GeoIp2Service geoIp2Service;

	/**
	 * 根据 IP 或 域名 查询地理信息
	 * @param host 要查询的 IP 或 域名，为 空 或者是 ? 时，将使用请求 IP
	 * @return 返回 IP 或 域名 查询地理信息
	 */
	@Override
	@WebMethod
	@WebResult(name = "response")
	public ResponseVo ip(@WebParam(name = "host") String host) {
		return geoIp2Service.ip(host);
	}

}
