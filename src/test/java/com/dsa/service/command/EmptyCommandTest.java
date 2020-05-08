package com.dsa.service.command;

import com.dsa.controller.ControllerResponse;
import com.dsa.controller.ResponseType;
import com.dsa.service.resource.ConfigManager;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;

class EmptyCommandTest {

  @Test
  void apply() {
    System.out.println("Start apply");
    ControllerResponse response = Mockito.mock(ControllerResponse.class);
    new EmptyCommand().apply(null, response);
    verify(response).setResponseType(ResponseType.FORWARD);
    verify(response).setResponseValue(ConfigManager.getProperty("path.page.login"));
  }

  @BeforeAll
  static void beforeAll() {
    System.out.println("Start testing EmptyCommandTest");
  }

  @AfterAll
  static void afterAll() {
    System.out.println("Finish testing EmptyCommandTest");
  }

}