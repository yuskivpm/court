package com.dsa.controller;

import com.dsa.InitDbForTests;
import com.dsa.dao.DbPoolException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ControllerTest {

  private static final String COMMAND = "command";
  private static final String COMMIT_QUERY = "commit";

  @Test
  void execute() throws SQLException, DbPoolException {
    System.out.println("Start execute - incorrect command");
    InitDbForTests.initDbPool();
    Controller.setDbPool(InitDbForTests.dbPool);
    ControllerRequest mainRequest = Mockito.mock(ControllerRequest.class);
    when(mainRequest.getParameter(COMMIT_QUERY)).thenReturn("");
    when(mainRequest.getParameter(COMMAND)).thenReturn("incorrect");
    mainRequest = Controller.execute(mainRequest);
    verify(mainRequest).getParameter(COMMIT_QUERY);
    verify(InitDbForTests.dbPool).getConnection();
    verify(InitDbForTests.connection).setAutoCommit(false);
    verify(InitDbForTests.connection).commit();
    System.out.println("Start execute - check test/fail");
    ControllerRequest mainRequest1 = Mockito.mock(ControllerRequest.class);
    when(mainRequest1.getParameter(COMMAND)).thenReturn("test/fail");
    when(mainRequest1.getParameter(COMMIT_QUERY)).thenReturn("");
    when(mainRequest1.getResponseType()).thenReturn(ResponseType.FAIL);
    Controller.registerExecutor("test/fail", request -> {
      request.setResponseType(ResponseType.FAIL);
      return request;
    });
    Controller.execute(mainRequest1);
    verify(InitDbForTests.connection).rollback();
  }

  @Test
  void registerExecutor() {
    System.out.println("Start registerExecutor");
    Controller.registerExecutor("test/registerExecutor", controllerRequest -> controllerRequest);
    assertTrue(true);
  }

  @BeforeAll
  static void beforeAll() throws ClassNotFoundException {
    System.out.println("Start testing ControllerTest");
  }

  @AfterAll
  static void afterAll() {
    System.out.println("Finish testing ControllerTest");
  }

}