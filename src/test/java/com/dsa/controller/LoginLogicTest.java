package com.dsa.controller;

import com.dsa.InitDbForTests;

import com.dsa.dao.AbstractEntityDao;
import com.dsa.dao.DbPoolException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginLogicTest {

  @Test
  void checkLogin_EmptyLogin() {
    System.out.println("Start checkLogin_EmptyLogin");
    assertNull(LoginLogic.checkLogin("", ""));
    assertNull(LoginLogic.checkLogin("fail", "fail"));
  }

  @Test
  void checkLogin_CorrectLogin() {
    System.out.println("Start checkLogin_CorrectLogin");
    assertNotNull(LoginLogic.checkLogin("admin", "admin"));
  }

  @BeforeAll
  static void beforeAll() throws DbPoolException {
    System.out.println("Start testing LoginLogicTest");
    InitDbForTests.initializeDb(true);
  }

  @AfterAll
  static void afterAll() {
    System.out.println("Finish testing LoginLogicTest");
  }

}