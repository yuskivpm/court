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
  MAIN_PAGE {
    {
      this.command = new MainPageCommand();
    }
  },
  REDIRECT {
    {
      this.command = new RedirectCommand();
    }
  },
  WRONG_COMMAND {
    {
      this.command = null;//todo: check!!! new EmptyCommand();
    }
  };

  IActionCommand command;

  public IActionCommand getCommand() {
    return command;
  }

}
