package com.dsa.domain.lawsuit;

import com.dsa.domain.IEntity;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LawsuitConstTest {

  private static final String ID = IEntity.ID;
  private static final String ENTITY_NAME = "Lawsuit";
  private static final String SUE_DATE = "sueDate";
  private static final String COURT_ID = "courtId";
  private static final String SUITOR_ID = "suitorId";
  private static final String CLAIM_TEXT = "claimText";
  private static final String DEFENDANT_ID = "defendantId";
  private static final String DEFENDANT_TEXT = "defendantText";
  private static final String JUDGE_ID = "judgeId";
  private static final String START_DATE = "startDate";
  private static final String VERDICT_DATE = "verdictDate";
  private static final String VERDICT_TEXT = "verdictText";
  private static final String APPEALED_LAWSUIT_ID = "appealedLawsuitId";
  private static final String APPEALED_STATUS = "appealStatus";
  private static final String EXECUTION_DATE = "executionDate";
  private static final String PATH = "/lawsuits/";

  @Test
  void constantsTest() {
    System.out.println("Start constantsTest");
    assertEquals(ID, LawsuitConst.ID);
    assertEquals(ENTITY_NAME, LawsuitConst.ENTITY_NAME);
    assertEquals(SUE_DATE, LawsuitConst.SUE_DATE);
    assertEquals(COURT_ID, LawsuitConst.COURT_ID);
    assertEquals(SUITOR_ID, LawsuitConst.SUITOR_ID);
    assertEquals(CLAIM_TEXT, LawsuitConst.CLAIM_TEXT);
    assertEquals(DEFENDANT_ID, LawsuitConst.DEFENDANT_ID);
    assertEquals(DEFENDANT_TEXT, LawsuitConst.DEFENDANT_TEXT);
    assertEquals(JUDGE_ID, LawsuitConst.JUDGE_ID);
    assertEquals(START_DATE, LawsuitConst.START_DATE);
    assertEquals(VERDICT_DATE, LawsuitConst.VERDICT_DATE);
    assertEquals(VERDICT_TEXT, LawsuitConst.VERDICT_TEXT);
    assertEquals(APPEALED_LAWSUIT_ID, LawsuitConst.APPEALED_LAWSUIT_ID);
    assertEquals(APPEALED_STATUS, LawsuitConst.APPEALED_STATUS);
    assertEquals(EXECUTION_DATE, LawsuitConst.EXECUTION_DATE);
  }

  @Test
  void getPath_Test() {
    System.out.println("Start getPath_Test");
    assertEquals(PATH, LawsuitConst.getPath());
  }

  @BeforeAll
  static void beforeAll() {
    System.out.println("Start testing LawsuitConstTest");
  }

  @AfterAll
  static void afterAll() {
    System.out.println("Finish testing LawsuitConstTest");
  }

}