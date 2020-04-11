package com.dsa.controller;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

// TODO: UNREADY ControllerFilter!!!
//@WebFilter(
//    urlPatterns = "/ccdcdcd/*",
//    dispatcherTypes = {DispatcherType.REQUEST}
//)
public class ControllerFilter implements Filter{

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{

  }
}
