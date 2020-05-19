package com.dsa.domain.user;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.dsa.domain.user.UserConst.*;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityTest {

  private static UserEntity userEntity;

  @Test
  void getName() {
    System.out.println("Start getName");
    userEntity.setName("");
    assertEquals("", userEntity.getName());
    userEntity.setName(NAME);
    assertEquals(NAME, userEntity.getName());
  }

  @Test
  void getLogin() {
    System.out.println("Start getLogin");
    userEntity.setLogin("");
    assertEquals("", userEntity.getLogin());
    userEntity.setLogin(LOGIN);
    assertEquals(LOGIN, userEntity.getLogin());
  }

  @Test
  void getPassword() {
    System.out.println("Start getPassword");
    userEntity.setPassword("");
    assertEquals("", userEntity.getPassword());
    userEntity.setPassword(PASSWORD);
    assertEquals(PASSWORD, userEntity.getPassword());
  }

  @Test
  void getRole() {
    System.out.println("Start getRole");
    userEntity.setRole(Role.ATTORNEY);
    assertEquals(Role.ATTORNEY, userEntity.getRole());
    userEntity.setRole(Role.ADMIN);
    assertEquals(Role.ADMIN, userEntity.getRole());
  }

  @Test
  void getCourtId() {
    System.out.println("Start getCourtId");
    userEntity.setRole(Role.ATTORNEY);
    userEntity.setCourtId(1);
    assertEquals(0, userEntity.getCourtId());
    userEntity.setRole(Role.ADMIN);
    userEntity.setCourtId(1);
    assertEquals(0, userEntity.getCourtId());
    userEntity.setRole(Role.JUDGE);
    userEntity.setCourtId(1);
    assertEquals(1, userEntity.getCourtId());
  }

  @Test
  void getIsActive() {
    System.out.println("Start getIsActive");
    userEntity.setIsActive(false);
    assertFalse(userEntity.getIsActive());
    userEntity.setIsActive(true);
    assertTrue(userEntity.getIsActive());
  }

  @Test
  void testHashCode() {
    System.out.println("Start testHashCode");
    assertNotEquals(0, userEntity.hashCode());
  }

  @Test
  void testToString() {
    System.out.println("Start testToString");
    assertNotEquals("", userEntity.toString());
  }

  @BeforeAll
  static void beforeAll() {
    System.out.println("Start testing UserEntityTest");
    userEntity = new UserEntity();
  }

  @AfterAll
  static void afterAll() {
    System.out.println("Finish testing UserEntityTest");
  }

}