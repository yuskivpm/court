package com.dsa.domain;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

public interface IEntity extends Serializable, Cloneable {

  String ID = "id";

  long getId();

  void setId(long id);

  @Contract("null, _ -> param2")
  static long getIdIfNotNull(IEntity entity, long defaultValue) {
    return entity == null ? defaultValue : entity.getId();
  }

  @NotNull
  @Contract("_ -> new")
  static SimpleDateFormat getDateFormatter(String dateFormat) {
    return new SimpleDateFormat(dateFormat == null ? "dd.MM.yyyy" : dateFormat);
  }

  @NotNull
  static String dateToStr(Date date) {
    return getDateFormatter(null).format(date);
  }

  static Date strToDate(String dateAsString) throws ParseException {
    return getDateFormatter(null).parse(dateAsString);
  }

}
