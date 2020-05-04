package com.dsa.service.command;

import com.dsa.view.ProxyRequest;
import com.dsa.service.resource.ConfigManager;

import org.jetbrains.annotations.NotNull;

public class LogoutCommand implements IActionCommand {

  @Override
  public String execute(@NotNull ProxyRequest request) {
    request.setAttribute("user", "");
    request.getSession().invalidate();
    return ConfigManager.getProperty("path.page.index");
  }

}
