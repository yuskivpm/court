package com.dsa.service.command;

import com.dsa.controller.ControllerRequest;
import com.dsa.controller.LoginLogic;
import com.dsa.controller.ResponseType;
import com.dsa.dao.DbPoolException;
import com.dsa.domain.user.User;
import com.dsa.service.resource.ConfigManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MainPageCommandTest {

  private static final String PARAM_NAME_LOGIN = "login";
  private static final String PARAM_NAME_PASSWORD = "password";
  private static final String USER_SESSION_ID = "user_id";
  private static final String LOGIN_VALUE = "admin";
  private static final String PASSWORD_VALUE = "admin";

  private static MainPageCommand mainPageCommand;
  private static ControllerRequest request;

  @Test
  void apply_IncorrectSessionUser() {
    System.out.println("Start apply_IncorrectLogin");
    mainPageCommand.apply(request);
    verify(request).setResponseType(ResponseType.FORWARD);
    verify(request).setResponseValue(ConfigManager.getProperty("path.page.login"));
  }

  @Test
  void apply_CorrectSessionUser() {
    System.out.println("Start apply_CorrectLogin");
    when(request.getParameter(PARAM_NAME_LOGIN)).thenReturn(LOGIN_VALUE);
    when(request.getParameter(PARAM_NAME_PASSWORD)).thenReturn(PASSWORD_VALUE);
    when(request.getSessionAttribute(USER_SESSION_ID)).thenReturn("1");
    User user = LoginLogic.checkLogin(LOGIN_VALUE, PASSWORD_VALUE);
    assertNotNull(user);
    mainPageCommand.apply(request);
    verify(request).setResponseType(ResponseType.FORWARD);
    verify(request).setResponseValue(ConfigManager.getProperty("path.page.main." + user.getRole()));
    verify(request).setAttribute("curUser", user);
  }

  @Test
  void apply_WithUser() {
    System.out.println("Start apply_CorrectLogin");
    User user = LoginLogic.checkLogin(LOGIN_VALUE, PASSWORD_VALUE);
    assertNotNull(user);
    mainPageCommand.apply(request, user);
    verify(request).setResponseType(ResponseType.FORWARD);
    verify(request).setResponseValue(ConfigManager.getProperty("path.page.main." + user.getRole()));
    verify(request).setAttribute("curUser", user);
  }


  @BeforeAll
  static void beforeAll() {
    System.out.println("Start testing MainPageCommandTest");
    mainPageCommand = new MainPageCommand();
  }

  @BeforeEach
  void beforeEach() {
    System.out.println("BeforeEach MainPageCommandTest");
    request = Mockito.mock(ControllerRequest.class);
  }

  @AfterAll
  static void afterAll() {
    System.out.println("Finish testing MainPageCommandTest");
  }

}