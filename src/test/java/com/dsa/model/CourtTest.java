package com.dsa.model;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;

//test parametrized (multi arg call)
class CourtTest {

  @BeforeAll
  public static void beforeClass(){
    System.out.println("BeforeAll");
  }

  @BeforeEach
  void beforeTest() {
    System.out.println("beforeTest");
  }

  @ParameterizedTest
  @ValueSource(ints = {1, 3, 5, -3, 15, Integer.MAX_VALUE}) // six numbers
  void getMainCourt(int number) {
    System.out.println("getMainCourt");
    System.out.println("courtId="+number);
  }

  @ParameterizedTest
  @ValueSource(strings = {"Supreme", "Appeal"})
//  short (with the shorts attribute)
//  byte (with the bytes attribute)
//  int (with the ints attribute)
//  long  (with the longs attribute)
//  float (with the floats attribute)
//  double (with the doubles attribute)
//  char (with the chars attribute)
//java.lang.String (with the strings attribute)
//      java.lang.Class (with the classes attribute)
//  @NullSource - for class
//  @EmptySource - for primitive
//  @NullAndEmptySource
//  @EnumSource(CourtInstance.class)
  void setMainCourt(String CourtLevel) {
    System.out.println("setMainCourt");
    System.out.println("CourtLevel="+CourtLevel);
  }

  @Test
  void getMainCourtId() {
    System.out.println("getMainCourtId");
  }

  @AfterEach
  void afterTest(){
    System.out.println("afterTest");
  }

  @AfterAll
  public static void afterAll(){
    System.out.println("afterAll");
  }

  @ParameterizedTest
  @CsvSource({"test,TEST", "tEst,TEST", "Java,JAVA"})
//  @CsvSource(value = {"test:test", "tEst:test", "Java:java"}, delimiter = ':')
//  @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
  void toUpperCase_ShouldGenerateTheExpectedUppercaseValue(String input, String expected) {
    String actualValue = input.toUpperCase();
    assertEquals(expected, actualValue);
  }

  @ParameterizedTest
  @MethodSource
//  @MethodSource("isBlank_ShouldReturnTrueForNullOrBlankStringsOneArgument")
  void isBlank_ShouldReturnTrueForNullOrBlankStringsOneArgument(String input) {
    // hmm, no method name ...
//  When we don't provide a name for the @MethodSource, JUnit will search for a source method with the same name as the test method.
//    assertTrue(Strings.isBlank(input));
    System.out.println("isBlank_ShouldReturnTrueForNullOrBlankStringsOneArgument="+input);
  }

  private static Stream<String> isBlank_ShouldReturnTrueForNullOrBlankStringsOneArgument() {
    return Stream.of(null, "Supreme", "Appeal");
  }
}