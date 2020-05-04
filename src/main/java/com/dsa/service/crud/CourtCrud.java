package com.dsa.service.crud;

import com.dsa.view.ProxyRequest;
import com.dsa.dao.entity.CourtDao;
import com.dsa.dao.services.DbPoolException;
import com.dsa.model.Court;
import com.dsa.model.CourtInstance;
import com.dsa.model.Role;
import com.dsa.model.User;
import com.dsa.service.command.LoginCommand;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class CourtCrud extends AbstractCrud<Court, CourtDao> {
  public static final String path = "/courts";

  public CourtCrud() {
    super(path);
  }

  @Override
  protected boolean checkAuthority(ProxyRequest request) {
    User user = LoginCommand.getSessionUser(request);
    return user != null && user.getRole() == Role.ADMIN;
  }

  @Override
  protected Court createEntityFromParameters(@NotNull ProxyRequest request, long id) {
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
