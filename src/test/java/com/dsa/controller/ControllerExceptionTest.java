package com.dsa.controller;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerExceptionTest {

  @Test
  void exception_Invoke() {
    System.out.println("Start exception_Invoke");
    assertThrows(ControllerException.class, () -> {
      throw new ControllerException("Controller exception test");
    });
  }

  @Test
  void exception_InvokeThrowable() {
    System.out.println("Start exception_InvokeThrowable");
    assertThrows(ControllerException.class, () -> {
      throw new ControllerException("Controller exception test", new Exception("Cause"));
    });
  }

  @BeforeAll
  static void beforeAll() {
    System.out.println("Start testing ControllerExceptionTest");
  }

  @AfterAll
  static void afterAll() {
    System.out.println("Finish testing ControllerExceptionTest");
  }

}