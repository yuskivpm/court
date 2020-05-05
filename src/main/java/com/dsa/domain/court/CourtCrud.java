package com.dsa.domain.court;

import com.dsa.controller.ControllerRequest;
import com.dsa.controller.ControllerResponse;
import com.dsa.dao.service.DbPoolException;
import com.dsa.domain.user.Role;
import com.dsa.domain.user.User;
import com.dsa.service.command.LoginCommand;
import com.dsa.service.crud.AbstractCrud;

import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class CourtCrud extends AbstractCrud<Court, CourtDao> {
  public static final String path = "/courts/";

  public CourtCrud() {
    super(path);
  }

  @Override
  protected boolean checkAuthority(ControllerRequest request, ControllerResponse controllerResponse) {
    User user = LoginCommand.getSessionUser(request);
    return user != null && user.getRole() == Role.ADMIN;
  }

  @Override
  protected Court createEntityFromParameters(@NotNull ControllerRequest request, long id) {
    Court court = null;
    String courtName = request.getParameter("courtName");
    CourtInstance courtInstance = CourtInstance.valueOf(request.getParameter("courtInstance"));
    long mainCourtId = Long.parseLong(request.getParameter("mainCourtId"));
    if (mainCourtId == id || courtInstance == CourtInstance.CASSATION) {
      mainCourtId = 0;
    } else {
      Court mainCourt;
      try (CourtDao courtDao = new CourtDao()) {
        mainCourt = courtDao.readEntity(mainCourtId);
        if (mainCourt.getCourtInstance().getInstanceLevel() <= courtInstance.getInstanceLevel()) {
          mainCourtId = 0;
        }
      } catch (SQLException | DbPoolException e) {
        mainCourtId = 0;
      }
    }
    if (courtName != null && !courtName.isEmpty()) {
      court = new Court(id, courtName, courtInstance, null);
      court.setMainCourtId(mainCourtId);
    }
    return court;
  }

  @Override
  protected CourtDao createEntityDao() throws SQLException, DbPoolException {
    return new CourtDao();
  }

}
