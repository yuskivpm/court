package com.dsa.view.http;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;

class MainServletFilterTest {

  private static final String LOGIN_PAGE = "/jsp/login.jsp";
  private static final String USER_SESSION_ID = "user_id";
  private static final String COMMAND = "command";
  private static final String CUR_USER = "curUser";

  private static MainServletFilter mainServletFilter;
  private static HttpServletRequest request;
  private static ServletResponse response;
  private static FilterChain chain;
  private static RequestDispatcher requestDispatcher;
  private static HttpSession session;

  @Test
  void doFilter_UserRequestForLoginJsp_Test() throws IOException, ServletException {
    System.out.println("Start doFilter_UserRequestForLoginJsp_Test");
    when(request.getRequestURI()).thenReturn(LOGIN_PAGE);
    mainServletFilter.doFilter(request, response, chain);
    verify(request).setAttribute(CUR_USER, null);
    verify(chain).doFilter(request, response);
  }

  @Test
  void doFilter_UserRequestForLoginCommand_Test() throws IOException, ServletException {
    System.out.println("Start doFilter_UserNotAuthorized_Test");
    when(request.getRequestURI()).thenReturn("");
    when(request.getParameterMap()).thenReturn(new HashMap<String, String[]>() {{
      put(COMMAND, new String[]{"login"});
    }});
    mainServletFilter.doFilter(request, response, chain);
    verify(request).setAttribute(CUR_USER, null);
    verify(chain).doFilter(request, response);
  }

  @Test
  void doFilter_UserDoNotRequestForLogin_WithoutSessionId_Test() throws IOException, ServletException {
    System.out.println("Start doFilter_UserDoNotRequestForLogin_WithoutSessionId_Test");
    when(request.getRequestURI()).thenReturn("");
    when(request.getSession()).thenReturn(session);
    when(request.getRequestDispatcher(LOGIN_PAGE)).thenReturn(requestDispatcher);
    mainServletFilter.doFilter(request, response, chain);
    verify(requestDispatcher).forward(request, response);
  }

  @Test
  void doFilter_UserDoNotRequestForLogin_WithSessionId_Test() throws IOException, ServletException {
    System.out.println("Start doFilter_UserDoNotRequestForLogin_WithSessionId_Test");
    when(request.getRequestURI()).thenReturn("");
    when(request.getParameter(COMMAND)).thenReturn("");
    when(request.getSession()).thenReturn(session);
    when(session.getAttributeNames()).thenReturn(Collections.enumeration(new ArrayList<String>() {{
      add(USER_SESSION_ID);
    }}));
    when(session.getAttribute(USER_SESSION_ID)).thenReturn("1");
    when(request.getRequestDispatcher(LOGIN_PAGE)).thenReturn(requestDispatcher);
    mainServletFilter.doFilter(request, response, chain);
    verify(request).setAttribute(eq(CUR_USER), Mockito.any());
    verify(chain).doFilter(request, response);
  }

  @BeforeAll
  static void beforeAll() {
    System.out.println("Start testing MainServletFilter");
    response = Mockito.mock(ServletResponse.class);
    session = Mockito.mock(HttpSession.class);
    requestDispatcher = Mockito.mock(RequestDispatcher.class);
    mainServletFilter = new MainServletFilter();
  }

  @BeforeEach
  void beforeEach() {
    System.out.println("BeforeEach MainServletFilter");
    request = Mockito.mock(HttpServletRequest.class);
    chain = Mockito.mock(FilterChain.class);
    requestDispatcher = Mockito.mock(RequestDispatcher.class);
  }

  @AfterAll
  static void afterAll() {
    System.out.println("Finish testing MainServletFilter");
  }

}
