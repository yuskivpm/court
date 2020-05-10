package com.dsa.dao.service;

import com.dsa.dao.DbPoolException;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDbPool {

  Connection getConnection() throws SQLException, DbPoolException;
  void closeDbPool();

}
