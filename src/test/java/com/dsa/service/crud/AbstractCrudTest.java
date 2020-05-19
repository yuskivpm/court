package com.dsa.service.crud;

import com.dsa.domain.MyEntity;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class AbstractCrudTest {

  @Test
  void idValue_Test() {
    System.out.println("Start idValue_Test");
    assertEquals(MyEntity.ID, AbstractCrud.ID);
  }

  @Test
  void getNotNull_String_String_Test() {
    System.out.println("Start getNotNull_String_String_Test");
    final String STRING_VALUE = "STRING_VALUE";
    final String DEFAULT_STRING_VALUE = "DEFAULT_STRING_VALUE";
    assertEquals(STRING_VALUE, AbstractCrud.getNotNull("", STRING_VALUE));
    assertEquals(DEFAULT_STRING_VALUE, AbstractCrud.getNotNull(DEFAULT_STRING_VALUE, ""));
  }

  @Test
  void getNotNull_String_Date_Test() throws ParseException {
    System.out.println("Start getNotNull_String_Date_Test");
    final Date DEFAULT_VALUE = new Date();
    Date date = null;
    assertEquals(DEFAULT_VALUE, AbstractCrud.getNotNull("", DEFAULT_VALUE));
    assertEquals(MyEntity.strToDate(MyEntity.dateToStr(DEFAULT_VALUE)), AbstractCrud.getNotNull(MyEntity.dateToStr(DEFAULT_VALUE), date));
  }

  @Test
  void getNotNull_String_Long_Test() {
    System.out.println("Start getNotNull_String_Long_Test");
    final long LONG_VALUE = 5L;
    final String STRING_VALUE = "7";
    assertEquals(LONG_VALUE, AbstractCrud.getNotNull("", LONG_VALUE));
    assertEquals(Long.parseLong(STRING_VALUE), AbstractCrud.getNotNull(STRING_VALUE, 0));
  }

  @BeforeAll
  static void beforeAll() {
    System.out.println("Start testing AbstractCrudTest");
  }

  @AfterAll
  static void afterAll() {
    System.out.println("Finish testing AbstractCrudTest");
  }

}