package cn.com.xuxiaowei.ip.vo;

import lombok.Data;

/**
 * 区域细分
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
public class Subdivision {

	/**
	 * 区域细分ISO代码
	 */
	private String isoCode;

	/**
	 * 区域细分地理ID
	 */
	private Long geoNameId;

	/**
	 * 区域细分地理名称
	 */
	private String name;

}
