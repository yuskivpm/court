package com.dsa.dao.entity;

import com.dsa.dao.services.DbPoolException;
import com.dsa.model.Role;
import com.dsa.model.User;

import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class UserDao extends AbstractEntityDao<User> {
  private static final String USER_TABLE_NAME = "USER";
  private static final String SQL_INSERT = "INSERT INTO " + USER_TABLE_NAME + " (LOGIN,PASSWORD,ROLE_ID,NAME,COURT_ID,IS_ACTIVE) " +
      "VALUES(?, ?, ?, ?, ?, ?)";
  private static final String SQL_UPDATE = "UPDATE " + USER_TABLE_NAME +
      " SET LOGIN = ?, PASSWORD = ?, ROLE_ID = ?,  NAME= ?,  COURT_ID= ?,  IS_ACTIVE= ?" +
      " WHERE ID = ?";
  public static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + USER_TABLE_NAME + "(" +
      "ID BIGINT AUTO_INCREMENT PRIMARY KEY, " +
      "LOGIN VARCHAR(255) NOT NULL, " +
      "PASSWORD VARCHAR(255) NOT NULL, " +
      "ROLE_ID VARCHAR(20) NOT NULL, " +
      "NAME VARCHAR(255) NOT NULL, " +
      "COURT_ID BIGINT DEFAULT NULL, " +
      "IS_ACTIVE BOOLEAN DEFAULT TRUE, " +
      "FOREIGN KEY (COURT_ID) REFERENCES COURT(ID) ON DELETE CASCADE" +
      ")";

  public UserDao() throws SQLException, DbPoolException {
    super(USER_TABLE_NAME, SQL_INSERT, SQL_UPDATE);
  }

  public UserDao(Connection connection) {
    super(connection, USER_TABLE_NAME, SQL_INSERT, SQL_UPDATE);
  }

  @Override
  protected User recordToEntity(@NotNull ResultSet resultSet) {
    User user = new User();
    try {
      user.setId(resultSet.getLong("ID"));
      user.setLogin(resultSet.getString("LOGIN"));
      user.setPassword(resultSet.getString("PASSWORD"));
      user.setRole(Role.valueOf(resultSet.getString("ROLE_ID")));
      user.setName(resultSet.getString("NAME"));
      user.setCourtId(resultSet.getLong("COURT_ID")); //      user.setCourt(new CourtDao(connection).readEntity(resultSet.getLong("COURT_ID")));
      user.setIsActive(resultSet.getBoolean("IS_ACTIVE"));
      return user;
    } catch (SQLException e) {
      log.error("SQLException in UserDao.recordToEntity: " + e);
      return null;
    }
  }

  @Override
  protected int setAllPreparedValues(@NotNull PreparedStatement preparedStatement, @NotNull User user, boolean isAddOperation) throws SQLException {
    int index = 0;
    preparedStatement.setString(++index, user.getLogin());
    preparedStatement.setString(++index, user.getPassword());
    preparedStatement.setString(++index, user.getRole().toString());
    preparedStatement.setString(++index, user.getName());
    setPreparedValueOrNull(preparedStatement, ++index, user.getCourtId());
    preparedStatement.setBoolean(++index, user.getIsActive());
    return ++index; // next index for preparedStatement.set_()
  }

  @Override
  public User loadAllSubEntities(User user) throws SQLException {
    if (user != null && user.getCourt() == null && user.getCourtId() > 0) {
      user.setCourt(new CourtDao(connection).readEntity(user.getCourtId()));
    }
    return user;
  }

}
