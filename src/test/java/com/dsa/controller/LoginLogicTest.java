package com.dsa.controller;

import com.dsa.dao.AbstractEntityDao;
import com.dsa.dao.DbPoolException;
import com.dsa.domain.user.Role;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static com.dsa.InitDbForTests.*;

import static com.dsa.domain.user.UserConst.ROLE;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginLogicTest {

  private static final String VALUE = "fail";

  @Test
  void checkLogin_EmptyLogin() throws SQLException {
    System.out.println("Start checkLogin_EmptyLogin");
    assertNull(LoginLogic.checkLogin("", ""));
    when(resultSet.next()).thenReturn(false);
    assertNull(LoginLogic.checkLogin(VALUE, VALUE));
  }

  @Test
  void checkLogin_CorrectLogin() throws SQLException {
    System.out.println("Start checkLogin_CorrectLogin");
    when(resultSet.next()).thenReturn(true);
    when(resultSet.getString(ROLE)).thenReturn(Role.ATTORNEY.toString());
    assertNotNull(LoginLogic.checkLogin(VALUE, VALUE));
  }

  @BeforeAll
  static void beforeAll() throws DbPoolException, SQLException {
    System.out.println("Start testing LoginLogicTest");
    initDbPool();
    AbstractEntityDao.setDbPool(dbPool);
  }

  @AfterAll
  static void afterAll() {
    System.out.println("Finish testing LoginLogicTest");
  }

}