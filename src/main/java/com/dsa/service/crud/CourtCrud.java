package com.dsa.service.crud;

import com.dsa.controller.ProxyRequest;

import com.dsa.dao.entity.CourtDao;
import com.dsa.model.Court;
import com.dsa.model.CourtInstance;
import com.dsa.model.Role;
import com.dsa.model.User;
import com.dsa.service.command.LoginCommand;
import org.apache.log4j.Logger;

import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

public class CourtCrud {
  private static final Logger log = Logger.getLogger(CourtCrud.class);
  public static final String path="/courts";

  public static CrudResult execute(ProxyRequest request, HttpServletResponse response){
    User user= LoginCommand.getSessionUser(request);
    if(user!=null && user.getRole()== Role.ADMIN){// only ADMIN can execute it
      CrudEnum ce=CrudExecutor.getCrudOperation(request,path);
      switch (ce) {
        case CREATE:
        case UPDATE:
          createOrUpdateCourt(request, response,ce==CrudEnum.CREATE);
          return CrudResult.EXECUTED;
        case DELETE:
          deleteCourt(request, response);
          return CrudResult.EXECUTED;
        case PREPARE_UPDATE_FORM:
          return prepareEditForm(request, response);
        case READ:
        case READ_ALL:
          readCourt(request, response,ce==CrudEnum.READ_ALL);
          return CrudResult.EXECUTED;
        default: //skip
      }
    }
    return CrudResult.FAILED;
  }

  private static void createOrUpdateCourt(ProxyRequest request, HttpServletResponse response, boolean create) {
    PrintWriter out =null;
    try{
      out = response.getWriter();
      long id=0;
      if(!create){
        id=Long.parseLong(request.getParameter("id"));
      }
      String courtName= request.getParameter("courtName");
      CourtInstance courtInstance= CourtInstance.valueOf(request.getParameter("courtInstance"));
      long mainCourtId= Long.parseLong(request.getParameter("mainCourtId"));
      if(courtName!=null && !courtName.isEmpty()){
        boolean result=false;
        Court court=new Court(id,courtName, courtInstance, null);
        court.setMainCourtId(mainCourtId);
        try(CourtDao courtDao=new CourtDao()){
          result=create?courtDao.createEntity(court):courtDao.updateEntity(court);
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
        out.print("{\"error\":\"Exception in createOrUpdateCourt(): "+e+"\"}");
      }
    }
  }

  private static void deleteCourt(ProxyRequest request, HttpServletResponse response) {
    PrintWriter out =null;
    try{
      out = response.getWriter();
      String id = request.getParameter("id");
      boolean result=false;
      try(CourtDao courtDao=new CourtDao()){
        result=courtDao.deleteEntity(id);
      }
      if (result){
        out.print("{\"status\":\"ok\"}");
      }else{
        out.print("{\"error\":\"db error\"}");
      }
    }catch(Exception e){
      if(out != null){
        out.print("{\"error\":\"Exception in deleteCourt(): "+e+"\"}");
      }
    }
  }

  private static CrudResult prepareEditForm(ProxyRequest request, HttpServletResponse response) {
    String id = request.getParameter("id");
    try(CourtDao courtDao=new CourtDao()){
      Court court= courtDao.readEntity("ID",id);
      if (court!=null){
        request.setAttribute("editCourt",court);
        return CrudResult.REDIRECT;
      };
    }catch(Exception e){
      log.error("Fail get User in prepareEditForm for Court.id("+id+"): "+e);
    }
    return CrudResult.FAILED;
  }

  private static void readCourt(ProxyRequest request, HttpServletResponse response, boolean readAll){
    PrintWriter out =null;
    try{
      out = response.getWriter();
      long id=0;
      if(!readAll){
        id=Long.parseLong(request.getParameter("id"));
      }
      Court court=null;
      List<Court> courts=null;
      String responseText="";
      try(CourtDao courtDao=new CourtDao()){
        if(readAll){
          courts=courtDao.readAll();
          responseText="["+
              courts.stream()
                  .map(Court::toString)
                  .collect(Collectors.joining(","))+
              "]";
        }else{
          court=courtDao.readEntity(id);
          responseText=court.toString();
        }
      }
      if (!responseText.isEmpty()){
        out.print("{" +
            "\"status\":\"ok\"," +
            "\"data\":"+responseText+"}"
        );
      }else{
        out.print("{\"error\":\"db error\"}");
      }
    }catch(Exception e){
      if(out != null){
        out.print("{\"error\":\"Exception in readUser(): "+e+"\"}");
      }
    }
  }
}
