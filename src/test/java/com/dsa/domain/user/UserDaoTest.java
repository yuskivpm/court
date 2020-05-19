package com.dsa.domain.user;

import com.dsa.dao.AbstractEntityDao;
import com.dsa.dao.DbPoolException;
import com.dsa.domain.court.Court;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;

import static com.dsa.InitDbForTests.*;

import static com.dsa.domain.user.UserConst.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDaoTest {

  private static final int PREPARED_VALUES_COUNT = 6;
  private static final Role ROLE_VALUE = Role.ATTORNEY;
  private static final long COURT_ID = 2L;
  private static final boolean IS_ACTIVE = true;

  @Test
  void recordToEntity() throws SQLException {
    System.out.println("Start recordToEntity");
    createResultSet();
    UserDao userDao = new UserDao(connection);
    System.out.println("Start recordToEntity - test normal invocation");
    when(resultSet.getString(ROLE)).thenReturn(Role.JUDGE.toString());
    assertNotNull(userDao.recordToEntity(resultSet));
    verify(resultSet, Mockito.times(2)).getLong(Mockito.anyString());
    verify(resultSet).getBoolean(Mockito.anyString());
    verify(resultSet, Mockito.times(4)).getString(Mockito.anyString());
    System.out.println("Start recordToEntity - test SQLException");
    when(resultSet.getLong(ID)).thenThrow(SQLException.class);
    assertNull(userDao.recordToEntity(resultSet));
    createResultSet();
    when(resultSet.getString(ROLE)).thenReturn(null);
    assertThrows(NullPointerException.class, () -> userDao.recordToEntity(resultSet));
    createResultSet();
  }

  @Test
  void setAllPreparedValues() throws SQLException {
    System.out.println("Start setAllPreparedValues - without court");
    User user = Mockito.mock(User.class);
    UserDao userDao = new UserDao(connection);
    int index = 0;
    when(user.getLogin()).thenReturn(LOGIN);
    when(user.getPassword()).thenReturn(PASSWORD);
    when(user.getRole()).thenReturn(ROLE_VALUE);
    when(user.getName()).thenReturn(NAME);
    when(user.getCourtId()).thenReturn(0L);
    when(user.getIsActive()).thenReturn(IS_ACTIVE);
    assertEquals(PREPARED_VALUES_COUNT + 1, userDao.setAllPreparedValues(preparedStatement, user, true));
    verify(preparedStatement).setString(++index, LOGIN);
    verify(preparedStatement).setString(++index, PASSWORD);
    verify(preparedStatement).setString(++index, ROLE_VALUE.toString());
    verify(preparedStatement).setString(++index, NAME);
    verify(preparedStatement).setNull(++index, 0);
    verify(preparedStatement).setBoolean(++index, IS_ACTIVE);
    System.out.println("Start setAllPreparedValues - has court id");
    createPreparedStatement();
    when(user.getCourtId()).thenReturn(COURT_ID);
    when(user.getIsActive()).thenReturn(!IS_ACTIVE);
    assertEquals(PREPARED_VALUES_COUNT + 1, userDao.setAllPreparedValues(preparedStatement, user, true));
    verify(preparedStatement, Mockito.times(4)).setString(Mockito.anyInt(), Mockito.anyString());
    verify(preparedStatement).setLong(Mockito.anyInt(), eq(COURT_ID));
    verify(preparedStatement).setBoolean(PREPARED_VALUES_COUNT,  !IS_ACTIVE);
    System.out.println("Start setAllPreparedValues - exception");
    createPreparedStatement();
    when(user.getRole()).thenReturn(null);
    assertThrows(NullPointerException.class, () -> userDao.setAllPreparedValues(preparedStatement, user, true));
  }

  @Test
  void loadAllSubEntities() throws SQLException {
    System.out.println("Start loadAllSubEntities");
    UserDao courtDao = new UserDao(connection);
    assertNull(courtDao.loadAllSubEntities(null));
    User user = Mockito.mock(User.class);
    Court court = Mockito.mock(Court.class);
    when(user.getCourt()).thenReturn(court);
    assertNotNull(courtDao.loadAllSubEntities(user));
    verify(user).getCourt();
    user = Mockito.mock(User.class);
    when(user.getCourt()).thenReturn(null);
    when(user.getCourtId()).thenReturn(0L);
    assertNotNull(courtDao.loadAllSubEntities(user));
    verify(user).getCourt();
    verify(user).getCourtId();
    user = Mockito.mock(User.class);
    when(user.getCourt()).thenReturn(null);
    when(user.getCourtId()).thenReturn(COURT_ID);
    assertNotNull(courtDao.loadAllSubEntities(user));
    verify(user).getCourt();
    verify(user, Mockito.times(2)).getCourtId();
    verify(user).setCourt(null);
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