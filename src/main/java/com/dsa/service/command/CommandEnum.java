package com.dsa.service.command;

public enum CommandEnum {
  LOGIN {
    {
      this.command = new LoginCommand();
    }
  },
  LOGOUT {
    {
      this.command = new LogoutCommand();
    }
  };

  ActionCommand command;

  public ActionCommand getCommand() {
    return command;
  }
}
