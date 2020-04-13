package com.dsa.service.command;

import com.dsa.controller.ProxyRequest;
import com.dsa.service.resource.ConfigManager;

public class LogoutCommand implements ActionCommand {
  @Override
  public String execute(ProxyRequest request){
    request.setAttribute("user", "");
    request.getSession().invalidate();
    return ConfigManager.getProperty("path.page.index");
  }
}
