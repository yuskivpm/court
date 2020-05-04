package com.dsa.service.crud;

import com.dsa.view.ProxyRequest;
import com.dsa.model.Role;
import com.dsa.model.User;
import com.dsa.service.command.LoginCommand;

public class LawsuitCrud extends SueCrud {
  public static final String path = "/lawsuits";
  private User judge;

  public LawsuitCrud() {
    super(path);
  }

  @Override
  protected boolean checkAuthority(ProxyRequest request) {
    judge = LoginCommand.getSessionUser(request);
    return judge != null && (judge.getRole() == Role.JUDGE);
  }

}