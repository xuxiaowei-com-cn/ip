package cn.com.xuxiaowei.ip.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 配置
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
@Component
@ConfigurationProperties("ip")
public class IpProperties {

	/**
	 * 是否开启 IP ASN 地址识别
	 */
	private boolean enableAsn;

	/**
	 * IP ASN 地址识别 数据库文件路径
	 */
	private String asnDatabase;

	/**
	 * 是否开启 城市 IP 地址识别
	 */
	private boolean enableCity;

	/**
	 * 城市 IP 地址识别 数据库文件路径
	 */
	private String cityDatabase;

	/**
	 * 是否开启 内容协商<br>
	 * 根据请求头中的 Accept 匹配到 application/json，将返回 json 类型的数据<br>
	 * 根据请求头中的 Accept 匹配到 application/xml，将返回 xml 类型的数据<br>
	 * 根据请求头中的 Accept 未匹配到时，将返回默认 json 类型的数据
	 */
	private boolean contentNegotiation;

}
