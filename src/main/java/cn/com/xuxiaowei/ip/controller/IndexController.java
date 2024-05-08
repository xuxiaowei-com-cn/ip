package cn.com.xuxiaowei.ip.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

	@RequestMapping
	public String index(HttpServletRequest request, HttpServletResponse response) {
		return "redirect:/swagger-ui/index.html";
	}

}
