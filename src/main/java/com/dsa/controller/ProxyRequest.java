package com.dsa.controller;

import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProxyRequest {
  private static final String DEFAULT_REQUEST_CHARSET="ISO-8859-1";
  private Map<String, String> params;
  private HttpServletRequest request;

  public ProxyRequest(@NotNull HttpServletRequest request, boolean readBuffer) {
    params = new HashMap<>();
    if (readBuffer){
      String line = null;
      try {
        BufferedReader reader = request.getReader();
        String[] arr;
        while ((line = reader.readLine()) != null) {
          arr = line.split("=");
          if (arr.length == 1) {
            params.put(line, "");
          } else if (arr.length >= 1) {
            params.put(arr[0], arr[1]);
          }
        }
      } catch (IOException e) {
      }
    }
    this.request = request;
  }

  public String getParameter(String key) {
    String result = request.getParameter(key);
    if (result == null || result.isEmpty()) {
      result = params.get(key);
    }
    try{
      result=new String(result.getBytes(DEFAULT_REQUEST_CHARSET),"UTF-8");
    }catch(Exception e){}
    return result == null ? "" : result;
  }

  public void setAttribute(String key, Object value) {
    request.setAttribute(key, value);
  }

  public HttpSession getSession() {
    return request.getSession();
  }

  public String getPathInfo(){
    return request.getPathInfo();
  }

  public String getQueryString(){
    return request.getQueryString();
  }

  public HttpServletRequest getRequest(){
    return request;
  }

  public String getMethod(){
    return request.getMethod().toUpperCase();
  }
}
