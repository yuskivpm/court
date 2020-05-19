package com.dsa.domain.user;

import com.dsa.dao.AbstractEntityDao;
import com.dsa.dao.DbPoolException;
import com.dsa.domain.court.CourtDao;

import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import static com.dsa.domain.user.UserConst.*;

public class UserDao extends AbstractEntityDao<User> {

  private static final String SQL_INSERT = INSERT_TO + ENTITY_NAME + " (" + LOGIN + "," +
      PASSWORD + "," + ROLE + "," + NAME + "," + COURT_ID + "," + IS_ACTIVE + ") " +
      "VALUES(?, ?, ?, ?, ?, ?)";
  private static final String SQL_UPDATE = UPDATE + ENTITY_NAME +
      " SET " + LOGIN + " = ?, " + PASSWORD + " = ?, " + ROLE + " = ?,  " +
      NAME + "= ?,  " + COURT_ID + "= ?,  " + IS_ACTIVE + "= ?" +
      " WHERE " + ID + " = ?";
  public static final String SQL_CREATE_TABLE = CREATE_TABLE_IF_NOT_EXISTS + ENTITY_NAME + "(" +
      ID + PRIMARY_KEY + ", " +
      LOGIN + VARCHAR_255 + NOT_NULL + ", " +
      PASSWORD + VARCHAR_255 + NOT_NULL + ", " +
      ROLE + " VARCHAR(20) " + NOT_NULL + ", " +
      NAME + VARCHAR_255 + NOT_NULL + ", " +
      COURT_ID + " BIGINT " + DEFAULT_NULL + ", " +
      IS_ACTIVE + " BOOLEAN DEFAULT TRUE, " +
      FOREIGN_KEY + COURT_ID + ") REFERENCES COURT(" + ID + ") ON DELETE CASCADE" +
      ")";

  public UserDao() throws SQLException, DbPoolException {
    super(ENTITY_NAME, SQL_INSERT, SQL_UPDATE);
  }

  public UserDao(Connection connection) {
    super(connection, ENTITY_NAME, SQL_INSERT, SQL_UPDATE);
  }

  @Override
  protected User recordToEntity(@NotNull ResultSet resultSet) {
    User user = new User();
    try {
      user.setId(resultSet.getLong(ID));
      user.setLogin(resultSet.getString(LOGIN));
      user.setPassword(resultSet.getString(PASSWORD));
      user.setRole(Role.valueOf(resultSet.getString(ROLE)));
      user.setName(resultSet.getString(NAME));
      user.setCourtId(resultSet.getLong(COURT_ID));
      user.setIsActive(resultSet.getBoolean(IS_ACTIVE));
      return user;
    } catch (SQLException e) {
      LOG.error("SQLException in UserDao.recordToEntity: " + e);
      return null;
    }
  }

  @Override
  protected int setAllPreparedValues(
      @NotNull PreparedStatement preparedStatement,
      @NotNull User user,
      boolean isAddOperation
  ) throws SQLException {
    int index = 0;
    preparedStatement.setString(++index, user.getLogin());
    preparedStatement.setString(++index, user.getPassword());
    preparedStatement.setString(++index, user.getRole().toString());
    preparedStatement.setString(++index, user.getName());
    setPreparedValueOrNull(preparedStatement, ++index, user.getCourtId());
    preparedStatement.setBoolean(++index, user.getIsActive());
    return ++index;
  }

  @Override
  public User loadAllSubEntities(User user) throws SQLException {
    if (user != null && user.getCourt() == null && user.getCourtId() > 0) {
      user.setCourt(new CourtDao(connection).readEntity(user.getCourtId()));
    }
    return user;
  }

}
