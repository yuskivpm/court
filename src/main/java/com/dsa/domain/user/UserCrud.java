package com.dsa.domain.user;

import com.dsa.controller.ControllerRequest;
import com.dsa.dao.DbPoolException;
import com.dsa.service.command.LoginCommand;
import com.dsa.service.crud.AbstractCrud;

import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

import static com.dsa.domain.user.UserConst.*;

public class UserCrud extends AbstractCrud<User, UserDao> {

  public static final String path = "/users/";

  private static final String ACTIVE_IS_ON = "on";

  public UserCrud() {
    super(path);
  }

  @Override
  protected boolean checkAuthority(@NotNull ControllerRequest request) {
    User user = LoginCommand.getSessionUser(request);
    return user != null && user.getRole() == Role.ADMIN;
  }

  @Override
  protected User createEntityFromParameters(@NotNull ControllerRequest request, long id) {
    User user = null;
    String name = request.getParameter(NAME);
    String login = request.getParameter(LOGIN);
    String password = request.getParameter(PASSWORD);
    if (!name.isEmpty() && !login.isEmpty() && !password.isEmpty()) {
      Role role = Role.valueOf(request.getParameter(ROLE));
      long courtId = Long.parseLong(request.getParameter(COURT_ID));
      boolean isActive = ACTIVE_IS_ON.equals(request.getParameter(IS_ACTIVE));
      user = new User(id, login, password, role, name, null, isActive);
      user.setCourtId(courtId);
    }
    return user;
  }

  @Override
  protected UserDao createEntityDao() throws SQLException, DbPoolException {
    return new UserDao();
  }

}
