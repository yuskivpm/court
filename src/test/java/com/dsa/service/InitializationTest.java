package com.dsa.service;

import com.dsa.dao.DbPoolException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InitializationTest {

  @Test
  void initialize() throws DbPoolException {
    System.out.println("Start initialize");
    Initialization.initialize(true);
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