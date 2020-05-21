package com.dsa.controller;

import org.jetbrains.annotations.NotNull;

public interface IController {

  ControllerRequest execute(@NotNull ControllerRequest mainRequest);

}
