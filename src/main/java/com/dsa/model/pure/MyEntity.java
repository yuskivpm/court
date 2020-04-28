package com.dsa.model.pure;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Objects;
import java.util.Date;

public abstract class MyEntity implements Serializable, Cloneable{
  public static String dateFormat="dd.MM.yyyy";
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
    return "\"entity\":\""+entityName+"\", \"id\":\""+id+"\"";
  }

  public static long getIdIfNotNull(MyEntity entity, long defaultValue){
    return entity==null?defaultValue:entity.getId();
  }

  public static SimpleDateFormat getDateFormatter(String dateFormat){
    return new SimpleDateFormat(dateFormat == null? MyEntity.dateFormat: dateFormat);
  };

  public static String dateToStr(Date date){
    return getDateFormatter(null).format(date);
  }

  public static Date strToDate(String dateAsString) throws ParseException{
    return getDateFormatter(null).parse(dateAsString);
  }
}
