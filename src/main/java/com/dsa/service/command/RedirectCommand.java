package com.dsa.service.command;

import com.dsa.controller.ControllerRequest;
import com.dsa.controller.ControllerResponse;
import com.dsa.controller.ResponseType;
import com.dsa.service.resource.ConfigManager;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class RedirectCommand implements Function<ControllerRequest, ControllerResponse> {

  public static final String path = "redirect";

  @Override
  public ControllerResponse apply(@NotNull ControllerRequest request) {
    String pageName = request.getParameter("page");
    String realPageName;
    try {
      realPageName = ConfigManager.getProperty("path.page." + pageName);
    } catch (Exception e) {
      realPageName = pageName;
    }
    return new ControllerResponse(ResponseType.FORWARD, realPageName);
  }

}
