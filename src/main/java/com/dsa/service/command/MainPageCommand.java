package com.dsa.service.command;

import com.dsa.controller.ControllerRequest;
import com.dsa.controller.ControllerResponse;
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
import java.util.function.BiFunction;

public class MainPageCommand implements BiFunction<ControllerRequest, ControllerResponse, ControllerResponse> {

  private static final Logger log = Logger.getLogger(MainPageCommand.class);
  public static final String path = "main_page";

  @Override
  public ControllerResponse apply(@NotNull ControllerRequest request, ControllerResponse controllerResponse) {
    User user = LoginCommand.getSessionUser(request);
    return user == null ? new EmptyCommand().apply(request, controllerResponse) : apply(controllerResponse, user);
  }

  public ControllerResponse apply(@NotNull ControllerResponse controllerResponse, User user) {
    controllerResponse.setResponseType(ResponseType.FORWARD);
    controllerResponse.setResponseValue(MainPageCommand.getMainPageForUser(user));
    MainPageCommand.initializeJsp(user, controllerResponse);
    return controllerResponse;
  }

  @NotNull
  private static String getMainPageForUser(User user) {
    return ConfigManager.getProperty("path.page.main" + (user == null ? "" : ("." + user.getRole())));
  }

  private static void initializeJspForAdmin(
      @NotNull User currentUser,
      @NotNull ControllerResponse controllerResponse
  ) {
    // get users list
    try (UserDao user = new UserDao()) {
      List<User> users = user.readAll();
      for (User curUser : users) {
        user.loadAllSubEntities(curUser);
      }
      controllerResponse.setAttribute("users", users);
    } catch (SQLException | DbPoolException e) {
      log.error("Fail get Users list in MainPageCommand for " + currentUser.getRole() + ": " + e);
    }
    // get courts list
    try (CourtDao court = new CourtDao()) {
      List<Court> courts = court.readAll();
      for (Court curCourt : courts) {
        court.loadAllSubEntities(curCourt);
      }
      controllerResponse.setAttribute("courts", courts);
    } catch (SQLException | DbPoolException e) {
      log.error("Fail get Courts list in MainPageCommand for " + currentUser.getRole() + ": " + e);
    }
  }

  private static void initializeJspForAttorney(
      @NotNull User currentUser,
      @NotNull ControllerResponse controllerResponse
  ) {
    try (LawsuitDao lawsuitDao = new LawsuitDao()) {
      // list of own sues
      List<Lawsuit> lawsuits = lawsuitDao.readAllBySuitorId(currentUser.getId());
      for (Lawsuit lawsuit : lawsuits) {
        lawsuitDao.loadAllSubEntities(lawsuit);
      }
      controllerResponse.setAttribute("ownLawsuits", lawsuits);
      // list of lawsuits for defendant role
      lawsuits = lawsuitDao.readAllByDefendantId(currentUser.getId());
      for (Lawsuit lawsuit : lawsuits) {
        lawsuitDao.loadAllSubEntities(lawsuit);
      }
      controllerResponse.setAttribute("asDefendantLawsuits", lawsuits);

    } catch (SQLException | DbPoolException e) {
      log.error("Fail get Lawsuits list in MainPageCommand for " + currentUser.getRole() + ": " + e);
    }
  }

  private static void initializeJspForJudge(
      @NotNull User currentUser,
      @NotNull ControllerResponse controllerResponse
  ) {
    try (LawsuitDao lawsuitDao = new LawsuitDao()) {
      List<Lawsuit> lawsuits = lawsuitDao.readAllForJudgeId(currentUser.getId());
      for (Lawsuit lawsuit : lawsuits) {
        lawsuitDao.loadAllSubEntities(lawsuit);
      }
      controllerResponse.setAttribute("lawsuits", lawsuits);
      lawsuits = lawsuitDao.readAllUnacceptedForCourtId(currentUser.getCourtId());
      for (Lawsuit lawsuit : lawsuits) {
        lawsuitDao.loadAllSubEntities(lawsuit);
      }
      controllerResponse.setAttribute("sues", lawsuits);
    } catch (SQLException | DbPoolException e) {
      log.error("Fail get Lawsuits list in MainPageCommand for " + currentUser.getRole() + ": " + e);
    }
  }

  private static void initializeJsp(
      User currentUser,
      @NotNull ControllerResponse controllerResponse
  ) {
    controllerResponse.setAttribute("curUser", currentUser);
    switch (currentUser.getRole()) {
      case JUDGE:
        initializeJspForJudge(currentUser, controllerResponse);
        break;
      case ATTORNEY:
        initializeJspForAttorney(currentUser, controllerResponse);
        break;
      case ADMIN:
        initializeJspForAdmin(currentUser, controllerResponse);
        break;
      default:
    }
  }

}
