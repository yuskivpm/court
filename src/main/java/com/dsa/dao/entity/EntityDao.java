package com.dsa.dao.entity;

import com.dsa.model.pure.MyEntity;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

interface EntityDao<E extends MyEntity> {

  List<E> getAll();

  E getEntity(long id) throws SQLException;

  boolean add(E entity);

  boolean update(E entity);

  boolean remove(long id);

  default boolean remove(E entity){
    return remove(entity.getId());
  }

  default void createTable(Connection connection, String sql, Logger log) {
    Statement st = null;
    try {
      st = connection.createStatement();
      sqlExecute(st,sql,true,log);
      st.execute(sql);
    } catch (SQLException e) {
      log.error("SQLException in createTable: " + e);
    }
  }

  default void sqlExecute(@NotNull Statement st, String sqlQuery, boolean autoClose, Logger log){
    try{
      st.execute(sqlQuery);
    }catch(SQLException e){
      log.error("SQLException in sqlExecute["+sqlQuery+"]: "+e);
    }finally{
      if(autoClose){
        try{
          st.close();
        }catch(SQLException e){
          log.error("SQLException in sqlExecute.onAutoCloseStatement["+sqlQuery+"]: "+e);
        }
      }
    }
  }

}
