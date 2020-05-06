package com.dsa.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InitializationTest {

  @Test
  void initialize() throws ClassNotFoundException {
    System.out.println("Start initialize");
    Initialization.initialize();
    assertTrue(true);
  }

  @BeforeAll
  static void beforeAll() {
    System.out.println("Start testing InitializationTest");
  }

  @AfterAll
  static void afterAll() {
    System.out.println("Finish testing InitializationTest");
  }
}