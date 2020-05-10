package com.dsa.dao.service;

import com.dsa.dao.DbPoolException;

import org.apache.log4j.Logger;
import org.h2.jdbcx.JdbcConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

public class DbPool implements IDbPool{

  private static final Logger LOG = Logger.getLogger(DbPool.class);
  private static JdbcConnectionPool connectionPool = null;

  public DbPool(String dbClassName, String url, String user, String password, int maxConnections) throws DbPoolException {
    LOG.trace("Init DbPool");
    if (connectionPool == null) {
      try {
        Class.forName(dbClassName);
      } catch (ClassNotFoundException e) {
        LOG.error("Init DbPool exception initialization db class(" + dbClassName + "): " + e);
        throw new DbPoolException("Fail init db class(" + dbClassName + ")");
      }
      connectionPool = JdbcConnectionPool.create(url, user, password);
      connectionPool.setMaxConnections(maxConnections);
    }
  }

  @Override
  public void closeDbPool() {
    LOG.trace("Close DbPool");
    if (connectionPool != null) {
      try {
        connectionPool.dispose();
        connectionPool = null;
      } catch (Exception e) {
        LOG.error("ClosePool. Exception connectionPool.dispose: " + e);
      }
    }
  }

  @Override
  public Connection getConnection() throws SQLException, DbPoolException {
    if (connectionPool == null) {
      throw new DbPoolException("DbPool isn't initialized");
    }
    return connectionPool.getConnection();
  }

}
