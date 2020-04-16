package com.dsa.service.crud;

import com.dsa.controller.ProxyRequest;
import com.dsa.dao.entity.UserDao;
import com.dsa.model.Role;
import com.dsa.model.User;
import com.dsa.service.command.RedirectCommand;
import org.apache.log4j.Logger;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

public class UserCrud {
  private static final Logger log = Logger.getLogger(UserCrud.class);
  public static final String path="/users";

  public static CrudResult execute(ProxyRequest request, HttpServletResponse response){
//    todo: check - only ADMIN can execute it
    CrudEnum ce=CrudExecutor.getCrudOperation(request,path);
      switch (ce) {
        case CREATE:
        case UPDATE:
          createOrUpdateUser(request, response,ce==CrudEnum.CREATE);
          return CrudResult.EXECUTED;
        case DELETE:
          deleteUser(request, response);
          return CrudResult.EXECUTED;
        case PREPARE_UPDATE_FORM:
          return prepareEditForm(request, response);
        case UNKNOWN:
        case READ:
        case READ_ALL:
        default: //READ_ALL: read all and list it on main page
//todo:          redirect to mainAdmin
//          listBook(request.getRequest(), response);
//          break;
      }
    return CrudResult.FAILED;
  }

  private static void createOrUpdateUser(ProxyRequest request, HttpServletResponse response, boolean create) {
    PrintWriter out =null;
    try{
      out = response.getWriter();
      long id=0;
      if(!create){
        id=Long.parseLong(request.getParameter("id"));
      }
      String name= request.getParameter("name");
      String login= request.getParameter("login");
      String password= request.getParameter("password");
      Role role= Role.valueOf(request.getParameter("role"));
      long courtId= Long.parseLong(request.getParameter("courtId"));
      boolean isActive= "on".equals(request.getParameter("isActive"));
      if(name!=null && !name.isEmpty()&& login!=null && !login.isEmpty()&& password!=null && !password.isEmpty()){
        boolean result=false;
        User user=new User(id, login, password, role, name, null, isActive);
        user.setCourtId(courtId);
        try(UserDao userDao=new UserDao()){
          result=create?userDao.createEntity(user):userDao.updateEntity(user);
        }
        if (result){
          out.print("{\"status\":\"ok\"}");
        }else{
          out.print("{\"error\":\"db error\"}");
        }
      }else{
        out.print("{\"error\":\"All fields are required\"}");
      }
    }catch(Exception e){
      if(out != null){
        out.print("{\"error\":\"Exception in createOrUpdateUser(): "+e+"\"}");
      }
    }
  }

  private static void deleteUser(ProxyRequest request, HttpServletResponse response) {
    PrintWriter out =null;
    try{
      out = response.getWriter();
      String id = request.getParameter("id");
      boolean result=false;
      try(UserDao userDao=new UserDao()){
        result=userDao.deleteEntity(id);
      }
      if (result){
        out.print("{\"status\":\"ok\"}");
      }else{
        out.print("{\"error\":\"db error\"}");
      }
    }catch(Exception e){
      if(out != null){
        out.print("{\"error\":\"Exception in deleteUser(): "+e+"\"}");
      }
    }
  }

  private static CrudResult prepareEditForm(ProxyRequest request, HttpServletResponse response) {
    String id = request.getParameter("id");
    try(UserDao userDao=new UserDao()){
      User user= userDao.readEntity("ID",id);
      if (user!=null){
        request.setAttribute("editUser",user);
        return CrudResult.REDIRECT;
      };
    }catch(Exception e){
      log.error("Fail get User in prepareEditForm for User.id("+id+"): "+e);
    }
    return CrudResult.FAILED;
  }

}
