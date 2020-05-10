package com.dsa.controller;

import com.dsa.service.resource.ConfigManager;
import com.dsa.service.resource.MessageManager;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerResponseTest {

  private static final String VALUE = "value";
  private static final String ATTRIBUTE_VALUE = "attribute value";
  private static final String PATH_PAGE_INDEX = ConfigManager.getProperty("path.page.index");
  private static final String NULL_PAGE_MESSAGE = MessageManager.getProperty("message.nullPage");
  private static final String NULL_PAGE_NAME = "nullPage";

  private static ControllerResponse controllerResponse;

  @Test
  void getResponseType() {
    System.out.println("Start getResponseType");
    controllerResponse.setResponseType(ResponseType.FAIL);
    assertEquals(ResponseType.FAIL, controllerResponse.getResponseType());
  }

  @Test
  void getResponseValue() {
    System.out.println("Start getResponseValue");
    controllerResponse.setResponseValue(VALUE);
    assertEquals(VALUE, controllerResponse.getResponseValue());
  }

  @Test
  void setAttribute() {
    System.out.println("Start setAttribute");
    controllerResponse.setAttribute(ATTRIBUTE_VALUE, VALUE);
    assertEquals(VALUE, controllerResponse.getAttributes().get(ATTRIBUTE_VALUE));
  }

  @Test
  void getSessionAttributes() {
    System.out.println("Start getSessionAttributes");
    final Object object = new Object();
    controllerResponse.setSessionAttribute(VALUE,object);
    assertEquals(object, controllerResponse.getSessionAttributes().get(VALUE));
  }

  @Test
  void resetToDefault() {
    System.out.println("Start resetToDefault");
    controllerResponse.setResponseType(ResponseType.FAIL);
    controllerResponse.setResponseValue(VALUE);
    controllerResponse.resetToDefault();
    assertEquals(ResponseType.REDIRECT, controllerResponse.getResponseType());
    assertEquals(PATH_PAGE_INDEX, controllerResponse.getResponseValue());
    assertEquals(NULL_PAGE_MESSAGE, controllerResponse.getAttributes().get(NULL_PAGE_NAME));
  }

  @BeforeAll
  static void beforeAll() {
    System.out.println("Start testing ControllerResponseTest");
    controllerResponse = new ControllerResponse();
  }

  @AfterAll
  static void afterAll() {
    System.out.println("Finish testing ControllerResponseTest");
  }

}