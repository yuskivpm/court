package com.dsa.model.pure;

import java.util.Date;
import java.util.Objects;

public class LawsuitPure extends MyEntity {
  private long suitorId;
  private long defendantId;
  private long jurisdictionCourtId;
  // current stage
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
  private Date executionDate;

  public LawsuitPure(){
    entityName="Lawsuit";
  }

  public LawsuitPure(
      long id,
      long suitorId,
      long defendantId,
      long jurisdictionCourtId,
      long judgeId,
      Date sueDate,
      Date startDate,
      String claimText,
      String defendantText,
      long verdictId,
      Date suitorAppealDate,
      String suitorAppealText,
      Date defendantAppealDate,
      String defendantAppealText,
      Date executionDate
  ) {
    super(id);
    entityName="Lawsuit";
    this.suitorId = suitorId;
    this.defendantId = defendantId;
    this.jurisdictionCourtId = jurisdictionCourtId;
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
    this.executionDate = executionDate;
  }

  public long getSuitorId() {
    return suitorId;
  }

  public void setSuitorId(long suitorId) {
    this.suitorId = suitorId;
  }

  public long getDefendantId() {
    return defendantId;
  }

  public void setDefendantId(long defendantId) {
    this.defendantId = defendantId;
  }

  public long getJurisdictionCourtId() {
    return jurisdictionCourtId;
  }

  public void setJurisdictionCourtId(long jurisdictionCourtId) {
    this.jurisdictionCourtId = jurisdictionCourtId;
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

  public Date getExecutionDate() {
    return executionDate;
  }

  public void setExecutionDate(Date executionDate) {
    this.executionDate = executionDate;
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        getId(),
        getSuitorId(),
        getDefendantId(),
        getJurisdictionCourtId(),
        getJudgeId(),
        getSueDate(),
        getStartDate(),
        getClaimText(),
        getDefendantText(),
        getVerdictId(),
        getSuitorAppealDate(),
        getSuitorAppealText(),
        getDefendantAppealDate(),
        getDefendantAppealText(),
        getExecutionDate()
    );
  }

  @Override
  public String toString(){
    return "{"+super.toString()+", "
        +"\"suitorId\":\""+suitorId+"\", "
        +"\"defendantId\":\""+defendantId+"\", "
        +"\"jurisdictionCourtId\":\""+jurisdictionCourtId+"\", "
        +"\"judgeId\":\""+judgeId+"\", "
        +"\"sueDate\":\""+sueDate+"\", "
        +"\"startDate\":\""+startDate+"\", "
        +"\"claimText\":\""+ClaimText+"\", "
        +"\"defendantText\":\""+DefendantText+"\", "
        +"\"verdictId\":\""+verdictId+"\", "
        +"\"suitorAppealDate\":\""+suitorAppealDate+"\", "
        +"\"suitorAppealText\":\""+suitorAppealText+"\", "
        +"\"defendantAppealDate\":\""+defendantAppealDate+"\", "
        +"\"defendantAppealText\":\""+defendantAppealText+"\", "
        +"\"executionDate\":\""+executionDate+"\""
        +"}";
  }
}
