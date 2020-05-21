package com.dsa.view.http;

import com.dsa.controller.ControllerRequest;

import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

class HttpToControllerConverter {

  private static final String METHOD = "method";
  private static final String PATH_INFO = "pathInfo";
  private static final String QUERY_STRING = "queryString";
  private static final String COMMAND = "command";

  private static void getMainRequestCommonData(
      @NotNull HttpServletRequest request,
      @NotNull ControllerRequest controllerRequest
  ) {
    controllerRequest.setParameter(METHOD, request.getMethod());
    controllerRequest.setParameter(PATH_INFO, request.getPathInfo() + "/");
    controllerRequest.setParameter(QUERY_STRING, request.getQueryString());
    Map<String, String[]> params = request.getParameterMap();
    params.forEach((name, values) -> {
      if (name != null && !name.isEmpty() && values != null) {
        controllerRequest.setParameter(name, String.join(",", values));
      }
    });
    if (controllerRequest.getParameter(COMMAND).isEmpty()) {
      controllerRequest.setParameter(COMMAND, request.getPathInfo() + "/");
    }
  }

  private static void getMainRequestReaderData(
      @NotNull HttpServletRequest request,
      @NotNull ControllerRequest controllerRequest
  ) {
    try {
      BufferedReader reader = request.getReader();
      if (reader != null) {
        String line;
        String[] subLines;
        while ((line = reader.readLine()) != null) {
          subLines = line.split("&");
          for (String subLine : subLines) {
            controllerRequest.setParameter(subLine);
          }
        }
      }
    } catch (IOException e) {
    }
  }

  private static void getMainRequestSessionData(HttpSession session, @NotNull ControllerRequest controllerRequest) {
    if (session != null) {
      Enumeration<String> enumerator = session.getAttributeNames();
      if (enumerator != null) {
        while (enumerator.hasMoreElements()) {
          String key = enumerator.nextElement();
          controllerRequest.setSessionAttribute(key, session.getAttribute(key));
        }
      }
    }
  }

  @NotNull
  static ControllerRequest prepareRequestDataForController(@NotNull HttpServletRequest request, boolean loadReaderData) {
    ControllerRequest controllerRequest = new ControllerRequest();
    getMainRequestCommonData(request, controllerRequest);
    if (loadReaderData) {
      getMainRequestReaderData(request, controllerRequest);
    }
    getMainRequestSessionData(request.getSession(), controllerRequest);
    return controllerRequest;
  }

}
