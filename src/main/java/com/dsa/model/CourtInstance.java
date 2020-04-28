package com.dsa.model;

import org.jetbrains.annotations.Contract;

public enum CourtInstance {

  LOCAL(1),
  APPEAL(2),
  CASSATION(3);

  private int instanceLevel;

  @Contract(pure = true)
  CourtInstance(int instanceLevel) {
    this.instanceLevel = instanceLevel;
  }

  @Contract(pure = true)
  public int getInstanceLevel() {
    return instanceLevel;
  }

}
