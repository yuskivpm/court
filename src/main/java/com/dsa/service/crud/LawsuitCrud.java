package com.dsa.service.crud;

import com.dsa.controller.ProxyRequest;
import com.dsa.dao.entity.LawsuitDao;
import com.dsa.dao.services.DbPoolException;
import com.dsa.model.Lawsuit;
import com.dsa.model.Role;
import com.dsa.model.User;
import com.dsa.service.command.LoginCommand;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class LawsuitCrud extends SueCrud{
  public static final String path="/lawsuits";
  private User judge;

  public LawsuitCrud(){
    super(path);
  }

  @Override
  protected boolean checkAuthority(ProxyRequest request){
    judge = LoginCommand.getSessionUser(request);
    return judge!=null && (judge.getRole() == Role.JUDGE);
  }
//
//  @Override
//  protected Lawsuit createEntityFromParameters(@NotNull ProxyRequest request, long id){
//    Lawsuit lawsuit=null;
//    try{
//      try(LawsuitDao lawsuitDao = new LawsuitDao()){
//        lawsuit = lawsuitDao.readEntity(id);
//      };
//      lawsuit.setClaimText(getNotNull(request.getParameter("claimText"),lawsuit.getClaimText()));
//      lawsuit.setDefendantText(getNotNull(request.getParameter("defendantText"),lawsuit.getDefendantText()));
//      lawsuit.setVerdictText(getNotNull(request.getParameter("verdictText"),lawsuit.getVerdictText()));
//      lawsuit.setJudgeId(getNotNull(request.getParameter("judgeId"),lawsuit.getJudgeId()));
//      lawsuit.setStartDate(getNotNull(request.getParameter("startDate"),lawsuit.getStartDate()));
//      lawsuit.setVerdictDate(getNotNull(request.getParameter("verdictDate"),lawsuit.getVerdictDate()));
//      lawsuit.setExecutionDate(getNotNull(request.getParameter("executionDate"),lawsuit.getExecutionDate()));
//    }catch(Exception e){}
//    return lawsuit;
//  }

}