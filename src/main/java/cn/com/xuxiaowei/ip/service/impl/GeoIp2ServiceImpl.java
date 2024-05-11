package cn.com.xuxiaowei.ip.service.impl;

import cn.com.xuxiaowei.ip.filter.RequestContextHolderFilter;
import cn.com.xuxiaowei.ip.properties.IpProperties;
import cn.com.xuxiaowei.ip.service.GeoIp2Service;
import cn.com.xuxiaowei.ip.vo.ResponseVo;
import cn.com.xuxiaowei.ip.vo.Subdivision;
import com.maxmind.db.CHMCache;
import com.maxmind.db.Network;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.AsnResponse;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Continent;
import com.maxmind.geoip2.record.Country;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Geoip2 服务接口实现
 *
 * @author xuxiaowei
 * @since 0.0.1
 * @see RequestContextHolderFilter
 */
@Slf4j
@Service
public class GeoIp2ServiceImpl implements GeoIp2Service {

	private IpProperties ipProperties;

	@Autowired
	public void setIpProperties(IpProperties ipProperties) {
		this.ipProperties = ipProperties;
	}

	/**
	 * 根据 IP 或 域名 查询地理信息
	 * @param host 要查询的 IP 或 域名，为 空 或者是 ? 时，将使用请求 IP
	 * @return 返回 IP 或 域名 查询地理信息
	 */
	@Override
	@SuppressWarnings("AlibabaMethodTooLong")
	public ResponseVo ip(String host) {

		if (!StringUtils.hasText(host) || "?".equals(host)) {

			// 从请求线程储存中获取请求
			RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
			// 清理
			RequestContextHolder.resetRequestAttributes();

			// 转换对象
			ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
			// 获取 HTTP 请求
			HttpServletRequest request = servletRequestAttributes.getRequest();

			host = request.getRemoteHost();
		}

		ResponseVo responseVo = new ResponseVo();

		responseVo.setHost(host);

		if (ipProperties.isEnableAsn()) {
			String asnDatabase = ipProperties.getAsnDatabase();
			if (StringUtils.hasText(asnDatabase)) {
				File database = new File(asnDatabase);

				// This reader object should be reused across lookups as creation of it is
				// expensive.
				// DatabaseReader reader = new DatabaseReader.Builder(database).build();

				// If you want to use caching at the cost of a small (~2MB) memory
				// overhead:
				// new DatabaseReader.Builder(file).withCache(new CHMCache()).build();
				DatabaseReader reader = null;
				try {
					reader = new DatabaseReader.Builder(database).locales(List.of("zh-CN"))
						.withCache(new CHMCache())
						.build();
				}
				catch (IOException e) {
					log.error("创建 ASN 读取器 异常：", e);
				}

				if (reader != null) {

					InetAddress ipAddress = null;
					try {
						ipAddress = InetAddress.getByName(host);
					}
					catch (UnknownHostException e) {
						log.error("ASN 解析地址 异常：", e);
					}

					if (ipAddress != null) {
						AsnResponse asnResponse = null;
						try {
							asnResponse = reader.asn(ipAddress);
						}
						catch (IOException e) {
							log.error("ASN IOException 异常：", e);
						}
						catch (GeoIp2Exception e) {
							log.error("ASN GeoIp2Exception 异常：", e);
						}

						if (asnResponse != null) {
							Network network = asnResponse.getNetwork();
							String autonomousSystemOrganization = asnResponse.getAutonomousSystemOrganization();
							Long autonomousSystemNumber = asnResponse.getAutonomousSystemNumber();

							responseVo.setNetwork(network.toString());
							responseVo.setSystemOrganization(autonomousSystemOrganization);
							responseVo.setSystemNumber(autonomousSystemNumber);
						}
					}
				}

			}
			else {
				log.warn("虽然已开启 ASN，但是 ASN 数据库为空，无法使用 ASN");
			}
		}

		if (ipProperties.isEnableCity()) {
			String cityDatabase = ipProperties.getCityDatabase();
			if (StringUtils.hasText(cityDatabase)) {
				File database = new File(cityDatabase);

				// This reader object should be reused across lookups as creation of it is
				// expensive.
				// DatabaseReader reader = new DatabaseReader.Builder(database).build();

				// If you want to use caching at the cost of a small (~2MB) memory
				// overhead:
				// new DatabaseReader.Builder(file).withCache(new CHMCache()).build();
				DatabaseReader reader = null;
				try {
					reader = new DatabaseReader.Builder(database).locales(List.of("zh-CN"))
						.withCache(new CHMCache())
						.build();
				}
				catch (IOException e) {
					log.error("创建 IP 城市匹配 读取器 异常：", e);
				}

				if (reader != null) {

					InetAddress ipAddress = null;
					try {
						ipAddress = InetAddress.getByName(host);
					}
					catch (UnknownHostException e) {
						log.error("IP 城市匹配 解析地址 异常：", e);
					}

					if (ipAddress != null) {
						CityResponse cityResponse = null;
						try {
							cityResponse = reader.city(ipAddress);
						}
						catch (IOException e) {
							log.error("IP 城市匹配 IOException 异常：", e);
						}
						catch (GeoIp2Exception e) {
							log.error("IP 城市匹配 GeoIp2Exception 异常：", e);
						}

						if (cityResponse != null) {
							Continent continent = cityResponse.getContinent();
							Country country = cityResponse.getCountry();
							City city = cityResponse.getCity();

							responseVo.setContinentCode(continent.getCode());
							responseVo.setContinentGeoNameId(continent.getGeoNameId());
							responseVo.setContinentName(continent.getName());

							responseVo.setCountryIsoCode(country.getIsoCode());
							responseVo.setCountryGeoNameId(country.getGeoNameId());
							responseVo.setCountryName(country.getName());

							responseVo.setInEuropeanUnion(country.isInEuropeanUnion());

							List<Subdivision> subdivisions = new ArrayList<>();
							for (com.maxmind.geoip2.record.Subdivision subdivision : cityResponse.getSubdivisions()) {
								Subdivision sub = new Subdivision();
								subdivisions.add(sub);
								sub.setIsoCode(subdivision.getIsoCode());
								sub.setGeoNameId(subdivision.getGeoNameId());
								sub.setName(subdivision.getName());
							}

							responseVo.setSubdivisions(subdivisions);

							responseVo.setCityGeoNameId(city.getGeoNameId());
							responseVo.setCityName(city.getName());
						}
					}
				}

			}
			else {
				log.warn("虽然已开启 IP 城市匹配，但是 IP 城市匹配 数据库为空，无法使用 IP 城市匹配");
			}
		}

		return responseVo;
	}

}
