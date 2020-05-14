package com.dsa.domain.lawsuit;

import com.dsa.dao.AbstractEntityDao;
import com.dsa.dao.DbPoolException;

import com.dsa.domain.court.Court;
import com.dsa.domain.user.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.Date;

import static com.dsa.InitDbForTests.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LawsuitDaoTest {
//todo: test

  protected static final String ID = "ID";
  private static final String SUITOR_ID = "SUITOR_ID";
  private static final String DEFENDANT_ID = "DEFENDANT_ID";
  private static final String COURT_ID = "COURT_ID";
  private static final String JUDGE_ID = "JUDGE_ID";
  private static final String SUE_DATE = "SUE_DATE";
  private static final String CLAIM_TEXT = "CLAIM_TEXT";
  private static final String DEFENDANT_TEXT = "DEFENDANT_TEXT";
  private static final String START_DATE = "START_DATE";
  private static final String VERDICT_DATE = "VERDICT_DATE";
  private static final String VERDICT_TEXT = "VERDICT_TEXT";
  private static final String APPEALED_LAWSUIT_ID = "APPEALED_LAWSUIT_ID";
  private static final String APPEAL_STATUS = "APPEAL_STATUS";
  private static final String EXECUTION_DATE = "EXECUTION_DATE";
  private static final int PREPARED_VALUES_COUNT = 13;

  @Test
  void constructors() throws SQLException, DbPoolException {
    System.out.println("Start recordToEntity");
    LawsuitDao lawsuitDao = new LawsuitDao();
    assertNotNull(lawsuitDao);
    lawsuitDao = new LawsuitDao(connection);
    assertNotNull(lawsuitDao);
  }

  @Test
  void recordToEntity() throws SQLException {
    System.out.println("Start recordToEntity");
    LawsuitDao lawsuitDao = new LawsuitDao(connection);
    System.out.println("Start recordToEntity - test normal invocation");
    assertNotNull(lawsuitDao.recordToEntity(resultSet));
    verify(resultSet, Mockito.times(6)).getLong(Mockito.anyString());
    verify(resultSet, Mockito.times(4)).getDate(Mockito.anyString());
    verify(resultSet, Mockito.times(4)).getString(Mockito.anyString());
    System.out.println("Start recordToEntity - test SQLException");
    when(resultSet.getLong(ID)).thenThrow(SQLException.class);
    assertNull(lawsuitDao.recordToEntity(resultSet));
    createResultSet();
  }

  @Test
  void setAllPreparedValues() throws SQLException {
    System.out.println("Start setAllPreparedValues");
    Court court = new Court(2, null, null, null);
    User user = new User(3, null, null, null, null, null, true);
    Lawsuit lawsuit = new Lawsuit(1, null, court, user, null, null, null,
        null, null, null, null, null, null, null);
    LawsuitDao lawsuitDao = new LawsuitDao(connection);
    assertEquals(PREPARED_VALUES_COUNT + 1, lawsuitDao.setAllPreparedValues(preparedStatement, lawsuit, true));
    verify(preparedStatement, Mockito.times(4)).setDate(Mockito.anyInt(), Mockito.isNull());
    verify(preparedStatement).setLong(Mockito.anyInt(), eq(court.getId()));
    verify(preparedStatement).setLong(Mockito.anyInt(), eq(user.getId()));
    verify(preparedStatement, Mockito.times(4)).setString(Mockito.anyInt(), Mockito.isNull());
    verify(preparedStatement, Mockito.times(3)).setNull(Mockito.anyInt(), eq(0));
  }

  @BeforeAll
  static void beforeAll() throws SQLException, DbPoolException {
    System.out.println("Start testing LawsuitDaoTest");
    initDbPool();
    AbstractEntityDao.setDbPool(dbPool);
  }

  @AfterAll
  static void afterAll() {
    System.out.println("Finish testing LawsuitDaoTest");
  }

  @Test
  void readAllBySuitorId() {
    System.out.println("Start readAllBySuitorId");
  }

  @Test
  void readAllByDefendantId() {
    System.out.println("Start readAllByDefendantId");
  }

  @Test
  void readAllUnacceptedForCourtId() {
    System.out.println("Start readAllUnacceptedForCourtId");
  }

  @Test
  void readAllForJudgeId() {
    System.out.println("Start readAllForJudgeId");
  }

  @Test
  void loadAllSubEntities() throws SQLException {
    System.out.println("Start loadAllSubEntities");
    LawsuitDao lawsuitDao = new LawsuitDao(connection);
    System.out.println("loadAllSubEntities from null");
    assertNull(lawsuitDao.loadAllSubEntities(null));
    Lawsuit lawsuit = Mockito.mock(Lawsuit.class);
    System.out.println("loadAllSubEntities exist");
    final long SUITOR_ID = 2;
    User user = new User(3, null, null, null, null, null, true);
    when(lawsuit.getSuitorId()).thenReturn(SUITOR_ID);

    9

    verify(lawsuit).setSuitor(null);

    /*


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
      }*/
     */
    lawsuitDao.loadAllSubEntities(lawsuit);



    Court court = new Court(2, null, null, null);
    User user = new User(3, null, null, null, null, null, true);

    assertEquals(.getMainCourtId(), 0);


    when(resultSet.next()).thenReturn(false);
    assertNull(courtDao.loadAllSubEntities(court).getMainCourt());
    court.setMainCourtId(MAIN_COURT_ID);
    when(resultSet.next()).thenReturn(true);
    when(resultSet.getLong(ID)).thenReturn(MAIN_COURT_ID);
    when(resultSet.getString(COURT_INSTANCE)).thenReturn(courtInstance.toString());
    court = courtDao.loadAllSubEntities(court);
    assertEquals(court.getMainCourtId(), MAIN_COURT_ID);
    assertNotNull(court.getMainCourt());
  }

}