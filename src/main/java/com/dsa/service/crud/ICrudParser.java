package com.dsa.service.crud;

import com.dsa.controller.ControllerRequest;
import org.jetbrains.annotations.NotNull;

public interface ICrudParser {

  CrudEnum getCrudOperation(@NotNull ControllerRequest request, @NotNull String entityPart);

}