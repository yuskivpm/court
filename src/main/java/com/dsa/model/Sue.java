package com.dsa.model;

import com.dsa.model.pure.SuePure;

import java.util.Date;

public class Sue extends SuePure {
  private User suitor;
  private User defendant;
  private Court court;

  public Sue(){
    super();
  }

  public Sue(long id, User suitor, User defendant, Court court, Date sueDate, String claimText) {
    super(id, getIdIfNotNull(suitor,0), getIdIfNotNull(defendant,0), getIdIfNotNull(court,0), sueDate, claimText);
    this.suitor=suitor;
    this.defendant=defendant;
    this.court=court;
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

  @Override
  public long getSuitorId(){
    return getIdIfNotNull(suitor,super.getSuitorId());
  }

  @Override
  public long getDefendantId(){
    return getIdIfNotNull(defendant,super.getDefendantId());
  }

  @Override
  public long getCourtId(){
    return getIdIfNotNull(court,super.getCourtId());
  }

}
