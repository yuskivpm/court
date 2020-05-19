package com.dsa.domain.court;

import com.dsa.controller.ControllerRequest;
import com.dsa.dao.DbPoolException;
import com.dsa.domain.user.Role;
import com.dsa.domain.user.User;
import com.dsa.service.command.LoginCommand;
import com.dsa.service.crud.AbstractCrud;

import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

import static com.dsa.domain.court.CourtConst.*;

public class CourtCrud extends AbstractCrud<Court, CourtDao> {

  public static final String path = "/courts/";

  public CourtCrud() {
    super(path);
  }

  @Override
  protected boolean checkAuthority(@NotNull ControllerRequest request) {
    User user = LoginCommand.getSessionUser(request);
    return user != null && user.getRole() == Role.ADMIN;
  }

  @Override
  protected Court createEntityFromParameters(@NotNull ControllerRequest request, long id) {
    Court court = null;
    String courtName = request.getParameter(COURT_NAME);
    if (!courtName.isEmpty()) {
      CourtInstance courtInstance = CourtInstance.valueOf(request.getParameter(COURT_INSTANCE));
      long mainCourtId = Long.parseLong(request.getParameter(MAIN_COURT_ID));
      if (mainCourtId == id || courtInstance == CourtInstance.CASSATION) {
        mainCourtId = 0;
      } else {
        Court mainCourt;
        try (CourtDao courtDao = new CourtDao()) {
          mainCourt = courtDao.readEntity(mainCourtId);
          if (mainCourt == null || mainCourt.getCourtInstance().getInstanceLevel() <= courtInstance.getInstanceLevel()) {
            mainCourtId = 0;
          }
        } catch (SQLException | DbPoolException e) {
          mainCourtId = 0;
        }
      }
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
