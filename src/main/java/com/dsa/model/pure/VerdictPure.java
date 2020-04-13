package com.dsa.model.pure;

import com.dsa.model.VerdictType;

import java.util.Date;
import java.util.Objects;
//todo: create Verdict & VerdictDao

public class VerdictPure extends MyEntity {
  long lawsuitId;
  Date effectiveDate;
  VerdictType verdictResult;
  String verdictText;
  long jurisdictionCourtId;

  public VerdictPure(
      long id,
      long lawsuitId,
      Date effectiveDate,
      VerdictType verdictResult,
      String verdictText,
      long jurisdictionCourtId
  ) {
    super(id);
    entityName="Verdict";
    this.lawsuitId = lawsuitId;
    this.effectiveDate = effectiveDate;
    this.verdictResult = verdictResult;
    this.verdictText=verdictText;
    this.jurisdictionCourtId=jurisdictionCourtId;
  }

  public long getLawsuitId() {
    return lawsuitId;
  }

  public void setLawsuitId(long lawsuitId) {
    this.lawsuitId = lawsuitId;
  }

  public Date getEffectiveDate() {
    return effectiveDate;
  }

  public void setEffectiveDate(Date effectiveDate) {
    this.effectiveDate = effectiveDate;
  }

  public VerdictType getVerdictResult() {
    return verdictResult;
  }

  public void setVerdictResult(VerdictType verdictResult) {
    this.verdictResult = verdictResult;
  }

  public String getVerdictText(){
    return verdictText;
  }

  public void setVerdictText(String verdictText){
    this.verdictText=verdictText;
  }

  public long getJurisdictionCourtId(){
    return jurisdictionCourtId;
  }

  public void setJurisdictionCourtId(long jurisdictionCourtId){
    this.jurisdictionCourtId=jurisdictionCourtId;
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        getId(),
        getLawsuitId(),
        getEffectiveDate(),
        getVerdictResult(),
        getVerdictText(),
        getJurisdictionCourtId()
    );
  }

  @Override
  public String toString(){
    return super.toString()+", "
        +"lawsuitId="+lawsuitId+", "
        +"effectiveDate="+effectiveDate+", "
        +"verdictResult="+verdictResult+", "
        +"verdictText="+verdictText+", "
        +"jurisdictionCourtId="+jurisdictionCourtId
        +"]";
  }
}
