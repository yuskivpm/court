package com.dsa.service.command;

import com.dsa.controller.ControllerRequest;
import com.dsa.controller.ResponseType;
import com.dsa.service.resource.ConfigManager;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class RedirectCommand implements Function<ControllerRequest, ControllerRequest> {

  public static final String path = "redirect";

  @Override
  public ControllerRequest apply(@NotNull ControllerRequest request) {
    String pageName = request.getParameter("page");
    String realPageName;
    try {
      realPageName = ConfigManager.getProperty("path.page." + pageName);
    } catch (Exception e) {
      realPageName = pageName;
    }
    request.setResponseType(ResponseType.FORWARD);
    request.setResponseValue(realPageName);
    return request;
  }

}
