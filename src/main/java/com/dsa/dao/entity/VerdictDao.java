package com.dsa.dao.entity;

import com.dsa.dao.services.DbPoolException;
import com.dsa.model.Court;
import com.dsa.model.Verdict;
import com.dsa.model.VerdictType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

public class VerdictDao extends AbstractEntityDao<Verdict> {
  private static final String VERDICT_TABLE_NAME="VERDICT";
  private static final String SQL_INSERT="INSERT INTO "+VERDICT_TABLE_NAME
      +" (LAWSUIT_ID,EFFECTIVE_DATE,VERDICT_TYPE_ID,VERDICT_TEXT,JURISDICTION_COURT_ID) VALUES(?, ?, ?, ?, ?)";
  private static final String SQL_UPDATE="UPDATE "+VERDICT_TABLE_NAME+
      " SET LAWSUIT_ID=?, EFFECTIVE_DATE=?, VERDICT_TYPE_ID=?, VERDICT_TEXT=?, JURISDICTION_COURT_ID=?" +
      " WHERE ID = ?";

  public static final String SQL_CREATE_TABLE="CREATE TABLE IF NOT EXISTS "+VERDICT_TABLE_NAME+" (" +
      "ID BIGINT AUTO_INCREMENT PRIMARY KEY, " +
      "LAWSUIT_ID BIGINT NOT NULL, " +
      "EFFECTIVE_DATE DATE NOT NULL, "+
      "VERDICT_TYPE_ID INT NOT NULL, " +
      "VERDICT_TEXT VARCHAR(255) NOT NULL, "+
      "JURISDICTION_COURT_ID BIGINT DEFAULT NULL, " +
      "FOREIGN KEY (JURISDICTION_COURT_ID) REFERENCES COURT(ID)" +
      ")";

  public VerdictDao() throws SQLException, DbPoolException {
    super(VERDICT_TABLE_NAME, SQL_INSERT, SQL_UPDATE);
  }

  public VerdictDao(Connection connection){
    super(connection,VERDICT_TABLE_NAME, SQL_INSERT, SQL_UPDATE);
  }

  @Override
  protected Verdict recordToEntity(ResultSet resultSet){
    Verdict verdict= new Verdict();
    try{
      verdict.setId(resultSet.getLong("ID"));
      verdict.setLawsuitId(resultSet.getLong("LAWSUIT_ID"));
      verdict.setEffectiveDate(sqlDateToDate(resultSet.getDate("EFFECTIVE_DATE")));
      verdict.setVerdictResult(VerdictType.valueOf(resultSet.getString("VERDICT_TYPE_ID")));
      verdict.setVerdictText(resultSet.getString("VERDICT_TEXT"));
      verdict.setJurisdictionCourt(new CourtDao(connection).readEntity(resultSet.getLong("JURISDICTION_COURT_ID")));
      return verdict;
    }catch(SQLException e){
      log.error("SQLException in VerdictDao.recordToEntity: "+e);
      return null;
    }
  }

  @Override
  protected int setAllPreparedValues(PreparedStatement preparedStatement, Verdict verdict, boolean isAddOperation) throws SQLException{
    setPreparedValueOrNull(preparedStatement,1, verdict.getLawsuitId());
    preparedStatement.setDate(2, dateToSqlDate(verdict.getEffectiveDate()));
    preparedStatement.setString(3, verdict.getVerdictResult().toString());
    preparedStatement.setString(4, verdict.getVerdictText());
    setPreparedValueOrNull(preparedStatement,5,verdict.getJurisdictionCourtId());
    return 6; // next index for preparedStatement.set_()
  };

}
