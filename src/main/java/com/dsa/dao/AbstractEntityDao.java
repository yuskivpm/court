package com.dsa.dao;

import com.dsa.dao.service.IDbPool;
import com.dsa.domain.MyEntity;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractEntityDao<E extends MyEntity> implements AutoCloseable {

  protected static final Logger LOG = Logger.getLogger(AbstractEntityDao.class);
  protected static final String ID = MyEntity.ID;
  protected static final String NOT_NULL = "NOT NULL";
  protected static final String DEFAULT_NULL = "DEFAULT NULL";
  protected static final String VARCHAR_255 = " VARCHAR(255) ";
  protected static final String INSERT_TO = "INSERT INTO ";
  protected static final String UPDATE = "UPDATE ";
  protected static final String CREATE_TABLE_IF_NOT_EXISTS = "CREATE TABLE IF NOT EXISTS ";
  protected static final String PRIMARY_KEY = " BIGINT AUTO_INCREMENT PRIMARY KEY";
  protected static final String FOREIGN_KEY = "FOREIGN KEY (";

  private static final String SQL_SELECT_ALL = "SELECT * FROM ";
  private static final String SQL_SELECT_BY_MAP = "SELECT * FROM %s WHERE";
  private static final String SQL_DELETE_BY_ID = "DELETE FROM %s WHERE " + ID + "=?";

  private static IDbPool dbPool;

  private final String TABLE_NAME;
  private final String SQL_INSERT;
  private final String SQL_UPDATE_BY_ID;

  protected final Connection connection;

  public AbstractEntityDao(
      String entityTableName,
      String sqlInsert,
      String sqlUpdate
  ) throws DbPoolException, SQLException {
    this(dbPool.getConnection(), entityTableName, sqlInsert, sqlUpdate);
  }

  @Contract(pure = true)
  public AbstractEntityDao(@NotNull Connection connection, String entityTableName, String sqlInsert, String sqlUpdate) {
    this.connection = connection;
    this.TABLE_NAME = entityTableName;
    this.SQL_INSERT = sqlInsert;
    this.SQL_UPDATE_BY_ID = sqlUpdate;
  }

  public boolean createEntity(E entity) {
    boolean result = false;
    try (PreparedStatement st = connection.prepareStatement(SQL_INSERT)) {
      setAllPreparedValues(st, entity, true);
      result = st.executeUpdate() > 0;
      LOG.info("Create entity (" + entity + ")");
    } catch (SQLException e) {
      LOG.error("Fail create entity(" + entity + "): " + e);
    }
    return result;
  }

  public List<E> readAll() throws SQLException {
    return readAll(null);
  }

  public List<E> readAll(String[] whereArray) throws SQLException {
    String selectString = prepareSqlRequestByMap(whereArray);
    List<E> entities = new ArrayList<>();
    try (
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(selectString)
    ) {
      while (rs.next()) {
        E entity = recordToEntity(rs);
        if (entity != null) {
          entities.add(entity);
        }
      }
    } catch (SQLException e) {
      LOG.error("Fail read all entities: " + e);
    }
    return entities;
  }

  public E readEntity(long id) throws SQLException {
    return readEntity(ID, Long.toString(id));
  }

  public E readEntity(String fieldName, String value) throws SQLException {
    String[] where = {fieldName, value};
    return readEntity(where);
  }

  public E readEntity(@NotNull String[] whereArray) throws SQLException {
    String selectString = prepareSqlRequestByMap(whereArray);
    E entity = null;
    try (
        PreparedStatement st = connection.prepareStatement(selectString);
        ResultSet rs = st.executeQuery()
    ) {
      if (rs.next()) {
        entity = recordToEntity(rs);
      }
    } catch (SQLException e) {
      LOG.error("SQLException in EntityDao.readEntity(" + Arrays.toString(whereArray) + "): " + e);
    }
    return entity;
  }

  public boolean updateEntity(E entity) {
    boolean result = false;
    try (PreparedStatement st = connection.prepareStatement(SQL_UPDATE_BY_ID)) {
      st.setLong(setAllPreparedValues(st, entity, false), entity.getId());
      result = st.executeUpdate() > 0;
      LOG.info("Update entity (" + entity + ")");
    } catch (SQLException e) {
      LOG.error("Fail updateEntity(" + entity + "): " + e);
    }
    return result;
  }

  public boolean deleteEntity(String id) {
    boolean result = false;
    try (PreparedStatement st = connection.prepareStatement(String.format(SQL_DELETE_BY_ID, TABLE_NAME))) {
      st.setString(1, id);
      result = st.executeUpdate() > 0;
      LOG.info("Delete entity (" + id + ")");
    } catch (SQLException e) {
      LOG.error("deleteEntity(" + id + ") fail: " + e);
    }
    return result;
  }

  protected void setPreparedValueOrNull(
      PreparedStatement preparedStatement,
      int parameterIndex,
      long value
  ) throws SQLException {
    if (value == 0) {
      preparedStatement.setNull(parameterIndex, 0);
    } else {
      preparedStatement.setLong(parameterIndex, value);
    }
  }

  protected abstract int setAllPreparedValues(
      PreparedStatement preparedStatement,
      E entity,
      boolean isAddOperation
  ) throws SQLException;

  protected abstract E recordToEntity(ResultSet resultSet);

  public abstract E loadAllSubEntities(E entity) throws SQLException;

  public static void sqlExecute(@NotNull Statement st, String sqlQuery, boolean autoClose, Logger log) {
    try {
      st.execute(sqlQuery);
    } catch (SQLException e) {
      log.error("SQLException in sqlExecute[" + sqlQuery + "]: " + e);
    } finally {
      if (autoClose) {
        try {
          st.close();
        } catch (SQLException e) {
          log.error("SQLException in sqlExecute.onAutoCloseStatement[" + sqlQuery + "]: " + e);
        }
      }
    }
  }

  @Contract("!null -> new; null -> null")
  protected static java.util.Date sqlDateToDate(java.sql.Date sqlDate) {
    return sqlDate != null ? new java.util.Date(sqlDate.getTime()) : null;
  }

  @Contract("!null -> new; null -> null")
  protected static java.sql.Date dateToSqlDate(java.util.Date date) {
    return date != null ? new java.sql.Date(date.getTime()) : null;
  }

  @NotNull
  @Contract(pure = true)
  private String getWhereParamAsString(String param) {
    return param == null ? " is null" : "='" + param + "'";
  }

  @NotNull
  @Contract("null -> !null")
  private String prepareSqlRequestByMap(String[] whereArray) throws SQLException {
    if (whereArray == null || whereArray.length == 0) {
      return SQL_SELECT_ALL + TABLE_NAME;
    }
    if ((whereArray.length & 1) == 1) {
      LOG.error("Incorrect whereArray length: " + Arrays.toString(whereArray));
      throw new SQLException("Incorrect whereArray length: " + whereArray.length);
    }
    StringBuilder selectString = new StringBuilder(String.format(SQL_SELECT_BY_MAP, TABLE_NAME));
    for (int i = 0; i < whereArray.length; ) {
      selectString
          .append(" ")
          .append(whereArray[i++])
          .append(getWhereParamAsString(whereArray[i++]))
          .append(i < whereArray.length ? " and" : "");
    }
    return selectString.toString();
  }

  @Override
  public void close() throws SQLException {
    if (connection != null) {
      connection.close();
    }
  }

  public static void setDbPool(IDbPool dbPool){
    AbstractEntityDao.dbPool = dbPool;
  }

}
