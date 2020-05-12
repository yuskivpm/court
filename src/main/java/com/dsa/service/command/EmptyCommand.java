package com.dsa.service.command;

import com.dsa.controller.ControllerRequest;
import com.dsa.controller.ResponseType;
import com.dsa.service.resource.ConfigManager;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class EmptyCommand implements Function<ControllerRequest, ControllerRequest> {

  @Override
  public ControllerRequest apply(@NotNull ControllerRequest request) {
    request.setResponseType(ResponseType.FORWARD);
    request.setResponseValue(ConfigManager.getProperty("path.page.login"));
    return request;
  }

}
