package com.dsa.service.command;

import com.dsa.view.ProxyRequest;

public interface IActionCommand {
  String execute(ProxyRequest request);
}
