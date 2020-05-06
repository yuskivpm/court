package com.dsa.controller;

import com.dsa.domain.user.User;
import com.dsa.domain.user.UserDao;
import com.dsa.dao.DbPoolException;

import org.apache.log4j.Logger;

import java.sql.SQLException;

public class LoginLogic {

  private static final Logger log = Logger.getLogger(LoginLogic.class);

  private static final String PASSWORD = "PASSWORD";
  private static final String LOGIN = "LOGIN";
  private static final String EXCEPTION_TEXT = "Can not get connection in checkLogin: ";

  public static User checkLogin(String login, String password) {
    User user = null;
    try (UserDao userDao = new UserDao()) {
      String[] loginRequest = {LOGIN, login, PASSWORD, password};
      user = userDao.loadAllSubEntities(userDao.readEntity(loginRequest));
    } catch (DbPoolException | SQLException e) {
      log.error(EXCEPTION_TEXT + e);
    }
    return user;
  }

}
