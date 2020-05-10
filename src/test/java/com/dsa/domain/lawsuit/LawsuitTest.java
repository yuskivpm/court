package com.dsa.domain.lawsuit;

import com.dsa.domain.court.Court;
import com.dsa.domain.court.CourtInstance;
import com.dsa.domain.user.Role;
import com.dsa.domain.user.User;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LawsuitTest {

  private static Lawsuit lawsuit;

  @Test
  void getSuitor() {
    System.out.println("Start testing getSuitor");
    lawsuit.setSuitor(null);
    assertNull(lawsuit.getSuitor());
    assertEquals(0, lawsuit.getSuitorId());
    User user = new User(1, null, null, Role.JUDGE, null, null, false);
    lawsuit.setSuitor(user);
    assertEquals(user, lawsuit.getSuitor());
    assertEquals(1, lawsuit.getSuitorId());
  }

  @Test
  void getDefendant() {
    System.out.println("Start testing getDefendant");
    lawsuit.setDefendant(null);
    assertNull(lawsuit.getDefendant());
    assertEquals(0, lawsuit.getDefendantId());
    User user = new User(1, null, null, Role.JUDGE, null, null, false);
    lawsuit.setDefendant(user);
    assertEquals(user, lawsuit.getDefendant());
    assertEquals(1, lawsuit.getDefendantId());
  }

  @Test
  void getCourt() {
    System.out.println("Start testing getCourt");
    lawsuit.setCourt(null);
    assertNull(lawsuit.getCourt());
    assertEquals(0, lawsuit.getCourtId());
    Court court = new Court(1, "", CourtInstance.LOCAL, null);
    lawsuit.setCourt(court);
    assertEquals(court, lawsuit.getCourt());
    assertEquals(1, lawsuit.getCourtId());
  }

  @Test
  void getJudge() {
    System.out.println("Start testing getJudge");
    lawsuit.setJudge(null);
    assertNull(lawsuit.getJudge());
    assertEquals(0, lawsuit.getJudgeId());
    User user = new User(1, null, null, Role.JUDGE, null, null, false);
    lawsuit.setJudge(user);
    assertEquals(user, lawsuit.getJudge());
    assertEquals(1, lawsuit.getJudgeId());
  }

  @Test
  void getAppealedLawsuit() {
    System.out.println("Start testing getAppealedLawsuit");
    lawsuit.setAppealedLawsuit(null);
    assertNull(lawsuit.getAppealedLawsuit());
    assertEquals(0, lawsuit.getAppealedLawsuitId());
    Lawsuit appealedLawsuit = new Lawsuit(1, null, null, null, null, null, null, null, null, null, null, null, null, null);
    lawsuit.setAppealedLawsuit(appealedLawsuit);
    assertEquals(appealedLawsuit, lawsuit.getAppealedLawsuit());
    assertEquals(1, lawsuit.getAppealedLawsuitId());
  }

  @Test
  void getSuitorId() {
    System.out.println("Start testing getSuitorId");
    lawsuit.setSuitor(null);
    assertEquals(0, lawsuit.getSuitorId());
    lawsuit.setSuitorId(1);
    assertEquals(1, lawsuit.getSuitorId());
  }

  @Test
  void getDefendantId() {
    System.out.println("Start testing getDefendantId");
    lawsuit.setDefendant(null);
    assertEquals(0, lawsuit.getDefendantId());
    lawsuit.setDefendantId(1);
    assertEquals(1, lawsuit.getDefendantId());
  }

  @Test
  void getCourtId() {
    System.out.println("Start testing getCourtId");
    lawsuit.setCourt(null);
    assertEquals(0, lawsuit.getCourtId());
    lawsuit.setCourtId(1);
    assertEquals(1, lawsuit.getCourtId());
  }

  @Test
  void getJudgeId() {
    System.out.println("Start testing getJudgeId");
    lawsuit.setJudge(null);
    assertEquals(0, lawsuit.getJudgeId());
    lawsuit.setJudgeId(1);
    assertEquals(1, lawsuit.getJudgeId());
  }

  @Test
  void getAppealedLawsuitId() {
    System.out.println("Start testing getAppealedLawsuitId");
    lawsuit.setAppealedLawsuit(null);
    assertEquals(0, lawsuit.getAppealedLawsuitId());
    lawsuit.setAppealedLawsuitId(1);
    assertEquals(1, lawsuit.getAppealedLawsuitId());
  }

  @BeforeAll
  static void beforeAll() {
    System.out.println("Start testing LawsuitTest");
    lawsuit = new Lawsuit();
  }

  @AfterAll
  static void afterAll() {
    System.out.println("Finish testing LawsuitTest");
  }

}