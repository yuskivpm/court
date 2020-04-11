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
  public static User checkLogin(String login, String password){
    Connection connection=null;
    User user=null;
    try {
      connection = DbPool.getConnection();
      UserDao userDao = new UserDao(connection);
      String[] loginRequest={"LOGIN", login, "PASSWORD",password};
      user = userDao.getEntity(loginRequest);
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
    return user;
  }
}
