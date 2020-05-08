package com.dsa;

public class InitDbForTests {

  public static void initializeDb() throws ClassNotFoundException {
    Class.forName("com.dsa.dao.service.DbCreator");
  }

}
