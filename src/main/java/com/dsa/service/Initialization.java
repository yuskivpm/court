package com.dsa.service;

import com.dsa.controller.Controller;
import com.dsa.dao.AbstractEntityDao;
import com.dsa.dao.DbPoolException;
import com.dsa.dao.service.DbPool;
import com.dsa.dao.service.IDbPool;
import com.dsa.service.command.LoginCommand;
import com.dsa.service.command.LogoutCommand;
import com.dsa.service.command.MainPageCommand;
import com.dsa.service.command.RedirectCommand;
import com.dsa.domain.court.CourtCrud;
import com.dsa.domain.lawsuit.LawsuitCrud;
import com.dsa.domain.lawsuit.SueCrud;
import com.dsa.domain.user.UserCrud;

import java.util.ResourceBundle;

public class Initialization {

  public static IDbPool dbPool;

  private static void registerExecutors() {
    Controller.registerExecutor(MainPageCommand.path, new MainPageCommand());
    Controller.registerExecutor(LoginCommand.path, new LoginCommand());
    Controller.registerExecutor(LogoutCommand.path, new LogoutCommand());
    Controller.registerExecutor(RedirectCommand.path, new RedirectCommand());
    Controller.registerExecutor(CourtCrud.path, new CourtCrud());
    Controller.registerExecutor(UserCrud.path, new UserCrud());
    Controller.registerExecutor(LawsuitCrud.path, new LawsuitCrud());
    Controller.registerExecutor(SueCrud.path, new SueCrud());
  }

  public static void initializeDbPool() throws DbPoolException {
      ResourceBundle resourceBundle = ResourceBundle.getBundle("database");
      String dbClassName = resourceBundle.getString("dbClassName");
      String url = resourceBundle.getString("url");
      String user = resourceBundle.getString("user");
      String password = resourceBundle.getString("password");
      int maxConnections = Integer.parseInt(resourceBundle.getString("maxConnections"));
      if (!dbClassName.isEmpty() && !url.isEmpty()) {
        dbPool = new DbPool(dbClassName, url, user, password, maxConnections);
      }
  }

//  private static void registerClasses() {
////    ClassProvider.registerClass(DB_POOL, DbPool.class);
//  }

  public static void createDefaultDb(boolean createDefaultDb, IDbPool defaultDbPool) {
    if (createDefaultDb){
      DbCreator.createDb(defaultDbPool);
    }
  }

  public static void initialize(boolean createDefaultDb) throws DbPoolException {
    registerExecutors();
//    registerClasses();
    initializeDbPool();
    createDefaultDb(createDefaultDb, dbPool);
    Controller.setDbPool(dbPool);
    AbstractEntityDao.setDbPool(dbPool);
  }

}
