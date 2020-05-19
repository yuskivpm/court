package com.dsa.service.command;

import com.dsa.controller.ControllerRequest;
import com.dsa.controller.LoginLogic;
import com.dsa.controller.ResponseType;
import com.dsa.dao.AbstractEntityDao;
import com.dsa.dao.DbPoolException;
import com.dsa.domain.user.Role;
import com.dsa.domain.user.User;
import com.dsa.service.resource.ConfigManager;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;

import static com.dsa.InitDbForTests.*;

import static com.dsa.domain.user.UserConst.ROLE;

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
  void apply_CorrectSessionUser() throws SQLException {
    System.out.println("Start apply_CorrectLogin");
    when(request.getParameter(PARAM_NAME_LOGIN)).thenReturn(LOGIN_VALUE);
    when(request.getParameter(PARAM_NAME_PASSWORD)).thenReturn(PASSWORD_VALUE);
    when(request.getSessionAttribute(USER_SESSION_ID)).thenReturn("1");
    when(resultSet.getString(ROLE)).thenReturn(Role.ATTORNEY.toString());
    when(resultSet.next()).thenReturn(true);
    User user = LoginLogic.checkLogin(LOGIN_VALUE, PASSWORD_VALUE);
    assertNotNull(user);
    when(resultSet.next()).thenReturn(true).thenReturn(false);
    mainPageCommand.apply(request);
    verify(request).setResponseType(ResponseType.FORWARD);
    verify(request).setResponseValue(ConfigManager.getProperty("path.page.main." + user.getRole()));
    verify(request).setAttribute("curUser", user);
  }

  @Test
  void apply_WithUser() throws SQLException {
    System.out.println("Start apply_CorrectLogin");
    when(request.getParameter(PARAM_NAME_LOGIN)).thenReturn(LOGIN_VALUE);
    when(request.getParameter(PARAM_NAME_PASSWORD)).thenReturn(PASSWORD_VALUE);
    when(request.getSessionAttribute(USER_SESSION_ID)).thenReturn("1");
    when(resultSet.getString(ROLE)).thenReturn(Role.ATTORNEY.toString());
    when(resultSet.next()).thenReturn(true);
    User user = LoginLogic.checkLogin(LOGIN_VALUE, PASSWORD_VALUE);
    assertNotNull(user);
    when(resultSet.next()).thenReturn(true).thenReturn(false);
    mainPageCommand.apply(request, user);
    verify(request).setResponseType(ResponseType.FORWARD);
    verify(request).setResponseValue(ConfigManager.getProperty("path.page.main." + user.getRole()));
    verify(request).setAttribute("curUser", user);
  }


  @BeforeAll
  static void beforeAll() throws SQLException, DbPoolException {
    System.out.println("Start testing MainPageCommandTest");
    initDbPool();
    AbstractEntityDao.setDbPool(dbPool);
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