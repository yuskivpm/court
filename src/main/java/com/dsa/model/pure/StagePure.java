package com.dsa.model.pure;

import java.util.Date;
import java.util.Objects;

public class StagePure extends MyEntity {
  private long lawsuitId;
  private long judgeId;
  private Date sueDate;
  private Date startDate;
  private String ClaimText;
  private String DefendantText;
  private long verdictId;
  private Date suitorAppealDate;
  private String suitorAppealText;
  private Date defendantAppealDate;
  private String defendantAppealText;

  static{
    MyEntity.entityName="Stage";
  }

  public StagePure(
      long id,
      long lawsuitId,
      long judgeId,
      Date sueDate,
      Date startDate,
      String claimText,
      String defendantText,
      long verdictId,
      Date suitorAppealDate,
      String suitorAppealText,
      Date defendantAppealDate,
      String defendantAppealText
  ) {
    super(id);
    this.lawsuitId = lawsuitId;
    this.judgeId = judgeId;
    this.sueDate = sueDate;
    this.startDate = startDate;
    ClaimText = claimText;
    DefendantText = defendantText;
    this.verdictId = verdictId;
    this.suitorAppealDate = suitorAppealDate;
    this.suitorAppealText = suitorAppealText;
    this.defendantAppealDate = defendantAppealDate;
    this.defendantAppealText = defendantAppealText;
  }

  public long getLawsuitId() {
    return lawsuitId;
  }

  public void setLawsuitId(long lawsuitId) {
    this.lawsuitId = lawsuitId;
  }

  public long getJudgeId() {
    return judgeId;
  }

  public void setJudgeId(long judgeId) {
    this.judgeId = judgeId;
  }

  public Date getSueDate() {
    return sueDate;
  }

  public void setSueDate(Date sueDate) {
    this.sueDate = sueDate;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public String getClaimText() {
    return ClaimText;
  }

  public void setClaimText(String claimText) {
    ClaimText = claimText;
  }

  public String getDefendantText() {
    return DefendantText;
  }

  public void setDefendantText(String defendantText) {
    DefendantText = defendantText;
  }

  public long getVerdictId() {
    return verdictId;
  }

  public void setVerdictId(long verdictId) {
    this.verdictId = verdictId;
  }

  public Date getSuitorAppealDate() {
    return suitorAppealDate;
  }

  public void setSuitorAppealDate(Date suitorAppealDate) {
    this.suitorAppealDate = suitorAppealDate;
  }

  public String getSuitorAppealText() {
    return suitorAppealText;
  }

  public void setSuitorAppealText(String suitorAppealText) {
    this.suitorAppealText = suitorAppealText;
  }

  public Date getDefendantAppealDate() {
    return defendantAppealDate;
  }

  public void setDefendantAppealDate(Date defendantAppealDate) {
    this.defendantAppealDate = defendantAppealDate;
  }

  public String getDefendantAppealText() {
    return defendantAppealText;
  }

  public void setDefendantAppealText(String defendantAppealText) {
    this.defendantAppealText = defendantAppealText;
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        getId(),
        getLawsuitId(),
        getJudgeId(),
        getSueDate(),
        getStartDate(),
        getClaimText(),
        getDefendantText(),
        getVerdictId(),
        getSuitorAppealDate(),
        getSuitorAppealText(),
        getDefendantAppealDate(),
        getDefendantAppealText()
    );
  }

  @Override
  public String toString(){
    return super.toString()+", "
        +"lawsuitId="+lawsuitId+", "
        +"judgeId="+judgeId+", "
        +"sueDate="+sueDate+", "
        +"startDate="+startDate+", "
        +"ClaimText="+ClaimText+", "
        +"DefendantText="+DefendantText+", "
        +"verdictId="+verdictId+", "
        +"suitorAppealDate="+suitorAppealDate+", "
        +"suitorAppealText="+suitorAppealText+", "
        +"defendantAppealDate="+defendantAppealDate+", "
        +"defendantAppealText="+defendantAppealText
        +"]";
  }

}
