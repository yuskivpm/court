package com.dsa.model.pure;

import java.util.Date;
import java.util.Objects;

public class SuePure extends MyEntity {
  long suitorId;
  long defendantId;
  long courtId;
  Date sueDate;
  String claimText;

  static{
    MyEntity.entityName="Sue";
  }

  public SuePure(long id, long suitorId, long defendantId, long courtId, Date sueDate, String claimText) {
    super(id);
    this.suitorId = suitorId;
    this.defendantId=defendantId;
    this.courtId=courtId;
    this.sueDate = sueDate;
    this.claimText = claimText;
  }

  public long getSuitorId() {
    return suitorId;
  }

  public void setSuitorId(long suitorId) {
    this.suitorId = suitorId;
  }

  public long getDefendantId(){
    return defendantId;
  }

  public void setDefendantId(long defendantId){
    this.defendantId=defendantId;
  }

  public long getCourtId(){
    return courtId;
  }

  public void setCourtId(long courtId){
    this.courtId=courtId;
  }

  public Date getSueDate() {
    return sueDate;
  }

  public void setSueDate(Date sueDate) {
    this.sueDate = sueDate;
  }

  public String getClaimText() {
    return claimText;
  }

  public void setClaimText(String claimText) {
    this.claimText = claimText;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getSuitorId(), getDefendantId(), getCourtId(), getSueDate(), getClaimText());
  }

  @Override
  public String toString(){
    return super.toString()+", "
        +"suitorId="+suitorId+", "
        +"defendantId="+defendantId+", "
        +"courtId="+courtId+", "
        +"sueDate="+sueDate+", "
        +"claimText="+claimText
        +"]";
  }
}
