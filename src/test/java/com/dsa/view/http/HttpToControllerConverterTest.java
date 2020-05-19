package com.dsa.view.http;

import com.dsa.controller.ControllerRequest;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class HttpToControllerConverterTest {

  private static HttpServletRequest request;

  @Test
  void prepareRequestDataForController_ServletFilter() {
    System.out.println("Start prepareRequestDataForController_ServletFilter");
    when(request.getMethod()).thenReturn("GET");
    when(request.getPathInfo()).thenReturn("api");
    when(request.getQueryString()).thenReturn("sues");
    when(request.getSession()).thenReturn(null);
    ControllerRequest controllerRequest = HttpToControllerConverter.prepareRequestDataForController(request, false);
    verify(request).getMethod();
    verify(request, Mockito.times(2)).getPathInfo();
    verify(request).getQueryString();
    verify(request).getSession();
  }

  @Test
  void prepareRequestDataForController_Servlet() throws IOException {
    System.out.println("Start prepareRequestDataForController_Servlet");
    when(request.getMethod()).thenReturn("GET");
    when(request.getPathInfo()).thenReturn("api");
    when(request.getQueryString()).thenReturn("sues");
    when(request.getSession()).thenReturn(null);
    when(request.getReader()).thenReturn(null);
    ControllerRequest controllerRequest = HttpToControllerConverter.prepareRequestDataForController(request, true);
    verify(request).getMethod();
    verify(request, Mockito.times(2)).getPathInfo();
    verify(request).getQueryString();
    verify(request).getSession();
    verify(request).getReader();
  }

  @Test
  void httpToControllerConverter_Constructor() throws IOException {
    System.out.println("Start httpToControllerConverter_Constructor");
    HttpToControllerConverter httpToControllerConverter = new HttpToControllerConverter();
    assertEquals(httpToControllerConverter.hashCode(), httpToControllerConverter.hashCode());
  }

  @BeforeAll
  static void beforeAll() {
    System.out.println("Start testing HttpToControllerConverterTest");
  }

  @BeforeEach
  void beforeEach() {
    System.out.println("BeforeEach HttpToControllerConverterTest");
    request = Mockito.mock(HttpServletRequest.class);
  }

  @AfterAll
  static void afterAll() {
    System.out.println("Finish testing HttpToControllerConverterTest");
  }

}