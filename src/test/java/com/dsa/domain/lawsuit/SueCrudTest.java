package com.dsa.domain.lawsuit;

import com.dsa.controller.ControllerRequest;
import com.dsa.dao.AbstractEntityDao;
import com.dsa.dao.DbPoolException;
import com.dsa.domain.MyEntity;
import com.dsa.domain.user.Role;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import static com.dsa.InitDbForTests.*;

import static com.dsa.domain.lawsuit.LawsuitConst.*;
import static com.dsa.domain.user.UserConst.ROLE;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SueCrudTest {
//todo: test

  private static final String PATH = "/sues/";
  private static final String USER_SESSION_ID = "user_id";
  private static final String USER_SESSION_ID_VALUE = "1";

  private static SueCrud sueCrud;

  @Test
  void pathTest() {
    System.out.println("Start pathTest");
    assertEquals(PATH, SueCrud.path);
  }

  @Test
  void checkAuthority() throws SQLException {
    System.out.println("Start checkAuthority empty user");
    ControllerRequest request = Mockito.mock(ControllerRequest.class);
    assertFalse(sueCrud.checkAuthority(request));
    System.out.println("Start checkAuthority incorrect user");
    verify(request).getSessionAttribute(USER_SESSION_ID);
    when(request.getSessionAttribute(USER_SESSION_ID)).thenReturn(USER_SESSION_ID_VALUE);
    assertFalse(sueCrud.checkAuthority(request));
    verify(connection).prepareStatement(Mockito.anyString());
    verify(preparedStatement).executeQuery();
    verify(resultSet).next();
    System.out.println("Start checkAuthority incorrect user. Exception nullPointer (empty role) ");
    when(resultSet.next()).thenReturn(true);
    assertThrows(NullPointerException.class, () -> sueCrud.checkAuthority(request));
    System.out.println("Start checkAuthority user - Judge");
    when(resultSet.getString(ROLE)).thenReturn(Role.JUDGE.toString());
    assertFalse(sueCrud.checkAuthority(request));
    System.out.println("Start checkAuthority user - ATTORNEY");
    when(resultSet.getString(ROLE)).thenReturn(Role.ATTORNEY.toString());
    assertTrue(sueCrud.checkAuthority(request));
  }

  @Test
  void createEntityFromParameters() throws SQLException, ParseException {
    ControllerRequest request = Mockito.mock(ControllerRequest.class);
    System.out.println("Start createEntityFromParameters - empty ID - create sue");
    assertNull(sueCrud.createEntityFromParameters(request, 0));
    System.out.println("Start createEntityFromParameters - has values for sue");
    final String SUITOR_ID_VALUE = "3";
    final String DEFENDANT_ID_VALUE = "4";
    final String COURT_ID_VALUE = "5";
    final String APPEALED_LAWSUIT_ID_VALUE = "6";
    final String SUE_DATE_VALUE = MyEntity.dateToStr(new Date());
    final String CLAIM_TEXT_VALUE = "CLAIM_TEXT_VALUE";
    when(request.getParameter(SUITOR_ID)).thenReturn(SUITOR_ID_VALUE);
    when(request.getParameter(DEFENDANT_ID)).thenReturn(DEFENDANT_ID_VALUE);
    when(request.getParameter(COURT_ID)).thenReturn(COURT_ID_VALUE);
    when(request.getParameter(APPEALED_LAWSUIT_ID)).thenReturn(APPEALED_LAWSUIT_ID_VALUE);
    when(request.getParameter(SUE_DATE)).thenReturn(SUE_DATE_VALUE);
    when(request.getParameter(CLAIM_TEXT)).thenReturn(CLAIM_TEXT_VALUE);
    Lawsuit lawsuit = sueCrud.createEntityFromParameters(request, 0);
    assertNotNull(lawsuit);
    assertEquals(0, lawsuit.getId());
    assertEquals(MyEntity.strToDate(SUE_DATE_VALUE), lawsuit.getSueDate());
    assertEquals(CLAIM_TEXT_VALUE, lawsuit.getClaimText());
    assertEquals(Long.parseLong(SUITOR_ID_VALUE), lawsuit.getSuitorId());
    assertEquals(Long.parseLong(DEFENDANT_ID_VALUE), lawsuit.getDefendantId());
    assertEquals(Long.parseLong(COURT_ID_VALUE), lawsuit.getCourtId());
    assertEquals(Long.parseLong(APPEALED_LAWSUIT_ID_VALUE), lawsuit.getAppealedLawsuitId());
    System.out.println("Start createEntityFromParameters - exists ID - edit lawsuit");
    final long SUE_ID = 9L;
    System.out.println("Start createEntityFromParameters - can't read lawsuit");
    when(resultSet.next()).thenReturn(false);
    assertNull(sueCrud.createEntityFromParameters(request, SUE_ID));
    System.out.println("Start createEntityFromParameters - can read lawsuit");
    when(resultSet.next()).thenReturn(true);
    when(request.getParameter(DEFENDANT_TEXT)).thenReturn(DEFENDANT_TEXT);
    when(request.getParameter(VERDICT_TEXT)).thenReturn(VERDICT_TEXT);
    when(request.getParameter(JUDGE_ID)).thenReturn("");
    when(request.getParameter(START_DATE)).thenReturn("");
    when(request.getParameter(VERDICT_DATE)).thenReturn("");
    when(request.getParameter(APPEALED_STATUS)).thenReturn(APPEALED_STATUS);
    when(request.getParameter(EXECUTION_DATE)).thenReturn("");
    when(resultSet.getLong(ID)).thenReturn(SUE_ID);
    lawsuit = sueCrud.createEntityFromParameters(request, SUE_ID);
    assertNotNull(lawsuit);
    assertEquals(SUE_ID, lawsuit.getId());
    assertEquals(DEFENDANT_TEXT, lawsuit.getDefendantText());
    assertEquals(VERDICT_TEXT, lawsuit.getVerdictText());
    assertEquals(APPEALED_STATUS, lawsuit.getAppealStatus());
    assertNull(lawsuit.getExecutionDate());
    assertNull(lawsuit.getVerdictDate());
    assertNull(lawsuit.getStartDate());
    assertNull(lawsuit.getExecutionDate());
    assertEquals(0, lawsuit.getJudgeId());
    assertEquals(0, lawsuit.getDefendantId());
  }

  @Test
  void createEntityDao() throws SQLException, DbPoolException {
    System.out.println("Start createEntityDao");
    assertNotNull(sueCrud.createEntityDao());
  }

  @BeforeAll
  static void beforeAll() throws SQLException, DbPoolException {
    System.out.println("Start testing SueCrudTest");
    sueCrud = new SueCrud();
    initDbPool();
    AbstractEntityDao.setDbPool(dbPool);
  }

  @AfterAll
  static void afterAll() {
    System.out.println("Finish testing SueCrudTest");
  }

}