package com.dsa.dao.entity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import com.dsa.model.pure.MyEntity;

public abstract class AbstractEntityDao <E extends MyEntity> implements EntityDao<E> {
  protected static final Logger log = Logger.getLogger(AbstractEntityDao.class);
  private static final String SQL_SELECT_ALL= "SELECT * FROM ";;
  private static final String SQL_SELECT_BY_FIELD_NAME="SELECT * FROM %s WHERE %s=?";
  protected final Connection connection;
  protected final String TABLE_NAME;
  protected final String SQL_INSERT;

  public AbstractEntityDao(Connection connection, String entityTableName, String sqlInsert){
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
  public E getEntity(long id){
    return getEntity("ID", Long.toString(id));
  }

  public E getEntity(String fieldName, String value){
    E entity=null;
    try{
      PreparedStatement st = connection.prepareStatement(String.format(SQL_SELECT_BY_FIELD_NAME,TABLE_NAME,fieldName));
      st.setString(1,value);
      ResultSet rs=st.executeQuery();
      if (rs.next()){
        entity=recordToEntity(rs);
      }
    }catch(SQLException e){
      log.error("SQLException in EntityDao.getItem("+fieldName+"="+ value+"): "+e);
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
}
