package com.dsa.service.command;

import com.dsa.controller.ControllerRequest;
import com.dsa.controller.ControllerResponse;
import com.dsa.controller.ResponseType;
import com.dsa.service.resource.ConfigManager;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;

class LogoutCommandTest {

  @Test
  void apply() {
    System.out.println("Start apply");
    ControllerRequest request = Mockito.mock(ControllerRequest.class);
    ControllerResponse response = Mockito.mock(ControllerResponse.class);
    new LogoutCommand().apply(request, response);
    verify(response).setResponseType(ResponseType.INVALIDATE);
    verify(response).setResponseValue(ConfigManager.getProperty("path.page.index"));
  }

  @BeforeAll
  static void beforeAll() {
    System.out.println("Start testing LogoutCommandTest");
  }

  @AfterAll
  static void afterAll() {
    System.out.println("Finish testing LogoutCommandTest");
  }

}