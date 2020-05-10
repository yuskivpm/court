package com.dsa.domain.court;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourtInstanceTest {

  private static final String[] ENUM_LIST = {"LOCAL", "APPEAL", "CASSATION"};

  @Test
  void test_EnumList() {
    System.out.println("Start test_EnumList");
    for (int i = 0; i < ENUM_LIST.length; i++) {
      assertEquals(i + 1, CourtInstance.valueOf(ENUM_LIST[i]).getInstanceLevel());
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