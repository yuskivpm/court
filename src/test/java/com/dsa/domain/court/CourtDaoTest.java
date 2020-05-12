package com.dsa.domain.court;

import com.dsa.dao.AbstractEntityDao;
import com.dsa.dao.DbPoolException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.apache.log4j.Logger;

import java.sql.SQLException;

import static com.dsa.InitDbForTests.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourtDaoTest {

  private static final String ID = "ID";
  private static final String COURT_INSTANCE = "COURT_INSTANCE";
  private static final CourtInstance courtInstance = CourtInstance.CASSATION;
  private static final String COURT_NAME = "court name";
  private static final long MAIN_COURT_ID = 2;
  private static final int PREPARED_VALUES_COUNT = 3;

  @Test
  void constructors() throws SQLException, DbPoolException {
    System.out.println("Start recordToEntity");
    CourtDao courtDao = new CourtDao();
    assertNotNull(courtDao);
    courtDao = new CourtDao(connection);
    assertNotNull(courtDao);
  }

  @Test
  void recordToEntity() throws SQLException {
    System.out.println("Start recordToEntity");
    CourtDao courtDao = new CourtDao(connection);
    System.out.println("Start recordToEntity - test exception");
    when(resultSet.getString(COURT_INSTANCE)).thenReturn(null);
    assertThrows(NullPointerException.class, () -> courtDao.recordToEntity(resultSet));
    System.out.println("Start recordToEntity - test SQLException");
    when(resultSet.getLong(ID)).thenThrow(SQLException.class);
    assertNull(courtDao.recordToEntity(resultSet));
    System.out.println("Start recordToEntity - test normal invocation");
    createResultSet();
    when(resultSet.getLong(ID)).thenReturn(MAIN_COURT_ID);
    when(resultSet.getString(COURT_INSTANCE)).thenReturn(courtInstance.toString());
    assertNotNull(courtDao.recordToEntity(resultSet));
  }

  @Test
  void setAllPreparedValues() throws SQLException {
    System.out.println("Start setAllPreparedValues");
    Court court = new Court(1, COURT_NAME, courtInstance, null);
    CourtDao courtDao = new CourtDao(connection);
    assertEquals(PREPARED_VALUES_COUNT + 1, courtDao.setAllPreparedValues(preparedStatement, court, true));
    verify(preparedStatement, Mockito.times(2)).setString(Mockito.anyInt(), Mockito.anyString());
    verify(preparedStatement).setNull(PREPARED_VALUES_COUNT, 0);
    court.setMainCourtId(MAIN_COURT_ID);
    courtDao.setAllPreparedValues(preparedStatement, court, true);
    verify(preparedStatement).setLong(PREPARED_VALUES_COUNT, MAIN_COURT_ID);
  }

  @Test
  void loadAllSubEntities() throws SQLException {
    System.out.println("Start loadAllSubEntities");
    CourtDao courtDao = new CourtDao(connection);
    assertNull(courtDao.loadAllSubEntities(null));
    Court court = new Court(1, COURT_NAME, courtInstance, null);
    assertEquals(courtDao.loadAllSubEntities(court).getMainCourtId(), 0);
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

  @Test
  void abstractEntityDao_CreateEntity_Exception_Test() throws SQLException {
    System.out.println("Start abstractEntityDao_CreateEntity_Exception_Test");
    CourtDao courtDao = new CourtDao(connection);
    Court court = new Court(1, "", courtInstance, null);
    when(preparedStatement.executeUpdate()).thenThrow(SQLException.class);
    assertFalse(courtDao.createEntity(court));
    createPreparedStatement();
  }

  @Test
  void abstractEntityDao_UpdateEntity_Test() throws SQLException {
    System.out.println("Start abstractEntityDao_UpdateEntity_Test");
    CourtDao courtDao = new CourtDao(connection);
    Court court = new Court(1, "", courtInstance, null);
    when(preparedStatement.executeUpdate()).thenThrow(SQLException.class);
    assertFalse(courtDao.updateEntity(court));
    createPreparedStatement();
    when(preparedStatement.executeUpdate()).thenReturn(1);
    assertTrue(courtDao.updateEntity(court));
  }

  @Test
  void abstractEntityDao_ReadAll_Test() throws SQLException {
    System.out.println("Start abstractEntityDao_ReadAll_Test");
    CourtDao courtDao = new CourtDao(connection);
    assertThrows(SQLException.class, () -> courtDao.readAll(new String[]{"1"}));
    when(resultSet.next()).thenThrow(SQLException.class);
    assertEquals(0, courtDao.readAll().size());
    createResultSet();
    when(resultSet.getString(COURT_INSTANCE)).thenReturn(courtInstance.toString());
    when(resultSet.next()).thenReturn(false);
    assertEquals(0, courtDao.readAll().size());
    when(resultSet.next()).thenReturn(true).thenReturn(false);
    assertEquals(1, courtDao.readAll().size());
  }

  @Test
  void abstractEntityDao_ReadEntity_Exception_Test() throws SQLException {
    System.out.println("Start abstractEntityDao_ReadEntity_Exception_Test");
    CourtDao courtDao = new CourtDao(connection);
    when(resultSet.next()).thenThrow(SQLException.class);
    assertNull(courtDao.readEntity(1));
    createResultSet();
  }

  @Test
  void abstractEntityDao_DeleteEntity_Test() throws SQLException {
    System.out.println("Start abstractEntityDao_DeleteEntity_Test");
    CourtDao courtDao = new CourtDao(connection);
    System.out.println("Start exception");
    when(preparedStatement.executeUpdate()).thenThrow(SQLException.class);
    assertFalse(courtDao.deleteEntity("1"));
    createPreparedStatement();
    System.out.println("Start delete");
    when(preparedStatement.executeUpdate()).thenReturn(1);
    assertTrue(courtDao.deleteEntity("1"));
  }

  @Test
  void abstractEntityDao_sqlExecute_Test() throws SQLException {
    System.out.println("Start abstractEntityDao_sqlExecute_Test");
    final String SQL = "TEXT SQL";
    Logger log = Mockito.mock(Logger.class);
    when(statement.execute(Mockito.anyString())).thenThrow(SQLException.class);
    AbstractEntityDao.sqlExecute(statement, SQL, true, log);
    verify(statement).execute(SQL);
    verify(statement).close();
    verify(log).error(Mockito.anyString());
    createPreparedStatement();
  }

  @BeforeAll
  static void beforeAll() throws SQLException, DbPoolException {
    System.out.println("Start testing CourtDaoTest");
    initDbPool();
    AbstractEntityDao.setDbPool(dbPool);
  }

  @AfterAll
  static void afterAll() {
    System.out.println("Finish testing CourtDaoTest");
  }
}