package cn.com.xuxiaowei.ip.vo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.Data;

import java.util.List;

/**
 * IP 地理信息
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
public class ResponseVo {

	/**
	 * 请求的域名或IP
	 */
	private String host;

	/**
	 * 网段
	 */
	private String network;

	/**
	 * 网络运营商
	 */
	private String systemOrganization;

	/**
	 * 网络运营商ID
	 */
	private Long systemNumber;

	/**
	 * 大陆代码
	 */
	private String continentCode;

	/**
	 * 大陆地理ID
	 */
	private Long continentGeoNameId;

	/**
	 * 大陆名称
	 */
	private String continentName;

	/**
	 * 国家/地区ISO代码
	 */
	private String countryIsoCode;

	/**
	 * 国家/地区地理ID
	 */
	private Long countryGeoNameId;

	/**
	 * 国家/地区名称
	 */
	private String countryName;

	/**
	 * 是否是欧盟国家
	 */
	private boolean inEuropeanUnion;

	/**
	 * 区域细分
	 */
	// @JacksonXmlElementWrapper(localName = "subdivisions")
	// @JacksonXmlProperty(localName = "subdivision")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<Subdivision> subdivisions;

	/**
	 * 城市区域代码
	 */
	private Long cityGeoNameId;

	/**
	 * 城市名称
	 */
	private String cityName;

}
