package com.dsa.domain;

import org.jetbrains.annotations.Contract;

import java.util.Objects;

public abstract class MyEntity implements IEntity {

  protected String entityName = "MyEntity";
  private long id;

  @Contract(pure = true)
  protected MyEntity() {
  }

  @Contract(pure = true)
  protected MyEntity(long id) {
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @Contract(value = "null -> false", pure = true)
  @Override
  public boolean equals(Object obj) {
    return (null != obj) && (getClass() == obj.getClass()) && ((this == obj) || (this.getId() == ((IEntity) obj).getId()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId());
  }

  @Override
  public String toString() {
    return "\"entity\":\"" + entityName + "\", \"" + ID + "\":\"" + id + "\"";
  }

}
