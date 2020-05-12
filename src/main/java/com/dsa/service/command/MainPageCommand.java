package com.dsa.service.command;

import com.dsa.controller.ControllerRequest;
import com.dsa.controller.ResponseType;
import com.dsa.domain.court.CourtDao;
import com.dsa.domain.court.Court;
import com.dsa.domain.lawsuit.Lawsuit;
import com.dsa.domain.lawsuit.LawsuitDao;
import com.dsa.domain.user.User;
import com.dsa.domain.user.UserDao;
import com.dsa.dao.DbPoolException;
import com.dsa.service.resource.ConfigManager;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;

public class MainPageCommand implements Function<ControllerRequest, ControllerRequest> {

  private static final Logger log = Logger.getLogger(MainPageCommand.class);
  public static final String path = "main_page";

  @Override
  public ControllerRequest apply(@NotNull ControllerRequest request) {
    User user = LoginCommand.getSessionUser(request);
    return user == null ? new EmptyCommand().apply(request) : apply(request, user);
  }

  public ControllerRequest apply(@NotNull ControllerRequest request, User user) {
    request.setResponseType(ResponseType.FORWARD);
    request.setResponseValue(MainPageCommand.getMainPageForUser(user));
    MainPageCommand.initializeJsp(user, request);
    return request;
  }

  @NotNull
  private static String getMainPageForUser(User user) {
    return ConfigManager.getProperty("path.page.main" + (user == null ? "" : ("." + user.getRole())));
  }

  private static void initializeJspForAdmin(@NotNull User currentUser, @NotNull ControllerRequest request) {
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
  }

  private static void initializeJspForAttorney(@NotNull User currentUser, @NotNull ControllerRequest request) {
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
  }

  private static void initializeJspForJudge(@NotNull User currentUser, @NotNull ControllerRequest request) {
    try (LawsuitDao lawsuitDao = new LawsuitDao()) {
      List<Lawsuit> lawsuits = lawsuitDao.readAllForJudgeId(currentUser.getId());
      for (Lawsuit lawsuit : lawsuits) {
        lawsuitDao.loadAllSubEntities(lawsuit);
      }
      request.setAttribute("lawsuits", lawsuits);
      lawsuits = lawsuitDao.readAllUnacceptedForCourtId(currentUser.getCourtId());
      for (Lawsuit lawsuit : lawsuits) {
        lawsuitDao.loadAllSubEntities(lawsuit);
      }
      request.setAttribute("sues", lawsuits);
    } catch (SQLException | DbPoolException e) {
      log.error("Fail get Lawsuits list in MainPageCommand for " + currentUser.getRole() + ": " + e);
    }
  }

  private static void initializeJsp(User currentUser, @NotNull ControllerRequest request) {
    request.setAttribute("curUser", currentUser);
    switch (currentUser.getRole()) {
      case JUDGE:
        initializeJspForJudge(currentUser, request);
        break;
      case ATTORNEY:
        initializeJspForAttorney(currentUser, request);
        break;
      case ADMIN:
        initializeJspForAdmin(currentUser, request);
        break;
      default:
    }
  }

}
