package com.dsa.domain.lawsuit;

import com.dsa.domain.IEntity;
import com.dsa.domain.court.Court;
import com.dsa.domain.user.User;

import java.util.Date;

public class Lawsuit extends LawsuitEntity {

  private User suitor;
  private User defendant;
  private Court court;
  private User judge;
  private Lawsuit appealedLawsuit;

  public Lawsuit() {
    super();
  }

  public Lawsuit(
      long id,
      Date sueDate,
      Court court,
      User suitor,
      String claimText,
      User defendant,
      String defendantText,
      User judge,
      Date startDate,
      Date verdictDate,
      String verdictText,
      Lawsuit appealedLawsuit,
      String appealStatus,
      Date executionDate
  ) {
    super(id,
        sueDate,
        IEntity.getIdIfNotNull(court, 0),
        IEntity.getIdIfNotNull(suitor, 0),
        claimText,
        IEntity.getIdIfNotNull(defendant, 0),
        defendantText,
        IEntity.getIdIfNotNull(judge, 0),
        startDate,
        verdictDate,
        verdictText,
        IEntity.getIdIfNotNull(appealedLawsuit, 0),
        appealStatus,
        executionDate);
    this.court = court;
    this.suitor = suitor;
    this.defendant = defendant;
    this.judge = judge;
    this.appealedLawsuit = appealedLawsuit;
  }

  public User getSuitor() {
    return suitor;
  }

  public void setSuitor(User suitor) {
    this.suitor = suitor;
    if (suitor == null) {
      super.setSuitorId(0);
    }
  }

  public User getDefendant() {
    return defendant;
  }

  public void setDefendant(User defendant) {
    this.defendant = defendant;
    if (defendant == null) {
      super.setDefendantId(0);
    }
  }

  public Court getCourt() {
    return court;
  }

  public void setCourt(Court court) {
    this.court = court;
    if (court == null) {
      super.setCourtId(0);
    }
  }

  public User getJudge() {
    return judge;
  }

  public void setJudge(User judge) {
    this.judge = judge;
    if (judge == null) {
      super.setJudgeId(0);
    }
  }

  public Lawsuit getAppealedLawsuit() {
    return appealedLawsuit;
  }

  public void setAppealedLawsuit(Lawsuit appealedLawsuit) {
    this.appealedLawsuit = appealedLawsuit;
    if (appealedLawsuit == null) {
      super.setAppealedLawsuitId(0);
    }
  }

  @Override
  public long getSuitorId() {
    return IEntity.getIdIfNotNull(suitor, super.getSuitorId());
  }

  @Override
  public long getDefendantId() {
    return IEntity.getIdIfNotNull(defendant, super.getDefendantId());
  }

  @Override
  public long getCourtId() {
    return IEntity.getIdIfNotNull(court, super.getCourtId());
  }

  @Override
  public long getJudgeId() {
    return IEntity.getIdIfNotNull(judge, super.getJudgeId());
  }

  @Override
  public long getAppealedLawsuitId() {
    return IEntity.getIdIfNotNull(appealedLawsuit, super.getAppealedLawsuitId());
  }

}
