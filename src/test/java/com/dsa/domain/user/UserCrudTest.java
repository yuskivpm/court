package com.dsa.domain.user;

import com.dsa.controller.ControllerRequest;
import com.dsa.dao.AbstractEntityDao;
import com.dsa.dao.DbPoolException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;

import static com.dsa.InitDbForTests.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserCrudTest {

  private static final String PATH = "/users/";
  private static final String USER_SESSION_ID = "user_id";
  private static final String USER_SESSION_ID_VALUE = "1";
  private static final String ROLE_ID = "ROLE_ID";
  private static final String NAME = "name";
  private static final String LOGIN = "login";
  private static final String PASSWORD = "password";
  private static final String ROLE = "role";
  private static final String COURT_ID = "courtId";
  private static final String IS_ACTIVE = "isActive";
  private static final String ACTIVE_IS_ON = "on";

  private static UserCrud userCrud;

  @Test
  void pathTest() {
    System.out.println("Start pathTest");
    assertEquals(PATH, UserCrud.path);
  }

  @Test
  void checkAuthority() throws SQLException {
    System.out.println("Start checkAuthority empty user");
    ControllerRequest request = Mockito.mock(ControllerRequest.class);
    assertFalse(userCrud.checkAuthority(request));
    System.out.println("Start checkAuthority incorrect user");
    verify(request).getSessionAttribute(USER_SESSION_ID);
    when(request.getSessionAttribute(USER_SESSION_ID)).thenReturn(USER_SESSION_ID_VALUE);
    assertFalse(userCrud.checkAuthority(request));
    verify(connection).prepareStatement(Mockito.anyString());
    verify(preparedStatement).executeQuery();
    verify(resultSet).next();
    System.out.println("Start checkAuthority incorrect user. Exception nullPointer (empty role) ");
    when(resultSet.next()).thenReturn(true);
    assertThrows(NullPointerException.class, () -> userCrud.checkAuthority(request));
    System.out.println("Start checkAuthority user - Judge");
    when(resultSet.getString(ROLE_ID)).thenReturn(Role.JUDGE.toString());
    assertFalse(userCrud.checkAuthority(request));
    System.out.println("Start checkAuthority user - Admin");
    when(resultSet.getString(ROLE_ID)).thenReturn(Role.ADMIN.toString());
    assertTrue(userCrud.checkAuthority(request));
  }

  @Test
  void createEntityFromParameters() {
    ControllerRequest request = Mockito.mock(ControllerRequest.class);
    System.out.println("Start createEntityFromParameters - empty court name");
    final long USER_ID_VALUE = 2L;
    final long COURT_ID_VALUE = 3L;
    final Role role = Role.JUDGE;
    when(request.getParameter(NAME)).thenReturn("");
    when(request.getParameter(LOGIN)).thenReturn("");
    when(request.getParameter(PASSWORD)).thenReturn("");
    assertNull(userCrud.createEntityFromParameters(request, USER_ID_VALUE));
    System.out.println("Start createEntityFromParameters - exception - incorrect Role");
    when(request.getParameter(NAME)).thenReturn(NAME);
    when(request.getParameter(LOGIN)).thenReturn(LOGIN);
    when(request.getParameter(PASSWORD)).thenReturn(PASSWORD);
    assertThrows(NullPointerException.class, () -> userCrud.createEntityFromParameters(request, USER_ID_VALUE));
    System.out.println("Start createEntityFromParameters - exception - incorrect Court Id");
    when(request.getParameter(ROLE)).thenReturn(role.toString());
    assertThrows(NumberFormatException.class, () -> userCrud.createEntityFromParameters(request, USER_ID_VALUE));
    System.out.println("Start createEntityFromParameters - exception - correct Court Id");
    when(request.getParameter(COURT_ID)).thenReturn(Long.toString(COURT_ID_VALUE));
    when(request.getParameter(IS_ACTIVE)).thenReturn(ACTIVE_IS_ON);
    User user = userCrud.createEntityFromParameters(request, USER_ID_VALUE);
    assertNotNull(user);
    assertEquals(USER_ID_VALUE, user.getId());
    assertEquals(NAME, user.getName());
    assertEquals(LOGIN, user.getLogin());
    assertEquals(PASSWORD, user.getPassword());
    assertEquals(role, user.getRole());
    assertEquals(COURT_ID_VALUE, user.getCourtId());
    assertTrue(user.getIsActive());
  }

  @Test
  void createEntityDao() throws SQLException, DbPoolException {
    System.out.println("Start createEntityDao");
    UserDao userDao = userCrud.createEntityDao();
    assertNotNull(userDao);
  }

  @BeforeAll
  static void beforeAll() throws SQLException, DbPoolException {
    System.out.println("Start testing UserCrudTest");
    userCrud = new UserCrud();
    initDbPool();
    AbstractEntityDao.setDbPool(dbPool);
  }

  @AfterAll
  static void afterAll() {
    System.out.println("Finish testing UserCrudTest");
  }

}