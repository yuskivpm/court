package com.dsa.service.resource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfigManagerTest {

  @Test
  void getProperty() {
    System.out.println("Start getProperty");
    assertEquals(ConfigManager.getProperty("path.page.main.ADMIN"), "/jsp/mainAdmin.jsp");
    assertEquals(ConfigManager.getProperty("path.page.main.JUDGE"), "/jsp/mainJudge.jsp");
    assertEquals(ConfigManager.getProperty("path.page.main.ATTORNEY"), "/jsp/mainAttorney.jsp");
    assertEquals(ConfigManager.getProperty("path.page.index"), "/index.jsp");
    assertEquals(ConfigManager.getProperty("path.page.login"), "/jsp/login.jsp");
  }

  @BeforeAll
  static void beforeAll() {
    System.out.println("Start testing ConfigManagerTest");
  }

  @AfterAll
  static void afterAll() {
    System.out.println("Finish testing ConfigManagerTest");
  }

}