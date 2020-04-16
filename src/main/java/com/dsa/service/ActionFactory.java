package com.dsa.service;

import com.dsa.controller.ProxyRequest;
import com.dsa.service.command.ActionCommand;
import com.dsa.service.command.CommandEnum;
import com.dsa.service.command.EmptyCommand;
import com.dsa.service.resource.MessageManager;

public class ActionFactory {
  public static CommandEnum getCommandEnum(String command){
    try{
      return CommandEnum.valueOf(command.toUpperCase());
    }catch(IllegalArgumentException e){
//      request.setAttribute("wrongAction", action + MessageManager.getProperty("message.wrongAction"));
      return CommandEnum.WRONG_COMMAND;
    }
  }

  public static CommandEnum getCommandEnum(ProxyRequest request){
    return getCommandEnum(getCommand(request));
  }

  public static String getCommand(ProxyRequest request){
    String action = request.getParameter("command");
    return action==null?"":action;
  }

  public static ActionCommand defineCommand(ProxyRequest request){
    ActionCommand currentCommand = null;
// todo: check!!   ActionCommand currentCommand = new EmptyCommand();
    String action = getCommand(request);
    if (action!=null && !action.isEmpty()){
        CommandEnum currentEnum = getCommandEnum(action);
        if (currentEnum==CommandEnum.WRONG_COMMAND){
          request.setAttribute("wrongAction", action + MessageManager.getProperty("message.wrongAction"));
        }
        currentCommand = currentEnum.getCommand();
    }
    return currentCommand;
  }
}
