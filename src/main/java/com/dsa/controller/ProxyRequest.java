package com.dsa.controller;

import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ProxyRequest {
  private HttpServletRequest request;

  public ProxyRequest(@NotNull HttpServletRequest request){
    this.request = request;
  }

  public String getParameter(String key){
    return request.getParameter(key);
  }

  public void setAttribute(String key, Object value){
    request.setAttribute(key, value);
  }

  public HttpSession getSession(){
    return request.getSession();
  }
}
