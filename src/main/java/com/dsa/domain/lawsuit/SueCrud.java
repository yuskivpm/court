package com.dsa.domain.lawsuit;

import com.dsa.controller.ControllerRequest;
import com.dsa.controller.ControllerResponse;
import com.dsa.dao.DbPoolException;
import com.dsa.domain.user.Role;
import com.dsa.domain.user.User;
import com.dsa.domain.MyEntity;
import com.dsa.service.command.LoginCommand;
import com.dsa.service.crud.AbstractCrud;

import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

public class SueCrud extends AbstractCrud<Lawsuit, LawsuitDao> {

  public static final String path = "/sues/";

  public SueCrud() {
    super(path);
  }

  protected SueCrud(String path) {
    super(path);
  }

  @Override
  protected boolean checkAuthority(ControllerRequest request) {
    User user = LoginCommand.getSessionUser(request);
    return user != null && (user.getRole() == Role.ATTORNEY);
  }

  @Override
  protected Lawsuit createEntityFromParameters(@NotNull ControllerRequest request, long id) {
    Lawsuit lawsuit = null;
    try {
      if (id == 0) {
        long suitorId = Long.parseLong(request.getParameter("suitorId"));
        long defendantId = Long.parseLong(request.getParameter("defendantId"));
        long courtId = Long.parseLong(request.getParameter("courtId"));
        long appealedLawsuitId = Long.parseLong(request.getParameter("appealedLawsuitId"));
        Date sueDate;
        try {
          sueDate = MyEntity.strToDate(request.getParameter("sueDate"));
        } catch (ParseException e) {
          sueDate = new Date();
        }
        String claimText = request.getParameter("claimText");
        if (claimText != null && !claimText.isEmpty() && courtId > 0 && defendantId > 0 && suitorId > 0 && defendantId != suitorId) {
          lawsuit = new Lawsuit(id, sueDate, null, null, claimText, null, "",
              null, null, null, null, null, null, null);
          lawsuit.setSuitorId(suitorId);
          lawsuit.setDefendantId(defendantId);
          lawsuit.setCourtId(courtId);
          lawsuit.setAppealedLawsuitId(appealedLawsuitId);
        }
      } else {
        try (LawsuitDao lawsuitDao = new LawsuitDao()) {
          lawsuit = lawsuitDao.readEntity(id);
        }
        lawsuit.setCourtId(getNotNull(request.getParameter("courtId"), lawsuit.getCourtId()));
        lawsuit.setClaimText(getNotNull(request.getParameter("claimText"), lawsuit.getClaimText()));
        lawsuit.setDefendantText(getNotNull(request.getParameter("defendantText"), lawsuit.getDefendantText()));
        lawsuit.setVerdictText(getNotNull(request.getParameter("verdictText"), lawsuit.getVerdictText()));
        lawsuit.setJudgeId(getNotNull(request.getParameter("judgeId"), lawsuit.getJudgeId()));
        lawsuit.setStartDate(getNotNull(request.getParameter("startDate"), lawsuit.getStartDate()));
        lawsuit.setVerdictDate(getNotNull(request.getParameter("verdictDate"), lawsuit.getVerdictDate()));
        lawsuit.setAppealedLawsuitId(getNotNull(request.getParameter("appealedLawsuitId"), lawsuit.getAppealedLawsuitId()));
        lawsuit.setAppealStatus(getNotNull(request.getParameter("appealStatus"), lawsuit.getAppealStatus()));
        lawsuit.setExecutionDate(getNotNull(request.getParameter("executionDate"), lawsuit.getExecutionDate()));
      }
    } catch (Exception e) {
    }
    return lawsuit;
  }

  @Override
  protected LawsuitDao createEntityDao() throws SQLException, DbPoolException {
    return new LawsuitDao();
  }

}