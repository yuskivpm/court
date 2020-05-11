package com.dsa.controller;

import com.dsa.InitDbForTests;
import com.dsa.dao.DbPoolException;
import com.dsa.dao.service.DbPool;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ControllerTest {

  private static final String COMMAND = "command";
  private static final String COMMIT_QUERY = "commit";

  @Test
  void execute() throws SQLException, DbPoolException {
    System.out.println("Start execute");
    InitDbForTests.initDbPool();
    Controller.setDbPool(InitDbForTests.dbPool);
    ControllerRequest mainRequest = Mockito.mock(ControllerRequest.class);
    when(mainRequest.getParameter(COMMIT_QUERY)).thenReturn("");
    when(mainRequest.getParameter(COMMAND)).thenReturn("incorrect");
    ControllerResponse controllerResponse = Controller.execute(mainRequest);
    assertEquals(controllerResponse.getResponseType(), ResponseType.REDIRECT);
    verify(mainRequest).getParameter(COMMIT_QUERY);
    verify(InitDbForTests.dbPool).getConnection();
    verify(InitDbForTests.connection).setAutoCommit(false);
    verify(InitDbForTests.connection).commit();
    when(mainRequest.getParameter(COMMAND)).thenReturn("test/fail");
    Controller.registerExecutor("test/fail", ((request, response) -> {
      response.setResponseType(ResponseType.FAIL);
      return response;
    }));
    Controller.execute(mainRequest);
    verify(InitDbForTests.connection).rollback();
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
  }

  @AfterAll
  static void afterAll() {
    System.out.println("Finish testing ControllerTest");
  }

}