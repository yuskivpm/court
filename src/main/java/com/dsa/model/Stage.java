package com.dsa.model;

import com.dsa.model.pure.StagePure;

import java.util.Date;

public class Stage extends StagePure {
  private Lawsuit lawsuit;
  private User judge;
  private Verdict verdict;

  public Stage (){
    super();
  }

  public Stage(
      long id,
      Lawsuit lawsuit,
      User judge,
      Date sueDate,
      Date startDate,
      String claimText,
      String defendantText,
      Verdict verdict,
      Date suitorAppealDate,
      String suitorAppealText,
      Date defendantAppealDate,
      String defendantAppealText
  ) {
    super(id,
        getIdIfNotNull(lawsuit,0),
        getIdIfNotNull(judge,0) ,
        sueDate,
        startDate,
        claimText,
        defendantText,
        getIdIfNotNull(verdict,0) ,
        suitorAppealDate,
        suitorAppealText,
        defendantAppealDate,
        defendantAppealText);
    this.lawsuit=lawsuit;
    this.judge=judge;
    this.verdict=verdict;
  }

  public Lawsuit getLawsuit() {
    return lawsuit;
  }

  public void setLawsuit(Lawsuit lawsuit) {
    this.lawsuit = lawsuit;
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
  public long getLawsuitId(){
    return getIdIfNotNull(lawsuit,super.getLawsuitId());
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
