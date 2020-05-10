package com.dsa.domain.court;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourtTest {

  private static Court court;

  @Test
  void getMainCourt() {
    System.out.println("Start getMainCourt");
    court.setMainCourt(null);
    assertNull(court.getMainCourt());
    assertEquals(0, court.getMainCourtId());
    Court mainCourt = new Court(1, "", CourtInstance.LOCAL, null);
    court.setMainCourt(mainCourt);
    assertEquals(mainCourt, court.getMainCourt());
    assertEquals(1, court.getMainCourtId());
  }

  @Test
  void getMainCourtId() {
    System.out.println("Start getMainCourtId");
    court.setMainCourt(null);
    assertEquals(0, court.getMainCourtId());
    court.setMainCourtId(1);
    assertEquals(1, court.getMainCourtId());
  }

  @BeforeAll
  static void beforeAll() {
    System.out.println("Start testing CourtTest");
    court = new Court();
  }

  @AfterAll
  static void afterAll() {
    System.out.println("Finish testing CourtTest");
  }

}