package com.dsa.dao.entity;

import com.dsa.dao.services.DbPoolException;
import com.dsa.model.Sue;

import java.sql.*;

public class SueDao  extends AbstractEntityDao<Sue> {
  private static final String SUE_TABLE_NAME="SUE";
  private static final String SQL_INSERT="INSERT INTO "+SUE_TABLE_NAME+
      " (SUITOR_ID,DEFENDANT_ID,COURT_ID,SUE_DATE,CLAIM_TEXT) VALUES(?, ?, ?, ?, ?)";
  private static final String SQL_UPDATE="UPDATE "+SUE_TABLE_NAME+
      " SET SUITOR_ID=?, DEFENDANT_ID=?, COURT_ID=?, SUE_DATE=?, CLAIM_TEXT=?" +
      " WHERE ID = ?";

  public static final String SQL_CREATE_TABLE="CREATE TABLE IF NOT EXISTS "+SUE_TABLE_NAME+" (" +
      "ID BIGINT AUTO_INCREMENT PRIMARY KEY, " +
      "SUITOR_ID BIGINT NOT NULL, " +
      "DEFENDANT_ID BIGINT NOT NULL, " +
      "COURT_ID BIGINT NOT NULL, " +
      "SUE_DATE DATE NOT NULL, "+
      "CLAIM_TEXT VARCHAR NOT NULL, " +
      "FOREIGN KEY (SUITOR_ID) REFERENCES USER(ID), " +
      "FOREIGN KEY (DEFENDANT_ID) REFERENCES USER(ID), " +
      "FOREIGN KEY (COURT_ID) REFERENCES COURT(ID)" +
      ")";

  public SueDao() throws SQLException, DbPoolException {
    super(SUE_TABLE_NAME, SQL_INSERT, SQL_UPDATE);
  }

  public SueDao(Connection connection){
    super(connection,SUE_TABLE_NAME, SQL_INSERT, SQL_UPDATE);
  }

  @Override
  protected Sue recordToEntity(ResultSet resultSet){
    Sue sue= new Sue();
    try{
      sue.setId(resultSet.getLong("ID"));
      sue.setSuitorId(resultSet.getLong("SUITOR_ID")); // sue.setSuitor(new UserDao(connection).readEntity(resultSet.getLong("SUITOR_ID")));
      sue.setDefendantId(resultSet.getLong("DEFENDANT_ID")); // sue.setDefendant(new UserDao(connection).readEntity(resultSet.getLong("DEFENDANT_ID")));
      sue.setCourtId(resultSet.getLong("COURT_ID")); // sue.setCourt(new CourtDao(connection).readEntity(resultSet.getLong("COURT_ID")));
      sue.setSueDate(sqlDateToDate(resultSet.getDate("SUE_DATE")));
      sue.setClaimText(resultSet.getString("CLAIM_TEXT"));
      return sue;
    }catch(SQLException e){
      log.error("SQLException in SueDao.recordToEntity: "+e);
      return null;
    }
  }

  @Override
  protected int setAllPreparedValues(PreparedStatement preparedStatement, Sue sue, boolean isAddOperation) throws SQLException{
    setPreparedValueOrNull(preparedStatement,1, sue.getSuitorId());
    setPreparedValueOrNull(preparedStatement,2, sue.getDefendantId());
    setPreparedValueOrNull(preparedStatement,3, sue.getCourtId());
    preparedStatement.setDate(4, dateToSqlDate(sue.getSueDate()));
    preparedStatement.setString(5, sue.getClaimText());
    return 6; // next index for preparedStatement.set_()
  };

  @Override
  public Sue loadAllSubEntities(Sue sue) throws SQLException {
    if (sue!=null) {
      if (sue.getSuitor()==null && sue.getSuitorId()>0) {
        sue.setSuitor(new UserDao(connection).readEntity(sue.getSuitorId()));
      }
      if (sue.getDefendant()==null && sue.getDefendantId()>0) {
        sue.setDefendant(new UserDao(connection).readEntity(sue.getDefendantId()));
      }
      if (sue.getCourt()==null && sue.getCourtId()>0) {
        sue.setCourt(new CourtDao(connection).readEntity(sue.getCourtId()));
      }
    }
    return sue;
  };

}
