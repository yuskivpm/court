package com.dsa.domain.user;

import com.dsa.domain.court.Court;
import com.dsa.domain.court.CourtInstance;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

  private static User user;

  @Test
  void getCourt() {
    System.out.println("Start getCourt");
    user.setCourt(null);
    assertNull(user.getCourt());
    assertEquals(0, user.getCourtId());
    Court court = new Court(1, "", CourtInstance.LOCAL, null);
    user.setCourt(court);
    assertEquals(court, user.getCourt());
    assertEquals(1, user.getCourtId());
  }

  @Test
  void getCourtId() {
    System.out.println("Start getCourtId");
    user.setCourt(null);
    assertEquals(0, user.getCourtId());
    user.setRole(Role.ATTORNEY);
    user.setCourtId(1);
    assertEquals(0, user.getCourtId());
    user.setRole(Role.ADMIN);
    user.setCourtId(1);
    assertEquals(0, user.getCourtId());
    user.setRole(Role.JUDGE);
    user.setCourtId(1);
    assertEquals(1, user.getCourtId());
  }

  @BeforeAll
  static void beforeAll() {
    System.out.println("Start testing UserTest");
    user = new User();
  }

  @AfterAll
  static void afterAll() {
    System.out.println("Finish testing UserTest");
  }

}