package com.dsa.service.command;

import com.dsa.controller.ControllerRequest;
import com.dsa.controller.ControllerResponse;
import com.dsa.controller.ResponseType;
import com.dsa.service.resource.ConfigManager;

import java.util.function.Function;

public class EmptyCommand implements Function<ControllerRequest, ControllerResponse> {

  @Override
  public ControllerResponse apply(ControllerRequest request) {
    ControllerResponse controllerResponse = new ControllerResponse(
        ResponseType.FORWARD,
        ConfigManager.getProperty("path.page.login")
    );
    return controllerResponse;
  }

}
