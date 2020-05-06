package com.dsa.service.command;

import com.dsa.controller.ControllerRequest;
import com.dsa.controller.ControllerResponse;
import com.dsa.controller.ResponseType;
import com.dsa.controller.LoginLogic;
import com.dsa.domain.user.UserDao;
import com.dsa.domain.user.User;
import com.dsa.dao.DbPoolException;
import com.dsa.service.resource.ConfigManager;
import com.dsa.service.resource.MessageManager;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.function.BiFunction;

public class LoginCommand implements BiFunction<ControllerRequest, ControllerResponse, ControllerResponse> {

  public static final String path = "login";
  private static final Logger log = Logger.getLogger(LoginCommand.class);
  private static final String PARAM_NAME_LOGIN = "login";
  private static final String PARAM_NAME_PASSWORD = "password";
  private static final String USER_SESSION_ID = "user_id";

  @Override
  public ControllerResponse apply(@NotNull ControllerRequest request, ControllerResponse controllerResponse) {
    String login = request.getParameter(PARAM_NAME_LOGIN);
    String password = request.getParameter(PARAM_NAME_PASSWORD);
    User user = LoginLogic.checkLogin(login, password);
    if (user != null) {
      controllerResponse = new MainPageCommand().apply(controllerResponse, user);
      LoginCommand.startUserSession(controllerResponse, user);
      controllerResponse.setAttribute("curUser", user);
      return controllerResponse;
    } else {
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
    String userSessionId = LoginCommand.getUserSessionId(request);
    if (!userSessionId.isEmpty()) {
      try (UserDao userDao = new UserDao()) {
        user = userDao.loadAllSubEntities(userDao.readEntity("id", userSessionId));
      } catch (DbPoolException | SQLException e) {
        log.error("Fail get user.readEntity in getSessionUser: " + e);
      }
    }
    return user;
  }

}
