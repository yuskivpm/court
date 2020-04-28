package com.dsa.service;

import com.dsa.controller.ProxyRequest;
import com.dsa.service.command.IActionCommand;
import com.dsa.service.command.CommandEnum;
import com.dsa.service.resource.MessageManager;

import org.jetbrains.annotations.NotNull;

public class ActionFactory {
  public static CommandEnum getCommandEnum(@NotNull String command) {
    try {
      return CommandEnum.valueOf(command.toUpperCase());
    } catch (IllegalArgumentException e) {
      return CommandEnum.WRONG_COMMAND;
    }
  }

  public static CommandEnum getCommandEnum(ProxyRequest request) {
    return getCommandEnum(getCommand(request));
  }

  @NotNull
  public static String getCommand(@NotNull ProxyRequest request) {
    String action = request.getParameter("command");
    return action == null ? "" : action;
  }

  public static IActionCommand defineCommand(ProxyRequest request) {
    IActionCommand currentCommand = null;
    String action = getCommand(request);
    if (action != null && !action.isEmpty()) {
      CommandEnum currentEnum = getCommandEnum(action);
      if (currentEnum == CommandEnum.WRONG_COMMAND) {
        request.setAttribute("wrongAction", action + MessageManager.getProperty("message.wrongAction"));
      }
      currentCommand = currentEnum.getCommand();
    }
    return currentCommand;
  }

}
