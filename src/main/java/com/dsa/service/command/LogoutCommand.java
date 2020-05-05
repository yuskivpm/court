package com.dsa.service.command;

import com.dsa.controller.ControllerRequest;
import com.dsa.controller.ControllerResponse;
import com.dsa.controller.ResponseType;
import com.dsa.service.resource.ConfigManager;

import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;

public class LogoutCommand implements BiFunction<ControllerRequest, ControllerResponse, ControllerResponse> {

  public static final String path = "logout";

  @Override
  public ControllerResponse apply(@NotNull ControllerRequest request, @NotNull ControllerResponse controllerResponse) {
    controllerResponse.setResponseType(ResponseType.INVALIDATE);
    controllerResponse.setResponseValue(ConfigManager.getProperty("path.page.index"));
    return controllerResponse;
  }

}
