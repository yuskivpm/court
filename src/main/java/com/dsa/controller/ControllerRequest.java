package com.dsa.controller;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.HashMap;

public class ControllerRequest {

  private static final String DEFAULT_REQUEST_CHARSET = "ISO-8859-1";
  private static final String UTF8 = "UTF-8";

  private final Map<String, String> params = new HashMap<>();
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
      value = new String(paramValue.getBytes(DEFAULT_REQUEST_CHARSET), UTF8);
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

}
