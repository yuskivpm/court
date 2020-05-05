package com.dsa.domain.user;

import com.dsa.domain.court.Court;

public class User extends UserPure {

  private Court court;

  public User() {
    super();
  }

  public User(long id, String login, String password, Role role, String name, Court court, boolean isActive) {
    super(id, login, password, role, name, getIdIfNotNull(court, 0), isActive);
    this.court = court;
  }

  public Court getCourt() {
    return court;
  }

  public void setCourt(Court court) {
    this.court = court;
  }

  @Override
  public long getCourtId() {
    return getIdIfNotNull(court, super.getCourtId());
  }

}
