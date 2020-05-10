package com.dsa.service.crud;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CrudEnumTest {

  private static final String[] ENUM_LIST = {"CREATE", "READ", "READ_ALL", "UPDATE", "DELETE", "PREPARE_UPDATE_FORM", "WRONG", "UNKNOWN"};

  @Test
  void test_EnumList() {
    System.out.println("Start test_EnumList");
    for (String enumValue : ENUM_LIST) {
      assertNotNull(CrudEnum.valueOf(enumValue));
    }
  }

  @BeforeAll
  static void beforeAll() {
    System.out.println("Start testing CrudEnumTest");
  }

  @AfterAll
  static void afterAll() {
    System.out.println("Finish testing CrudEnumTest");
  }

}