package com.dsa.domain.court;

import com.dsa.domain.MyEntity;

import java.util.Objects;

import static com.dsa.domain.court.CourtConst.*;

public class CourtEntity extends MyEntity {

  private String courtName;
  private CourtInstance courtInstance;
  private long mainCourtId;

  public CourtEntity() {
    entityName = ENTITY_NAME;
  }

  public CourtEntity(long id, String courtName, CourtInstance courtInstance, long mainCourtId) {
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
        + "\"" + COURT_NAME + "\":\"" + courtName + "\", "
        + "\"" + COURT_INSTANCE + "\":\"" + courtInstance + "\", "
        + "\"" + MAIN_COURT_ID + "\":\"" + mainCourtId + "\""
        + "}";
  }

}
