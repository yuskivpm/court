package com.dsa.service.command;

import com.dsa.controller.ProxyRequest;
import com.dsa.service.resource.ConfigManager;

public class EmptyCommand implements ActionCommand {
  @Override
  public String execute(ProxyRequest request){
    return ConfigManager.getProperty("path.page.login");
  }

}
