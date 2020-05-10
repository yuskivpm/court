package com.dsa.controller;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResponseTypeTest {

  private static final String[] ENUM_LIST = {"FORWARD", "REDIRECT", "INVALIDATE", "PLAIN_TEXT", "FAIL"};

  @Test
  void test_EnumList() {
    System.out.println("Start test_EnumList");
    for (String enumValue : ENUM_LIST) {
      assertNotNull(ResponseType.valueOf(enumValue));
    }
  }

  @BeforeAll
  static void beforeAll() {
    System.out.println("Start testing ResponseTypeTest");
  }

  @AfterAll
  static void afterAll() {
    System.out.println("Finish testing ResponseTypeTest");
  }

}