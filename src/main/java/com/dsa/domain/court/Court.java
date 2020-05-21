package com.dsa.domain.court;

import com.dsa.domain.IEntity;

public class Court extends CourtEntity {

  private Court mainCourt;

  public Court() {
    super();
  }

  public Court(long id, String courtName, CourtInstance courtInstance, Court mainCourt) {
    super(id, courtName, courtInstance, IEntity.getIdIfNotNull(mainCourt, 0));
    this.setMainCourt(mainCourt);
  }

  public Court getMainCourt() {
    return mainCourt;
  }

  public void setMainCourt(Court mainCourt) {
    this.mainCourt = mainCourt;
    if (mainCourt == null) {
      super.setMainCourtId(0);
    }
  }

  @Override
  public long getMainCourtId() {
    return IEntity.getIdIfNotNull(mainCourt, super.getMainCourtId());
  }

}
