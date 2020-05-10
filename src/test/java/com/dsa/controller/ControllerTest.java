package com.dsa.controller;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ControllerTest {

  public static final String COMMAND = "command";

  private static final String COMMIT_QUERY = "commit";

  @Test
  void execute() {
    System.out.println("Start execute");
    ControllerRequest mainRequest = Mockito.mock(ControllerRequest.class);
    when(mainRequest.getParameter(COMMIT_QUERY)).thenReturn("");
    when(mainRequest.getParameter(COMMAND)).thenReturn("incorrect");
    ControllerResponse controllerResponse = Controller.execute(mainRequest);
    assertEquals(controllerResponse.getResponseType(), ResponseType.REDIRECT);
    verify(mainRequest).getParameter(COMMIT_QUERY);
  }

  @Test
  void registerExecutor() {
    System.out.println("Start registerExecutor");
    Controller.registerExecutor("test/registerExecutor", ((controllerRequest, controllerResponse) -> controllerResponse));
    assertTrue(true);
  }

  @BeforeAll
  static void beforeAll() throws ClassNotFoundException {
    System.out.println("Start testing ControllerTest");
    Class.forName("com.dsa.dao.service.DbPool");
  }

  @AfterAll
  static void afterAll() {
    System.out.println("Finish testing ControllerTest");
  }

}