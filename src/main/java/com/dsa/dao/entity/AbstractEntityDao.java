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
  private static final String SQL_DELETE_BY_ID="DELETE FROM %s WHERE ID=?";
  protected Connection connection;
  protected final String TABLE_NAME;
  protected final String SQL_INSERT;
  protected final String SQL_UPDATE_BY_ID;

  public AbstractEntityDao(String entityTableName, String sqlInsert, String sqlUpdate) throws DbPoolException, SQLException {
    this(DbPool.getConnection(), entityTableName, sqlInsert, sqlUpdate);
  }

  public AbstractEntityDao(@NotNull Connection connection, String entityTableName, String sqlInsert, String sqlUpdate){
    this.connection = connection;
    this.TABLE_NAME=entityTableName;
    this.SQL_INSERT=sqlInsert;
    this.SQL_UPDATE_BY_ID=sqlUpdate;
  }

  @Override
  public boolean createEntity(E entity){
    boolean result=false;
    try(PreparedStatement st = connection.prepareStatement(SQL_INSERT)){
      setAllPreparedValues(st,entity,true);
      result=st.executeUpdate()>0;
    }catch(SQLException e){
      log.error("SQLException in EntityDao.createEntity("+entity+"): "+e);
    }
    return result;
  };

  @Override
  public List<E> readAll(){
    List<E> entities = new ArrayList<>();
    try(
        Statement st = connection.createStatement();
        ResultSet rs=st.executeQuery(SQL_SELECT_ALL+TABLE_NAME);
    ){
      while(rs.next()){
        E entity= recordToEntity(rs);
        if(entity!=null){
          entities.add(entity);
        }
      }
    }catch(SQLException e){
      log.error("SQLException in readAll: "+e);
    }
    return entities;
  }

  @Override
  public E readEntity(long id) throws SQLException {
    return readEntity("ID", Long.toString(id));
  }

  public E readEntity(String fieldName, String value) throws SQLException{
    String[] where={fieldName, value};
    return readEntity(where);
  }

  public E readEntity(@NotNull String[] whereArray) throws SQLException{
    if((whereArray.length==0)||((whereArray.length&1)==1)){
      log.error("Incorrect whereArray length: "+whereArray);
      throw new SQLException("Incorrect whereArray length: "+whereArray.length);
    }
    E entity=null;
    String selectString = String.format(SQL_SELECT_BY_MAP,TABLE_NAME);
    for(int i=0; i<whereArray.length;){
      selectString+=" "+whereArray[i++]+"='"+whereArray[i++]+"'"+(i<whereArray.length?" and":"");
    }
    try(
        PreparedStatement st = connection.prepareStatement(selectString);
        ResultSet rs=st.executeQuery();
    ){
      if (rs.next()){
        entity=recordToEntity(rs);
      }
    }catch(SQLException e){
      log.error("SQLException in EntityDao.readEntity("+whereArray+"): "+e);
    }
    return entity;
  }

  public boolean updateEntity(E entity){
    boolean result=false;
    try(PreparedStatement st = connection.prepareStatement(SQL_UPDATE_BY_ID)){
      st.setLong(setAllPreparedValues(st,entity,false), entity.getId());
      result=st.executeUpdate()>0;
    }catch(SQLException e){
      log.error("Fail updateEntity("+entity+"): "+e);
    }
    return result;
  }

  public boolean deleteEntity(String id){
    boolean result=false;
    try(PreparedStatement st = connection.prepareStatement(String.format(SQL_DELETE_BY_ID,TABLE_NAME))){
      st.setString(1, id);
      result=st.executeUpdate() > 0;
    }catch(SQLException e){
      log.error("deleteEntity("+id+") fail: "+e);
    }
    return result;
  }

  public boolean deleteEntity(long id){
    return deleteEntity(Long.toString(id));
  }

  protected void setPreparedValueOrNull(PreparedStatement preparedStatement, int parameterIndex, long value) throws SQLException {
    if(value==0){
      preparedStatement.setNull(parameterIndex,0);
    }else{
      preparedStatement.setLong(parameterIndex, value);
    }
  }

  protected abstract int setAllPreparedValues(PreparedStatement preparedStatement, E entity, boolean isAddOperation) throws SQLException;

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
