package com.hua.utils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebFilter(urlPatterns={"/*"},initParams= {
		@WebInitParam(name="exclude",value="/login.jsp,/login,/noprivilege.jsp,.css,.png,.jpg,.js")
})
public class PermissionFilter implements Filter {
	public static String excludeString;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		excludeString=filterConfig.getInitParameter("exclude");
	}  

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		Object user=httpServletRequest.getSession().getAttribute("user");
		String uri=httpServletRequest.getRequestURI();
		//System.out.println(isExist(uri));
		if(isExist(uri)||uri.equals(httpServletRequest.getContextPath()+"/")) {
			chain.doFilter(httpServletRequest, httpServletResponse);
		}
		else {
			if(user!=null) {
				chain.doFilter(httpServletRequest, httpServletResponse);
			}else {
				httpServletResponse.sendRedirect("noprivilege.jsp");
			}
		}
	}
	public static boolean isExist(String uri) {
		//最好url结尾与excludeString匹配
		String[] arr=excludeString.split(",");
		boolean flag=false;
		for(String string: arr) {
			if(uri.endsWith(string)) {
				 flag=true;
			}
		}
		return flag;
	}
	
	@Override
	public void destroy() {

	}


}
