package com.psedb.service;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(value = "/*")
public class ServletFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
		HttpServletRequest request = (HttpServletRequest) req;
	    HttpServletResponse response = (HttpServletResponse) res;
		if(request.getServletPath().contains("index.jsp") || request.getServletPath().endsWith("login")){
			chain.doFilter(req, res);
			return;
		}else if(request.getSession().getAttribute("user")!=null){
			chain.doFilter(req, res);
			return;
		}
		response.sendRedirect("index.jsp");
	}

	public void destroy() {
	}
}