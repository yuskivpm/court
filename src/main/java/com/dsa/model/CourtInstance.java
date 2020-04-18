package com.dsa.model;

public enum CourtInstance {
  LOCAL(1),
  APPEAL(2),
  CASSATION(3);

  private int instanceLevel;

  private CourtInstance(int instanceLevel){
    this.instanceLevel=instanceLevel;
  }

  public int getInstanceLevel(){
    return instanceLevel;
  }
}
