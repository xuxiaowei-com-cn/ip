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

	private boolean enableAsn;

	private String asnDatabase;

	private boolean enableCity;

	private String cityDatabase;

}
