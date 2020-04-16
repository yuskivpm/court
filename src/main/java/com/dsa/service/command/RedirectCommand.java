package com.dsa.service.command;

import com.dsa.controller.ProxyRequest;
import com.dsa.service.resource.ConfigManager;

public class RedirectCommand implements ActionCommand {
  @Override
  public String execute(ProxyRequest request){
    String pageName=request.getParameter("page");
    try{
      return ConfigManager.getProperty("path.page."+pageName);
    }catch(Exception e){
      return pageName;
    }
  }
}
