package com.dsa.controller;

import com.dsa.dao.services.DbPool;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.function.BiFunction;

public class Controller {
//  private static final Logger log = Logger.getLogger(MainServlet.class);
  public static final String METHOD = "method";
  public static final String PATH_INFO = "pathInfo";
  public static final String QUERY_STRING = "queryString";
  public static final String COMMAND = "command";
  public static final String CRUD_COMMAND = "crud";
  public static final String COMMIT_QUERY = "commit";
  public static final String COMMIT_SEPARATOR = "^";
  public static final String COMMIT_COMMAND_SEPARATOR = "~"; // ~ -> &
//  commit= method=GET ~ id=1 ^ method=POST ~ id=2

  private static final Map<String, BiFunction<ControllerRequest, ControllerResponse, ControllerResponse>> executors = new HashMap<>();

  @NotNull
  private static List<ControllerRequest> fillRequestList(@NotNull ControllerRequest mainRequest){
    List<ControllerRequest> requestList = new ArrayList<>();
    requestList.add(mainRequest);
    String commit = mainRequest.getParameter(COMMIT_QUERY);
    if (!commit.isEmpty()){
      for (String singleCommitCommands : commit.split(COMMIT_SEPARATOR)){
        ControllerRequest nextRequest = new ControllerRequest(mainRequest);
        requestList.add(nextRequest);
        for (String commitCommand : singleCommitCommands.split(COMMIT_COMMAND_SEPARATOR)){
          nextRequest.setParameter(commitCommand);
        }
      }
    }
    return requestList;
  }

  @NotNull
  public static ControllerResponse execute(@NotNull ControllerRequest mainRequest){
    ControllerResponse controllerResponse = new ControllerResponse();
    controllerResponse.resetToDefault();
    List<ControllerRequest> requestList = fillRequestList(mainRequest);
    try(Connection connection = DbPool.getConnection()){
      connection.setAutoCommit(false);
      try {
        for (ControllerRequest request : requestList) {
          String command = request.getParameter(COMMAND).toLowerCase();
          if (command.isEmpty()) {
            command = request.getParameter(PATH_INFO).toLowerCase() + "/";
          }
          for (String entityPath : executors.keySet()) {
            if (command.startsWith(entityPath)) {
              controllerResponse = executors.get(entityPath).apply(request, controllerResponse);
              break;
            }
          }
          if (controllerResponse.getResponseType() == ResponseType.FAIL){
            throw new Exception("Fail execute");
          }
        }
        connection.commit();
      }catch(Exception e) {
        connection.rollback();
        throw e;
      }
    }catch(Exception e) {
      controllerResponse.resetToDefault();
    }
    return controllerResponse;
  }

  public static void registerExecutor(String path, BiFunction<ControllerRequest, ControllerResponse, ControllerResponse> executor){
    executors.put(path, executor);
  }
}
