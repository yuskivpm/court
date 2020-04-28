package com.dsa.service.resource;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ResourceBundle;

public class MessageManager {
  private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");

  @Contract(pure = true)
  private MessageManager() {
  }

  @NotNull
  public static String getProperty(String key) {
    return resourceBundle.getString(key);
  }

}
