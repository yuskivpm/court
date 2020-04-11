package com.dsa.controller;

import com.dsa.service.command.LoginCommand;

import javax.servlet.Filter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Servlet filter for entry
 * Verifies that the user has been authenticated,
 * otherwise redirect ршь to the login page
 *
 * accept all URL patterns
 *
 * Login page - LOGIN_PAGE
 * Login request - CHECK_LOGIN_REQUEST
 * login process page - LOGIN_PROCESS_PAGE
 */

@WebFilter(
    urlPatterns = "/*",
    dispatcherTypes = {DispatcherType.REQUEST}
)
public class ControllerFilter implements Filter{
  private static final String LOGIN_PAGE="/jsp/login.jsp";
  private static final String CHECK_LOGIN_REQUEST="/login";
  private static final String LOGIN_PROCESS_PAGE="/controller";

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
    HttpServletRequest req=(HttpServletRequest)request;
    String path = req.getRequestURI();
    if(path.equals(CHECK_LOGIN_REQUEST)){
      // receive "/login" - redirect it to controller
      req.getRequestDispatcher(LOGIN_PROCESS_PAGE).forward(request,response);
      return;
    }else if (!path.equals(LOGIN_PAGE)) {
      // all requests except "/jsp/login.jsp" check for prior login
      ProxyRequest proxyRequest = new ProxyRequest((HttpServletRequest) request);
      String UserSession = LoginCommand.getUserSessionId(proxyRequest);
      if (UserSession.isEmpty()) {
        // user wasn't authorized - redirect to "/jsp/login.jsp"
        req.getRequestDispatcher(LOGIN_PAGE).forward(request,response);
        return;
      }
    }
    chain.doFilter(request,response);
  }
}
