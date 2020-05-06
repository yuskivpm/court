package com.dsa.domain;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.io.Serializable;
import java.util.Objects;
import java.util.Date;

public abstract class MyEntity implements Serializable, Cloneable {

  private static String dateFormat = "dd.MM.yyyy";
  protected String entityName = "MyEntity";
  private long id;

  @Contract(pure = true)
  public MyEntity() {
  }

  @Contract(pure = true)
  public MyEntity(long id) {
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
    return (null != obj) && (getClass() == obj.getClass()) && ((this == obj) || (this.id == ((MyEntity) obj).id));
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId());
  }

  @Override
  public String toString() {
    return "\"entity\":\"" + entityName + "\", \"id\":\"" + id + "\"";
  }

  @Contract("null, _ -> param2")
  protected static long getIdIfNotNull(MyEntity entity, long defaultValue) {
    return entity == null ? defaultValue : entity.getId();
  }

  @NotNull
  @Contract("_ -> new")
  private static SimpleDateFormat getDateFormatter(String dateFormat) {
    return new SimpleDateFormat(dateFormat == null ? MyEntity.dateFormat : dateFormat);
  }

  @NotNull
  public static String dateToStr(Date date) {
    return getDateFormatter(null).format(date);
  }

  public static Date strToDate(String dateAsString) throws ParseException {
    return getDateFormatter(null).parse(dateAsString);
  }

}
