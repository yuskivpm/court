package com.dsa.domain.user;

import com.dsa.domain.MyEntity;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserConstTest {

  private static final String ID = MyEntity.ID;
  private static final String ENTITY_NAME = "User";
  private static final String LOGIN = "login";
  private static final String PASSWORD = "password";
  private static final String ROLE = "role";
  private static final String NAME = "name";
  private static final String COURT_ID = "courtId";
  private static final String IS_ACTIVE = "isActive";
  private static final String PATH = "/users/";

  @Test
  void constantsTest() {
    System.out.println("Start constantsTest");
    assertEquals(ID, UserConst.ID);
    assertEquals(ENTITY_NAME, UserConst.ENTITY_NAME);
    assertEquals(LOGIN, UserConst.LOGIN);
    assertEquals(PASSWORD, UserConst.PASSWORD);
    assertEquals(ROLE, UserConst.ROLE);
    assertEquals(NAME, UserConst.NAME);
    assertEquals(COURT_ID, UserConst.COURT_ID);
    assertEquals(IS_ACTIVE, UserConst.IS_ACTIVE);
  }

  @Test
  void getPath_Test() {
    System.out.println("Start getPath_Test");
    assertEquals(PATH, UserConst.getPath());
  }

  @BeforeAll
  static void beforeAll() {
    System.out.println("Start testing UserConstTest");
  }

  @AfterAll
  static void afterAll() {
    System.out.println("Finish testing UserConstTest");
  }


}