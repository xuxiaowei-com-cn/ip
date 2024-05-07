package cn.com.xuxiaowei.ip.controller;

import cn.com.xuxiaowei.ip.properties.IpProperties;
import com.maxmind.db.CHMCache;
import com.maxmind.db.Network;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.AsnResponse;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Continent;
import com.maxmind.geoip2.record.Country;
import com.maxmind.geoip2.record.Subdivision;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@RestController
@RequestMapping("/ip")
public class IpRestController {

	private IpProperties ipProperties;

	@Autowired
	public void setIpProperties(IpProperties ipProperties) {
		this.ipProperties = ipProperties;
	}

	@RequestMapping
	public Map<String, Object> ip(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<>(8);

		String remoteHost = request.getRemoteHost();

		map.put("remoteHost", remoteHost);

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
						ipAddress = InetAddress.getByName(remoteHost);
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

							map.put("network", network.toString());
							map.put("systemOrganization", autonomousSystemOrganization);
							map.put("systemNumber", autonomousSystemNumber);
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
						ipAddress = InetAddress.getByName(remoteHost);
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
							List<Subdivision> subdivisions = cityResponse.getSubdivisions();
							City city = cityResponse.getCity();

							map.put("continentCode", continent.getCode());
							map.put("continentGeoNameId", continent.getGeoNameId());
							map.put("continentName", continent.getName());

							map.put("countryIsoCode", country.getIsoCode());
							map.put("countryGeoNameId", country.getGeoNameId());
							map.put("countryName", country.getName());

							List<String> subdivisionIsoCodes = new ArrayList<>();
							List<Long> subdivisionGeoNameIds = new ArrayList<>();
							List<String> subdivisionNames = new ArrayList<>();
							for (Subdivision subdivision : subdivisions) {
								subdivisionIsoCodes.add(subdivision.getIsoCode());
								subdivisionGeoNameIds.add(subdivision.getGeoNameId());
								subdivisionNames.add(subdivision.getName());
							}

							map.put("subdivisionIsoCodes", subdivisionIsoCodes);
							map.put("subdivisionGeoNameIds", subdivisionGeoNameIds);
							map.put("subdivisionNames", subdivisionNames);

							map.put("cityGeoNameId", city.getGeoNameId());
							map.put("cityName", city.getName());
						}
					}
				}

			}
			else {
				log.warn("虽然已开启 IP 城市匹配，但是 IP 城市匹配 数据库为空，无法使用 IP 城市匹配");
			}
		}

		return map;
	}

}
