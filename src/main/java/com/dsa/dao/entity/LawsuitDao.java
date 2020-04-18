package com.dsa.dao.entity;

import com.dsa.dao.services.DbPoolException;
import com.dsa.model.Lawsuit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

public class LawsuitDao  extends AbstractEntityDao<Lawsuit> {
  private static final String LAWSUIT_TABLE_NAME="LAWSUIT";
  private static final String SQL_INSERT="INSERT INTO "+LAWSUIT_TABLE_NAME+
      " (SUITOR_ID,DEFENDANT_ID,JURISDICTION_COURT_ID,JUDGE_ID,SUE_DATE,START_DATE,CLAIM_TEXT,DEFENDANT_TEXT,VERDICT_ID,"+
      "SUITOR_APPEAL_DATE,SUITOR_APPEAL_TEXT,DEFENDANT_APPEAL_DATE,DEFENDANT_APPEAL_TEXT,EXECUTION_DATE) "+
      "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
  private static final String SQL_UPDATE="UPDATE "+LAWSUIT_TABLE_NAME+
      " SET SUITOR_ID=?, DEFENDANT_ID=?, JURISDICTION_COURT_ID=?, JUDGE_ID=?, SUE_DATE=?, START_DATE=?, CLAIM_TEXT=?," +
      " DEFENDANT_TEXT=?, VERDICT_ID=?, SUITOR_APPEAL_DATE=?, SUITOR_APPEAL_TEXT=?, DEFENDANT_APPEAL_DATE=?," +
      " DEFENDANT_APPEAL_TEXT=?, EXECUTION_DATE=?" +
      " WHERE ID = ?";

  public static final String SQL_CREATE_TABLE="CREATE TABLE IF NOT EXISTS "+LAWSUIT_TABLE_NAME+" (" +
      "ID BIGINT AUTO_INCREMENT PRIMARY KEY, " +
      "SUITOR_ID BIGINT NOT NULL, " +
      "DEFENDANT_ID BIGINT NOT NULL, " +
      "JURISDICTION_COURT_ID BIGINT DEFAULT NULL, " +
      "JUDGE_ID BIGINT NOT NULL, "+
      "SUE_DATE DATE NOT NULL, "+
      "START_DATE DATE DEFAULT NULL, "+
      "CLAIM_TEXT VARCHAR(255) NOT NULL, " +
      "DEFENDANT_TEXT VARCHAR(255) DEFAULT NULL, " +
      "VERDICT_ID BIGINT DEFAULT NULL, " +
      "SUITOR_APPEAL_DATE DATE DEFAULT NULL, "+
      "SUITOR_APPEAL_TEXT VARCHAR(255) DEFAULT NULL, " +
      "DEFENDANT_APPEAL_DATE DATE DEFAULT NULL, "+
      "DEFENDANT_APPEAL_TEXT VARCHAR(255) DEFAULT NULL, " +
      "EXECUTION_DATE DATE DEFAULT NULL,"+
      "FOREIGN KEY (SUITOR_ID) REFERENCES USER(ID), " +
      "FOREIGN KEY (DEFENDANT_ID) REFERENCES USER(ID), " +
      "FOREIGN KEY (JURISDICTION_COURT_ID) REFERENCES COURT(ID), " +
      "FOREIGN KEY (JUDGE_ID) REFERENCES USER(ID), "+
      "FOREIGN KEY (VERDICT_ID) REFERENCES VERDICT(ID)" +
      ")";

  public LawsuitDao() throws SQLException, DbPoolException {
    super(LAWSUIT_TABLE_NAME, SQL_INSERT, SQL_UPDATE);
  }

  public LawsuitDao(Connection connection){
    super(connection,LAWSUIT_TABLE_NAME, SQL_INSERT, SQL_UPDATE);
  }

  @Override
  protected Lawsuit recordToEntity(ResultSet resultSet){
    Lawsuit lawsuit= new Lawsuit();
    try{
      lawsuit.setId(resultSet.getLong("ID"));
      lawsuit.setSuitor(new UserDao(connection).readEntity(resultSet.getLong("SUITOR_ID")));
      lawsuit.setDefendant(new UserDao(connection).readEntity(resultSet.getLong("DEFENDANT_ID")));
      lawsuit.setJurisdictionCourt(new CourtDao(connection).readEntity(resultSet.getLong("JURISDICTION_COURT_ID")));
      lawsuit.setJudge(new UserDao(connection).readEntity(resultSet.getLong("JUDGE_ID")));
      lawsuit.setSueDate(sqlDateToDate(resultSet.getDate("SUE_DATE")));
      lawsuit.setStartDate(sqlDateToDate(resultSet.getDate("START_DATE")));
      lawsuit.setClaimText(resultSet.getString("CLAIM_TEXT"));
      lawsuit.setDefendantText(resultSet.getString("DEFENDANT_TEXT"));
      lawsuit.setVerdict(new VerdictDao(connection).readEntity(resultSet.getLong("VERDICT_ID")));
      lawsuit.setSuitorAppealDate(sqlDateToDate(resultSet.getDate("SUITOR_APPEAL_DATE")));
      lawsuit.setSuitorAppealText(resultSet.getString("SUITOR_APPEAL_TEXT"));
      lawsuit.setDefendantAppealDate(sqlDateToDate(resultSet.getDate("DEFENDANT_APPEAL_DATE")));
      lawsuit.setDefendantAppealText(resultSet.getString("DEFENDANT_APPEAL_TEXT"));
      lawsuit.setExecutionDate(sqlDateToDate(resultSet.getDate("EXECUTION_DATE")));
      return lawsuit;
    }catch(SQLException e){
      log.error("SQLException in LawsuitDao.recordToEntity: "+e);
      return null;
    }
  }

  @Override
  protected int setAllPreparedValues(PreparedStatement preparedStatement, Lawsuit lawsuit, boolean isAddOperation) throws SQLException{
    setPreparedValueOrNull(preparedStatement,1, lawsuit.getSuitorId());
    setPreparedValueOrNull(preparedStatement,2, lawsuit.getDefendantId());
    setPreparedValueOrNull(preparedStatement,3, lawsuit.getJurisdictionCourtId());
    setPreparedValueOrNull(preparedStatement,4,lawsuit.getJudgeId());
    preparedStatement.setDate(5, dateToSqlDate(lawsuit.getSueDate()));
    preparedStatement.setDate(6, dateToSqlDate(lawsuit.getStartDate()));
    preparedStatement.setString(7, lawsuit.getClaimText());
    preparedStatement.setString(8, lawsuit.getDefendantText());
    setPreparedValueOrNull(preparedStatement,9,lawsuit.getVerdictId());
    preparedStatement.setDate(10, dateToSqlDate(lawsuit.getSuitorAppealDate()));
    preparedStatement.setString(11, lawsuit.getSuitorAppealText());
    preparedStatement.setDate(12, dateToSqlDate(lawsuit.getDefendantAppealDate()));
    preparedStatement.setString(13, lawsuit.getDefendantAppealText());
    preparedStatement.setDate(14, dateToSqlDate(lawsuit.getExecutionDate()));
    return 15; // next index for preparedStatement.set_()
  };

}
