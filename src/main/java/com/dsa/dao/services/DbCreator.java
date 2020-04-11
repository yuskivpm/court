package com.dsa.dao.services;

import com.dsa.dao.entity.CourtDao;
import com.dsa.dao.entity.UserDao;
import com.dsa.model.Court;
import com.dsa.model.CourtInstance;
import com.dsa.model.Role;
import com.dsa.model.User;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.sql.*;

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

      courtDao.sqlExecute(st,CourtDao.SQL_CREATE_TABLE,false,log);
      userDao.sqlExecute(st,UserDao.SQL_CREATE_TABLE,false,log);

      stage="SUE table";
      sqlExecute(st,"CREATE TABLE IF NOT EXISTS SUE(" +
          "ID BIGINT AUTO_INCREMENT PRIMARY KEY, " +
          "SUITOR_ID BIGINT NOT NULL, " +
          "DEFENDANT_ID BIGINT NOT NULL, " +
          "COURT_ID BIGINT NOT NULL, " +
          "SUE_DATE DATE NOT NULL, "+
          "CLAIM_TEXT VARCHAR NOT NULL, " +
          "FOREIGN KEY (SUITOR_ID) REFERENCES USER(ID), " +
          "FOREIGN KEY (DEFENDANT_ID) REFERENCES USER(ID), " +
          "FOREIGN KEY (COURT_ID) REFERENCES COURT(ID)" +
          ")",stage,false);

      // Verdicts table
      stage="VERDICT table";
      sqlExecute(st,"CREATE TABLE IF NOT EXISTS VERDICT(" +
          "ID BIGINT AUTO_INCREMENT PRIMARY KEY, " +
          "LAWSUIT_ID BIGINT NOT NULL, " +
          "EFFECTIVE_DATE DATE NOT NULL, "+
          "VERDICT_TYPE_ID INT NOT NULL, " +
          "JURISDICTION_COURT_ID BIGINT DEFAULT NULL, " +
          "FOREIGN KEY (JURISDICTION_COURT_ID) REFERENCES COURT(ID)" +
//          "FOREIGN KEY (LAWSUIT_ID) REFERENCES LAWSUIT(ID)" +
          ")",stage,false);

      stage="LAWSUIT table";
      sqlExecute(st,"CREATE TABLE IF NOT EXISTS LAWSUIT(" +
          "ID BIGINT AUTO_INCREMENT PRIMARY KEY, " +
          "SUITOR_ID BIGINT NOT NULL, " +
          "DEFENDANT_ID BIGINT NOT NULL, " +
          "JURISDICTION_COURT_ID BIGINT DEFAULT NULL, " +
          "JUDGE_ID BIGINT NOT NULL, "+
          "SUE_DATE DATE NOT NULL, "+
          "START_DATE DATE DEFAULT NULL, "+
          "CLAIM_TEXT VARCHAR(255) NOT NULL, " +
          "DEFENDANT_TEXT VARCHAR(255) DEFAULT NULL, " +
          "VERDICT_ID BIGINT DEFAULT NULL, " +
          "SUITOR_APPEAL_DATE DATE DEFAULT NULL, "+
          "SUITOR_APPEAL_TEXT VARCHAR(255) DEFAULT NULL, " +
          "DEFENDANT_APPEAL_DATE DATE DEFAULT NULL, "+
          "DEFENDANT_APPEAL_TEXT VARCHAR(255) DEFAULT NULL, " +
          "EXECUTION_DATE DATE DEFAULT NULL,"+
          "FOREIGN KEY (SUITOR_ID) REFERENCES USER(ID), " +
          "FOREIGN KEY (DEFENDANT_ID) REFERENCES USER(ID), " +
          "FOREIGN KEY (JURISDICTION_COURT_ID) REFERENCES COURT(ID), " +
          "FOREIGN KEY (JUDGE_ID) REFERENCES USER(ID), "+
          "FOREIGN KEY (VERDICT_ID) REFERENCES VERDICT(ID)" +
          ")",stage,false);

      stage="STAGE table";
      sqlExecute(st,"CREATE TABLE IF NOT EXISTS STAGE(" +
          "ID BIGINT AUTO_INCREMENT PRIMARY KEY, " +
          "LAWSUIT_ID BIGINT NOT NULL, " +
          "JUDGE_ID BIGINT NOT NULL, "+
          "SUE_DATE DATE NOT NULL, "+
          "START_DATE DATE NOT NULL, "+
          "CLAIM_TEXT VARCHAR NOT NULL, " +
          "DEFENDANT_TEXT VARCHAR DEFAULT NULL, " +
          "VERDICT_ID BIGINT DEFAULT NULL, " +
          "SUITOR_APPEAL_DATE DATE DEFAULT NULL, "+
          "SUITOR_APPEAL_TEXT VARCHAR DEFAULT NULL, " +
          "DEFENDANT_APPEAL_DATE DATE DEFAULT NULL, "+
          "DEFENDANT_APPEAL_TEXT VARCHAR DEFAULT NULL, " +
          "FOREIGN KEY (LAWSUIT_ID) REFERENCES LAWSUIT(ID) ON DELETE CASCADE, " +
          "FOREIGN KEY (JUDGE_ID) REFERENCES USER(ID), "+
          "FOREIGN KEY (VERDICT_ID) REFERENCES VERDICT(ID)" +
          ")",stage,false);

      // Verdicts table add foreign key
      stage="VERDICT table - add foreign key";
      sqlExecute(st,"ALTER TABLE VERDICT "+
          "ADD CONSTRAINT FK_TempVERDICT "+
          "FOREIGN KEY (LAWSUIT_ID) "+
          "REFERENCES LAWSUIT(ID)"
          ,stage,false);

      // Court table add values
      Court supremeCourt=new Court(1,"Supreme court", CourtInstance.CASSATION, null);
      Court appealCourt= new Court(2,"Appeal court", CourtInstance.APPEAL, supremeCourt);
      Court localCourt=new Court(3,"Local court", CourtInstance.LOCAL, appealCourt);
      courtDao.add(supremeCourt);
      courtDao.add(appealCourt);
      courtDao.add(localCourt);
      // User table add value
      userDao.add(new User(1,"admin", "admin", Role.ADMIN,"admin name",null,true));
      userDao.add(new User(2,"supreme1", "supreme1", Role.JUDGE,"Supreme name",supremeCourt,true));
      userDao.add(new User(3,"appeal1", "appeal1", Role.JUDGE,"Appeal name",appealCourt,true));
      userDao.add(new User(4,"local1", "local1", Role.JUDGE,"Local name",localCourt,true));
      userDao.add(new User(5,"attorney1", "attorney1", Role.ATTORNEY,"Attorney1 name",null,true));
      userDao.add(new User(6,"attorney2", "attorney2", Role.ATTORNEY,"Attorney2",null,true));

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
