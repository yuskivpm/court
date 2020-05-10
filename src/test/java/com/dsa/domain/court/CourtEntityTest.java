package com.dsa.domain.court;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourtEntityTest {

  private static CourtEntity courtEntity;

  @Test
  void getCourtName() {
    System.out.println("Start getCourtName");
    final String COURT_NAME = "COURT_NAME";
    courtEntity.setCourtName("");
    assertEquals("", courtEntity.getCourtName());
    courtEntity.setCourtName(COURT_NAME);
    assertEquals(COURT_NAME, courtEntity.getCourtName());
  }

  @Test
  void getCourtInstance() {
    System.out.println("Start getCourtInstance");
    courtEntity.setCourtInstance(CourtInstance.LOCAL);
    assertEquals(CourtInstance.LOCAL, courtEntity.getCourtInstance());
    courtEntity.setCourtInstance(CourtInstance.CASSATION);
    assertEquals(CourtInstance.CASSATION, courtEntity.getCourtInstance());
  }

  @Test
  void getMainCourtId() {
    System.out.println("Start getMainCourtId");
    courtEntity.setMainCourtId(0);
    assertEquals(0, courtEntity.getMainCourtId());
    courtEntity.setMainCourtId(1);
    assertEquals(1, courtEntity.getMainCourtId());
  }

  @Test
  void testHashCode() {
    System.out.println("Start testHashCode");
    assertNotEquals(0, courtEntity.hashCode());
  }

  @Test
  void testToString() {
    System.out.println("Start testToString");
    assertNotEquals("", courtEntity.toString());
  }

  @BeforeAll
  static void beforeAll() {
    System.out.println("Start testing CourtEntityTest");
    courtEntity = new CourtEntity();
  }

  @AfterAll
  static void afterAll() {
    System.out.println("Finish testing CourtEntityTest");
  }

}