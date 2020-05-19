package com.dsa.domain.lawsuit;

import com.dsa.dao.AbstractEntityDao;
import com.dsa.dao.DbPoolException;
import com.dsa.domain.court.Court;
import com.dsa.domain.user.Role;
import com.dsa.domain.user.User;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.List;

import static com.dsa.InitDbForTests.*;

import static com.dsa.domain.lawsuit.LawsuitConst.*;
import static com.dsa.domain.user.UserConst.ROLE;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LawsuitDaoTest {

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
    createResultSet();
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
    assertEquals(PREPARED_VALUES_COUNT + 1, lawsuitDao.setAllPreparedValues(preparedStatement, lawsuit));
    verify(preparedStatement, Mockito.times(4)).setDate(Mockito.anyInt(), Mockito.isNull());
    verify(preparedStatement).setLong(Mockito.anyInt(), eq(court.getId()));
    verify(preparedStatement).setLong(Mockito.anyInt(), eq(user.getId()));
    verify(preparedStatement, Mockito.times(4)).setString(Mockito.anyInt(), Mockito.isNull());
    verify(preparedStatement, Mockito.times(3)).setNull(Mockito.anyInt(), eq(0));
  }

  @Test
  void loadAllSubEntities() throws SQLException {
    System.out.println("Start loadAllSubEntities");
    LawsuitDao lawsuitDao = new LawsuitDao(connection);
    System.out.println("loadAllSubEntities from null");
    assertNull(lawsuitDao.loadAllSubEntities(null));
    System.out.println("loadAllSubEntities exist");
    Lawsuit lawsuit = Mockito.mock(Lawsuit.class);
    final long SUITOR_ID = 2;
    when(lawsuit.getSuitorId()).thenReturn(SUITOR_ID);
    when(resultSet.next()).thenReturn(true);
    when(resultSet.getString(ROLE)).thenReturn(Role.ATTORNEY.toString());
    lawsuitDao.loadAllSubEntities(lawsuit);
    verify(lawsuit).getSuitor();
    verify(lawsuit, Mockito.times(2)).getSuitorId();
    verify(lawsuit).setSuitor(Mockito.any());
    verify(lawsuit).getDefendant();
    verify(lawsuit).getCourt();
    verify(lawsuit).getJudge();
    verify(lawsuit).getAppealedLawsuit();
  }

  @Test
  void readAllBySuitorId() throws SQLException {
    System.out.println("Start readAllBySuitorId");
    final int LIST_SIZE = 2;
    when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    LawsuitDao lawsuitDao = new LawsuitDao(connection);
    List<Lawsuit> lawsuits = lawsuitDao.readAllBySuitorId(1);
    assertNotNull(lawsuits);
    assertEquals(lawsuits.size(), LIST_SIZE);
    when(resultSet.next()).thenReturn(false);
    lawsuitDao = new LawsuitDao(connection);
    lawsuits = lawsuitDao.readAllBySuitorId(1);
    assertNotNull(lawsuits);
    assertEquals(lawsuits.size(), 0);
  }

  @Test
  void readAllByDefendantId() throws SQLException {
    System.out.println("Start readAllByDefendantId");
    final int LIST_SIZE = 2;
    when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    LawsuitDao lawsuitDao = new LawsuitDao(connection);
    List<Lawsuit> lawsuits = lawsuitDao.readAllByDefendantId(1);
    assertNotNull(lawsuits);
    assertEquals(lawsuits.size(), LIST_SIZE);
    when(resultSet.next()).thenReturn(false);
    lawsuitDao = new LawsuitDao(connection);
    lawsuits = lawsuitDao.readAllByDefendantId(1);
    assertNotNull(lawsuits);
    assertEquals(lawsuits.size(), 0);
  }

  @Test
  void readAllUnacceptedForCourtId() throws SQLException {
    System.out.println("Start readAllUnacceptedForCourtId");
    final int LIST_SIZE = 2;
    when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    LawsuitDao lawsuitDao = new LawsuitDao(connection);
    List<Lawsuit> lawsuits = lawsuitDao.readAllUnacceptedForCourtId(1);
    assertNotNull(lawsuits);
    assertEquals(lawsuits.size(), LIST_SIZE);
    when(resultSet.next()).thenReturn(false);
    lawsuitDao = new LawsuitDao(connection);
    lawsuits = lawsuitDao.readAllUnacceptedForCourtId(1);
    assertNotNull(lawsuits);
    assertEquals(lawsuits.size(), 0);
  }

  @Test
  void readAllForJudgeId() throws SQLException {
    System.out.println("Start readAllForJudgeId");
    final int LIST_SIZE = 2;
    when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    LawsuitDao lawsuitDao = new LawsuitDao(connection);
    List<Lawsuit> lawsuits = lawsuitDao.readAllForJudgeId(1);
    assertNotNull(lawsuits);
    assertEquals(lawsuits.size(), LIST_SIZE);
    when(resultSet.next()).thenReturn(false);
    lawsuitDao = new LawsuitDao(connection);
    lawsuits = lawsuitDao.readAllForJudgeId(1);
    assertNotNull(lawsuits);
    assertEquals(lawsuits.size(), 0);
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

}