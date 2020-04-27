package com.dsa.service.crud;

import com.dsa.controller.ProxyRequest;
import com.dsa.dao.entity.LawsuitDao;
import com.dsa.dao.entity.SueDao;
import com.dsa.dao.services.DbPoolException;
import com.dsa.model.Lawsuit;
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

public class LawsuitCrud extends AbstractCrud<Lawsuit, LawsuitDao>{
  public static final String path="/lawsuits";
  private User judge;

  public LawsuitCrud(){
    super(path);
  }

  public static LawsuitCrud newInstance(){
    return new LawsuitCrud();
  }

  @Override
  protected boolean checkAuthority(ProxyRequest request){
    judge = LoginCommand.getSessionUser(request);
    return judge!=null && (judge.getRole() == Role.JUDGE);
  }

  @Override
  protected Lawsuit createEntityFromParameters(@NotNull ProxyRequest request, long id){
    Lawsuit lawsuit=null;
    try{
      String sueId= request.getParameter("sueId"); // if present - create lawsuit from
      if(!sueId.isEmpty()){
        try(SueDao sueDao=new SueDao()){
          Sue sue = sueDao.loadAllSubEntities(sueDao.readEntity(Long.parseLong(sueId)));
          lawsuit = new Lawsuit(
              id,
              sue.getSuitor(),
              sue.getDefendant(),
              null,
              judge,
              sue.getSueDate(),
              new Date(),
              sue.getClaimText(),
              "",
              null,
              null,
              "",
              null,
              "",
              null
          );
        }
      }else{
//  todo:      create/update lawsuit not from sue
      }

//      long suitorId= Long.parseLong(request.getParameter("suitorId"));
//      long defendantId= Long.parseLong(request.getParameter("defendantId"));
//      long courtId= Long.parseLong(request.getParameter("courtId"));
//      SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
//      Date sueDate = null;
//      try {
//        sueDate = formatter.parse(request.getParameter("sueDate"));
//      }catch(ParseException e){
//        sueDate=new Date();
//      }
//      String claimText= request.getParameter("claimText");
//      if(claimText!=null && !claimText.isEmpty()&& courtId>0 && defendantId>0 && suitorId>0 && defendantId != suitorId){
//        sue=new Sue(id, null, null, null, sueDate, claimText);
//        sue.setSuitorId(suitorId);
//        sue.setDefendantId(defendantId);
//        sue.setCourtId(courtId);
//      }
    }catch(Exception e){}
    return lawsuit;
  }

  @Override
  protected LawsuitDao createEntityDao() throws SQLException, DbPoolException {
    return new LawsuitDao();
  }

}