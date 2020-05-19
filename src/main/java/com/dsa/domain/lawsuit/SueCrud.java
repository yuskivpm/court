package com.dsa.domain.lawsuit;

import com.dsa.controller.ControllerRequest;
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

import static com.dsa.domain.lawsuit.LawsuitConst.*;

public class SueCrud extends AbstractCrud<Lawsuit, LawsuitDao> {

  public static final String path = "/sues/";

  public SueCrud() {
    super(path);
  }

  protected SueCrud(String path) {
    super(path);
  }

  @Override
  protected boolean checkAuthority(@NotNull ControllerRequest request) {
    User user = LoginCommand.getSessionUser(request);
    return user != null && (user.getRole() == Role.ATTORNEY);
  }

  @Override
  protected Lawsuit createEntityFromParameters(@NotNull ControllerRequest request, long id) {
    Lawsuit lawsuit = null;
    try {
      if (id == 0) {
        long suitorId = Long.parseLong(request.getParameter(SUITOR_ID));
        long defendantId = Long.parseLong(request.getParameter(DEFENDANT_ID));
        long courtId = Long.parseLong(request.getParameter(COURT_ID));
        long appealedLawsuitId = Long.parseLong(request.getParameter(APPEALED_LAWSUIT_ID));
        Date sueDate;
        try {
          sueDate = MyEntity.strToDate(request.getParameter(SUE_DATE));
        } catch (ParseException e) {
          sueDate = new Date();
        }
        String claimText = request.getParameter(CLAIM_TEXT);
        if (!claimText.isEmpty() && courtId > 0 && defendantId > 0 && suitorId > 0 && defendantId != suitorId) {
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
        if (lawsuit != null) {
          lawsuit.setCourtId(getNotNull(request.getParameter(COURT_ID), lawsuit.getCourtId()));
          lawsuit.setClaimText(getNotNull(request.getParameter(CLAIM_TEXT), lawsuit.getClaimText()));
          lawsuit.setDefendantText(getNotNull(request.getParameter(DEFENDANT_TEXT), lawsuit.getDefendantText()));
          lawsuit.setVerdictText(getNotNull(request.getParameter(VERDICT_TEXT), lawsuit.getVerdictText()));
          lawsuit.setJudgeId(getNotNull(request.getParameter(JUDGE_ID), lawsuit.getJudgeId()));
          lawsuit.setStartDate(getNotNull(request.getParameter(START_DATE), lawsuit.getStartDate()));
          lawsuit.setVerdictDate(getNotNull(request.getParameter(VERDICT_DATE), lawsuit.getVerdictDate()));
          lawsuit.setAppealedLawsuitId(getNotNull(request.getParameter(APPEALED_LAWSUIT_ID), lawsuit.getAppealedLawsuitId()));
          lawsuit.setAppealStatus(getNotNull(request.getParameter(APPEALED_STATUS), lawsuit.getAppealStatus()));
          lawsuit.setExecutionDate(getNotNull(request.getParameter(EXECUTION_DATE), lawsuit.getExecutionDate()));
        }
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