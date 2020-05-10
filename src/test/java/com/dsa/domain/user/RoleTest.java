package com.dsa.domain.user;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

  private static final String[] ENUM_LIST = {"ADMIN", "JUDGE", "ATTORNEY"};

  @Test
  void test_EnumList() {
    System.out.println("Start test_EnumList");
    for (String enumValue : ENUM_LIST) {
      assertNotNull(Role.valueOf(enumValue));
    }
  }

  @BeforeAll
  static void beforeAll() {
    System.out.println("Start testing RoleTest");
  }

  @AfterAll
  static void afterAll() {
    System.out.println("Finish testing RoleTest");
  }

}