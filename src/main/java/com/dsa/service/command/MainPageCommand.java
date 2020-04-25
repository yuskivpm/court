package com.dsa.service.command;

import com.dsa.controller.ProxyRequest;
import com.dsa.dao.entity.CourtDao;
import com.dsa.dao.entity.UserDao;
import com.dsa.dao.services.DbPoolException;
import com.dsa.model.Court;
import com.dsa.model.User;
import com.dsa.service.resource.ConfigManager;

import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class MainPageCommand implements IActionCommand {
  private static final Logger log = Logger.getLogger(MainPageCommand.class);

  @Override
  public String execute(ProxyRequest request){
    User user=LoginCommand.getSessionUser(request);
    if(user==null){// if session user not found - redirect to Login page
      return new EmptyCommand().execute(request);
    }
    return execute(request, user);
  }

  public String execute(ProxyRequest request, User user){
    MainPageCommand.InitializeJsp(user, request);
    return MainPageCommand.getMainPageForUser(user);
  }

  public static String getMainPageForUser(User user){
    return ConfigManager.getProperty("path.page.main"+(user==null?"":("."+user.getRole())));
  }

  private static void InitializeJsp(User currentUser, ProxyRequest request){
    request.setAttribute("user", currentUser.getName());
    switch(currentUser.getRole().toString()){
      case "ADMIN":
        //users
        try(UserDao user=new UserDao()){
          List<User> users= user.readAll();
          for(User curUser: users){
            user.loadAllSubEntities(curUser);
          }
          request.setAttribute("users",users);
        }catch(SQLException | DbPoolException e){
          log.error("Fail get Users list in MainPageCommand for "+currentUser.getRole()+": "+e);
        }
//        courts
        try(CourtDao court=new CourtDao()){
          List<Court> courts= court.readAll();
          for(Court curCourt: courts){
            court.loadAllSubEntities(curCourt);
          }
          request.setAttribute("courts",courts);
        }catch(SQLException | DbPoolException e){
          log.error("Fail get Courts list in MainPageCommand for "+currentUser.getRole()+": "+e);
        }
        break;
      case "JUDGE":
//        todo: unready
//        break;
      case "ATTORNEY":
//        todo: unready
//        break;
      case "GUEST":
//        todo: unready
//        break;
      default:
//        todo: unready
    }
  }

}
