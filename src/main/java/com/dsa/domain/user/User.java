package com.dsa.domain.user;

import com.dsa.domain.IEntity;
import com.dsa.domain.court.Court;

public class User extends UserEntity {

  private Court court;

  public User() {
    super();
  }

  public User(long id, String login, String password, Role role, String name, Court court, boolean isActive) {
    super(id, login, password, role, name, IEntity.getIdIfNotNull(court, 0), isActive);
    this.court = court;
  }

  public Court getCourt() {
    return court;
  }

  public void setCourt(Court court) {
    this.court = court;
    if (court == null) {
      super.setCourtId(0);
    }
  }

  @Override
  public long getCourtId() {
    return IEntity.getIdIfNotNull(court, super.getCourtId());
  }

}
