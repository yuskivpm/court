package com.dsa.service.command;

import com.dsa.controller.ControllerRequest;
import com.dsa.controller.ControllerResponse;
import com.dsa.controller.ResponseType;
import com.dsa.service.resource.ConfigManager;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class LogoutCommand implements Function<ControllerRequest, ControllerResponse> {

  public static final String path = "logout";

  @Override
  public ControllerResponse apply(@NotNull ControllerRequest request) {
//    ControllerResponse controllerResponse = ;
//    request.setAttribute("user", "");
//    request.getSession().invalidate();
    return new ControllerResponse(ResponseType.INVALIDATE, ConfigManager.getProperty("path.page.index"));
  }

}
