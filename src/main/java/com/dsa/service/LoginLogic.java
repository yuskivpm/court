package com.dsa.service;

import com.dsa.model.User;
import com.dsa.dao.entity.UserDao;
import com.dsa.dao.services.DbPoolException;

import org.apache.log4j.Logger;

import java.sql.SQLException;

public class LoginLogic {

  private static final Logger log = Logger.getLogger(LoginLogic.class);

  public static User checkLogin(String login, String password) {
    User user = null;
    try (UserDao userDao = new UserDao()) {
      String[] loginRequest = {"LOGIN", login, "PASSWORD", password};
      user = userDao.loadAllSubEntities(userDao.readEntity(loginRequest));
    } catch (DbPoolException | SQLException e) {
      log.error("Can not get connection in checkLogin: " + e);
    }
    return user;
  }

}