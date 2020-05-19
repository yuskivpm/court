package com.dsa.domain.user;

import com.dsa.domain.MyEntity;

import org.jetbrains.annotations.Contract;

public class UserConst {

  public static final String ID = MyEntity.ID;
  public static final String ENTITY_NAME = "User";
  public static final String LOGIN = "login";
  public static final String PASSWORD = "password";
  public static final String ROLE = "role";
  public static final String NAME = "name";
  public static final String COURT_ID = "courtId";
  public static final String IS_ACTIVE = "isActive";

  private static final String PATH = "/users/";

  @Contract(pure = true)
  public static String getPath() {
    return PATH;
  }

}
