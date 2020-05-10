package com.dsa.service.command;

import com.dsa.controller.ControllerRequest;
import com.dsa.controller.ControllerResponse;
import com.dsa.controller.LoginLogic;
import com.dsa.controller.ResponseType;
import com.dsa.dao.AbstractEntityDao;
import com.dsa.dao.DbPoolException;
import com.dsa.dao.service.DbPool;
import com.dsa.domain.user.User;
import com.dsa.service.resource.ConfigManager;
import com.dsa.service.resource.MessageManager;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginCommandTest {

  private static final String PARAM_NAME_LOGIN = "login";
  private static final String PARAM_NAME_PASSWORD = "password";
  private static final String USER_SESSION_ID = "user_id";
  private static final String ROLE_ID = "ROLE_ID";
  private static final String ROLE_VALUE = "JUDGE";

  private static LoginCommand loginCommand;
  private static ControllerRequest request;
  private static ControllerResponse response;
  private static ResultSet resultSet;

  @Test
  void getUserSessionId() {
    System.out.println("Start getUserSessionId");
    when(request.getSessionAttribute(USER_SESSION_ID)).thenReturn(null);
    assertEquals(LoginCommand.getUserSessionId(request), "");
    when(request.getSessionAttribute(USER_SESSION_ID)).thenReturn(USER_SESSION_ID);
    assertEquals(LoginCommand.getUserSessionId(request), USER_SESSION_ID);
  }

  @Test
  void getSessionUser() throws SQLException {
    System.out.println("Start getSessionUser");
    when(request.getSessionAttribute(USER_SESSION_ID)).thenReturn(null);
    assertNull(LoginCommand.getSessionUser(request));
    when(resultSet.next()).thenReturn(false);
    when(request.getSessionAttribute(USER_SESSION_ID)).thenReturn("-1");
    assertNull(LoginCommand.getSessionUser(request));
    when(resultSet.next()).thenReturn(true);
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
  void apply_CorrectLogin() throws SQLException {
    System.out.println("Start apply_CorrectLogin");
    final String LOGIN_VALUE = "admin";
    final String PASSWORD_VALUE = "admin";
    when(request.getParameter(PARAM_NAME_LOGIN)).thenReturn(LOGIN_VALUE);
    when(request.getParameter(PARAM_NAME_PASSWORD)).thenReturn(PASSWORD_VALUE);
    when(resultSet.next()).thenReturn(true);
    User user = LoginLogic.checkLogin(LOGIN_VALUE, PASSWORD_VALUE);
    assertNotNull(user);
    when(resultSet.next()).thenReturn(true).thenReturn(false);
    loginCommand.apply(request, response);
    verify(request).getParameter(PARAM_NAME_LOGIN);
    verify(request).getParameter(PARAM_NAME_PASSWORD);
    verify(response).setResponseType(ResponseType.FORWARD);
    verify(response).setAttribute("curUser", user);
    verify(response).setSessionAttribute(USER_SESSION_ID, user.getId());
    verify(response).setResponseValue(ConfigManager.getProperty("path.page.main." + user.getRole()));
  }

  @BeforeAll
  static void beforeAll() {
    System.out.println("Start testing LoginCommandTest");
    loginCommand = new LoginCommand();
  }

  @BeforeEach
  void beforeEach() throws SQLException, DbPoolException {
    System.out.println("BeforeEach LoginCommandTest");
    DbPool dbPool = Mockito.mock(DbPool.class);
    AbstractEntityDao.setDbPool(dbPool);
    Connection connection = Mockito.mock(Connection.class);
    when(dbPool.getConnection()).thenReturn(connection);
    PreparedStatement statement = Mockito.mock(PreparedStatement.class);
    when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
    when(connection.createStatement()).thenReturn(statement);
    resultSet = Mockito.mock(ResultSet.class);
    when(statement.executeQuery()).thenReturn(resultSet);
    when(statement.executeQuery(Mockito.anyString())).thenReturn(resultSet);
    when(resultSet.getString(ROLE_ID)).thenReturn(ROLE_VALUE);
    request = Mockito.mock(ControllerRequest.class);
    response = Mockito.mock(ControllerResponse.class);
  }

  @AfterAll
  static void afterAll() {
    System.out.println("Finish testing LoginCommandTest");
  }

}