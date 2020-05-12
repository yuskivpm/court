package com.dsa.domain.lawsuit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class LawsuitEntityTest {

  private static LawsuitEntity lawsuitEntity;

  @Test
  void getSueDate() {
    System.out.println("Start getSueDate");
    Date date = new Date();
    lawsuitEntity.setSueDate(null);
    assertNull(lawsuitEntity.getSueDate());
    lawsuitEntity.setSueDate(date);
    assertEquals(date, lawsuitEntity.getSueDate());
  }

  @Test
  void getCourtId() {
    System.out.println("Start getCourtId");
    lawsuitEntity.setCourtId(0);
    assertEquals(0, lawsuitEntity.getCourtId());
    lawsuitEntity.setCourtId(1);
    assertEquals(1, lawsuitEntity.getCourtId());
  }

  @Test
  void getSuitorId() {
    System.out.println("Start getSuitorId");
    lawsuitEntity.setSuitorId(0);
    assertEquals(0, lawsuitEntity.getSuitorId());
    lawsuitEntity.setSuitorId(1);
    assertEquals(1, lawsuitEntity.getSuitorId());
  }

  @Test
  void getClaimText() {
    System.out.println("Start getClaimText");
    final String VALUE = "CLAIM";
    lawsuitEntity.setClaimText("");
    assertEquals("", lawsuitEntity.getClaimText());
    lawsuitEntity.setClaimText(VALUE);
    assertEquals(VALUE, lawsuitEntity.getClaimText());
  }

  @Test
  void getDefendantId() {
    System.out.println("Start getDefendantId");
    lawsuitEntity.setDefendantId(0);
    assertEquals(0, lawsuitEntity.getDefendantId());
    lawsuitEntity.setDefendantId(1);
    assertEquals(1, lawsuitEntity.getDefendantId());
  }

  @Test
  void getDefendantText() {
    System.out.println("Start getDefendantText");
    final String VALUE = "getDefendantText";
    lawsuitEntity.setDefendantText("");
    assertEquals("", lawsuitEntity.getDefendantText());
    lawsuitEntity.setDefendantText(VALUE);
    assertEquals(VALUE, lawsuitEntity.getDefendantText());
  }

  @Test
  void getJudgeId() {
    System.out.println("Start getJudgeId");
    lawsuitEntity.setJudgeId(0);
    assertEquals(0, lawsuitEntity.getJudgeId());
    lawsuitEntity.setJudgeId(1);
    assertEquals(1, lawsuitEntity.getJudgeId());
  }

  @Test
  void getStartDate() {
    System.out.println("Start getStartDate");
    Date date = new Date();
    lawsuitEntity.setStartDate(null);
    assertNull(lawsuitEntity.getStartDate());
    lawsuitEntity.setStartDate(date);
    assertEquals(date, lawsuitEntity.getStartDate());
  }

  @Test
  void getVerdictDate() {
    System.out.println("Start getVerdictDate");
    Date date = new Date();
    lawsuitEntity.setVerdictDate(null);
    assertNull(lawsuitEntity.getVerdictDate());
    lawsuitEntity.setVerdictDate(date);
    assertEquals(date, lawsuitEntity.getVerdictDate());
  }

  @Test
  void getVerdictText() {
    System.out.println("Start getVerdictText");
    final String VALUE = "getVerdictText";
    lawsuitEntity.setVerdictText("");
    assertEquals("", lawsuitEntity.getVerdictText());
    lawsuitEntity.setVerdictText(VALUE);
    assertEquals(VALUE, lawsuitEntity.getVerdictText());
  }

  @Test
  void getExecutionDate() {
    System.out.println("Start getExecutionDate");
    Date date = new Date();
    lawsuitEntity.setExecutionDate(null);
    assertNull(lawsuitEntity.getExecutionDate());
    lawsuitEntity.setExecutionDate(date);
    assertEquals(date, lawsuitEntity.getExecutionDate());
  }

  @Test
  void getAppealedLawsuitId() {
    System.out.println("Start getAppealedLawsuitId");
    lawsuitEntity.setAppealedLawsuitId(0);
    assertEquals(0, lawsuitEntity.getAppealedLawsuitId());
    lawsuitEntity.setAppealedLawsuitId(1);
    assertEquals(1, lawsuitEntity.getAppealedLawsuitId());
  }

  @Test
  void getAppealStatus() {
    System.out.println("Start getAppealStatus");
    final String VALUE = "getAppealStatus";
    lawsuitEntity.setAppealStatus("");
    assertEquals("", lawsuitEntity.getAppealStatus());
    lawsuitEntity.setAppealStatus(VALUE);
    assertEquals(VALUE, lawsuitEntity.getAppealStatus());
  }

  @Test
  void testHashCode() {
    System.out.println("Start testHashCode");
    assertNotEquals(0, lawsuitEntity.hashCode());
  }

  @Test
  void testToString() {
    System.out.println("Start testToString");
    assertNotEquals("", lawsuitEntity.toString());
  }

  @BeforeAll
  static void beforeAll() {
    System.out.println("Start testing LawsuitEntityTest");
    lawsuitEntity = new LawsuitEntity();
  }

  @AfterAll
  static void afterAll() {
    System.out.println("Finish testing LawsuitEntityTest");
  }

}