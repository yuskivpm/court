package com.dsa.controller;

import com.dsa.service.resource.ConfigManager;
import com.dsa.service.resource.MessageManager;

import java.util.Map;
import java.util.HashMap;

public class ControllerResponse {

  private static final String PATH_PAGE_INDEX = ConfigManager.getProperty("path.page.index");
  private static final String NULL_PAGE_MESSAGE = MessageManager.getProperty("message.nullPage");
  private static final String NULL_PAGE_NAME = "nullPage";

  private ResponseType responseType;
  private String responseValue;
  private final Map<String, Object> attributes = new HashMap<>();
  private final Map<String, Object> sessionAttributes = new HashMap<>();

  public ControllerResponse() {
  }

  public ResponseType getResponseType() {
    return responseType;
  }

  public void setResponseType(ResponseType responseType) {
    this.responseType = responseType;
  }

  public String getResponseValue() {
    return responseValue;
  }

  public void setResponseValue(String responseValue) {
    this.responseValue = responseValue;
  }

  public void setAttribute(String key, Object value) {
    attributes.put(key, value);
  }

  public Map<String, Object> getAttributes() {
    return attributes;
  }

  public void setSessionAttribute(String key, Object value) {
    sessionAttributes.put(key, value);
  }

  public Map<String, Object> getSessionAttributes() {
    return sessionAttributes;
  }

  public void resetToDefault() {
    responseType = ResponseType.REDIRECT;
    responseValue = PATH_PAGE_INDEX;
    setAttribute(NULL_PAGE_NAME, NULL_PAGE_MESSAGE);
  }

}
