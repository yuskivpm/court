package com.dsa.domain.court;

import com.dsa.controller.ControllerRequest;
import com.dsa.dao.AbstractEntityDao;
import com.dsa.dao.DbPoolException;
import com.dsa.domain.user.Role;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;

import static com.dsa.InitDbForTests.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourtCrudTest {

  private static final String PATH = "/courts/";
  private static final String USER_SESSION_ID = "user_id";
  private static final String USER_SESSION_ID_VALUE = "1";
  private static final String ROLE_ID = "ROLE_ID";
  private static final String COURT_NAME = "courtName";
  private static final String COURT_INSTANCE = "courtInstance";
  private static final String RESULT_SET_COURT_INSTANCE = "COURT_INSTANCE";
  private static final String MAIN_COURT_ID = "mainCourtId";

  private static CourtCrud courtCrud;

  @Test
  void pathTest() {
    System.out.println("Start pathTest");
    assertEquals(PATH, CourtCrud.path);
  }

  @Test
  void checkAuthority() throws SQLException {
    System.out.println("Start checkAuthority empty user");
    ControllerRequest request = Mockito.mock(ControllerRequest.class);
    assertFalse(courtCrud.checkAuthority(request));
    System.out.println("Start checkAuthority incorrect user");
    verify(request).getSessionAttribute(USER_SESSION_ID);
    when(request.getSessionAttribute(USER_SESSION_ID)).thenReturn(USER_SESSION_ID_VALUE);
    assertFalse(courtCrud.checkAuthority(request));
    verify(connection).prepareStatement(Mockito.anyString());
    verify(preparedStatement).executeQuery();
    verify(resultSet).next();
    System.out.println("Start checkAuthority incorrect user. Exception nullPointer (empty role) ");
    when(resultSet.next()).thenReturn(true);
    assertThrows(NullPointerException.class, () -> courtCrud.checkAuthority(request));
    System.out.println("Start checkAuthority user - Judge");
    when(resultSet.getString(ROLE_ID)).thenReturn(Role.JUDGE.toString());
    assertFalse(courtCrud.checkAuthority(request));
    System.out.println("Start checkAuthority user - Admin");
    when(resultSet.getString(ROLE_ID)).thenReturn(Role.ADMIN.toString());
    assertTrue(courtCrud.checkAuthority(request));
  }

  @Test
  void createEntityDao() throws SQLException, DbPoolException {
    System.out.println("Start createEntityDao");
    assertNotNull(courtCrud.createEntityDao());
  }

  @Test
  void createEntityFromParameters() throws SQLException {
    System.out.println("Start createEntityFromParameters - empty court name");
    ControllerRequest request = Mockito.mock(ControllerRequest.class);
    final long COURT_ID_VALUE = 2L;
    final long MAIN_COURT_ID_VALUE = 3L;
    when(request.getParameter(COURT_NAME)).thenReturn("");
    assertNull(courtCrud.createEntityFromParameters(request, COURT_ID_VALUE));
    System.out.println("Start createEntityFromParameters - exception - incorrect CourtInstance");
    when(request.getParameter(COURT_NAME)).thenReturn(COURT_NAME);
    assertThrows(NullPointerException.class, () -> courtCrud.createEntityFromParameters(request, COURT_ID_VALUE));
    System.out.println("Start createEntityFromParameters - supreme court");
    when(request.getParameter(COURT_INSTANCE)).thenReturn(CourtInstance.CASSATION.toString());
    when(request.getParameter(MAIN_COURT_ID)).thenReturn(Long.toString(MAIN_COURT_ID_VALUE));
    Court court = courtCrud.createEntityFromParameters(request, COURT_ID_VALUE);
    assertEquals(COURT_ID_VALUE, court.getId());
    assertEquals(0, court.getMainCourtId());
    System.out.println("Start createEntityFromParameters - local court");
    when(request.getParameter(COURT_INSTANCE)).thenReturn(CourtInstance.LOCAL.toString());
    when(resultSet.getString(RESULT_SET_COURT_INSTANCE)).thenReturn(CourtInstance.APPEAL.toString());
    when(resultSet.next()).thenReturn(true);
    court = courtCrud.createEntityFromParameters(request, COURT_ID_VALUE);
    assertEquals(COURT_ID_VALUE, court.getId());
    assertEquals(MAIN_COURT_ID_VALUE, court.getMainCourtId());
  }

  @BeforeAll
  static void beforeAll() throws SQLException, DbPoolException {
    System.out.println("Start testing CourtCrudTest");
    courtCrud = new CourtCrud();
    initDbPool();
    AbstractEntityDao.setDbPool(dbPool);
  }

  @AfterAll
  static void afterAll() {
    System.out.println("Finish testing CourtCrudTest");
  }

}