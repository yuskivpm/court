package com.dsa.dao.service;

import com.dsa.dao.DbPoolException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DbPoolTest {

  @Test
  void getConnection() throws SQLException, DbPoolException {
    System.out.println("Start getConnection");
    assertNotNull(DbPool.getConnection());
  }

  @BeforeAll
  static void beforeAll() {
    System.out.println("Start testing DbPoolTest");
  }

  @AfterAll
  static void afterAll() {
    System.out.println("Finish testing DbPoolTest");
  }

}