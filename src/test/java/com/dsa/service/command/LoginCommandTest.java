package com.dsa.service.command;

import com.dsa.InitDbForTests;
import com.dsa.controller.ControllerRequest;
import com.dsa.controller.ControllerResponse;
import com.dsa.controller.LoginLogic;
import com.dsa.controller.ResponseType;
import com.dsa.domain.user.User;
import com.dsa.service.resource.ConfigManager;
import com.dsa.service.resource.MessageManager;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginCommandTest {

  private static final String PARAM_NAME_LOGIN = "login";
  private static final String PARAM_NAME_PASSWORD = "password";
  private static final String USER_SESSION_ID = "user_id";

  private static LoginCommand loginCommand;
  private static ControllerRequest request;
  private static ControllerResponse response;

  @Test
  void getUserSessionId() {
    System.out.println("Start getUserSessionId");
    when(request.getSessionAttribute(USER_SESSION_ID)).thenReturn(null);
    assertEquals(LoginCommand.getUserSessionId(request), "");
    when(request.getSessionAttribute(USER_SESSION_ID)).thenReturn(USER_SESSION_ID);
    assertEquals(LoginCommand.getUserSessionId(request), USER_SESSION_ID);
  }

  @Test
  void getSessionUser() {
    System.out.println("Start getSessionUser");
    when(request.getSessionAttribute(USER_SESSION_ID)).thenReturn(null);
    assertNull(LoginCommand.getSessionUser(request));
    when(request.getSessionAttribute(USER_SESSION_ID)).thenReturn("1");
    assertNotNull(LoginCommand.getSessionUser(request));
  }

  @Test
  void apply_IncorrectLogin() {
    System.out.println("Start apply_IncorrectLogin");
    when(request.getParameter(PARAM_NAME_LOGIN)).thenReturn("");
    when(request.getParameter(PARAM_NAME_PASSWORD)).thenReturn("");
    loginCommand.apply(request, response);
    verify(request).getParameter(PARAM_NAME_LOGIN);
    verify(request).getParameter(PARAM_NAME_PASSWORD);
    verify(response).setAttribute("errorFailLoginPassMessage", MessageManager.getProperty("message.loginError"));
    verify(response).setResponseType(ResponseType.FORWARD);
    verify(response).setResponseValue(ConfigManager.getProperty("path.page.login"));
  }

  @Test
  void apply_CorrectLogin() {
    System.out.println("Start apply_CorrectLogin");
    final String LOGIN_VALUE = "admin";
    final String PASSWORD_VALUE = "admin";
    when(request.getParameter(PARAM_NAME_LOGIN)).thenReturn(LOGIN_VALUE);
    when(request.getParameter(PARAM_NAME_PASSWORD)).thenReturn(PASSWORD_VALUE);
    User user = LoginLogic.checkLogin(LOGIN_VALUE, PASSWORD_VALUE);
    assertNotNull(user);
    loginCommand.apply(request, response);
    verify(request).getParameter(PARAM_NAME_LOGIN);
    verify(request).getParameter(PARAM_NAME_PASSWORD);
    verify(response).setResponseType(ResponseType.FORWARD);
    verify(response).setAttribute("curUser", user);
    verify(response).setSessionAttribute(USER_SESSION_ID, user.getId());
    verify(response).setResponseValue(ConfigManager.getProperty("path.page.main." + user.getRole()));
  }

  @BeforeAll
  static void beforeAll() throws ClassNotFoundException {
    System.out.println("Start testing LoginCommandTest");
    InitDbForTests.initializeDb();
    loginCommand = new LoginCommand();
  }

  @BeforeEach
  void beforeEach() {
    System.out.println("BeforeEach LoginCommandTest");
    request = Mockito.mock(ControllerRequest.class);
    response = Mockito.mock(ControllerResponse.class);
  }

  @AfterAll
  static void afterAll() {
    System.out.println("Finish testing LoginCommandTest");
  }

}