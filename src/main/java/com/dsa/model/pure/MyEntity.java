package com.dsa.model.pure;

import java.io.Serializable;
import java.util.Objects;

public abstract class MyEntity implements Serializable, Cloneable{
  protected String entityName="MyEntity";
  private long id;

  public MyEntity(){}

  public MyEntity(long id){
    this.id=id;
  }

  public long getId(){
    return id;
  }

  public void setId(long id){
    this.id=id;
  }

  @Override
  public boolean equals(Object obj) {
    return (null!=obj) && (getClass()==obj.getClass()) && ((this==obj) || (this.id ==((MyEntity)obj).id));
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId());
  }

  @Override
  public String toString(){
    return entityName+" [id="+id;
  }
}
