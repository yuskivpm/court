package com.dsa.service.crud;

import com.dsa.controller.ProxyRequest;
import com.dsa.dao.entity.AbstractEntityDao;
import com.dsa.dao.services.DbPoolException;
import com.dsa.model.pure.MyEntity;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractCrud<E extends MyEntity, D extends AbstractEntityDao> implements Icrud {
  private static final Logger log = Logger.getLogger(AbstractCrud.class);
  protected String path="";

  public AbstractCrud(String path){
    this.path=path;
  }

  public CrudResult execute(ProxyRequest request, HttpServletResponse response){
    if(checkAuthority(request)){
      CrudEnum ce=CrudExecutor.getCrudOperation(request,getPath());
      switch (ce) {
        case CREATE:
        case UPDATE:
          createOrUpdateEntity(request, response,ce==CrudEnum.CREATE);
          return CrudResult.EXECUTED;
        case READ:
        case READ_ALL:
          readEntity(request, response,ce==CrudEnum.READ_ALL);
          return CrudResult.EXECUTED;
        case DELETE:
          deleteEntity(request, response);
          return CrudResult.EXECUTED;
        case PREPARE_UPDATE_FORM:
          return prepareEditForm(request, response);
        default: //skip
      }
    }
    return CrudResult.FAILED;
  }

  private void createOrUpdateEntity(ProxyRequest request, HttpServletResponse response, boolean create) {
    PrintWriter out =null;
    try{
      out = response.getWriter();
      long id=0;
      if(!create){
        id=Long.parseLong(request.getParameter("id"));
      }
      E entity=createEntityFromParameters(request,id);
      if(entity!=null){
        boolean result=false;
        try(D EntityDao=createEntityDao()){
          result=create?EntityDao.createEntity(entity):EntityDao.updateEntity(entity);
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
        out.print("{\"error\":\"Exception in createOrUpdateEntity(): "+e+"\"}");
      }
    }
  }

  protected static String getNotNull(String tryValue, String defaultValue){
    return tryValue.isEmpty() ? defaultValue : tryValue;
  }

  protected static long getNotNull(String tryValue, long defaultValue){
    return tryValue.isEmpty() ? defaultValue : Long.parseLong(tryValue);
  }

  protected static Date getNotNull(String tryValue, Date defaultValue) throws ParseException {
    return tryValue.isEmpty() ? defaultValue : MyEntity.strToDate(tryValue);
  }

  private void deleteEntity(ProxyRequest request, HttpServletResponse response) {
    PrintWriter out =null;
    try{
      out = response.getWriter();
      String id = request.getParameter("id");
      boolean result=false;
      try(D entityDao=createEntityDao()){
        result=entityDao.deleteEntity(id);
      }
      if (result){
        out.print("{\"status\":\"ok\"}");
      }else{
        out.print("{\"error\":\"db error\"}");
      }
    }catch(Exception e){
      if(out != null){
        out.print("{\"error\":\"Exception in deleteEntity(): "+e+"\"}");
      }
    }
  }

  private void readEntity(ProxyRequest request, HttpServletResponse response, boolean readAll){
    PrintWriter out =null;
    try{
      out = response.getWriter();
      long id=0;
      if(!readAll){
        id=Long.parseLong(request.getParameter("id"));
      }
      List<E> entities=null;
      String responseText="";
      try(D entityDao=createEntityDao()){
        if(readAll){
          entities=entityDao.readAll();
          responseText="["+
              entities.stream()
                  .map(E::toString)
                  .collect(Collectors.joining(","))+
              "]";
        }else{
          responseText=entityDao.readEntity(id).toString();
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
        out.print("{\"error\":\"Exception in readEntity(): "+e+"\"}");
      }
    }
  }

  private CrudResult prepareEditForm(@NotNull ProxyRequest request, HttpServletResponse response) {
    String id = request.getParameter("id");
    try(D entityDao=createEntityDao()){
      MyEntity entity= entityDao.readEntity("ID",id);
      if (entity!=null){
        request.setAttribute("editEntity",entity);
        return CrudResult.REDIRECT;
      };
    }catch(Exception e){
      log.error("Fail get User in prepareEditForm for Entity.id("+id+"): "+e);
    }
    return CrudResult.FAILED;
  }

  public String getPath(){
    return path;
  }

  protected boolean checkAuthority(ProxyRequest request){
    return true;
  }

  protected abstract E createEntityFromParameters(ProxyRequest request, long id);

  protected abstract D createEntityDao() throws SQLException, DbPoolException;
}
