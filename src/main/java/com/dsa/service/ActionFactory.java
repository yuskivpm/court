package com.dsa.service;

import com.dsa.controller.ProxyRequest;
import com.dsa.service.command.ActionCommand;
import com.dsa.service.command.CommandEnum;
import com.dsa.service.command.EmptyCommand;
import com.dsa.service.resource.MessageManager;

public class ActionFactory {
  public ActionCommand defineCommand(ProxyRequest request){
    ActionCommand currentCommand = new EmptyCommand();
    String action = request.getParameter("command");
    if (action==null || action.isEmpty()){
      return currentCommand;
    }else{
      try{
        CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());
        currentCommand = currentEnum.getCommand();
      }catch(IllegalArgumentException e){
        request.setAttribute("wrongAction", action + MessageManager.getProperty("message.wrongAction"));
      }
      return currentCommand;
    }
  }
}
