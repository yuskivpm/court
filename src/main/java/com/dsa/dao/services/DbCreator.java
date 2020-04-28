package com.dsa.dao.services;

import com.dsa.dao.entity.*;
import com.dsa.model.*;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.Date;

public final class DbCreator {
  private static final Logger log = Logger.getLogger(DbCreator.class);

  static{
    // auto start create in-memory db
    createDb();
  }

  private DbCreator(){}

  private static void sqlExecute(@NotNull Statement st, String sqlQuery, String stage, boolean autoClose){
    try{
      st.execute(sqlQuery);
    }catch(SQLException e){
      log.error(stage+" SQLException in DbCreator.sqlExecute["+sqlQuery+"]: "+e);
    }finally{
      if(autoClose){
        try{
          st.close();
        }catch(SQLException e){
          log.error(stage+" SQLException in DbCreator.sqlExecute.onAutoCloseStatement["+sqlQuery+"]: "+e);
        }
      }
    }
  }

  private static void recreateTables(@NotNull Connection con){
    Statement st=null;
    String stage="Create statements";
    try{
      st=con.createStatement();
      CourtDao courtDao=new CourtDao(con);
      UserDao userDao=new UserDao(con);
      SueLawsuitDao sueLawsuitDao=new SueLawsuitDao(con);

      CourtDao.sqlExecute(st,CourtDao.SQL_CREATE_TABLE,false,log);
      UserDao.sqlExecute(st,UserDao.SQL_CREATE_TABLE,false,log);
      SueLawsuitDao.sqlExecute(st,SueLawsuitDao.SQL_CREATE_TABLE,false,log);

      // Court table add values
      Court supremeCourt=new Court(1,"Supreme court", CourtInstance.CASSATION, null);
      Court appealCourt= new Court(2,"Appeal court", CourtInstance.APPEAL, supremeCourt);
      Court localCourt=new Court(3,"Local court", CourtInstance.LOCAL, appealCourt);
      courtDao.createEntity(supremeCourt);
      courtDao.createEntity(appealCourt);
      courtDao.createEntity(localCourt);
      // User table add value
      userDao.createEntity(new User(1,"admin", "admin", Role.ADMIN,"admin name",null,true));
      userDao.createEntity(new User(2,"supreme1", "supreme1", Role.JUDGE,"Supreme name",supremeCourt,true));
      userDao.createEntity(new User(3,"appeal1", "appeal1", Role.JUDGE,"Appeal name",appealCourt,true));
      User localJudge = new User(4,"localJudge1", "localJudge1", Role.JUDGE,"Local name",localCourt,true);
      User attorney1 = new User(5,"attorney1", "attorney1", Role.ATTORNEY,"Attorney1 name",null,true);
      User attorney2 = new User(6,"attorney2", "attorney2", Role.ATTORNEY,"Attorney2",null,true);
      userDao.createEntity(localJudge);
      userDao.createEntity(attorney1);
      userDao.createEntity(attorney2);

      sueLawsuitDao.createEntity(new SueLawsuit(1,new Date(),localCourt,attorney1,"claimText",attorney2,"",null,null,null,"",null));

    }catch(SQLException e){
      log.error("Exception in DbCreator.recreateTables: "+e);
    }finally{
      try{
        st.close();
      }catch(SQLException e){
        log.error("Exception in DbCreator.recreateTables.CloseStatements"+e);
      }
    }
  }

  public static void createDb(){
    log.info("==> Start create DB process");
    Connection connection=null;
    try{
      connection=DbPool.getConnection();
      connection.setAutoCommit(false);
      recreateTables(connection);
      connection.commit();
    }catch (SQLException e){
      log.error("Can't connect to db in DbCreator.createDb: "+e);
    } catch (DbPoolException e) {
      e.printStackTrace();
      log.error("Fail DbPool.getConnection in DbCreator.createDb: "+e);
    } finally{
      if(connection!=null){
        try{
          connection.close();
        }catch(SQLException e){
          log.error("Fail close connection in DbCreator.createDb: "+e);
        }
      }
    }
    log.info("==> Finish create DB process");
  }

}
