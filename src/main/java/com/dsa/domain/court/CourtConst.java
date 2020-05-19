package com.dsa.domain.court;

import com.dsa.domain.MyEntity;
import org.jetbrains.annotations.Contract;

public class CourtConst {

  public static final String ID = MyEntity.ID;
  public static final String ENTITY_NAME = "Court";
  public static final String COURT_NAME = "courtName";
  public static final String COURT_INSTANCE = "courtInstance";
  public static final String MAIN_COURT_ID = "mainCourtId";

  private static final String PATH = "/courts/";

  @Contract(pure = true)
  public static String getPath() {
    return PATH;
  }

}
