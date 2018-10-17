package com.ssmueditor.resolver;

import javax.servlet.http.HttpServletRequest;

//重写 过滤器
public class CommonsMultipartResolver extends org.springframework.web.multipart.commons.CommonsMultipartResolver {

	@Override
	public boolean isMultipart(HttpServletRequest request) {
		String uri = request.getRequestURI();
		//过滤 UEditor 的 URI
		if (uri.indexOf("ueditor/ueditorConfig") > 0) {
//			System.out.println("CommonsMultipartResolver 放行 UEditor 的 URI");
			return false;
		}
//		System.out.println("CommonsMultipartResolver 拦截");
		return super.isMultipart(request);
	}
}
