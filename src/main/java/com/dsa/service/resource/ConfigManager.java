package com.dsa.service.resource;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ResourceBundle;

public class ConfigManager {
  private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("config");

  @Contract(pure = true)
  private ConfigManager() {
  }

  @NotNull
  public static String getProperty(String key) {
    return resourceBundle.getString(key);
  }
}
