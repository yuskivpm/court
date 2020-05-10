package com.dsa;

import com.dsa.dao.AbstractEntityDao;
import com.dsa.dao.DbPoolException;
import com.dsa.service.Initialization;

public class InitDbForTests {


  public static void initializeDb(boolean initializeDao) throws DbPoolException {
    Initialization.initializeDbPool();
    Initialization.createDefaultDb(true, Initialization.dbPool);
    if (initializeDao){
      AbstractEntityDao.setDbPool(Initialization.dbPool);
    }
  }

}
