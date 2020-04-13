package com.dsa.dao.entity;

import com.dsa.dao.services.DbPool;
import com.dsa.dao.services.DbPoolException;
import com.dsa.model.pure.MyEntity;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractEntityDao <E extends MyEntity> implements EntityDao<E>, AutoCloseable {
  protected static final Logger log = Logger.getLogger(AbstractEntityDao.class);
  private static final String SQL_SELECT_ALL= "SELECT * FROM ";
  private static final String SQL_SELECT_BY_MAP="SELECT * FROM %s WHERE";
  protected Connection connection;
  protected final String TABLE_NAME;
  protected final String SQL_INSERT;

  public AbstractEntityDao(String entityTableName, String sqlInsert) throws DbPoolException, SQLException {
    this(DbPool.getConnection(), entityTableName, sqlInsert);
  }

  public AbstractEntityDao(@NotNull Connection connection, String entityTableName, String sqlInsert){
    this.connection = connection;
    this.TABLE_NAME=entityTableName;
    this.SQL_INSERT=sqlInsert;
  }

  @Override
  public boolean add(E entity){
    boolean result=false;
    try{
      PreparedStatement st = connection.prepareStatement(SQL_INSERT);
      setAllPreparedValues(st,entity);
      st.executeUpdate();
      result=true;
    }catch(SQLException e){
      log.error("SQLException in EntityDao.add("+entity+"): "+e);
    }
    return result;
  };

  @Override
  public List<E> getAll(){
    List<E> entities = new ArrayList<>();
    Statement st = null;
    try{
      st = connection.createStatement();
      ResultSet rs=st.executeQuery(SQL_SELECT_ALL+TABLE_NAME);
      while(rs.next()){
        E entity= recordToEntity(rs);
        if(entity!=null){
          entities.add(entity);
        }
      }
    }catch(SQLException e){
      log.error("SQLException in getAll: "+e);
    }finally{
      closeStatements(st);
    }
    return entities;
  }

  @Override
  public E getEntity(long id) throws SQLException {
    return getEntity("ID", Long.toString(id));
  }

  public E getEntity(String fieldName, String value) throws SQLException{
    String[] where={fieldName, value};
    return getEntity(where);
  }

  public E getEntity(@NotNull String[] whereArray) throws SQLException{
    if((whereArray.length==0)||((whereArray.length&1)==1)){
      log.error("Incorrect whereArray length: "+whereArray);
      throw new SQLException("Incorrect whereArray length: "+whereArray.length);
    }
    E entity=null;
    String selectString = String.format(SQL_SELECT_BY_MAP,TABLE_NAME);
    for(int i=0; i<whereArray.length;){
      selectString+=" "+whereArray[i++]+"='"+whereArray[i++]+"'"+(i<whereArray.length?" and":"");
    }
    try{
      PreparedStatement st = connection.prepareStatement(selectString);
      ResultSet rs=st.executeQuery();
      if (rs.next()){
        entity=recordToEntity(rs);
      }
    }catch(SQLException e){
      log.error("SQLException in EntityDao.getItem("+whereArray+"): "+e);
    }
    return entity;
  }

  protected void setPreparedValueOrNull(PreparedStatement preparedStatement, int parameterIndex, long value) throws SQLException {
    if(value==0){
      preparedStatement.setNull(parameterIndex,0);
    }else{
      preparedStatement.setLong(parameterIndex, value);
    }
  }

  protected abstract void setAllPreparedValues(PreparedStatement preparedStatement, E entity) throws SQLException;

  protected abstract E recordToEntity(ResultSet resultSet);

  public void closeStatements(Statement st){
    if(st!=null){
      try{
        st.close();
      }catch(SQLException e){
        log.error("SQLException in closeStatement()"+e);
      }
    }
  }

  public void close() throws SQLException{
    if(connection!=null){
      connection.close();
    }
  }
}
