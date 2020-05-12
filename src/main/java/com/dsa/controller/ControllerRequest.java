package com.dsa.controller;

import com.dsa.service.resource.ConfigManager;
import com.dsa.service.resource.MessageManager;

import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.HashMap;

public class ControllerRequest {

  private static final String DEFAULT_REQUEST_CHARSET = "ISO-8859-1";
  private static final String PATH_PAGE_INDEX = ConfigManager.getProperty("path.page.index");
  private static final String NULL_PAGE_MESSAGE = MessageManager.getProperty("message.nullPage");
  private static final String NULL_PAGE_NAME = "nullPage";

  private ResponseType responseType;
  private String responseValue;
  private final Map<String, String> params = new HashMap<>();
  private final Map<String, Object> attributes = new HashMap<>();
  private final Map<String, Object> sessionAttributes = new HashMap<>();

  public ControllerRequest() {
    super();
  }

  public String getParameter(String paramName) {
    String value = params.get(paramName);
    return value == null ? "" : value;
  }

  public void setParameter(String paramName, String paramValue) {
    String value;
    try {
      value = new String(paramValue.getBytes(DEFAULT_REQUEST_CHARSET), StandardCharsets.UTF_8);
    } catch (Exception e) {
      value = paramValue;
    }
    params.put(paramName, value);
  }

  public void setParameter(@NotNull String command) {
    String[] arr = command.split("=");
    if (arr.length == 1) {
      setParameter(command, "");
    } else if (arr.length >= 1) {
      setParameter(arr[0], arr[1]);
    }
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

  public Map<String, Object> getSessionAttributes() {
    return sessionAttributes;
  }

  public Object getSessionAttribute(String key) {
    return sessionAttributes.get(key);
  }

  public void setSessionAttribute(String key, Object value) {
    sessionAttributes.put(key, value);
  }

  public boolean loadNextCommitParameters(@NotNull String[] commit) {
    boolean result = false;
    params.clear();
    for (String commitCommand : commit) {
      if (!commitCommand.isEmpty()) {
        setParameter(commitCommand);
        result = true;
      }
    }
    return result;
  }

  public void resetToDefault() {
    responseType = ResponseType.REDIRECT;
    responseValue = PATH_PAGE_INDEX;
    setAttribute(NULL_PAGE_NAME, NULL_PAGE_MESSAGE);
  }

}
