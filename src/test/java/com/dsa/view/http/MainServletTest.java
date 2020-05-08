package com.dsa.view.http;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

class MainServletTest {

  private static HttpServletRequest request;
  private static HttpServletResponse response;
  private static MainServlet mainServlet;
  private static HttpSession session;

  @Test
  void doGet() throws ServletException, IOException {
    System.out.println("Start doGet");
    when(request.getSession()).thenReturn(session);
    mainServlet.doGet(request, response);
    verify(response).sendRedirect("null/index.jsp");
  }

  @Test
  void doPost() throws ServletException, IOException {
    System.out.println("Start doPost");
    when(request.getSession()).thenReturn(session);
    mainServlet.doPost(request, response);
    verify(response).sendRedirect("null/index.jsp");
  }

  @Test
  void doPut() throws ServletException, IOException {
    System.out.println("Start doPut");
    when(request.getSession()).thenReturn(session);
    mainServlet.doPut(request, response);
    verify(response).sendRedirect("null/index.jsp");
  }

  @Test
  void doDelete() throws ServletException, IOException {
    System.out.println("Start doDelete");
    when(request.getSession()).thenReturn(session);
    mainServlet.doDelete(request, response);
    verify(response).sendRedirect("null/index.jsp");
  }

  @BeforeAll
  static void beforeAll() {
    System.out.println("Start testing MainServletTest");
    session = Mockito.mock(HttpSession.class);
    mainServlet = new MainServlet();
  }

  @BeforeEach
  void beforeEach() {
    System.out.println("BeforeEach MainServletTest");
    request = Mockito.mock(HttpServletRequest.class);
    response = Mockito.mock(HttpServletResponse.class);
  }

  @AfterAll
  static void afterAll() {
    System.out.println("Finish testing MainServletTest");
  }

}