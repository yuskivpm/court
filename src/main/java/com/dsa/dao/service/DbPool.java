package com.dsa.dao.service;

import org.apache.log4j.Logger;
import org.h2.jdbcx.JdbcConnectionPool;
import org.jetbrains.annotations.Contract;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public final class DbPool {

  private static JdbcConnectionPool connectionPool = null;
  private static final Logger log = Logger.getLogger(DbPool.class);
  private static final int MAX_CONNECTIONS = 30;

  static {
    try {
      ResourceBundle resourceBundle = ResourceBundle.getBundle("database");
      String dbClassName = resourceBundle.getString("dbClassName");
      String url = resourceBundle.getString("url");
      String user = resourceBundle.getString("user");
      String password;
      try {
        password = resourceBundle.getString("password");
      } catch (Exception e) {
        password = "";
      }
      int maxConnections;
      try {
        maxConnections = Integer.parseInt(resourceBundle.getString("maxConnections"));
      } catch (Exception e) {
        maxConnections = MAX_CONNECTIONS;
      }
      if (!dbClassName.isEmpty() && !url.isEmpty()) {
        log.trace("Auto init DbPool");
        initializeDbPool(dbClassName, url, user, password, maxConnections);
      }
    } catch (Exception e) {
      log.error("Exception while static initialization of DbPool: " + e);
    }
  }

  @Contract(pure = true)
  private DbPool() {
  }

  public static void initializeDbPool(String dbClassName, String url, String user, String password, int maxConnections) {
    log.trace("Init DbPool");
    if (connectionPool == null) {
      try {
        Class.forName(dbClassName);
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
        log.error("Init DbPool exception initialization db class(" + dbClassName + "): " + e);
      }
      connectionPool = JdbcConnectionPool.create(url, user, password);
      connectionPool.setMaxConnections(maxConnections);
    }
  }

  public static void closeDbPool() {
    log.trace("Close DbPool");
    if (connectionPool != null) {
      try {
        connectionPool.dispose();
        connectionPool = null;
      } catch (Exception e) {
        log.error("ClosePool. Exception connectionPool.dispose: " + e);
      }
    }
  }

  public static Connection getConnection() throws SQLException, DbPoolException {
    if (connectionPool == null) {
      throw new DbPoolException("DbPool isn't initialized");
    }
    return connectionPool.getConnection();
  }

  @Override
  protected void finalize() {
    closeDbPool();
  }

}
