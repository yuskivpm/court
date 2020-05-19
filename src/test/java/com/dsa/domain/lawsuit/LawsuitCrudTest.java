package com.dsa.domain.lawsuit;

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

import static com.dsa.domain.user.UserConst.ROLE;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LawsuitCrudTest {

  private static final String PATH = "/lawsuits/";
  private static final String USER_SESSION_ID = "user_id";
  private static final String USER_SESSION_ID_VALUE = "1";

  private static LawsuitCrud lawsuitCrud;

  @Test
  void pathTest() {
    System.out.println("Start pathTest");
    assertEquals(PATH, LawsuitCrud.path);
  }

  @Test
  void checkAuthority() throws SQLException {
    System.out.println("Start checkAuthority empty user");
    ControllerRequest request = Mockito.mock(ControllerRequest.class);
    assertFalse(lawsuitCrud.checkAuthority(request));
    System.out.println("Start checkAuthority incorrect user");
    verify(request).getSessionAttribute(USER_SESSION_ID);
    when(request.getSessionAttribute(USER_SESSION_ID)).thenReturn(USER_SESSION_ID_VALUE);
    assertFalse(lawsuitCrud.checkAuthority(request));
    verify(connection).prepareStatement(Mockito.anyString());
    verify(preparedStatement).executeQuery();
    verify(resultSet).next();
    System.out.println("Start checkAuthority incorrect user. Exception nullPointer (empty role) ");
    when(resultSet.next()).thenReturn(true);
    assertThrows(NullPointerException.class, () -> lawsuitCrud.checkAuthority(request));
    System.out.println("Start checkAuthority user - Admin");
    when(resultSet.getString(ROLE)).thenReturn(Role.ADMIN.toString());
    assertFalse(lawsuitCrud.checkAuthority(request));
    System.out.println("Start checkAuthority user - Judge");
    when(resultSet.getString(ROLE)).thenReturn(Role.JUDGE.toString());
    assertTrue(lawsuitCrud.checkAuthority(request));
  }

  @BeforeAll
  static void beforeAll() throws SQLException, DbPoolException {
    System.out.println("Start testing LawsuitCrudTest");
    lawsuitCrud = new LawsuitCrud();
    initDbPool();
    AbstractEntityDao.setDbPool(dbPool);
  }

  @AfterAll
  static void afterAll() {
    System.out.println("Finish testing LawsuitCrudTest");
  }

}