package com.dsa.service;

import com.dsa.service.crud.CrudExecutor;
import com.dsa.service.crud.UserCrud;

public class Initialization {
  public static void initialize() throws ClassNotFoundException{
    // initialization of DbCreator
    Class.forName("com.dsa.dao.services.DbCreator");
    CrudExecutor.register(UserCrud.path, UserCrud::execute);
  }
}
