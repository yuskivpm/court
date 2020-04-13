package com.dsa.service.command;

import com.dsa.controller.ProxyRequest;
import com.dsa.model.User;
import com.dsa.service.LoginLogic;
import com.dsa.service.resource.ConfigManager;
import com.dsa.service.resource.MessageManager;

import javax.servlet.http.HttpSession;

public class LoginCommand implements ActionCommand {
  private static final String PARAM_NAME_LOGIN = "login";
  private static final String PARAM_NAME_PASSWORD = "password";
  private static final String USER_SESSION_ID = "user_id";

  @Override
  public String execute(ProxyRequest request){
    String page = null;
    String login = request.getParameter(PARAM_NAME_LOGIN);
    String password = request.getParameter(PARAM_NAME_PASSWORD);
    User user=LoginLogic.checkLogin(login, password);
    if (user!=null){
      LoginCommand.startUserSession(request,user);
      return new MainPageCommand().execute(request);
//      page = ConfigManager.getProperty("path.page.main."+user.getRole());
    }else{
      request.setAttribute("errorFailLoginPassMessage", MessageManager.getProperty("message.loginError"));
      page = ConfigManager.getProperty("path.page.login");
    }
    return page;
  }

  private static void startUserSession(ProxyRequest request, User user){
    HttpSession session=request.getSession();
    request.setAttribute("user", user.getName());
    session.setAttribute(USER_SESSION_ID, user.getId());
  }

  public static String getUserSessionId(ProxyRequest request){
    HttpSession session=request.getSession();
    Object UserSessionId=session.getAttribute(USER_SESSION_ID);
    return UserSessionId==null?"":UserSessionId.toString();
  }

}
