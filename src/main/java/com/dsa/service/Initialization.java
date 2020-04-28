package com.dsa.service;

import com.dsa.service.crud.*;

public class Initialization {
  public static void initialize() throws ClassNotFoundException{
    // initialization of DbCreator
    Class.forName("com.dsa.dao.services.DbCreator");
    CrudExecutor.register(UserCrud.path, new UserCrud());
    CrudExecutor.register(CourtCrud.path, new CourtCrud());
    CrudExecutor.register(SueCrud.path, new SueCrud());
    CrudExecutor.register(SueLawsuitCrud.path, new SueLawsuitCrud());
//    todo: add ALL CrudExecutor's files here
  }
}
