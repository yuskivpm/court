package com.dsa.model.pure;

import com.dsa.model.Role;

import java.util.Objects;

public class UserPure extends MyEntity {
  String login;
  String password;
  Role role;
  String name;
  long courtId;
  boolean isActive;

  public UserPure(){
    entityName="User";
  }

  public UserPure(long id, String login, String password, Role role, String name, long courtId, boolean isActive) {
    super(id);
    entityName="User";
    this.login = login;
    this.password = password;
    this.role = role;
    this.name=name;
    this.courtId=courtId;
    this.isActive=isActive;
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

  public String getName(){
    return name;
  }

  public void setName(String name){
    this.name=name;
  }

  public long getCourtId() {
    return courtId;
  }

  protected void setCourtId(long courtId) {
    this.courtId = courtId;
  }

  public boolean getIsActive(){
    return isActive;
  }

  public void setIsActive(boolean isActive){
    this.isActive=isActive;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getLogin(), getPassword(), getRole(), getName(), getCourtId(), getIsActive());
  }

  @Override
  public String toString(){
    return super.toString()+", "
        +"login="+login+", "
        +"password="+password+", "
        +"role="+role+", "
        +"name="+name+", "
        +"courtId="+courtId+", "
        +"isActive="+isActive
        +"]";
  }
}
