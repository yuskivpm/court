package com.dsa.service;

import com.dsa.dao.entity.UserDao;
import com.dsa.dao.services.DbPool;
import com.dsa.dao.services.DbPoolException;
import com.dsa.model.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class LoginLogic {
  private static final Logger log = Logger.getLogger(LoginLogic.class);
//  private static final String ADMIN_LOGIN = "admin";
//  private static final String ADMIN_PASS = "qwe";
  public static User checkLogin(String login, String password){
    Connection connection=null;
    User user=null;
    try {
      connection = DbPool.getConnection();
      UserDao userDao = new UserDao(connection);
      user = userDao.getEntity("LOGIN", login);
    }catch(DbPoolException | SQLException e){
      log.error("Can not get connection in checkLogin: "+e);
    }finally{
      if(connection!=null){
        try{
          connection.close();
        }catch(SQLException e){
          log.error("Can not close connection in checkLogin: "+e);
        }
      }
    }
    return (user!=null) && user.getPassword().equals(password) ? user :null;
//    return ADMIN_LOGIN.equals(login) && ADMIN_PASS.equals(password);
  }
}
