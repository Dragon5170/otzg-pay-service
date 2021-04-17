package com.otzg;

import com.otzg.log.util.LogUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 系统访问控制类
 * @author G/2018/3/29 15:17
 */
@Configuration
@Component
@ConfigurationProperties(prefix = "server")
public class WebAppConfig implements WebMvcConfigurer {

	//设置服务器访问文件路径
	public void setServUrl(String path) {
		LogUtil.setServUrl(path);
	}

	//设置服务器保存文件路径
	public void setFileSavePath(String path) {
		LogUtil.setFileSavePath(path);
	}
}
