package com.dsa.service.command;

import com.dsa.controller.ControllerRequest;
import com.dsa.controller.ControllerResponse;
import com.dsa.controller.ResponseType;
import com.dsa.dao.entity.UserDao;
import com.dsa.dao.services.DbPoolException;
import com.dsa.model.User;
import com.dsa.service.LoginLogic;
import com.dsa.service.resource.ConfigManager;
import com.dsa.service.resource.MessageManager;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.function.Function;

public class LoginCommand implements Function<ControllerRequest, ControllerResponse> {

  public static final String path = "login";
  private static final Logger log = Logger.getLogger(LoginCommand.class);
  private static final String PARAM_NAME_LOGIN = "login";
  private static final String PARAM_NAME_PASSWORD = "password";
  private static final String USER_SESSION_ID = "user_id";

  @Override
  public ControllerResponse apply(@NotNull ControllerRequest request) {
    String login = request.getParameter(PARAM_NAME_LOGIN);
    String password = request.getParameter(PARAM_NAME_PASSWORD);
    User user = LoginLogic.checkLogin(login, password);
    ControllerResponse controllerResponse;
    if (user != null) {
      controllerResponse = new MainPageCommand().apply(request, user);
      LoginCommand.startUserSession(controllerResponse, user);
      controllerResponse.setAttribute("curUser", user);
      return controllerResponse;
    } else {
      controllerResponse = new ControllerResponse();
      controllerResponse.setAttribute("errorFailLoginPassMessage", MessageManager.getProperty("message.loginError"));
      controllerResponse.setResponseValue(ConfigManager.getProperty("path.page.login"));
      controllerResponse.setResponseType(ResponseType.FORWARD);
    }
    return controllerResponse;
  }

  private static void startUserSession(@NotNull ControllerResponse response, @NotNull User user) {
    response.setSessionAttribute(USER_SESSION_ID, user.getId());
  }

  public static String getUserSessionId(@NotNull ControllerRequest request) {
    Object UserSessionId = request.getSessionAttribute(USER_SESSION_ID);
    return UserSessionId == null ? "" : UserSessionId.toString();
  }

  public static User getSessionUser(@NotNull ControllerRequest request) {
    User user = null;
    try (UserDao userDao = new UserDao()) {
      user = userDao.loadAllSubEntities(userDao.readEntity("id", LoginCommand.getUserSessionId(request)));
    } catch (DbPoolException | SQLException e) {
      log.error("Fail get user.readEntity in getSessionUser: " + e);
    }
    return user;
  }

}
