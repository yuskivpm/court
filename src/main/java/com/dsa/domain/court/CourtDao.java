package com.dsa.domain.court;

import com.dsa.dao.AbstractEntityDao;
import com.dsa.dao.DbPoolException;

import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static com.dsa.domain.court.CourtConst.*;

public class CourtDao extends AbstractEntityDao<Court> {

  private static final String SQL_INSERT = INSERT_TO + ENTITY_NAME +
      " (" + COURT_NAME + "," + COURT_INSTANCE + "," + MAIN_COURT_ID + ") VALUES(?, ?, ?)";

  private static final String SQL_UPDATE = UPDATE + ENTITY_NAME +
      " SET " + COURT_NAME + " = ?, " + COURT_INSTANCE + " = ?, " + MAIN_COURT_ID + " = ?" +
      " WHERE " + ID + " = ?";

  public static final String SQL_CREATE_TABLE = CREATE_TABLE_IF_NOT_EXISTS + ENTITY_NAME + " (" +
      ID + PRIMARY_KEY + ", " +
      COURT_NAME + VARCHAR_255 + NOT_NULL + ", " +
      COURT_INSTANCE + " VARCHAR(20) " + NOT_NULL + ", " +
      MAIN_COURT_ID + " BIGINT " + DEFAULT_NULL + "," +
      FOREIGN_KEY + MAIN_COURT_ID + ") REFERENCES (" + ID + ") ON DELETE CASCADE" +
      ")";

  public CourtDao() throws SQLException, DbPoolException {
    super(ENTITY_NAME, SQL_INSERT, SQL_UPDATE);
  }

  public CourtDao(Connection connection) {
    super(connection, ENTITY_NAME, SQL_INSERT, SQL_UPDATE);
  }

  @Override
  protected Court recordToEntity(@NotNull ResultSet resultSet) {
    Court court = new Court();
    try {
      court.setId(resultSet.getLong(ID));
      court.setCourtName(resultSet.getString(COURT_NAME));
      court.setCourtInstance(CourtInstance.valueOf(resultSet.getString(COURT_INSTANCE)));
      court.setMainCourtId(resultSet.getLong(MAIN_COURT_ID));
      return court;
    } catch (SQLException e) {
      LOG.error("SQLException in CourtDao.recordToEntity: " + e);
      return null;
    }
  }

  @Override
  protected int setAllPreparedValues(
      @NotNull PreparedStatement preparedStatement,
      @NotNull Court court,
      boolean isAddOperation
  ) throws SQLException {
    int index = 0;
    preparedStatement.setString(++index, court.getCourtName());
    preparedStatement.setString(++index, court.getCourtInstance().toString());
    setPreparedValueOrNull(preparedStatement, ++index, court.getMainCourtId());
    return ++index;
  }

  @Override
  public Court loadAllSubEntities(Court court) throws SQLException {
    if (court != null && court.getMainCourt() == null && court.getMainCourtId() > 0) {
      court.setMainCourt(readEntity(court.getMainCourtId()));
    }
    return court;
  }

}
