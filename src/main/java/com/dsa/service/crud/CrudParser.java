package com.dsa.service.crud;

import com.dsa.controller.Controller;
import com.dsa.controller.ControllerRequest;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class CrudParser {

  private static final String POST = "POST";
  private static final String PUT = "PUT";
  private static final String GET = "GET";
  private static final String DELETE = "DELETE";
  private static final String ID = "id";

  @Contract(value = "null -> false", pure = true)
  public static boolean hasId(String id) {
    return (id != null && !id.isEmpty());
  }

  public static CrudEnum getCrudOperation(@NotNull ControllerRequest request, @NotNull String entityPart) {
    String method = request.getParameter(Controller.METHOD);
    if (method.equals(DELETE)) {
      return hasId(request.getParameter(ID)) ? CrudEnum.DELETE : CrudEnum.WRONG;
    }
    if (method.equals(POST) || method.equals(PUT)) {
      return hasId(request.getParameter(ID)) ? CrudEnum.UPDATE : CrudEnum.CREATE;
    }
    if (method.equals(GET)) {
      String redirect = request.getParameter("redirect");
      boolean hasIdValue = hasId(request.getParameter(ID));
      if (!redirect.isEmpty()) {
        return hasIdValue ? CrudEnum.PREPARE_UPDATE_FORM : CrudEnum.WRONG;
      }
      String path = request.getParameter(Controller.PATH_INFO);
      path = path.length() <= entityPart.length() ? "" : path.substring(entityPart.length()).toUpperCase();
      if (hasIdValue) {
        if (path.startsWith("/" + DELETE)) {
          return CrudEnum.DELETE;
        }
        return (path.length() <= 1) ? CrudEnum.READ : CrudEnum.UNKNOWN;
      }
      return (path.length() <= 1) ? CrudEnum.READ_ALL : CrudEnum.UNKNOWN;
    }
    return CrudEnum.WRONG;
  }

}
