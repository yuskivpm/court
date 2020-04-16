package com.dsa.service.command;

import com.dsa.controller.ProxyRequest;
import com.dsa.dao.entity.UserDao;
import com.dsa.dao.services.DbPoolException;
import com.dsa.model.User;
import com.dsa.service.LoginLogic;
import com.dsa.service.resource.ConfigManager;
import com.dsa.service.resource.MessageManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public class LoginCommand implements ActionCommand {
  private static final Logger log = Logger.getLogger(LoginCommand.class);
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
      return new MainPageCommand().execute(request,user);
    }else{
      request.setAttribute("errorFailLoginPassMessage", MessageManager.getProperty("message.loginError"));
      page = ConfigManager.getProperty("path.page.login");
    }
    return page;
  }

  private static void startUserSession(ProxyRequest request, User user){
    HttpSession session=request.getSession();
    session.setAttribute(USER_SESSION_ID, user.getId());
  }

  public static String getUserSessionId(ProxyRequest request){
    HttpSession session=request.getSession();
    Object UserSessionId=session.getAttribute(USER_SESSION_ID);
    return UserSessionId==null?"":UserSessionId.toString();
  }

  public static User getSessionUser(ProxyRequest request){
    User user=null;
    try(UserDao userDao= new UserDao()){
      user=userDao.readEntity("id",LoginCommand.getUserSessionId(request));
    }catch(DbPoolException | SQLException e){
      log.error("Fail get user.readEntity in getSessionUser: "+e);
    }
    return user;
  }

}
