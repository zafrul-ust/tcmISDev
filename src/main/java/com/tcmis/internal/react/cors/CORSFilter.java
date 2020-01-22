package com.tcmis.internal.react.cors;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class CORSFilter
 */
// Enable it for Servlet 3.x implementations
/* @ WebFilter(asyncSupported = true, urlPatterns = { "/*" }) */
public class CORSFilter implements Filter {

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
	// TODO Auto-generated method stub
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
	    throws IOException, ServletException {

	HttpServletRequest request = (HttpServletRequest) servletRequest;
	System.out.println("CORSFilter HTTP Request: " + request.getMethod());

	// Authorize (allow) all domains to consume the content
	((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Origin", "*");
	((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Methods", "GET,OPTIONS,HEAD,PUT,POST");
	((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Headers",
		"access-control-allow-origin,authorization,content-type");
	HttpServletResponse resp = (HttpServletResponse) servletResponse;

	// For HTTP OPTIONS verb/method reply with ACCEPTED status code -- per CORS
	// handshake
	if (request.getMethod().equals("OPTIONS")) {
	    resp.setStatus(HttpServletResponse.SC_ACCEPTED);
	    return;
	}

	// pass the request along the filter chain
	chain.doFilter(request, servletResponse);
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
	// TODO Auto-generated method stub

    }

}