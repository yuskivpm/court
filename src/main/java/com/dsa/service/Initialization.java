package com.dsa.service;

import com.dsa.service.crud.CourtCrud;
import com.dsa.service.crud.CrudExecutor;
import com.dsa.service.crud.UserCrud;

public class Initialization {
  public static void initialize() throws ClassNotFoundException{
    // initialization of DbCreator
    Class.forName("com.dsa.dao.services.DbCreator");
    CrudExecutor.register(UserCrud.path, new UserCrud());
    CrudExecutor.register(CourtCrud.path, new CourtCrud());
//    todo: add ALL CrudExecutor's files here
  }
}
