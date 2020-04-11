package com.dsa.controller;

import com.dsa.service.command.LoginCommand;

import javax.servlet.Filter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

// TODO: UNREADY ControllerFilter!!!
//@WebFilter(
//    urlPatterns = "/ccdcdcd/*",
//    dispatcherTypes = {DispatcherType.REQUEST}
//)
public class ControllerFilter implements Filter{
  private static final String LOGIN_PAGE="/jsp/login.jsp";
  private static final String CHECK_LOGIN_REQUEST="/login";
  private static final String CHECK_LOGIN_PAGE="/controller";

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
    HttpServletRequest req=(HttpServletRequest)request;
    String path = req.getRequestURI();
    if(path.equals(CHECK_LOGIN_REQUEST)){
      req.getRequestDispatcher(CHECK_LOGIN_PAGE).forward(request,response);
      return;
    }else if (!path.equals(LOGIN_PAGE)) {
      ProxyRequest proxyRequest = new ProxyRequest((HttpServletRequest) request);
      String UserSession = LoginCommand.getUserSessionId(proxyRequest);
      if (UserSession.isEmpty()) {
        req.getRequestDispatcher(LOGIN_PAGE).forward(request,response);
        return;
      }
    }
    chain.doFilter(request,response);
  }
}
