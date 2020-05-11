package com.dsa;

import com.dsa.dao.DbPoolException;
import com.dsa.dao.service.DbPool;

import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class InitDbForTests {

  public static DbPool dbPool;
  public static Connection connection;
  public static PreparedStatement preparedStatement;
  public static Statement statement;
  public static ResultSet resultSet;

  public static void createResultSet() throws SQLException {
    resultSet = Mockito.mock(ResultSet.class);
    when(preparedStatement.executeQuery()).thenReturn(resultSet);
    when(statement.executeQuery(Mockito.anyString())).thenReturn(resultSet);
  }

  public static void createPreparedStatement() throws SQLException {
    preparedStatement = Mockito.mock(PreparedStatement.class);
    when(connection.prepareStatement(Mockito.anyString())).thenReturn(preparedStatement);
    statement = Mockito.mock(Statement.class);
    when(connection.createStatement()).thenReturn(statement);
    createResultSet();
  }

  public static void createConnection() throws SQLException, DbPoolException {
    connection = Mockito.mock(Connection.class);
    when(dbPool.getConnection()).thenReturn(connection);
    createPreparedStatement();
  }

  public static void initDbPool() throws SQLException, DbPoolException {
    dbPool = Mockito.mock(DbPool.class);
    createConnection();
  }

}
