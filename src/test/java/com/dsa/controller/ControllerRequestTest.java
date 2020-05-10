package com.dsa.controller;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerRequestTest {

  private static final String INCORRECT_KEY_NAME = "INCORRECT";
  private static final String KEY_PARAMETER = "KEY_PARAMETER";
  private static final String VALUE_PARAMETER = "VALUE_PARAMETER";
  private static final String KEY_SESSION_ATTRIBUTE = "KEY_SESSION_ATTRIBUTE";
  private static final String VALUE_SESSION_ATTRIBUTE = "VALUE_SESSION_ATTRIBUTE";
  private static final String[] COMMIT_TEXT = {"command=value", "commit=yes"};

  private static ControllerRequest controllerRequest;

  @Test
  void getParameter() {
    System.out.println("Start getParameter");
    assertEquals("", controllerRequest.getParameter(INCORRECT_KEY_NAME));
    controllerRequest.setParameter(KEY_PARAMETER, VALUE_PARAMETER);
    assertEquals(VALUE_PARAMETER, controllerRequest.getParameter(KEY_PARAMETER));
  }

  @Test
  void setParameter() {
    System.out.println("Start setParameter");
    controllerRequest.setParameter(KEY_PARAMETER, VALUE_PARAMETER);
    assertEquals(VALUE_PARAMETER, controllerRequest.getParameter(KEY_PARAMETER));
    controllerRequest.setParameter(VALUE_PARAMETER + "=" + KEY_PARAMETER);
    assertEquals(KEY_PARAMETER, controllerRequest.getParameter(VALUE_PARAMETER));
  }

  @Test
  void getSessionAttribute() {
    System.out.println("Start getSessionAttribute");
    controllerRequest.setSessionAttribute(KEY_SESSION_ATTRIBUTE, VALUE_SESSION_ATTRIBUTE);
    assertEquals(VALUE_SESSION_ATTRIBUTE, controllerRequest.getSessionAttribute(KEY_SESSION_ATTRIBUTE));
  }

  @Test
  void loadNextCommitParameters() {
    System.out.println("Start loadNextCommitParameters");
    assertTrue(controllerRequest.loadNextCommitParameters(COMMIT_TEXT));
    assertEquals(COMMIT_TEXT[0].split("=")[1], controllerRequest.getParameter(COMMIT_TEXT[0].split("=")[0]));
  }

  @BeforeAll
  static void beforeAll() {
    System.out.println("Start testing ControllerRequestTest");
    controllerRequest = new ControllerRequest();
  }

  @AfterAll
  static void afterAll() {
    System.out.println("Finish testing ControllerRequestTest");
  }

}