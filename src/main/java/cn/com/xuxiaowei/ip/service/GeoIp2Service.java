package cn.com.xuxiaowei.ip.service;

import cn.com.xuxiaowei.ip.vo.ResponseVo;

/**
 * Geoip2 服务接口
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public interface GeoIp2Service {

	/**
	 * 根据 IP 或 域名 查询地理信息
	 * @param host 要查询的 IP 或 域名
	 * @return 返回 IP 或 域名 查询地理信息
	 */
	ResponseVo ip(String host);

}
