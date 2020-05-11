package com.dsa.dao;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DbPoolExceptionTest {

  @Test
  void exception_Invoke() {
    System.out.println("Start exception_Invoke");
    assertThrows(DbPoolException.class, () -> {
      throw new DbPoolException("DbPoolException test");
    });
  }

  @BeforeAll
  static void beforeAll() {
    System.out.println("Start testing DbPoolExceptionTest");
  }

  @AfterAll
  static void afterAll() {
    System.out.println("Finish testing DbPoolExceptionTest");
  }

}