package com.dsa.service.initJsp;

import com.dsa.controller.ProxyRequest;
import com.dsa.dao.entity.CourtDao;
import com.dsa.dao.entity.UserDao;
import com.dsa.dao.services.DbPoolException;
import com.dsa.model.Court;
import com.dsa.model.User;
import org.apache.log4j.Logger;

import java.util.List;
import java.sql.SQLException;

public class InitJsp {
  private static final Logger log = Logger.getLogger(InitJsp.class);

  public static void InitializeJsp(String page, ProxyRequest proxyRequest){
    switch(page){
      case "/jsp/mainAdmin.jsp":
        //users
        try(UserDao user=new UserDao()){
          List<User> users= user.getAll();
          proxyRequest.setAttribute("users",users);
        }catch(SQLException | DbPoolException e){
          log.error("Fail get Users list in InitializeJsp for "+page+": "+e);
        }
//        courts
        try(CourtDao court=new CourtDao()){
          List<Court> courts= court.getAll();
          proxyRequest.setAttribute("courts",courts);
        }catch(SQLException | DbPoolException e){
          log.error("Fail get Courts list in InitializeJsp for "+page+": "+e);
        }
      default:
    }
  }
}
