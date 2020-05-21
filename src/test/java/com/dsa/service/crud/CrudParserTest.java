package com.dsa.service.crud;

import com.dsa.controller.Controller;
import com.dsa.controller.ControllerRequest;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class crudParserTest {

  private static final String POST = "POST";
  private static final String PUT = "PUT";
  private static final String GET = "GET";
  private static final String DELETE = "DELETE";
  private static final String ID = "id";

  private static ControllerRequest request;
  private static CrudParser crudParser;

  @Test
  void hasId() {
    System.out.println("Start hasId");
    assertFalse(crudParser.hasId(null));
    assertFalse(crudParser.hasId(""));
    assertTrue(crudParser.hasId("1"));
  }

  @Test
  void getCrudOperation_Delete() {
    System.out.println("Start getCrudOperation_Delete");
    when(request.getParameter(Controller.METHOD)).thenReturn(DELETE);
    assertEquals(crudParser.getCrudOperation(request, ""), CrudEnum.WRONG);
    when(request.getParameter(ID)).thenReturn("1");
    assertEquals(crudParser.getCrudOperation(request, ""), CrudEnum.DELETE);
  }

  @Test
  void getCrudOperation_Post() {
    System.out.println("Start getCrudOperation_Post");
    when(request.getParameter(Controller.METHOD)).thenReturn(POST);
    assertEquals(crudParser.getCrudOperation(request, ""), CrudEnum.CREATE);
    when(request.getParameter(ID)).thenReturn("1");
    assertEquals(crudParser.getCrudOperation(request, ""), CrudEnum.UPDATE);
  }

  @Test
  void getCrudOperation_Put() {
    System.out.println("Start getCrudOperation_Put");
    when(request.getParameter(Controller.METHOD)).thenReturn(PUT);
    assertEquals(crudParser.getCrudOperation(request, ""), CrudEnum.CREATE);
    when(request.getParameter(ID)).thenReturn("1");
    assertEquals(crudParser.getCrudOperation(request, ""), CrudEnum.UPDATE);
  }

  @Test
  void getCrudOperation_IncorrectMethod() {
    System.out.println("Start getCrudOperation_IncorrectMethod");
    when(request.getParameter(Controller.METHOD)).thenReturn("1");
    assertEquals(crudParser.getCrudOperation(request, ""), CrudEnum.WRONG);
  }

  @Test
  void getCrudOperation_Get() {
    System.out.println("Start getCrudOperation_Get");
    when(request.getParameter(Controller.METHOD)).thenReturn(GET);
    assertThrows(NullPointerException.class, () -> crudParser.getCrudOperation(request, ""));
    when(request.getParameter("redirect")).thenReturn("");
    when(request.getParameter(Controller.PATH_INFO)).thenReturn("");
    assertEquals(crudParser.getCrudOperation(request, ""), CrudEnum.READ_ALL);
    when(request.getParameter(Controller.PATH_INFO)).thenReturn("12");
    assertEquals(crudParser.getCrudOperation(request, ""), CrudEnum.UNKNOWN);
    when(request.getParameter(ID)).thenReturn("1");
    assertEquals(crudParser.getCrudOperation(request, ""), CrudEnum.UNKNOWN);
    when(request.getParameter(Controller.PATH_INFO)).thenReturn("");
    assertEquals(crudParser.getCrudOperation(request, ""), CrudEnum.READ);
    when(request.getParameter(Controller.PATH_INFO)).thenReturn("/" + DELETE);
    assertEquals(crudParser.getCrudOperation(request, ""), CrudEnum.DELETE);
  }

  @BeforeAll
  static void beforeAll() {
    System.out.println("Start testing crudParserTest");
    crudParser = new CrudParser();
  }

  @BeforeEach
  void beforeEach() {
    System.out.println("BeforeEach crudParserTest");
    request = Mockito.mock(ControllerRequest.class);
  }

  @AfterAll
  static void afterAll() {
    System.out.println("Finish testing crudParserTest");
  }

}