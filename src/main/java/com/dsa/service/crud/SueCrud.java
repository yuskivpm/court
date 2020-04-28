package com.dsa.service.crud;

import com.dsa.controller.ProxyRequest;
import com.dsa.dao.entity.LawsuitDao;
import com.dsa.dao.services.DbPoolException;
import com.dsa.model.Role;
import com.dsa.model.Lawsuit;
import com.dsa.model.User;
import com.dsa.model.pure.MyEntity;
import com.dsa.service.command.LoginCommand;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

public class SueCrud extends AbstractCrud<Lawsuit, LawsuitDao>{
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
  protected Lawsuit createEntityFromParameters(@NotNull ProxyRequest request, long id){
    Lawsuit sue=null;
    try{
      long suitorId= Long.parseLong(request.getParameter("suitorId"));
      long defendantId= Long.parseLong(request.getParameter("defendantId"));
      long courtId= Long.parseLong(request.getParameter("courtId"));
      Date sueDate = null;
      try {
        sueDate = MyEntity.strToDate(request.getParameter("sueDate"));
      }catch(ParseException e){
        sueDate=new Date();
      }
      String claimText= request.getParameter("claimText");
      if(claimText!=null && !claimText.isEmpty()&& courtId>0 && defendantId>0 && suitorId>0 && defendantId != suitorId){
        sue=new Lawsuit(id, sueDate, null, null, claimText, null, "", null, null,null,"",null);
        sue.setSuitorId(suitorId);
        sue.setDefendantId(defendantId);
        sue.setCourtId(courtId);
      }
    }catch(Exception e){}
    return sue;
  }

  @Override
  protected LawsuitDao createEntityDao() throws SQLException, DbPoolException {
    return new LawsuitDao();
  }

}