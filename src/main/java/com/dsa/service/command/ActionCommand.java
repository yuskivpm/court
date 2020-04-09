package com.dsa.service.command;

import com.dsa.controller.ProxyRequest;

public interface ActionCommand {
  String execute(ProxyRequest request);
}
