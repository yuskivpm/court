package com.dsa.model;

import com.dsa.model.pure.CourtPure;

public class Court extends CourtPure {
  private Court mainCourt;

  public Court(){
    super();
  }

  public Court(long id, String courtName, CourtInstance courtInstance, Court mainCourt){
    super(id, courtName, courtInstance, mainCourt==null?0:mainCourt.getId());
    this.mainCourt=mainCourt;
  }

  public Court getMainCourt() {
    return mainCourt;
  }

  public void setMainCourt(Court mainCourt) {
    this.mainCourt = mainCourt;
  }

  @Override
  public long getMainCourtId(){
    return mainCourt==null?super.getMainCourtId():mainCourt.getId();
  }
}
