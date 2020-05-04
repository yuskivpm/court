package com.dsa.service;
// todo: REWRITE ALL!!!

import com.dsa.controller.Controller;
import com.dsa.service.command.LoginCommand;
import com.dsa.service.command.LogoutCommand;
import com.dsa.service.command.MainPageCommand;
import com.dsa.service.command.RedirectCommand;
//import com.dsa.service.crud.*;

public class Initialization {

  public static void initialize() throws ClassNotFoundException {
    Class.forName("com.dsa.dao.services.DbCreator");
    Controller.registerExecutor(MainPageCommand.path, new MainPageCommand());
    Controller.registerExecutor(LoginCommand.path, new LoginCommand());
    Controller.registerExecutor(LogoutCommand.path, new LogoutCommand());
    Controller.registerExecutor(RedirectCommand.path, new RedirectCommand());
//    CrudExecutor.register(UserCrud.path, new UserCrud());
//    CrudExecutor.register(CourtCrud.path, new CourtCrud());
//    CrudExecutor.register(SueCrud.path, new SueCrud());
//    CrudExecutor.register(LawsuitCrud.path, new LawsuitCrud());
//    todo: add ALL CrudExecutor's files here
  }

}
