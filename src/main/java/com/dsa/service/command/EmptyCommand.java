package com.dsa.service.command;

import com.dsa.controller.ControllerRequest;
import com.dsa.controller.ControllerResponse;
import com.dsa.controller.ResponseType;
import com.dsa.service.resource.ConfigManager;

import java.util.function.BiFunction;

public class EmptyCommand implements BiFunction<ControllerRequest, ControllerResponse, ControllerResponse> {

  @Override
  public ControllerResponse apply(ControllerRequest request, ControllerResponse controllerResponse) {
    controllerResponse.setResponseType(ResponseType.FORWARD);
    controllerResponse.setResponseValue(ConfigManager.getProperty("path.page.login"));
    return controllerResponse;
  }

}
