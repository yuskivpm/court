package com.dsa.service.command;
// todo: REWRITE ALL!!!

import com.dsa.controller.ControllerRequest;
import com.dsa.controller.ControllerResponse;
import com.dsa.controller.ResponseType;
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
import java.util.function.Function;

public class MainPageCommand implements Function<ControllerRequest, ControllerResponse> {

  private static final Logger log = Logger.getLogger(MainPageCommand.class);
  public static final String path = "main_page";

  @Override
  public ControllerResponse apply(@NotNull ControllerRequest request) {
    User user = LoginCommand.getSessionUser(request);
    return user == null ? new EmptyCommand().apply(request) : apply(request, user);
  }

  public ControllerResponse apply(@NotNull ControllerRequest request, User user) {
    ControllerResponse controllerResponse = new ControllerResponse(
        ResponseType.FORWARD,
        MainPageCommand.getMainPageForUser(user)
    );
    MainPageCommand.initializeJsp(user, request, controllerResponse);
    return controllerResponse;
  }

  @NotNull
  public static String getMainPageForUser(User user) {
    return ConfigManager.getProperty("path.page.main" + (user == null ? "" : ("." + user.getRole())));
  }
  private static void initializeJspForAdmin(
      @NotNull User currentUser,
      @NotNull ControllerRequest controllerRequest,
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
      @NotNull ControllerRequest controllerRequest,
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
      @NotNull ControllerRequest controllerRequest,
      @NotNull ControllerResponse controllerResponse
  ){
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
      @NotNull ControllerRequest controllerRequest,
      @NotNull ControllerResponse controllerResponse
  ) {
    controllerResponse.setAttribute("curUser", currentUser);
    switch (currentUser.getRole().toString()) {
      case "JUDGE":
        initializeJspForJudge(currentUser, controllerRequest, controllerResponse);
        break;
      case "ATTORNEY":
        initializeJspForAttorney(currentUser, controllerRequest, controllerResponse);
        break;
      case "ADMIN":
        initializeJspForAdmin(currentUser, controllerRequest, controllerResponse);
        break;
      case "GUEST":
//        todo:  unready prepare jsp variables for GUEST main page
//        break;
      default:
//        todo:  unready ERROR main page
    }
  }

}
