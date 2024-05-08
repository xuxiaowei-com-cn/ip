package cn.com.xuxiaowei.ip.vo;

import lombok.Data;

import java.util.List;

/**
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
public class ResponseVo {

	private String host;

	private String network;

	private String systemOrganization;

	private Long systemNumber;

	private String continentCode;

	private Long continentGeoNameId;

	private String continentName;

	private String countryIsoCode;

	private Long countryGeoNameId;

	private String countryName;

	private boolean inEuropeanUnion;

	private List<String> subdivisionIsoCodes;

	private List<Long> subdivisionGeoNameIds;

	private List<String> subdivisionNames;

	private Long cityGeoNameId;

	private String cityName;

}
