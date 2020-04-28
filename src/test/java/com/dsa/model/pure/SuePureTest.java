package com.dsa.model.pure;

////
////import org.junit.Test;
////import org.junit.runner.RunWith;
////import org.mockito.Mockito;
////import org.mockito.runners.MockitoJUnitRunner;
////
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;


///////////////////
// without mockito

public class SuePureTest {
  private static final int id=10;
  private static final int suitorId=20;
  private static final int defendantId=30;
  private static final int courtId=40;
  private static final String claimText="Claim text";
  private static Date sueDate=new Date();

  @BeforeAll
  public static void beforeClass(){
    System.out.println("BeforeAll");
  }

  @BeforeEach
  void beforeTest(){
    System.out.println("beforeTest");
  }

  @Test
  void testConstructor(){
    System.out.println("testConstructor");
//    SuePure suePure=new SuePure(id,suitorId,defendantId,courtId,sueDate,claimText);
//    assertEquals(suePure.getId(),id);
  }

  @AfterEach
  void afterTest(){
    System.out.println("afterTest");
  }

  @AfterAll
  public static void afterAll(){
    System.out.println("afterAll");
  }
}

//@Test
//@BeforeAll
//@AfterAll
//@BeforeEach
//@AfterEach
//@Disabled
//@TestFactory
//@Nested
//@Tag()
//@ExtendWith()
//@RepeatedTest()
//fail()
//assertTrue()
//assertFalse()
//assertSame()
//assertNotSame()
//assertNull()
//assertNotNull()
//assertEquals()
//assertNotEquals()
//assertArrayEquals()
//assertAll()
//assertTrows()
//assumeFalse()
//assumeTrue()
//assumeThat()

