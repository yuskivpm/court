package com.dsa.domain.lawsuit;

import com.dsa.dao.AbstractEntityDao;
import com.dsa.dao.DbPoolException;
import com.dsa.domain.court.Court;
import com.dsa.domain.court.CourtDao;
import com.dsa.domain.user.UserDao;

import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.List;

import static com.dsa.domain.lawsuit.LawsuitConst.*;

public class LawsuitDao extends AbstractEntityDao<Lawsuit> {

  private static final String SQL_INSERT = INSERT_TO + ENTITY_NAME +
      " (" + SUE_DATE + ", " + COURT_ID + ", " + SUITOR_ID + ", " + CLAIM_TEXT + ", " + DEFENDANT_ID + ", " +
      DEFENDANT_TEXT + ", " + JUDGE_ID + ", " + START_DATE + ", " + VERDICT_DATE + ", " + VERDICT_TEXT + ", " +
      APPEALED_LAWSUIT_ID + ", " + APPEALED_STATUS + ", " + EXECUTION_DATE + ") " +
      "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

  private static final String SQL_UPDATE = UPDATE + ENTITY_NAME +
      " SET " + SUE_DATE + " = ?, " + COURT_ID + " = ?, " + SUITOR_ID + " = ?, " + CLAIM_TEXT + " = ?, " +
      DEFENDANT_ID + " = ?, " + DEFENDANT_TEXT + " = ?, " + JUDGE_ID + " = ?, " + START_DATE + " = ?, " +
      VERDICT_DATE + " = ?, " + VERDICT_TEXT + " = ?, " + APPEALED_LAWSUIT_ID + " = ?, " + APPEALED_STATUS + " = ?, " +
      EXECUTION_DATE + " = ?" +
      " WHERE " + ID + " = ?";

  public static final String SQL_CREATE_TABLE = CREATE_TABLE_IF_NOT_EXISTS + ENTITY_NAME + " (" +
      ID + PRIMARY_KEY + ", " +
      SUE_DATE + " DATE " + NOT_NULL + ", " +
      COURT_ID + " BIGINT " + NOT_NULL + ", " +
      SUITOR_ID + " BIGINT " + NOT_NULL + ", " +
      CLAIM_TEXT + VARCHAR_255 + NOT_NULL + ", " +
      DEFENDANT_ID + " BIGINT " + NOT_NULL + ", " +
      DEFENDANT_TEXT + VARCHAR_255 + DEFAULT_NULL + ", " +
      JUDGE_ID + " BIGINT " + DEFAULT_NULL + ", " +
      START_DATE + " DATE " + DEFAULT_NULL + ", " +
      VERDICT_DATE + " DATE " + DEFAULT_NULL + ", " +
      VERDICT_TEXT + VARCHAR_255 + DEFAULT_NULL + ", " +
      APPEALED_LAWSUIT_ID + " BIGINT " + DEFAULT_NULL + ", " +
      APPEALED_STATUS + VARCHAR_255 + DEFAULT_NULL + ", " +
      EXECUTION_DATE + " DATE " + DEFAULT_NULL + "," +
      FOREIGN_KEY + SUITOR_ID + ") REFERENCES USER(" + ID + "), " +
      FOREIGN_KEY + DEFENDANT_ID + ") REFERENCES USER(" + ID + "), " +
      FOREIGN_KEY + COURT_ID + ") REFERENCES COURT(" + ID + "), " +
      FOREIGN_KEY + JUDGE_ID + ") REFERENCES USER(" + ID + "), " +
      FOREIGN_KEY + APPEALED_LAWSUIT_ID + ") REFERENCES LAWSUIT(" + ID + ") " +
      ")";

  public LawsuitDao() throws SQLException, DbPoolException {
    super(ENTITY_NAME, SQL_INSERT, SQL_UPDATE);
  }

  public LawsuitDao(Connection connection) {
    super(connection, ENTITY_NAME, SQL_INSERT, SQL_UPDATE);
  }

  public List<Lawsuit> readAllBySuitorId(long suitorId) throws SQLException {
    String[] where = {SUITOR_ID, Long.toString(suitorId)};
    return readAll(where);
  }

  public List<Lawsuit> readAllByDefendantId(long defendantId) throws SQLException {
    String[] where = {DEFENDANT_ID, Long.toString(defendantId)};
    return readAll(where);
  }

  public List<Lawsuit> readAllUnacceptedForCourtId(long courtId) throws SQLException {
    String[] where = {COURT_ID, Long.toString(courtId), JUDGE_ID, null};
    return readAll(where);
  }

  public List<Lawsuit> readAllForJudgeId(long judgeId) throws SQLException {
    String[] where = {JUDGE_ID, Long.toString(judgeId)};
    return readAll(where);
  }

  @Override
  protected Lawsuit recordToEntity(@NotNull ResultSet resultSet) {
    Lawsuit lawsuit = new Lawsuit();
    try {
      lawsuit.setId(resultSet.getLong(ID));
      lawsuit.setSueDate(sqlDateToDate(resultSet.getDate(SUE_DATE)));
      lawsuit.setCourtId(resultSet.getLong(COURT_ID));
      lawsuit.setSuitorId(resultSet.getLong(SUITOR_ID));
      lawsuit.setClaimText(resultSet.getString(CLAIM_TEXT));
      lawsuit.setDefendantId(resultSet.getLong(DEFENDANT_ID));
      lawsuit.setDefendantText(resultSet.getString(DEFENDANT_TEXT));
      lawsuit.setJudgeId(resultSet.getLong(JUDGE_ID));
      lawsuit.setStartDate(sqlDateToDate(resultSet.getDate(START_DATE)));
      lawsuit.setVerdictDate(sqlDateToDate(resultSet.getDate(VERDICT_DATE)));
      lawsuit.setVerdictText(resultSet.getString(VERDICT_TEXT));
      lawsuit.setAppealedLawsuitId(resultSet.getLong(APPEALED_LAWSUIT_ID));
      lawsuit.setAppealStatus(resultSet.getString(APPEALED_STATUS));
      lawsuit.setExecutionDate(sqlDateToDate(resultSet.getDate(EXECUTION_DATE)));
      return lawsuit;
    } catch (SQLException e) {
      LOG.error("SQLException in LawsuitDao.recordToEntity: " + e);
      return null;
    }
  }

  @Override
  protected int setAllPreparedValues(
      @NotNull PreparedStatement preparedStatement,
      @NotNull Lawsuit lawsuit,
      boolean isAddOperation
  ) throws SQLException {
    int index = 0;
    preparedStatement.setDate(++index, dateToSqlDate(lawsuit.getSueDate()));
    setPreparedValueOrNull(preparedStatement, ++index, lawsuit.getCourtId());
    setPreparedValueOrNull(preparedStatement, ++index, lawsuit.getSuitorId());
    preparedStatement.setString(++index, lawsuit.getClaimText());
    setPreparedValueOrNull(preparedStatement, ++index, lawsuit.getDefendantId());
    preparedStatement.setString(++index, lawsuit.getDefendantText());
    setPreparedValueOrNull(preparedStatement, ++index, lawsuit.getJudgeId());
    preparedStatement.setDate(++index, dateToSqlDate(lawsuit.getStartDate()));
    preparedStatement.setDate(++index, dateToSqlDate(lawsuit.getVerdictDate()));
    preparedStatement.setString(++index, lawsuit.getVerdictText());
    setPreparedValueOrNull(preparedStatement, ++index, lawsuit.getAppealedLawsuitId());
    preparedStatement.setString(++index, lawsuit.getAppealStatus());
    preparedStatement.setDate(++index, dateToSqlDate(lawsuit.getExecutionDate()));
    return ++index;
  }

  @Override
  public Lawsuit loadAllSubEntities(Lawsuit lawsuit) throws SQLException {
    if (lawsuit != null) {
      if (lawsuit.getSuitor() == null && lawsuit.getSuitorId() > 0) {
        lawsuit.setSuitor(new UserDao(connection).readEntity(lawsuit.getSuitorId()));
      }
      if (lawsuit.getDefendant() == null && lawsuit.getDefendantId() > 0) {
        lawsuit.setDefendant(new UserDao(connection).readEntity(lawsuit.getDefendantId()));
      }
      if (lawsuit.getCourt() == null && lawsuit.getCourtId() > 0) {
        Court court = new CourtDao(connection).readEntity(lawsuit.getCourtId());
        lawsuit.setCourt(court);
        if (court != null && court.getMainCourt() == null && court.getMainCourtId() > 0) {
          court.setMainCourt(new CourtDao(connection).readEntity(court.getMainCourtId()));
        }
      }
      if (lawsuit.getJudge() == null && lawsuit.getJudgeId() > 0) {
        lawsuit.setJudge(new UserDao(connection).readEntity(lawsuit.getJudgeId()));
      }
      if (lawsuit.getAppealedLawsuit() == null && lawsuit.getAppealedLawsuitId() > 0) {
        lawsuit.setAppealedLawsuit(new LawsuitDao(connection).readEntity(lawsuit.getAppealedLawsuitId()));
      }
    }
    return lawsuit;
  }

}
