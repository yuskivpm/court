package com.dsa.service.command;

import com.dsa.controller.ControllerRequest;
import com.dsa.controller.ControllerResponse;
import com.dsa.controller.ResponseType;
import com.dsa.service.resource.ConfigManager;

import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;

public class RedirectCommand implements BiFunction<ControllerRequest, ControllerResponse, ControllerResponse> {

  public static final String path = "redirect";

  @Override
  public ControllerResponse apply(@NotNull ControllerRequest request, @NotNull ControllerResponse controllerResponse) {
    String pageName = request.getParameter("page");
    String realPageName;
    try {
      realPageName = ConfigManager.getProperty("path.page." + pageName);
    } catch (Exception e) {
      realPageName = pageName;
    }
    controllerResponse.setResponseType(ResponseType.FORWARD);
    controllerResponse.setResponseValue(realPageName);
    return controllerResponse;
  }

}
