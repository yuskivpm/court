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
  },
  WRONG_COMMAND {
    {
      this.command = new EmptyCommand();
    }
  };

  ActionCommand command;

  public ActionCommand getCommand() {
    return command;
  }
}
