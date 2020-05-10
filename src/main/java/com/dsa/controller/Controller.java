package com.dsa.controller;

import com.dsa.dao.service.IDbPool;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.util.Map;
import java.util.HashMap;
import java.util.function.BiFunction;

public class Controller {

  public static final String METHOD = "method";
  public static final String PATH_INFO = "pathInfo";
  public static final String QUERY_STRING = "queryString";
  public static final String COMMAND = "command";

  private static final String COMMIT_QUERY = "commit";
  private static final String COMMIT_SEPARATOR = "^"; // between different commits
  private static final String COMMIT_COMMAND_SEPARATOR = "~"; // -> &
  private static final String COMMIT_COMMAND_OPERAND = "@"; // -> =
// USAGE: commit= method@GET ~ id=1 ^ method@POST ~ id@2

  private static final Map<String, BiFunction<ControllerRequest, ControllerResponse, ControllerResponse>> executors = new HashMap<>();

  private static IDbPool dbPool;

  public static void setDbPool(IDbPool dbPool){
    Controller.dbPool = dbPool;
  }

  @NotNull
  public static ControllerResponse execute(@NotNull ControllerRequest mainRequest) {
    ControllerResponse controllerResponse = new ControllerResponse();
    controllerResponse.resetToDefault();
    String[] commits = (mainRequest.getParameter(COMMIT_QUERY))
        .replace(COMMIT_COMMAND_OPERAND, "=")
        .split(COMMIT_SEPARATOR, -1);
    try (Connection connection = dbPool.getConnection()) {
      connection.setAutoCommit(false);
      try {
        int i = 0;
        do {
          String command = mainRequest.getParameter(COMMAND).toLowerCase();
          for (String entityPath : executors.keySet()) {
            if (command.startsWith(entityPath)) {
              controllerResponse = executors.get(entityPath).apply(mainRequest, controllerResponse);
              break;
            }
          }
          if (controllerResponse.getResponseType() == ResponseType.FAIL) {
            throw new ControllerException("Fail process request");
          }
        } while (i < commits.length && mainRequest.loadNextCommitParameters(commits[i++].split(COMMIT_COMMAND_SEPARATOR)));
        connection.commit();
      } catch (Exception e) {
        connection.rollback();
        throw new ControllerException("Rollback", e);
      }
    } catch (Exception e) {
      controllerResponse.resetToDefault();
    }
    return controllerResponse;
  }

  public static void registerExecutor(String path, BiFunction<ControllerRequest, ControllerResponse, ControllerResponse> executor) {
    executors.put(path, executor);
  }

}
