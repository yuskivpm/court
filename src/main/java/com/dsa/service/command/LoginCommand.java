package com.dsa.service.command;

import com.dsa.controller.ProxyRequest;
import com.dsa.model.User;
import com.dsa.service.LoginLogic;
import com.dsa.service.resource.ConfigManager;
import com.dsa.service.resource.MessageManager;

public class LoginCommand implements ActionCommand {
  private static final String PARAM_NAME_LOGIN = "login";
  private static final String PARAM_NAME_PASSWORD = "password";

  @Override
  public String execute(ProxyRequest request){
    String page = null;
    String login = request.getParameter(PARAM_NAME_LOGIN);
    String password = request.getParameter(PARAM_NAME_PASSWORD);
    User user=LoginLogic.checkLogin(login, password);
    if (user!=null){
      request.setAttribute("user", user.getName());
      page = ConfigManager.getProperty("path.page.main");
    }else{
      request.setAttribute("errorFailLoginPassMessage", MessageManager.getProperty("message.loginError"));
      page = ConfigManager.getProperty("path.page.login");
    }
    return page;
  }
}
