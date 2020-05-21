package com.dsa.domain.court;

import com.dsa.controller.ControllerRequest;
import com.dsa.controller.ResponseType;
import com.dsa.dao.AbstractEntityDao;
import com.dsa.dao.DbPoolException;
import com.dsa.domain.user.Role;
import com.dsa.service.crud.AbstractCrud;
import com.dsa.service.crud.CrudEnum;
import com.dsa.service.crud.CrudParser;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;

import static com.dsa.InitDbForTests.*;

import static com.dsa.domain.court.CourtConst.*;
import static com.dsa.domain.user.UserConst.ROLE;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourtCrudTest {

  private static final String PATH = "/courts/";
  private static final String USER_SESSION_ID = "user_id";
  private static final String USER_SESSION_ID_VALUE = "1";

  private static CourtCrud courtCrud;
  private static CrudParser crudParser;

  @Test
  void pathTest() {
    System.out.println("Start pathTest");
    assertEquals(PATH, CourtCrud.path);
    assertEquals(PATH, new CourtCrud().getPath());
  }

  @Test
  void checkAuthority() throws SQLException, DbPoolException {
    System.out.println("Start checkAuthority empty user");
    ControllerRequest request = Mockito.mock(ControllerRequest.class);
    createConnection();
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
    when(resultSet.getString(ROLE)).thenReturn(Role.JUDGE.toString());
    assertFalse(courtCrud.checkAuthority(request));
    System.out.println("Start checkAuthority user - Admin");
    when(resultSet.getString(ROLE)).thenReturn(Role.ADMIN.toString());
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
    when(resultSet.getString(COURT_INSTANCE)).thenReturn(CourtInstance.APPEAL.toString());
    when(resultSet.next()).thenReturn(true);
    court = courtCrud.createEntityFromParameters(request, COURT_ID_VALUE);
    assertEquals(COURT_ID_VALUE, court.getId());
    assertEquals(MAIN_COURT_ID_VALUE, court.getMainCourtId());
  }

  @Test
  void apply_fail_test() throws SQLException {
    System.out.println("Start apply_fail_test");
    ControllerRequest request = Mockito.mock(ControllerRequest.class);
    assertNotNull(courtCrud.apply(request));
    verify(request).setResponseType(ResponseType.FAIL);
  }

  @Test
  void apply_correct_authority_PREPARE_UPDATE_FORM_test() throws SQLException {
    System.out.println("Start apply_correct_authority_PREPARE_UPDATE_FORM_test");
    ControllerRequest request = Mockito.mock(ControllerRequest.class);
    System.out.println("Start apply - correct authority - PREPARE_UPDATE_FORM");
    when(request.getSessionAttribute(USER_SESSION_ID)).thenReturn("1");
    createResultSet();
    when(resultSet.getString(ROLE)).thenReturn(Role.ADMIN.toString());
    when(resultSet.next()).thenReturn(true);
    when(crudParser.getCrudOperation(request, courtCrud.getPath())).thenReturn(CrudEnum.PREPARE_UPDATE_FORM);
    when(resultSet.getString(COURT_INSTANCE)).thenReturn(CourtInstance.LOCAL.toString());
    assertNotNull(courtCrud.apply(request));
    verify(request).setResponseType(ResponseType.FORWARD);
    verify(request).setResponseValue(null);
    verify(request).setAttribute(eq("editEntity"), Mockito.any());
    System.out.println("Start apply - PREPARE_UPDATE_FORM - exception");
    when(request.getParameter("page")).thenThrow(NullPointerException.class);
    assertNotNull(courtCrud.apply(request));
    verify(request).setResponseType(ResponseType.FAIL);
  }

  @Test
  void apply_correct_authority_CREATE_test() throws SQLException {
    System.out.println("Start apply_correct_authority_CREATE_test");
    ControllerRequest request = Mockito.mock(ControllerRequest.class);
    when(request.getSessionAttribute(USER_SESSION_ID)).thenReturn("1");
    createResultSet();
    when(resultSet.getString(ROLE)).thenReturn(Role.ADMIN.toString());
    when(resultSet.next()).thenReturn(true);
    when(crudParser.getCrudOperation(request, courtCrud.getPath())).thenReturn(CrudEnum.CREATE);
    when(resultSet.getString(COURT_INSTANCE)).thenReturn(CourtInstance.LOCAL.toString());
    when(request.getParameter(ID)).thenReturn("1");
    when(request.getParameter(COURT_NAME)).thenReturn("");
    assertNotNull(courtCrud.apply(request));
    verify(request).setResponseType(ResponseType.PLAIN_TEXT);
    verify(request).setResponseValue(startsWith("{\"error\":\"All fields are required\"}"));
    System.out.println("Start apply_correct_authority_CREATE_test - correct id");
    request = Mockito.mock(ControllerRequest.class);
    when(crudParser.getCrudOperation(request, courtCrud.getPath())).thenReturn(CrudEnum.CREATE);
    when(request.getParameter(COURT_INSTANCE)).thenReturn(CourtInstance.CASSATION.toString());
    when(request.getParameter(MAIN_COURT_ID)).thenReturn("0");
    when(request.getParameter(COURT_NAME)).thenReturn(COURT_NAME);
    when(preparedStatement.executeUpdate()).thenReturn(1);
    when(request.getSessionAttribute(USER_SESSION_ID)).thenReturn("1");
    when(request.getParameter(ID)).thenReturn("1");
    assertNotNull(courtCrud.apply(request));
    verify(request).setResponseValue(startsWith("{\"status\":\"ok\"}"));
  }

  @Test
  void apply_correct_authority_READ_ALL_test() throws SQLException {
    System.out.println("Start apply_correct_authority_READ_ALL_test");
    ControllerRequest request = Mockito.mock(ControllerRequest.class);
    when(request.getSessionAttribute(USER_SESSION_ID)).thenReturn("1");
    createResultSet();
    when(resultSet.getString(ROLE)).thenReturn(Role.ADMIN.toString());
    when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    when(crudParser.getCrudOperation(request, courtCrud.getPath())).thenReturn(CrudEnum.READ_ALL);
    when(resultSet.getString(COURT_INSTANCE)).thenReturn(CourtInstance.LOCAL.toString());
    when(request.getParameter(ID)).thenReturn("1");
    assertNotNull(courtCrud.apply(request));
    verify(request).setResponseType(ResponseType.PLAIN_TEXT);
    verify(request).setResponseValue(startsWith("{\"status\":\"ok\"[{\"entity\":\"Court\","));
  }

  @Test
  void apply_correct_authority_READ_test() throws SQLException {
    System.out.println("Start apply_correct_authority_READ_test");
    ControllerRequest request = Mockito.mock(ControllerRequest.class);
    when(request.getSessionAttribute(USER_SESSION_ID)).thenReturn("1");
    createResultSet();
    when(resultSet.getString(ROLE)).thenReturn(Role.ADMIN.toString());
    when(resultSet.next()).thenReturn(true);
    when(crudParser.getCrudOperation(request, courtCrud.getPath())).thenReturn(CrudEnum.READ);
    when(resultSet.getString(COURT_INSTANCE)).thenReturn(CourtInstance.LOCAL.toString());
    when(request.getParameter(ID)).thenReturn("1");
    assertNotNull(courtCrud.apply(request));
    verify(request).setResponseType(ResponseType.PLAIN_TEXT);
    verify(request).setResponseValue(startsWith("{\"status\":\"ok\"{\"entity\":\"Court\","));
  }

  @Test
  void apply_correct_authority_DELETE_test() throws SQLException {
    System.out.println("Start apply_correct_authority_DELETE_test");
    ControllerRequest request = Mockito.mock(ControllerRequest.class);
    when(request.getSessionAttribute(USER_SESSION_ID)).thenReturn("1");
    createResultSet();
    when(resultSet.getString(ROLE)).thenReturn(Role.ADMIN.toString());
    when(resultSet.next()).thenReturn(true);
    when(crudParser.getCrudOperation(request, courtCrud.getPath())).thenReturn(CrudEnum.DELETE);
    when(resultSet.getString(COURT_INSTANCE)).thenReturn(CourtInstance.LOCAL.toString());
    when(request.getParameter(ID)).thenReturn("1");
    when(preparedStatement.executeUpdate()).thenReturn(1);
    assertNotNull(courtCrud.apply(request));
    verify(request).setResponseType(ResponseType.PLAIN_TEXT);
    verify(request).setResponseValue(startsWith("{\"status\":\"ok\"}"));
  }

  @BeforeAll
  static void beforeAll() throws SQLException, DbPoolException {
    System.out.println("Start testing CourtCrudTest");
    courtCrud = new CourtCrud();
    initDbPool();
    AbstractEntityDao.setDbPool(dbPool);
    crudParser = Mockito.mock(CrudParser.class);
    AbstractCrud.setCrudParser(crudParser);
  }

  @AfterAll
  static void afterAll() {
    System.out.println("Finish testing CourtCrudTest");
  }

}