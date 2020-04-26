package com.dsa.service.crud;

import com.dsa.controller.ProxyRequest;
import com.dsa.dao.entity.SueDao;
import com.dsa.dao.services.DbPoolException;
import com.dsa.model.Role;
import com.dsa.model.Sue;
import com.dsa.model.User;
import com.dsa.service.command.LoginCommand;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SueCrud extends AbstractCrud<Sue, SueDao>{
  public static final String path="/sues";

  public SueCrud(){
    super(path);
  }

  public static SueCrud newInstance(){
    return new SueCrud();
  }

  @Override
  protected boolean checkAuthority(ProxyRequest request){
    User user= LoginCommand.getSessionUser(request);
    return user!=null && (user.getRole()== Role.ATTORNEY || user.getRole()== Role.JUDGE);
  }

  @Override
  protected Sue createEntityFromParameters(@NotNull ProxyRequest request, long id){
    Sue sue=null;
    try{
      long suitorId= Long.parseLong(request.getParameter("suitorId"));
      long defendantId= Long.parseLong(request.getParameter("defendantId"));
      long courtId= Long.parseLong(request.getParameter("courtId"));
      SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
      Date sueDate = null;
      try {
        sueDate = formatter.parse(request.getParameter("sueDate"));
      }catch(ParseException e){
        sueDate=new Date();
      }
      String claimText= request.getParameter("claimText");
      if(claimText!=null && !claimText.isEmpty()&& courtId>0 && defendantId>0 && suitorId>0 && defendantId != suitorId){
        sue=new Sue(id, null, null, null, sueDate, claimText);
        sue.setSuitorId(suitorId);
        sue.setDefendantId(defendantId);
        sue.setCourtId(courtId);
      }
    }catch(Exception e){}
    return sue;
  }

  @Override
  protected SueDao createEntityDao() throws SQLException, DbPoolException {
    return new SueDao();
  }

}