package com.dsa.dao.service;

import com.dsa.dao.DbPoolException;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class DbPoolTest {

  private static ResourceBundle resourceBundle = ResourceBundle.getBundle("database");
  private static final String DB_CLASS_NAME = resourceBundle.getString("dbClassName");
  private static final int MAX_CONNECTIONS = 5;

  @NotNull
  @Contract(" -> new")
  private static DbPool initDbPool() throws DbPoolException {
    String url = resourceBundle.getString("url");
    String user = resourceBundle.getString("user");
    String password = resourceBundle.getString("password");
    return new DbPool(DB_CLASS_NAME, url, user, password, MAX_CONNECTIONS);
  }

  @Test
  void constructor_Test() throws DbPoolException {
    System.out.println("Start constructor_Test");
    assertThrows(DbPoolException.class, () -> new DbPool("", "", "", "", MAX_CONNECTIONS));
    DbPool dbPool = initDbPool();
    dbPool.closeDbPool();
  }

  @Test
  void getConnection() throws SQLException, DbPoolException {
    System.out.println("Start getConnection");
    DbPool dbPool = new DbPool(DB_CLASS_NAME, "", "", "", MAX_CONNECTIONS);
    dbPool.closeDbPool();
    assertThrows(DbPoolException.class, dbPool::getConnection);
    dbPool = new DbPool(DB_CLASS_NAME, "", "", "", MAX_CONNECTIONS);
    assertThrows(SQLException.class, dbPool::getConnection);
    dbPool.closeDbPool();
    dbPool = initDbPool();
    assertNotNull(dbPool.getConnection());
    dbPool.closeDbPool();
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