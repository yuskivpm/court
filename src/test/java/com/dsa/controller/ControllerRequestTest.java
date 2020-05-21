package com.dsa.controller;

import com.dsa.service.resource.ConfigManager;
import com.dsa.service.resource.MessageManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerRequestTest {

  private static final String INCORRECT_KEY_NAME = "INCORRECT";
  private static final String KEY_PARAMETER = "KEY_PARAMETER";
  private static final String VALUE_PARAMETER = "VALUE_PARAMETER";
  private static final String KEY_ATTRIBUTE = "KEY_ATTRIBUTE";
  private static final String VALUE_ATTRIBUTE = "VALUE_ATTRIBUTE";
  private static final String KEY_SESSION_ATTRIBUTE = "KEY_SESSION_ATTRIBUTE";
  private static final String VALUE_SESSION_ATTRIBUTE = "VALUE_SESSION_ATTRIBUTE";
  private static final String[] COMMIT_TEXT = {"command=value", "commit=yes"};
  private static final String PATH_PAGE_INDEX = ConfigManager.getProperty("path.page.index");
  private static final String NULL_PAGE_MESSAGE = MessageManager.getProperty("message.nullPage");
  private static final String NULL_PAGE_NAME = "nullPage";

  private static ControllerRequest controllerRequest;

  @Test
  void getParameter() {
    System.out.println("Start getParameter");
    assertEquals("", controllerRequest.getParameter(INCORRECT_KEY_NAME));
    controllerRequest.setParameter(KEY_PARAMETER, VALUE_PARAMETER);
    assertEquals(VALUE_PARAMETER, controllerRequest.getParameter(KEY_PARAMETER));
    controllerRequest.setParameter(VALUE_PARAMETER + "=" + KEY_PARAMETER);
    assertEquals(KEY_PARAMETER, controllerRequest.getParameter(VALUE_PARAMETER));
    controllerRequest.setParameter(VALUE_PARAMETER + KEY_PARAMETER);
    assertEquals("", controllerRequest.getParameter(VALUE_PARAMETER + KEY_PARAMETER));
  }

  @Test
  void getResponseType() {
    System.out.println("Start getResponseType");
    controllerRequest.setResponseType(ResponseType.FAIL);
    assertEquals(ResponseType.FAIL, controllerRequest.getResponseType());
    controllerRequest.setResponseType(ResponseType.INVALIDATE);
    assertEquals(ResponseType.INVALIDATE, controllerRequest.getResponseType());
  }

  @Test
  void getResponseValue() {
    System.out.println("Start getResponseValue");
    controllerRequest.setResponseValue("");
    assertEquals("", controllerRequest.getResponseValue());
    controllerRequest.setResponseValue(VALUE_PARAMETER);
    assertEquals(VALUE_PARAMETER, controllerRequest.getResponseValue());
  }

  @Test
  void getSessionAttribute() {
    System.out.println("Start getSessionAttribute");
    controllerRequest.setSessionAttribute(KEY_SESSION_ATTRIBUTE, VALUE_SESSION_ATTRIBUTE);
    assertEquals(VALUE_SESSION_ATTRIBUTE, controllerRequest.getSessionAttribute(KEY_SESSION_ATTRIBUTE));
    assertEquals(VALUE_SESSION_ATTRIBUTE, controllerRequest.getSessionAttributes().get(KEY_SESSION_ATTRIBUTE));
  }

  @Test
  void loadNextCommitParameters() {
    System.out.println("Start loadNextCommitParameters");
    assertTrue(controllerRequest.loadNextCommitParameters(COMMIT_TEXT));
    assertEquals(COMMIT_TEXT[0].split("=")[1], controllerRequest.getParameter(COMMIT_TEXT[0].split("=")[0]));
  }

  @Test
  void resetToDefault() {
    System.out.println("Start resetToDefault");
    controllerRequest.resetToDefault();
    assertEquals(ResponseType.REDIRECT, controllerRequest.getResponseType());
    assertEquals(PATH_PAGE_INDEX, controllerRequest.getResponseValue());
    assertEquals(NULL_PAGE_MESSAGE, controllerRequest.getAttributes().get(NULL_PAGE_NAME));
  }

  @Test
  void getAttributes() {
    System.out.println("Start getAttributes");
    controllerRequest.setAttribute(KEY_ATTRIBUTE, VALUE_ATTRIBUTE);
    assertEquals(VALUE_ATTRIBUTE, controllerRequest.getAttributes().get(KEY_ATTRIBUTE));
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