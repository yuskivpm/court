package com.dsa.domain.user;

import com.dsa.domain.MyEntity;

import java.util.Objects;

public class UserEntity extends MyEntity {

  private static final String ENTITY_NAME = "User";
  private String name;
  private String login;
  private String password;
  private Role role;
  long courtId;
  boolean isActive;

  public UserEntity() {
    entityName = ENTITY_NAME;
  }

  public UserEntity(long id, String login, String password, Role role, String name, long courtId, boolean isActive) {
    super(id);
    entityName = ENTITY_NAME;
    this.login = login;
    this.password = password;
    this.role = role;
    this.name = name;
    this.courtId = role != Role.JUDGE ? 0 : courtId;
    this.isActive = isActive;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public long getCourtId() {
    return courtId;
  }

  public void setCourtId(long courtId) {
    this.courtId = role != Role.JUDGE ? 0 : courtId;
  }

  public boolean getIsActive() {
    return isActive;
  }

  public void setIsActive(boolean isActive) {
    this.isActive = isActive;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getLogin(), getPassword(), getRole(), getName(), getCourtId(), getIsActive());
  }

  @Override
  public String toString() {
    return "{" + super.toString() + ", "
        + "\"login\":\"" + login + "\", "
        + "\"password\":\"" + password + "\", "
        + "\"role\":\"" + role + "\", "
        + "\"name\":\"" + name + "\", "
        + "\"courtId\":\"" + courtId + "\", "
        + "\"isActive\":\"" + isActive + "\""
        + "}";
  }

}
