package com.dsa.service.command;

import com.dsa.controller.ProxyRequest;
import com.dsa.dao.entity.UserDao;
import com.dsa.dao.services.DbPoolException;
import com.dsa.model.User;
import com.dsa.service.resource.ConfigManager;
import org.apache.log4j.Logger;

import java.sql.SQLException;

public class MainPageCommand implements ActionCommand  {
  private static final Logger log = Logger.getLogger(MainPageCommand.class);

  @Override
  public String execute(ProxyRequest request){
    User user=null;
    try(UserDao userDao= new UserDao()){
      String UserSession = LoginCommand.getUserSessionId(request);
      user=userDao.getEntity("id",UserSession);
    }catch(DbPoolException | SQLException e){
      log.error("Fail get user.getEntity in MainPageCommand: "+e);
    }
    return getMainPageForUser(user);
  }

  public static String getMainPageForUser(User user){
    return ConfigManager.getProperty("path.page.main"+(user==null?"":("."+user.getRole())));
  }
}
