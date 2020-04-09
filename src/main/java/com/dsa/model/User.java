package com.dsa.model;

import com.dsa.model.pure.UserPure;

public class User extends UserPure {
  private Court court;

  public User(){
    super();
  }
//
//  public User(long id, String login, String password, Role role, String name, long courtId, boolean isActive) {
//    super(id, login, password, role, name, courtId, isActive);
//  }

  public User(long id, String login, String password, Role role, String name, Court court, boolean isActive) {
    super(id, login, password, role, name, court==null?0:court.getId(), isActive);
    this.court=court;
  }

  public Court getCourt() {
    return court;
  }

  public void setCourt(Court court) {
    this.court = court;
  }

  @Override
  public long getCourtId(){
    return court==null?0:court.getId();
  }
}
