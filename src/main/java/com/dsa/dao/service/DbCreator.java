package com.dsa.dao.service;

import com.dsa.domain.court.Court;
import com.dsa.domain.court.CourtDao;
import com.dsa.domain.court.CourtInstance;
import com.dsa.domain.lawsuit.Lawsuit;
import com.dsa.domain.lawsuit.LawsuitDao;
import com.dsa.domain.user.Role;
import com.dsa.domain.user.User;
import com.dsa.domain.user.UserDao;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.Date;

public final class DbCreator {
  private static final Logger log = Logger.getLogger(DbCreator.class);

  static {
    // auto start create in-memory db
    createDb();
  }

  @Contract(pure = true)
  private DbCreator() {
  }

  private static void sqlExecute(@NotNull Statement st, String sqlQuery, String stage, boolean autoClose) {
    try {
      st.execute(sqlQuery);
    } catch (SQLException e) {
      log.error(stage + " SQLException in DbCreator.sqlExecute[" + sqlQuery + "]: " + e);
    } finally {
      if (autoClose) {
        try {
          st.close();
        } catch (SQLException e) {
          log.error(stage + " SQLException in DbCreator.sqlExecute.onAutoCloseStatement[" + sqlQuery + "]: " + e);
        }
      }
    }
  }

  private static void recreateTables(@NotNull Connection con) {
    Statement st = null;
    try {
      st = con.createStatement();
      CourtDao courtDao = new CourtDao(con);
      UserDao userDao = new UserDao(con);
      LawsuitDao lawsuitDao = new LawsuitDao(con);

      CourtDao.sqlExecute(st, CourtDao.SQL_CREATE_TABLE, false, log);
      UserDao.sqlExecute(st, UserDao.SQL_CREATE_TABLE, false, log);
      LawsuitDao.sqlExecute(st, LawsuitDao.SQL_CREATE_TABLE, false, log);

      int index = 0;
      // Court table add values
      Court supremeCourt = new Court(++index, "Supreme Court of Ukraine", CourtInstance.CASSATION, null);
      courtDao.createEntity(supremeCourt);
      Court lvivAppealCourt = new Court(++index, "Lviv Oblast Court of Appeals", CourtInstance.APPEAL, supremeCourt);
      courtDao.createEntity(lvivAppealCourt);
      Court lvivHalytskyiLocalCourt = new Court(++index, "Halytskyi District Court of Lviv City", CourtInstance.LOCAL, lvivAppealCourt);
      courtDao.createEntity(lvivHalytskyiLocalCourt);
      Court lvivZaliznychnyiLocalCourt = new Court(++index, "Zaliznychnyi District Court of Lviv City", CourtInstance.LOCAL, lvivAppealCourt);
      courtDao.createEntity(lvivZaliznychnyiLocalCourt);
      Court kyivAppealCourt = new Court(++index, "Kyiv Oblast Court of Appeals", CourtInstance.APPEAL, supremeCourt);
      courtDao.createEntity(kyivAppealCourt);
      Court kyivSviatoshynCourt = new Court(++index, "Kyiv-Sviatoshyn Raion Court of Kyiv Oblast", CourtInstance.LOCAL, kyivAppealCourt);
      courtDao.createEntity(kyivSviatoshynCourt);
      Court kyivObukhivskyiCourt = new Court(++index, "Obukhivskyi Raion Court of Kyiv Oblast", CourtInstance.LOCAL, kyivAppealCourt);
      courtDao.createEntity(kyivObukhivskyiCourt);

      index = 0;
      // User table add value
      userDao.createEntity(new User(++index, "admin", "admin", Role.ADMIN, "Smith", null, true));
      userDao.createEntity(new User(++index, "supreme1", "supreme1", Role.JUDGE, "Bill", supremeCourt, true));
      userDao.createEntity(new User(++index, "supreme2", "supreme2", Role.JUDGE, "Dron", supremeCourt, true));
      userDao.createEntity(new User(++index, "LvivAppeal1", "appeal1", Role.JUDGE, "Cordon", lvivAppealCourt, true));
      userDao.createEntity(new User(++index, "LvivAppeal2", "appeal2", Role.JUDGE, "Aaron", lvivAppealCourt, true));
      userDao.createEntity(new User(++index, "KyivAppeal1", "appeal3", Role.JUDGE, "Alcorn", kyivAppealCourt, true));
      userDao.createEntity(new User(++index, "KyivAppeal2", "appeal4", Role.JUDGE, "Avey", kyivAppealCourt, true));
      User lvivHalytskyiLocalJudge1 = new User(++index, "lvivHalytskyiLocalJudge1", "localJudge1", Role.JUDGE, "Baines", lvivHalytskyiLocalCourt, true);
      userDao.createEntity(lvivHalytskyiLocalJudge1);
      User lvivHalytskyiLocalJudge2 = new User(++index, "lvivHalytskyiLocalJudge2", "localJudge2", Role.JUDGE, "Bates", lvivHalytskyiLocalCourt, true);
      userDao.createEntity(lvivHalytskyiLocalJudge2);
      User lvivZaliznychnyiLocalJudge1 = new User(++index, "lvivZaliznychnyiLocalJudge1", "localJudge3", Role.JUDGE, "Beere", lvivZaliznychnyiLocalCourt, true);
      userDao.createEntity(lvivZaliznychnyiLocalJudge1);
      User lvivZaliznychnyiLocalJudge2 = new User(++index, "lvivZaliznychnyiLocalJudge2", "localJudge4", Role.JUDGE, "Bomer", lvivZaliznychnyiLocalCourt, true);
      userDao.createEntity(lvivZaliznychnyiLocalJudge2);
      User kyivSviatoshynJudge1 = new User(++index, "kyivSviatoshynJudge1", "localJudge5", Role.JUDGE, "Burke", kyivSviatoshynCourt, true);
      userDao.createEntity(kyivSviatoshynJudge1);
      User kyivSviatoshynJudge2 = new User(++index, "kyivSviatoshynJudge2", "localJudge6", Role.JUDGE, "Cauley", kyivSviatoshynCourt, true);
      userDao.createEntity(kyivSviatoshynJudge2);
      User kyivObukhivskyiJudge1 = new User(++index, "kyivObukhivskyiJudge1", "localJudge7", Role.JUDGE, "Chorlton", kyivObukhivskyiCourt, true);
      userDao.createEntity(kyivObukhivskyiJudge1);
      User kyivObukhivskyiJudge2 = new User(++index, "kyivObukhivskyiJudge2", "localJudge8", Role.JUDGE, "Dines", kyivObukhivskyiCourt, true);
      userDao.createEntity(kyivObukhivskyiJudge2);

      User attorney1 = new User(++index, "attorney1", "attorney1", Role.ATTORNEY, "Arakka", null, true);
      userDao.createEntity(attorney1);
      User attorney2 = new User(++index, "attorney2", "attorney2", Role.ATTORNEY, "Mirra", null, true);
      userDao.createEntity(attorney2);
      userDao.createEntity(new User(++index, "attorney3", "attorney3", Role.ATTORNEY, "Eady", null, true));
      userDao.createEntity(new User(++index, "attorney4", "attorney4", Role.ATTORNEY, "Fearon", null, true));
      userDao.createEntity(new User(++index, "attorney5", "attorney5", Role.ATTORNEY, "Gannis", null, true));
      userDao.createEntity(new User(++index, "attorney6", "attorney6", Role.ATTORNEY, "Isler", null, true));
      userDao.createEntity(new User(++index, "attorney7", "attorney7", Role.ATTORNEY, "Lainson", null, true));

      index = 0;
      lawsuitDao.createEntity(new Lawsuit(++index, new Date(), lvivHalytskyiLocalCourt, attorney1,
          "Dismissal", attorney2, "Not acceptable", lvivHalytskyiLocalJudge1, new Date(),
          new Date(), "Verdict text", null, null, null));
      lawsuitDao.createEntity(new Lawsuit(++index, new Date(), lvivZaliznychnyiLocalCourt, attorney1,
          "Termination", attorney2, "", null, null,
          null, null, null, null, null));
      lawsuitDao.createEntity(new Lawsuit(++index, new Date(), lvivZaliznychnyiLocalCourt, attorney2,
          "Liability", attorney1, "", lvivZaliznychnyiLocalJudge1, new Date(),
          null, null, null, null, null));
      lawsuitDao.createEntity(new Lawsuit(++index, new Date(), lvivHalytskyiLocalCourt, attorney2,
          "Bankruptcy", attorney1, "", null, null,
          null, null, null, null, null));

    } catch (SQLException e) {
      log.error("Exception in DbCreator.recreateTables: " + e);
    } finally {
      try {
        st.close();
      } catch (SQLException e) {
        log.error("Exception in DbCreator.recreateTables.CloseStatements" + e);
      }
    }
  }

  public static void createDb() {
    log.info("==> Start create DB process");
    Connection connection = null;
    try {
      connection = DbPool.getConnection();
      connection.setAutoCommit(false);
      recreateTables(connection);
      connection.commit();
    } catch (SQLException e) {
      log.error("Can't connect to db in DbCreator.createDb: " + e);
    } catch (DbPoolException e) {
      e.printStackTrace();
      log.error("Fail DbPool.getConnection in DbCreator.createDb: " + e);
    } finally {
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException e) {
          log.error("Fail close connection in DbCreator.createDb: " + e);
        }
      }
    }
    log.info("==> Finish create DB process");
  }

}
