package com.dsa.domain.lawsuit;

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
        getIdIfNotNull(court, 0),
        getIdIfNotNull(suitor, 0),
        claimText,
        getIdIfNotNull(defendant, 0),
        defendantText,
        getIdIfNotNull(judge, 0),
        startDate,
        verdictDate,
        verdictText,
        getIdIfNotNull(appealedLawsuit, 0),
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
  }

  public User getDefendant() {
    return defendant;
  }

  public void setDefendant(User defendant) {
    this.defendant = defendant;
  }

  public Court getCourt() {
    return court;
  }

  public void setCourt(Court court) {
    this.court = court;
  }

  public User getJudge() {
    return judge;
  }

  public void setJudge(User judge) {
    this.judge = judge;
  }

  public Lawsuit getAppealedLawsuit() {
    return appealedLawsuit;
  }

  public void setAppealedLawsuit(Lawsuit appealedLawsuit) {
    this.appealedLawsuit = appealedLawsuit;
  }

  @Override
  public long getSuitorId() {
    return getIdIfNotNull(suitor, super.getSuitorId());
  }

  @Override
  public long getDefendantId() {
    return getIdIfNotNull(defendant, super.getDefendantId());
  }

  @Override
  public long getCourtId() {
    return getIdIfNotNull(court, super.getCourtId());
  }

  @Override
  public long getJudgeId() {
    return getIdIfNotNull(judge, super.getJudgeId());
  }

  @Override
  public long getAppealedLawsuitId() {
    return getIdIfNotNull(appealedLawsuit, super.getAppealedLawsuitId());
  }

}
