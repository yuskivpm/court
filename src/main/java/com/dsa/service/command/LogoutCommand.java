package com.dsa.service.command;

import com.dsa.controller.ControllerRequest;
import com.dsa.controller.ResponseType;
import com.dsa.service.resource.ConfigManager;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class LogoutCommand implements Function<ControllerRequest, ControllerRequest> {

  public static final String path = "logout";

  @Override
  public ControllerRequest apply(@NotNull ControllerRequest request) {
    request.setResponseType(ResponseType.INVALIDATE);
    request.setResponseValue(ConfigManager.getProperty("path.page.index"));
    return request;
  }

}
