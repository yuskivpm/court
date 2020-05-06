package com.dsa.dao;

import org.apache.log4j.Logger;

public class DbPoolException extends Exception {

  private static final Logger LOG = Logger.getLogger(DbPoolException.class);

  public DbPoolException(String s) {
    super(s);
    LOG.error("DB exception: " + s);
  }

}
