package com.dsa.service.command;

import com.dsa.controller.ProxyRequest;

public interface IActionCommand {
  String execute(ProxyRequest request);
}
