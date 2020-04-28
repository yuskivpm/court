package com.dsa.service.crud;

import com.dsa.controller.ProxyRequest;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.HashMap;

public class CrudExecutor {
  private static final Map<String, Icrud> executors = new HashMap<>();
  private static final String POST = "POST";
  private static final String PUT = "PUT";
  // /? or /create - create
  // /?id or /update?id - update
  private static final String GET = "GET";
  // /? or /read - read all
  // /?id or /read?id - read by id
  // /delete?id - delete
  private static final String DELETE = "DELETE";
  // ?id or /delete?id
  private static final String ID = "id";

  public static void register(@NotNull String pathInfo, Icrud exeFunction) {
    executors.put(pathInfo.toLowerCase() + "/", exeFunction);
  }

  public static CrudResult execute(@NotNull ProxyRequest request, HttpServletResponse response) {
    String pathInfo = request.getPathInfo();
    if (pathInfo != null && !pathInfo.isEmpty()) {
      String path = pathInfo.toLowerCase() + "/";
      for (String entityPath : executors.keySet()) {
        if (path.startsWith(entityPath)) {
          return executors.get(entityPath).execute(request, response);
        }
      }
    }
    return CrudResult.FAILED;
  }

  @Contract(value = "null -> false", pure = true)
  public static boolean hasId(String id) {
    return (id != null && !id.isEmpty());
  }

  public static CrudEnum getCrudOperation(@NotNull ProxyRequest request, @NotNull String entityPart) {
    String method = request.getMethod();
    if (method.equals(DELETE)) {
      return hasId(request.getParameter(ID)) ? CrudEnum.DELETE : CrudEnum.WRONG;
    }
    if (method.equals(POST) || method.equals(PUT)) {
      return hasId(request.getParameter(ID)) ? CrudEnum.UPDATE : CrudEnum.CREATE;
    }
    if (method.equals(GET)) {
      String redirect = request.getParameter("redirect");
      boolean hasIdValue = hasId(request.getParameter(ID));
      if (redirect != null && !redirect.isEmpty()) {
        return hasIdValue ? CrudEnum.PREPARE_UPDATE_FORM : CrudEnum.WRONG;
      }
      String path = request.getPathInfo();
      path = ((path == null) || (path.length() <= entityPart.length())) ? "" : path.substring(entityPart.length()).toUpperCase();
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
