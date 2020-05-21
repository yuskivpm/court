package com.dsa.view.http;

import com.dsa.controller.Controller;
import com.dsa.controller.ControllerRequest;
import com.dsa.controller.IController;
import com.dsa.controller.ResponseType;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MainServletTest {

  private static HttpServletRequest request;
  private static HttpServletResponse response;
  private static MainServlet mainServlet;
  private static HttpSession session;
  private static IController controller;
  private static ControllerRequest controllerRequest;

  @Test
  void doGet() throws ServletException, IOException {
    System.out.println("Start doGet");
    when(request.getSession()).thenReturn(session);
    when(controllerRequest.getResponseType()).thenReturn(ResponseType.REDIRECT);
    mainServlet.doGet(request, response);
    verify(response).sendRedirect("nullnull");
  }

  @Test
  void doPost() throws ServletException, IOException {
    System.out.println("Start doPost");
    when(request.getSession()).thenReturn(session);
    when(controllerRequest.getResponseType()).thenReturn(ResponseType.REDIRECT);
    mainServlet.doPost(request, response);
    verify(response).sendRedirect("nullnull");
  }

  @Test
  void doPut() throws ServletException, IOException {
    System.out.println("Start doPut");
    when(request.getSession()).thenReturn(session);
    when(controllerRequest.getResponseType()).thenReturn(ResponseType.REDIRECT);
    mainServlet.doPut(request, response);
    verify(response).sendRedirect("nullnull");
  }

  @Test
  void doDelete() throws ServletException, IOException {
    System.out.println("Start doDelete");
    when(request.getSession()).thenReturn(session);
    when(controllerRequest.getResponseType()).thenReturn(ResponseType.REDIRECT);
    mainServlet.doDelete(request, response);
    verify(response).sendRedirect("nullnull");
  }

  @Test
  void doGet_FORWARD() throws ServletException, IOException {
    System.out.println("Start processRequest FORWARD");
    when(controllerRequest.getResponseType()).thenReturn(ResponseType.FORWARD);
    when(request.getSession()).thenReturn(session);
    when(controllerRequest.getResponseValue()).thenReturn("1");
    assertThrows(IllegalStateException.class, () -> mainServlet.doGet(request, response));
    System.out.println("Start processRequest PLAIN_TEXT");
    PrintWriter printWriter = Mockito.mock(PrintWriter.class);
    when(response.getWriter()).thenReturn(printWriter);
    when(controllerRequest.getResponseType()).thenReturn(ResponseType.PLAIN_TEXT);
    final String RESPONSE_TYPE_VALUE = "RESPONSE_TYPE_VALUE";
    when(controllerRequest.getResponseValue()).thenReturn(RESPONSE_TYPE_VALUE);
    mainServlet.doGet(request, response);
    verify(printWriter).print(RESPONSE_TYPE_VALUE);
    System.out.println("Start processRequest INVALIDATE");
    when(controllerRequest.getResponseType()).thenReturn(ResponseType.INVALIDATE);
    mainServlet.doGet(request, response);
    verify(request).setAttribute("user", "");
    verify(session).invalidate();
    System.out.println("Start processRequest REDIRECT");
    when(controllerRequest.getResponseType()).thenReturn(ResponseType.REDIRECT);
    when(request.getContextPath()).thenReturn("");
    mainServlet.doGet(request, response);
    verify(response, Mockito.times(2)).sendRedirect(Mockito.anyString());
  }

  @BeforeAll
  static void beforeAll() {
    System.out.println("Start testing MainServletTest");
    session = Mockito.mock(HttpSession.class);
    mainServlet = new MainServlet();
    controller = Mockito.mock(Controller.class);
    MainServlet.setController(controller);
  }

  @BeforeEach
  void beforeEach() {
    System.out.println("BeforeEach MainServletTest");
    request = Mockito.mock(HttpServletRequest.class);
    response = Mockito.mock(HttpServletResponse.class);
    controllerRequest = Mockito.mock(ControllerRequest.class);
    when(controller.execute(Mockito.any())).thenReturn(controllerRequest);
  }

  @AfterAll
  static void afterAll() {
    System.out.println("Finish testing MainServletTest");
  }

}