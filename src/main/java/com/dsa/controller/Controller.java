package com.dsa.controller;

import com.dsa.dao.service.IDbPool;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.util.Map;
import java.util.HashMap;
import java.util.function.Function;

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

  private static final Map<String, Function<ControllerRequest, ControllerRequest>> executors = new HashMap<>();

  private static IDbPool dbPool;

  public static void setDbPool(IDbPool dbPool){
    Controller.dbPool = dbPool;
  }

  @NotNull
  public static ControllerRequest execute(@NotNull ControllerRequest mainRequest) {
    mainRequest.resetToDefault();
    String[] commits = mainRequest.getParameter(COMMIT_QUERY)
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
              mainRequest = executors.get(entityPath).apply(mainRequest);
              break;
            }
          }
          if (mainRequest.getResponseType() == ResponseType.FAIL) {
            throw new ControllerException("Fail process request");
          }
        } while (i < commits.length && mainRequest.loadNextCommitParameters(commits[i++].split(COMMIT_COMMAND_SEPARATOR)));
        connection.commit();
      } catch (Exception e) {
        connection.rollback();
        throw new ControllerException("Rollback", e);
      }
    } catch (Exception e) {
      mainRequest.resetToDefault();
    }
    return mainRequest;
  }

  public static void registerExecutor(String path, Function<ControllerRequest, ControllerRequest> executor) {
    executors.put(path, executor);
  }

}
