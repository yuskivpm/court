package com.dsa.model.pure;

import com.dsa.model.CourtInstance;

import java.util.Objects;

public class CourtPure extends MyEntity {

  private static final String ENTITY_NAME = "Court";
  private String courtName;
  private CourtInstance courtInstance;
  protected long mainCourtId;

  public CourtPure() {
    entityName = ENTITY_NAME;
  }

  public CourtPure(long id, String courtName, CourtInstance courtInstance, long mainCourtId) {
    super(id);
    entityName = ENTITY_NAME;
    this.courtName = courtName;
    this.courtInstance = courtInstance;
    this.mainCourtId = mainCourtId;
  }

  public String getCourtName() {
    return courtName;
  }

  public void setCourtName(String courtName) {
    this.courtName = courtName;
  }

  public CourtInstance getCourtInstance() {
    return courtInstance;
  }

  public void setCourtInstance(CourtInstance courtInstance) {
    this.courtInstance = courtInstance;
  }

  public long getMainCourtId() {
    return mainCourtId;
  }

  public void setMainCourtId(long mainCourtId) {
    this.mainCourtId = mainCourtId;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getCourtName(), getCourtInstance(), getMainCourtId());
  }

  @Override
  public String toString() {
    return "{" + super.toString() + ", "
        + "\"courtName\":\"" + courtName + "\", "
        + "\"courtInstance\":\"" + courtInstance + "\", "
        + "\"mainCourtId\":\"" + mainCourtId + "\""
        + "}";
  }

}
