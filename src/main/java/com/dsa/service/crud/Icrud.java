package com.dsa.service.crud;

import com.dsa.controller.ProxyRequest;

import javax.servlet.http.HttpServletResponse;

@FunctionalInterface
public interface Icrud {
  CrudResult execute(ProxyRequest request, HttpServletResponse response);
}
