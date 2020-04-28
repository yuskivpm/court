package com.dsa.service.crud;

import com.dsa.controller.ProxyRequest;
import com.dsa.dao.entity.LawsuitDao;
import com.dsa.dao.services.DbPoolException;
import com.dsa.model.Lawsuit;
import com.dsa.model.Role;
import com.dsa.model.User;
import com.dsa.model.pure.MyEntity;
import com.dsa.service.command.LoginCommand;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.Date;
import java.text.ParseException;

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

  private String getNotNull(String tryValue, String defaultValue){
    return tryValue.isEmpty() ? defaultValue : tryValue;
  }

  private long getNotNull(String tryValue, long defaultValue){
    return tryValue.isEmpty() ? defaultValue : Long.parseLong(tryValue);
  }

  private Date getNotNull(String tryValue, Date defaultValue) throws ParseException{
    return tryValue.isEmpty() ? defaultValue : MyEntity.strToDate(tryValue);
  }

  @Override
  protected Lawsuit createEntityFromParameters(@NotNull ProxyRequest request, long id){
    Lawsuit lawsuit=null;
    try{
      try(LawsuitDao lawsuitDao = new LawsuitDao()){
        lawsuit = lawsuitDao.readEntity(id);
      };
      lawsuit.setClaimText(getNotNull(request.getParameter("claimText"),lawsuit.getClaimText()));
      lawsuit.setDefendantText(getNotNull(request.getParameter("defendantText"),lawsuit.getDefendantText()));
      lawsuit.setVerdictText(getNotNull(request.getParameter("verdictText"),lawsuit.getVerdictText()));
      lawsuit.setJudgeId(getNotNull(request.getParameter("judgeId"),lawsuit.getJudgeId()));
      lawsuit.setStartDate(getNotNull(request.getParameter("startDate"),lawsuit.getStartDate()));
      lawsuit.setVerdictDate(getNotNull(request.getParameter("verdictDate"),lawsuit.getVerdictDate()));
      lawsuit.setExecutionDate(getNotNull(request.getParameter("executionDate"),lawsuit.getExecutionDate()));
    }catch(Exception e){}
    return lawsuit;
  }

  @Override
  protected LawsuitDao createEntityDao() throws SQLException, DbPoolException {
    return new LawsuitDao();
  }

}