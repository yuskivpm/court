package com.dsa.service.crud;

import com.dsa.controller.ProxyRequest;
import com.dsa.dao.entity.UserDao;
import com.dsa.dao.services.DbPoolException;
import com.dsa.model.Role;
import com.dsa.model.User;
import com.dsa.service.command.LoginCommand;

import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class UserCrud extends AbstractCrud<User, UserDao>{
  public static final String path="/users";

  public UserCrud(){
    super(path);
  }

  @Override
  protected boolean checkAuthority(ProxyRequest request){
    User user= LoginCommand.getSessionUser(request);
    return user!=null && user.getRole()== Role.ADMIN;
  }

  @Override
  protected User createEntityFromParameters(@NotNull ProxyRequest request, long id){
    User user=null;
    String name= request.getParameter("name");
    String login= request.getParameter("login");
    String password= request.getParameter("password");
    if(name!=null && !name.isEmpty()&& login!=null && !login.isEmpty()&& password!=null && !password.isEmpty()){
      Role role= Role.valueOf(request.getParameter("role"));
      long courtId= Long.parseLong(request.getParameter("courtId"));
      boolean isActive= "on".equals(request.getParameter("isActive"));
      user=new User(id, login, password, role, name, null, isActive);
      user.setCourtId(courtId);
    }
    return user;
  }

  @Override
  protected UserDao createEntityDao() throws SQLException, DbPoolException {
    return new UserDao();
  }

}
