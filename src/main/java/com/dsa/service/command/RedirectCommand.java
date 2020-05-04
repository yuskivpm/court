package com.dsa.service.command;

import com.dsa.view.ProxyRequest;
import com.dsa.service.resource.ConfigManager;

import org.jetbrains.annotations.NotNull;

public class RedirectCommand implements IActionCommand {

  @Override
  public String execute(@NotNull ProxyRequest request) {
    String pageName = request.getParameter("page");
    try {
      return ConfigManager.getProperty("path.page." + pageName);
    } catch (Exception e) {
      return pageName;
    }
  }

}
