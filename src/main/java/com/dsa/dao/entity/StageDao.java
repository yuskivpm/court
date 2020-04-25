package com.dsa.dao.entity;

import com.dsa.dao.services.DbPoolException;
import com.dsa.model.Stage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

public class StageDao  extends AbstractEntityDao<Stage> {
  private static final String STAGE_TABLE_NAME="STAGE";
  private static final String SQL_INSERT="INSERT INTO "+STAGE_TABLE_NAME+
      " (LAWSUIT_ID,JUDGE_ID,SUE_DATE,START_DATE,CLAIM_TEXT,DEFENDANT_TEXT,VERDICT_ID,SUITOR_APPEAL_DATE,"+
      "SUITOR_APPEAL_TEXT,DEFENDANT_APPEAL_DATE,DEFENDANT_APPEAL_TEXT) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
  private static final String SQL_UPDATE="UPDATE "+STAGE_TABLE_NAME+
      " SET LAWSUIT_ID=?, JUDGE_ID=?, SUE_DATE=?, START_DATE=?, CLAIM_TEXT=?, DEFENDANT_TEXT=?, VERDICT_ID=?," +
      " SUITOR_APPEAL_DATE=?, SUITOR_APPEAL_TEXT=?, DEFENDANT_APPEAL_DATE=?, DEFENDANT_APPEAL_TEXT=?" +
      " WHERE ID = ?";

  public static final String SQL_CREATE_TABLE="CREATE TABLE IF NOT EXISTS "+STAGE_TABLE_NAME+" (" +
      "ID BIGINT AUTO_INCREMENT PRIMARY KEY, " +
      "LAWSUIT_ID BIGINT NOT NULL, " +
      "JUDGE_ID BIGINT NOT NULL, "+
      "SUE_DATE DATE NOT NULL, "+
      "START_DATE DATE NOT NULL, "+
      "CLAIM_TEXT VARCHAR NOT NULL, " +
      "DEFENDANT_TEXT VARCHAR DEFAULT NULL, " +
      "VERDICT_ID BIGINT DEFAULT NULL, " +
      "SUITOR_APPEAL_DATE DATE DEFAULT NULL, "+
      "SUITOR_APPEAL_TEXT VARCHAR DEFAULT NULL, " +
      "DEFENDANT_APPEAL_DATE DATE DEFAULT NULL, "+
      "DEFENDANT_APPEAL_TEXT VARCHAR DEFAULT NULL, " +
      "FOREIGN KEY (LAWSUIT_ID) REFERENCES LAWSUIT(ID) ON DELETE CASCADE, " +
      "FOREIGN KEY (JUDGE_ID) REFERENCES USER(ID), "+
      "FOREIGN KEY (VERDICT_ID) REFERENCES VERDICT(ID)" +
      ")";

  public StageDao() throws SQLException, DbPoolException {
    super(STAGE_TABLE_NAME, SQL_INSERT, SQL_UPDATE);
  }

  public StageDao(Connection connection){
    super(connection,STAGE_TABLE_NAME, SQL_INSERT, SQL_UPDATE);
  }

  @Override
  protected Stage recordToEntity(ResultSet resultSet){
    Stage stage= new Stage();
    try{
      stage.setId(resultSet.getLong("ID"));
      stage.setLawsuitId(resultSet.getLong("LAWSUIT_ID")); // stage.setLawsuit(new LawsuitDao(connection).readEntity(resultSet.getLong("LAWSUIT_ID")));
      stage.setJudgeId(resultSet.getLong("JUDGE_ID")); // stage.setJudge(new UserDao(connection).readEntity(resultSet.getLong("JUDGE_ID")));
      stage.setSueDate(sqlDateToDate(resultSet.getDate("SUE_DATE")));
      stage.setStartDate(sqlDateToDate(resultSet.getDate("START_DATE")));
      stage.setClaimText(resultSet.getString("CLAIM_TEXT"));
      stage.setDefendantText(resultSet.getString("DEFENDANT_TEXT"));
      stage.setVerdictId(resultSet.getLong("VERDICT_ID")); // stage.setVerdict(new VerdictDao(connection).readEntity(resultSet.getLong("VERDICT_ID")));
      stage.setSuitorAppealDate(sqlDateToDate(resultSet.getDate("SUITOR_APPEAL_DATE")));
      stage.setSuitorAppealText(resultSet.getString("SUITOR_APPEAL_TEXT"));
      stage.setDefendantAppealDate(sqlDateToDate(resultSet.getDate("DEFENDANT_APPEAL_DATE")));
      stage.setDefendantText(resultSet.getString("DEFENDANT_APPEAL_TEXT"));
      return stage;
    }catch(SQLException e){
      log.error("SQLException in StageDao.recordToEntity: "+e);
      return null;
    }
  }

  @Override
  protected int setAllPreparedValues(PreparedStatement preparedStatement, Stage stage, boolean isAddOperation) throws SQLException{
    setPreparedValueOrNull(preparedStatement,1, stage.getLawsuitId());
    setPreparedValueOrNull(preparedStatement,2,stage.getJudgeId());
    preparedStatement.setDate(3, dateToSqlDate(stage.getSueDate()));
    preparedStatement.setDate(4, dateToSqlDate(stage.getStartDate()));
    preparedStatement.setString(5, stage.getClaimText());
    preparedStatement.setString(6, stage.getDefendantText());
    setPreparedValueOrNull(preparedStatement,7,stage.getVerdictId());
    preparedStatement.setDate(8, dateToSqlDate(stage.getSuitorAppealDate()));
    preparedStatement.setString(9, stage.getSuitorAppealText());
    preparedStatement.setDate(10, dateToSqlDate(stage.getDefendantAppealDate()));
    preparedStatement.setString(11, stage.getDefendantAppealText());
    return 12; // next index for preparedStatement.set_()
  };

  @Override
  public Stage loadAllSubEntities(Stage stage) throws SQLException {
    if (stage!=null) {
      if (stage.getLawsuit()==null && stage.getLawsuitId()>0) {
        stage.setLawsuit(new LawsuitDao(connection).readEntity(stage.getLawsuitId()));
      }
      if (stage.getJudge()==null && stage.getJudgeId()>0) {
        stage.setJudge(new UserDao(connection).readEntity(stage.getJudgeId()));
      }
      if (stage.getVerdict()==null && stage.getVerdictId()>0) {
        stage.setVerdict(new VerdictDao(connection).readEntity(stage.getVerdictId()));
      }
    }
    return stage;
  };

}
