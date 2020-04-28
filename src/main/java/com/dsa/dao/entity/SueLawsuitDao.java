package com.dsa.dao.entity;

import com.dsa.dao.services.DbPoolException;
import com.dsa.model.SueLawsuit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Date;
import java.util.List;

public class SueLawsuitDao  extends AbstractEntityDao<SueLawsuit> {
  private static final String LAWSUIT_TABLE_NAME="LAWSUIT";
  private static final String SQL_INSERT="INSERT INTO "+LAWSUIT_TABLE_NAME+
      " (SUE_DATE, COURT_ID, SUITOR_ID, CLAIM_TEXT, DEFENDANT_ID, DEFENDANT_TEXT, JUDGE_ID, START_DATE, VERDICT_DATE," +
      " VERDICT_TEXT, EXECUTION_DATE) "+
      "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

  private static final String SQL_UPDATE="UPDATE "+LAWSUIT_TABLE_NAME+
      " SET SUE_DATE = ?, COURT_ID = ?, SUITOR_ID = ?, CLAIM_TEXT = ?, DEFENDANT_ID = ?, DEFENDANT_TEXT = ?," +
      " JUDGE_ID = ?, START_DATE = ?, VERDICT_DATE = ?, VERDICT_TEXT = ?, EXECUTION_DATE = ?" +
      " WHERE ID = ?";

  public static final String SQL_CREATE_TABLE="CREATE TABLE IF NOT EXISTS "+LAWSUIT_TABLE_NAME+" (" +
      "ID BIGINT AUTO_INCREMENT PRIMARY KEY, " +
      "SUE_DATE DATE NOT NULL, "+
      "COURT_ID BIGINT NOT NULL, "+
      "SUITOR_ID BIGINT NOT NULL, " +
      "CLAIM_TEXT VARCHAR(255) NOT NULL, " +
      "DEFENDANT_ID BIGINT NOT NULL, " +
      "DEFENDANT_TEXT VARCHAR(255) DEFAULT NULL, " +
      "JUDGE_ID BIGINT DEFAULT NULL, "+
      "START_DATE DATE DEFAULT NULL, "+
      "VERDICT_DATE DATE DEFAULT NULL, "+
      "VERDICT_TEXT VARCHAR(255) DEFAULT NULL, "+
      "EXECUTION_DATE DATE DEFAULT NULL,"+
      "FOREIGN KEY (SUITOR_ID) REFERENCES USER(ID), " +
      "FOREIGN KEY (DEFENDANT_ID) REFERENCES USER(ID), " +
      "FOREIGN KEY (COURT_ID) REFERENCES COURT(ID), " +
      "FOREIGN KEY (JUDGE_ID) REFERENCES USER(ID) "+
      ")";

  public SueLawsuitDao() throws SQLException, DbPoolException {
    super(LAWSUIT_TABLE_NAME, SQL_INSERT, SQL_UPDATE);
  }

  public SueLawsuitDao(Connection connection){
    super(connection,LAWSUIT_TABLE_NAME, SQL_INSERT, SQL_UPDATE);
  }

  public List<SueLawsuit> readAllBySuitorId(long suitorId) throws SQLException{
    String[] where={"SUITOR_ID", Long.toString(suitorId)};
    return readAll(where);
  }

  public List<SueLawsuit> readAllByDefendantId(long defendantId) throws SQLException{
    String[] where={"DEFENDANT_ID", Long.toString(defendantId)};
    return readAll(where);
  }

  public List<SueLawsuit> readAllUnacceptedForCourtId(long courtId) throws SQLException{
    String[] where={"COURT_ID", Long.toString(courtId), "JUDGE_ID", null};
    return readAll(where);
  }

  public List<SueLawsuit> readAllForJudgeId(long judgeId) throws SQLException{
    String[] where={"JUDGE_ID", Long.toString(judgeId)};
    return readAll(where);
  }

  @Override
  protected SueLawsuit recordToEntity(ResultSet resultSet){
    SueLawsuit lawsuit= new SueLawsuit();
    try{
      lawsuit.setId(resultSet.getLong("ID"));
      lawsuit.setSueDate(sqlDateToDate(resultSet.getDate("SUE_DATE")));
      lawsuit.setCourtId(resultSet.getLong("COURT_ID"));
      lawsuit.setSuitorId(resultSet.getLong("SUITOR_ID"));
      lawsuit.setClaimText(resultSet.getString("CLAIM_TEXT"));
      lawsuit.setDefendantId(resultSet.getLong("DEFENDANT_ID"));
      lawsuit.setDefendantText(resultSet.getString("DEFENDANT_TEXT"));
      lawsuit.setJudgeId(resultSet.getLong("JUDGE_ID"));
      lawsuit.setStartDate(sqlDateToDate(resultSet.getDate("START_DATE")));
      lawsuit.setVerdictDate(sqlDateToDate(resultSet.getDate("VERDICT_DATE")));
      lawsuit.setVerdictText(resultSet.getString("VERDICT_TEXT"));
      lawsuit.setExecutionDate(sqlDateToDate(resultSet.getDate("EXECUTION_DATE")));
      return lawsuit;
    }catch(SQLException e){
      log.error("SQLException in LawsuitDao.recordToEntity: "+e);
      return null;
    }
  }

  @Override
  protected int setAllPreparedValues(PreparedStatement preparedStatement, SueLawsuit lawsuit, boolean isAddOperation) throws SQLException{
    int index=0;
    preparedStatement.setDate(++index, dateToSqlDate(lawsuit.getSueDate()));
    setPreparedValueOrNull(preparedStatement,++index, lawsuit.getCourtId());
    setPreparedValueOrNull(preparedStatement,++index, lawsuit.getSuitorId());
    preparedStatement.setString(++index, lawsuit.getClaimText());
    setPreparedValueOrNull(preparedStatement,++index, lawsuit.getDefendantId());
    preparedStatement.setString(++index, lawsuit.getDefendantText());
    setPreparedValueOrNull(preparedStatement,++index,lawsuit.getJudgeId());
    preparedStatement.setDate(++index, dateToSqlDate(lawsuit.getStartDate()));
    preparedStatement.setDate(++index, dateToSqlDate(lawsuit.getVerdictDate()));
    preparedStatement.setString(++index, lawsuit.getVerdictText());
    preparedStatement.setDate(++index, dateToSqlDate(lawsuit.getExecutionDate()));
    return ++index; // next index for preparedStatement.set_()
  };

  @Override
  public SueLawsuit loadAllSubEntities(SueLawsuit lawsuit) throws SQLException {
    if (lawsuit!=null) {
      if (lawsuit.getSuitor()==null && lawsuit.getSuitorId()>0) {
        lawsuit.setSuitor(new UserDao(connection).readEntity(lawsuit.getSuitorId()));
      }
      if (lawsuit.getDefendant()==null && lawsuit.getDefendantId()>0) {
        lawsuit.setDefendant(new UserDao(connection).readEntity(lawsuit.getDefendantId()));
      }
      if (lawsuit.getCourt()==null && lawsuit.getCourtId()>0) {
        lawsuit.setCourt(new CourtDao(connection).readEntity(lawsuit.getCourtId()));
      }
      if (lawsuit.getJudge()==null && lawsuit.getJudgeId()>0) {
        lawsuit.setJudge(new UserDao(connection).readEntity(lawsuit.getJudgeId()));
      }
    }
    return lawsuit;
  };

}
