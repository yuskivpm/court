package com.dsa.domain.lawsuit;

import com.dsa.domain.MyEntity;

import java.util.Date;
import java.util.Objects;

public class LawsuitEntity extends MyEntity {

  private static final String ENTITY_NAME = "Lawsuit";

  private Date sueDate;
  private long courtId;
  private long suitorId;
  private String claimText;
  private long defendantId;
  private String defendantText;
  private long judgeId;
  private Date startDate;
  private Date verdictDate;
  private String verdictText;
  private long appealedLawsuitId;
  private String appealStatus;
  private Date executionDate;

  public LawsuitEntity() {
    entityName = ENTITY_NAME;
  }

  public LawsuitEntity(
      long id,
      Date sueDate,
      long courtId,
      long suitorId,
      String claimText,
      long defendantId,
      String defendantText,
      long judgeId,
      Date startDate,
      Date verdictDate,
      String verdictText,
      long appealedLawsuitId,
      String appealStatus,
      Date executionDate
  ) {
    super(id);
    entityName = ENTITY_NAME;
    this.sueDate = sueDate;
    this.courtId = courtId;
    this.suitorId = suitorId;
    this.claimText = claimText;
    this.defendantId = defendantId;
    this.defendantText = defendantText;
    this.judgeId = judgeId;
    this.startDate = startDate;
    this.verdictDate = verdictDate;
    this.verdictText = verdictText;
    this.appealedLawsuitId = appealedLawsuitId;
    this.appealStatus = appealStatus;
    this.executionDate = executionDate;
  }

  public Date getSueDate() {
    return sueDate;
  }

  public void setSueDate(Date sueDate) {
    this.sueDate = sueDate;
  }

  public long getCourtId() {
    return courtId;
  }

  public void setCourtId(long courtId) {
    this.courtId = courtId;
  }

  public long getSuitorId() {
    return suitorId;
  }

  public void setSuitorId(long suitorId) {
    this.suitorId = suitorId;
  }

  public String getClaimText() {
    return claimText;
  }

  public void setClaimText(String claimText) {
    this.claimText = claimText;
  }

  public long getDefendantId() {
    return defendantId;
  }

  public void setDefendantId(long defendantId) {
    this.defendantId = defendantId;
  }

  public String getDefendantText() {
    return defendantText;
  }

  public void setDefendantText(String defendantText) {
    this.defendantText = defendantText;
  }

  public long getJudgeId() {
    return judgeId;
  }

  public void setJudgeId(long judgeId) {
    this.judgeId = judgeId;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getVerdictDate() {
    return verdictDate;
  }

  public void setVerdictDate(Date verdictDate) {
    this.verdictDate = verdictDate;
  }

  public String getVerdictText() {
    return verdictText;
  }

  public void setVerdictText(String verdictText) {
    this.verdictText = verdictText;
  }

  public Date getExecutionDate() {
    return executionDate;
  }

  public void setExecutionDate(Date executionDate) {
    this.executionDate = executionDate;
  }

  public long getAppealedLawsuitId() {
    return appealedLawsuitId;
  }

  public void setAppealedLawsuitId(long appealedLawsuitId) {
    this.appealedLawsuitId = appealedLawsuitId;
  }

  public String getAppealStatus() {
    return appealStatus;
  }

  public void setAppealStatus(String appealStatus) {
    this.appealStatus = appealStatus;
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        super.hashCode(),
        getSueDate(),
        getCourtId(),
        getSuitorId(),
        getClaimText(),
        getDefendantId(),
        getDefendantText(),
        getJudgeId(),
        getStartDate(),
        getVerdictDate(),
        getVerdictText(),
        getAppealedLawsuitId(),
        getAppealStatus(),
        getExecutionDate());
  }

  @Override
  public String toString() {
    return "{" + super.toString() + ", "
        + "\"sueDate\":\"" + sueDate + "\", "
        + "\"courtId\":\"" + courtId + "\", "
        + "\"suitorId\":\"" + suitorId + "\", "
        + "\"claimText\":\"" + claimText + "\", "
        + "\"defendantId\":\"" + defendantId + "\", "
        + "\"defendantText\":\"" + defendantText + "\", "
        + "\"judgeId\":\"" + judgeId + "\", "
        + "\"startDate\":\"" + startDate + "\", "
        + "\"verdictDate\":\"" + verdictDate + "\", "
        + "\"verdictText\":\"" + verdictText + "\", "
        + "\"AppealedLawsuitId\":\"" + appealedLawsuitId + "\", "
        + "\"appealStatus\":\"" + appealStatus + "\", "
        + "\"executionDate\":\"" + executionDate + "\""
        + "}";
  }

}
