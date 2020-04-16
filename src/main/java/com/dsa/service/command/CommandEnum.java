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
  MAINPAGE{
    {
      this.command = new MainPageCommand();
    }
  },
  REDIRECT{
    {
      this.command= new RedirectCommand();
    }
  },
  WRONG_COMMAND {
    {
      this.command = null;//todo: check!!! new EmptyCommand();
    }
  };

  ActionCommand command;

  public ActionCommand getCommand() {
    return command;
  }
}
