package com.dsa.domain.court;

import com.dsa.domain.MyEntity;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourtConstTest {

  private static final String ID = MyEntity.ID;
  private static final String ENTITY_NAME = "Court";
  private static final String COURT_NAME = "courtName";
  private static final String COURT_INSTANCE = "courtInstance";
  private static final String MAIN_COURT_ID = "mainCourtId";
  private static final String PATH = "/courts/";

  @Test
  void constants_Test() {
    System.out.println("Start constantsTest");
    assertEquals(ID, CourtConst.ID);
    assertEquals(ENTITY_NAME, CourtConst.ENTITY_NAME);
    assertEquals(COURT_NAME, CourtConst.COURT_NAME);
    assertEquals(COURT_INSTANCE, CourtConst.COURT_INSTANCE);
    assertEquals(MAIN_COURT_ID, CourtConst.MAIN_COURT_ID);
  }

  @Test
  void getPath_Test() {
    System.out.println("Start getPath_Test");
    assertEquals(PATH, CourtConst.getPath());
  }

  @BeforeAll
  static void beforeAll() {
    System.out.println("Start testing CourtConstTest");
  }

  @AfterAll
  static void afterAll() {
    System.out.println("Finish testing CourtConstTest");
  }

}