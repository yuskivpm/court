package com.dsa.service.command;

import com.dsa.controller.ControllerRequest;
import com.dsa.controller.ControllerResponse;
import com.dsa.controller.ResponseType;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class RedirectCommandTest {

  private static final String UNKNOWN_PAGE = "FAIL";

  @Test
  void apply() {
    System.out.println("Start apply");
    RedirectCommand redirectCommand = new RedirectCommand();
    ControllerRequest request = Mockito.mock(ControllerRequest.class);
    ControllerResponse response = Mockito.mock(ControllerResponse.class);
    when(request.getParameter("page")).thenReturn(UNKNOWN_PAGE);
    redirectCommand.apply(request, response);
    verify(response).setResponseType(ResponseType.FORWARD);
    verify(response).setResponseValue(UNKNOWN_PAGE);
    when(request.getParameter("page")).thenReturn("main.ADMIN");
    redirectCommand.apply(request, response);
    verify(response, Mockito.times(2)).setResponseType(ResponseType.FORWARD);
    verify(response).setResponseValue("/jsp/mainAdmin.jsp");
  }

  @BeforeAll
  static void beforeAll() {
    System.out.println("Start testing RedirectCommandTest");
  }

  @AfterAll
  static void afterAll() {
    System.out.println("Finish testing RedirectCommandTest");
  }

}