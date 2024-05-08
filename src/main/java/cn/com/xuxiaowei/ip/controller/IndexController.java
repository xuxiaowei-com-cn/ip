package cn.com.xuxiaowei.ip.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@Controller
public class IndexController {

	@SneakyThrows
	@RequestMapping
	public void index(HttpServletRequest request, HttpServletResponse response) {
		response.sendRedirect("/swagger-ui/index.html");
	}

}
