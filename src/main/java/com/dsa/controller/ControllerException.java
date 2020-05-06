package com.dsa.controller;

import org.apache.log4j.Logger;

public class ControllerException extends Exception {

  private static final Logger LOG = Logger.getLogger(ControllerException.class);

  static final String EXCEPTION_TEXT = "Controller exception: ";

  public ControllerException(String message) {
    super(message);
    LOG.error(EXCEPTION_TEXT + message);
  }

  public ControllerException(String message, Throwable cause){
    super(message, cause);
    LOG.error(EXCEPTION_TEXT + message);
    LOG.error(EXCEPTION_TEXT + cause.getMessage());
  }

}
