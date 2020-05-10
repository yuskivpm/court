package com.dsa.domain.lawsuit;

import com.dsa.controller.ControllerRequest;
import com.dsa.domain.user.Role;
import com.dsa.domain.user.User;
import com.dsa.service.command.LoginCommand;

public class LawsuitCrud extends SueCrud {

  public static final String path = "/lawsuits/";

  public LawsuitCrud() {
    super(path);
  }

  @Override
  protected boolean checkAuthority(ControllerRequest request) {
    User judge = LoginCommand.getSessionUser(request);
    return judge != null && (judge.getRole() == Role.JUDGE);
  }

}