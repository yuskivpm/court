package com.dsa.dao.entity;

import com.dsa.dao.services.DbPoolException;
import com.dsa.model.Court;
import com.dsa.model.CourtInstance;

import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CourtDao extends AbstractEntityDao<Court> {
  private static final String COURT_TABLE_NAME = "COURT";
  private static final String SQL_INSERT = "INSERT INTO " + COURT_TABLE_NAME + " (COURT_NAME,COURT_INSTANCE,MAIN_COURT_ID) VALUES(?, ?, ?)";
  private static final String SQL_UPDATE = "UPDATE " + COURT_TABLE_NAME +
      " SET COURT_NAME = ?, COURT_INSTANCE = ?, MAIN_COURT_ID = ?" +
      " WHERE ID = ?";
  public static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + COURT_TABLE_NAME + " (" +
      "ID BIGINT AUTO_INCREMENT PRIMARY KEY, " +
      "COURT_NAME VARCHAR(255) NOT NULL, " +
      "COURT_INSTANCE VARCHAR(20) NOT NULL, " +
      "MAIN_COURT_ID BIGINT DEFAULT NULL," +
      "FOREIGN KEY (MAIN_COURT_ID) REFERENCES (ID) ON DELETE CASCADE" +
      ")";

  public CourtDao() throws SQLException, DbPoolException {
    super(COURT_TABLE_NAME, SQL_INSERT, SQL_UPDATE);
  }

  public CourtDao(Connection connection) {
    super(connection, COURT_TABLE_NAME, SQL_INSERT, SQL_UPDATE);
  }

  @Override
  protected Court recordToEntity(@NotNull ResultSet resultSet) {
    Court court = new Court();
    try {
      court.setId(resultSet.getLong("ID"));
      court.setCourtName(resultSet.getString("COURT_NAME"));
      court.setCourtInstance(CourtInstance.valueOf(resultSet.getString("COURT_INSTANCE")));
      court.setMainCourtId(resultSet.getLong("MAIN_COURT_ID")); //      court.setMainCourt(readEntity(resultSet.getLong("MAIN_COURT_ID")));
      return court;
    } catch (SQLException e) {
      log.error("SQLException in CourtDao.recordToEntity: " + e);
      return null;
    }
  }

  @Override
  protected int setAllPreparedValues(@NotNull PreparedStatement preparedStatement, @NotNull Court court, boolean isAddOperation) throws SQLException {
    int index = 0;
    preparedStatement.setString(++index, court.getCourtName());
    preparedStatement.setString(++index, court.getCourtInstance().toString());
    setPreparedValueOrNull(preparedStatement, ++index, court.getMainCourtId());
    return ++index; // next index for preparedStatement.set_()
  }

  @Override
  public Court loadAllSubEntities(Court court) throws SQLException {
    if (court != null && court.getMainCourt() == null && court.getMainCourtId() > 0) {
      court.setMainCourt(readEntity(court.getMainCourtId()));
    }
    return court;
  }

}
