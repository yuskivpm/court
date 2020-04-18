package com.dsa.model;

import com.dsa.model.pure.VerdictPure;

import java.util.Date;

public class Verdict extends VerdictPure {
  private Lawsuit lawsuit;
  private Court jurisdictionCourt;

  public Verdict(){
    super();
  }

  public Verdict(
      long id,
      Lawsuit lawsuit,
      Date effectiveDate,
      VerdictType verdictResult,
      String verdictText,
      Court jurisdictionCourt
  ) {
    super(id, getIdIfNotNull(lawsuit,0),effectiveDate,verdictResult,verdictText,getIdIfNotNull(jurisdictionCourt,0));
    this.lawsuit=lawsuit;
    this.jurisdictionCourt=jurisdictionCourt;
  }

  public Lawsuit getLawsuit() {
    return lawsuit;
  }

  public void setLawsuit(Lawsuit lawsuit) {
    this.lawsuit = lawsuit;
  }

  public Court getJurisdictionCourt() {
    return jurisdictionCourt;
  }

  public void setJurisdictionCourt(Court jurisdictionCourt) {
    this.jurisdictionCourt = jurisdictionCourt;
  }

  @Override
  public long getLawsuitId(){
    return getIdIfNotNull(lawsuit,super.getLawsuitId());
  }

  @Override
  public long getJurisdictionCourtId(){
    return getIdIfNotNull(jurisdictionCourt,super.getJurisdictionCourtId());
  }

}
