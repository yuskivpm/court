package com.dsa.dao.entity;

import com.dsa.model.Court;
import com.dsa.model.CourtInstance;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CourtDao extends AbstractEntityDao<Court> {
  private static final String SQL_INSERT="INSERT INTO COURT (COURT_NAME,COURT_INSTANCE,MAIN_COURT_ID) VALUES(?, ?, ?)";
  public static final String SQL_CREATE_TABLE="CREATE TABLE IF NOT EXISTS COURT(" +
      "ID BIGINT AUTO_INCREMENT PRIMARY KEY, " +
      "COURT_NAME VARCHAR(255) NOT NULL, " +
      "COURT_INSTANCE VARCHAR(20) NOT NULL, " +
      "MAIN_COURT_ID BIGINT DEFAULT NULL," +
      "FOREIGN KEY (MAIN_COURT_ID) REFERENCES (ID) ON DELETE CASCADE" +
      ")";

  public CourtDao(Connection connection){
    super(connection,"COURT", SQL_INSERT);
  }

  @Override
  protected Court recordToEntity(ResultSet resultSet){
    Court court= new Court();
    try{
      court.setId(resultSet.getLong("ID"));
      court.setCourtName(resultSet.getString("COURT_NAME"));
      court.setCourtInstance(CourtInstance.valueOf(resultSet.getString("COURT_INSTANCE")));
      court.setMainCourt(getEntity(resultSet.getLong("MAIN_COURT_ID")));
      return court;
    }catch(SQLException e){
      log.error("SQLException in CourtDao.recordToEntity: "+e);
      return null;
    }
  }

  @Override
  protected void setAllPreparedValues(PreparedStatement preparedStatement, Court court) throws SQLException{
    preparedStatement.setString(1, court.getCourtName());
    preparedStatement.setString(2, court.getCourtInstance().toString());
    setPreparedValueOrNull(preparedStatement,3,court.getMainCourtId());
  };

  @Override
  public boolean update(Court entity){
//    TODO: update for Court
    throw new RuntimeException("Unready!!");
  };

  @Override
  public boolean remove(long id){
//    TODO: remove for Court
    throw new RuntimeException("Unready!!");
  };
}
