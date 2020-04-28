package com.dsa.service.command;

import com.dsa.controller.ProxyRequest;
import com.dsa.dao.entity.CourtDao;
import com.dsa.dao.entity.LawsuitDao;
import com.dsa.dao.entity.UserDao;
import com.dsa.dao.services.DbPoolException;
import com.dsa.service.resource.ConfigManager;
import com.dsa.model.*;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.List;

public class MainPageCommand implements IActionCommand {

  private static final Logger log = Logger.getLogger(MainPageCommand.class);

  @Override
  public String execute(ProxyRequest request) {
    User user = LoginCommand.getSessionUser(request);
    return user == null ? new EmptyCommand().execute(request) : execute(request, user);
  }

  public String execute(ProxyRequest request, User user) {
    MainPageCommand.InitializeJsp(user, request);
    return MainPageCommand.getMainPageForUser(user);
  }

  @NotNull
  public static String getMainPageForUser(User user) {
    return ConfigManager.getProperty("path.page.main" + (user == null ? "" : ("." + user.getRole())));
  }

  private static void InitializeJsp(User currentUser, @NotNull ProxyRequest request) {
    request.setAttribute("curUser", currentUser);
    switch (currentUser.getRole().toString()) {

      case "JUDGE":
        try (LawsuitDao lawsuitDao = new LawsuitDao()) {
          List<Lawsuit> lawsuits = lawsuitDao.readAllForJudgeId(currentUser.getId());
          for (Lawsuit lawsuit : lawsuits) {
            lawsuitDao.loadAllSubEntities(lawsuit);
          }
          request.setAttribute("lawsuits", lawsuits);
          if (currentUser.getCourt().getCourtInstance() == CourtInstance.LOCAL) {
            // for local courts - get list of unaccepted sues
            lawsuits = lawsuitDao.readAllUnacceptedForCourtId(currentUser.getCourtId());
            for (Lawsuit lawsuit : lawsuits) {
              lawsuitDao.loadAllSubEntities(lawsuit);
            }
            request.setAttribute("sues", lawsuits);
          }
        } catch (SQLException | DbPoolException e) {
          log.error("Fail get Lawsuits list in MainPageCommand for " + currentUser.getRole() + ": " + e);
        }
        break;

      case "ATTORNEY":
        try (LawsuitDao lawsuitDao = new LawsuitDao()) {
          // list of own sues
          List<Lawsuit> lawsuits = lawsuitDao.readAllBySuitorId(currentUser.getId());
          for (Lawsuit lawsuit : lawsuits) {
            lawsuitDao.loadAllSubEntities(lawsuit);
          }
          request.setAttribute("ownLawsuits", lawsuits);
          // list of lawsuits for defendant role
          lawsuits = lawsuitDao.readAllByDefendantId(currentUser.getId());
          for (Lawsuit lawsuit : lawsuits) {
            lawsuitDao.loadAllSubEntities(lawsuit);
          }
          request.setAttribute("asDefendantLawsuits", lawsuits);

        } catch (SQLException | DbPoolException e) {
          log.error("Fail get Lawsuits list in MainPageCommand for " + currentUser.getRole() + ": " + e);
        }
        break;

      case "ADMIN":
        // get users list
        try (UserDao user = new UserDao()) {
          List<User> users = user.readAll();
          for (User curUser : users) {
            user.loadAllSubEntities(curUser);
          }
          request.setAttribute("users", users);
        } catch (SQLException | DbPoolException e) {
          log.error("Fail get Users list in MainPageCommand for " + currentUser.getRole() + ": " + e);
        }
        // get courts list
        try (CourtDao court = new CourtDao()) {
          List<Court> courts = court.readAll();
          for (Court curCourt : courts) {
            court.loadAllSubEntities(curCourt);
          }
          request.setAttribute("courts", courts);
        } catch (SQLException | DbPoolException e) {
          log.error("Fail get Courts list in MainPageCommand for " + currentUser.getRole() + ": " + e);
        }
        break;

      case "GUEST":
//        todo:  unready prepare jsp variables for GUEST main page
//        break;
      default:
//        todo:  unready ERROR main page
    }
  }

}
