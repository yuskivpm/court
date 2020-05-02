package com.dsa.model;

import com.dsa.model.pure.LawsuitPure;

import java.util.Date;

public class Lawsuit extends LawsuitPure {

  private User suitor;
  private User defendant;
  private Court court;
  private User judge;

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
        appealStatus,
        executionDate);
    this.court = court;
    this.suitor = suitor;
    this.defendant = defendant;
    this.judge = judge;
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

}
