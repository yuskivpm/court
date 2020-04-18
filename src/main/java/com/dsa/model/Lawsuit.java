package com.dsa.model;

import com.dsa.model.pure.LawsuitPure;

import java.util.Date;

public class Lawsuit extends LawsuitPure {
  private User suitor;
  private User defendant;
  private Court jurisdictionCourt;
  private User judge;
  private Verdict verdict;

  public Lawsuit(){
    super();
  }

  public Lawsuit(
      long id,
      User suitor,
      User defendant,
      Court jurisdictionCourt,
      User judge,
      Date sueDate,
      Date startDate,
      String claimText,
      String defendantText,
      Verdict verdict,
      Date suitorAppealDate,
      String suitorAppealText,
      Date defendantAppealDate,
      String defendantAppealText,
      Date executionDate
  ) {
    super(id,
        getIdIfNotNull(suitor,0),
        getIdIfNotNull(defendant,0),
        getIdIfNotNull(jurisdictionCourt,0),
        getIdIfNotNull(judge,0),
        sueDate,
        startDate,
        claimText,
        defendantText,
        getIdIfNotNull(verdict,0),
        suitorAppealDate,
        suitorAppealText,
        defendantAppealDate,
        defendantAppealText,
        executionDate);
    this.suitor=suitor;
    this.defendant=defendant;
    this.jurisdictionCourt=jurisdictionCourt;
    this.judge=judge;
    this.verdict=verdict;
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

  public Court getJurisdictionCourt() {
    return jurisdictionCourt;
  }

  public void setJurisdictionCourt(Court jurisdictionCourt) {
    this.jurisdictionCourt = jurisdictionCourt;
  }

  public User getJudge() {
    return judge;
  }

  public void setJudge(User judge) {
    this.judge = judge;
  }

  public Verdict getVerdict() {
    return verdict;
  }

  public void setVerdict(Verdict verdict) {
    this.verdict = verdict;
  }

  @Override
  public long getSuitorId(){
    return getIdIfNotNull(suitor,super.getSuitorId());
  }

  @Override
  public long getDefendantId(){
    return getIdIfNotNull(defendant,super.getDefendantId());
  }

  @Override
  public long getJurisdictionCourtId(){
    return getIdIfNotNull(jurisdictionCourt,super.getJurisdictionCourtId());
  }

  @Override
  public long getJudgeId(){
    return getIdIfNotNull(judge,super.getJudgeId());
  }

  @Override
  public long getVerdictId(){
    return getIdIfNotNull(verdict,super.getVerdictId());
  }

}
