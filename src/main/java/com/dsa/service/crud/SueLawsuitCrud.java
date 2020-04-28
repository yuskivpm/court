package com.dsa.service.crud;

import com.dsa.controller.ProxyRequest;
import com.dsa.dao.entity.SueLawsuitDao;
import com.dsa.dao.services.DbPoolException;
import com.dsa.model.SueLawsuit;
import com.dsa.model.Role;
import com.dsa.model.User;
import com.dsa.model.pure.MyEntity;
import com.dsa.service.command.LoginCommand;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.Date;
import java.text.ParseException;

public class SueLawsuitCrud extends AbstractCrud<SueLawsuit, SueLawsuitDao>{
  public static final String path="/lawsuits";
  private User judge;

  public SueLawsuitCrud(){
    super(path);
  }

  public static SueLawsuitCrud newInstance(){
    return new SueLawsuitCrud();
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
  protected SueLawsuit createEntityFromParameters(@NotNull ProxyRequest request, long id){
    SueLawsuit lawsuit=null;
    try{
      try(SueLawsuitDao sueLawsuitDao = new SueLawsuitDao()){
        lawsuit = sueLawsuitDao.readEntity(id);
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
  protected SueLawsuitDao createEntityDao() throws SQLException, DbPoolException {
    return new SueLawsuitDao();
  }

}