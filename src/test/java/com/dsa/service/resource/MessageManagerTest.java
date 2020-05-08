package com.dsa.service.resource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageManagerTest {

  @Test
  void getProperty() {
    System.out.println("Start getProperty");
    assertEquals(MessageManager.getProperty("message.loginError"), "Incorrect login or password");
    assertEquals(MessageManager.getProperty("message.nullPage"), "Page not found. Business logic error.");
  }

  @BeforeAll
  static void beforeAll() {
    System.out.println("Start testing MessageManagerTest");
  }

  @AfterAll
  static void afterAll() {
    System.out.println("Finish testing MessageManagerTest");
  }

}