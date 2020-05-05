package com.dsa.service;
// todo: REWRITE ALL!!!

import com.dsa.controller.Controller;
import com.dsa.service.command.LoginCommand;
import com.dsa.service.command.LogoutCommand;
import com.dsa.service.command.MainPageCommand;
import com.dsa.service.command.RedirectCommand;
import com.dsa.domain.court.CourtCrud;
import com.dsa.domain.lawsuit.LawsuitCrud;
import com.dsa.domain.lawsuit.SueCrud;
import com.dsa.domain.user.UserCrud;
//import com.dsa.service.crud.*;

public class Initialization {

  public static void initialize() throws ClassNotFoundException {
    Class.forName("com.dsa.dao.service.DbCreator");
    Controller.registerExecutor(MainPageCommand.path, new MainPageCommand());
    Controller.registerExecutor(LoginCommand.path, new LoginCommand());
    Controller.registerExecutor(LogoutCommand.path, new LogoutCommand());
    Controller.registerExecutor(RedirectCommand.path, new RedirectCommand());
    Controller.registerExecutor(CourtCrud.path, new CourtCrud());
    Controller.registerExecutor(UserCrud.path, new UserCrud());
    Controller.registerExecutor(LawsuitCrud.path, new LawsuitCrud());
    Controller.registerExecutor(SueCrud.path, new SueCrud());
//    todo: add ALL CrudExecutor's files here
  }

}
