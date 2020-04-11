package com.dsa.dao.entity;

import com.dsa.model.Role;
import com.dsa.model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class UserDao extends AbstractEntityDao<User> {
  private static final String SQL_INSERT="INSERT INTO USER (LOGIN,PASSWORD,ROLE_ID,NAME,COURT_ID,IS_ACTIVE) "+
      "VALUES(?, ?, ?, ?, ?, ?)";
  public static final String SQL_CREATE_TABLE="CREATE TABLE IF NOT EXISTS USER(" +
      "ID BIGINT AUTO_INCREMENT PRIMARY KEY, " +
      "LOGIN VARCHAR(255) NOT NULL, " +
      "PASSWORD VARCHAR(255) NOT NULL, " +
      "ROLE_ID VARCHAR(20) NOT NULL, " +
      "NAME VARCHAR(255) NOT NULL, " +
      "COURT_ID BIGINT DEFAULT NULL, " +
      "IS_ACTIVE BOOLEAN DEFAULT TRUE, " +
      "FOREIGN KEY (COURT_ID) REFERENCES COURT(ID) ON DELETE CASCADE" +
      ")";

  public UserDao(Connection connection){
    super(connection,"USER", SQL_INSERT);
  }

  @Override
  protected User recordToEntity(ResultSet resultSet){
    User user= new User();
    try{
      user.setId(resultSet.getLong("ID"));
      user.setLogin(resultSet.getString("LOGIN"));
      user.setPassword(resultSet.getString("PASSWORD"));
      user.setRole(Role.valueOf(resultSet.getString("ROLE_ID")));
      user.setName(resultSet.getString("NAME"));
      user.setCourt(new CourtDao(connection).getEntity(resultSet.getLong("COURT_ID")));
      user.setIsActive(resultSet.getBoolean("IS_ACTIVE"));
      return user;
    }catch(SQLException e){
      log.error("SQLException in UserDao.recordToEntity: "+e);
      return null;
    }
  }

  @Override
  protected void setAllPreparedValues(PreparedStatement preparedStatement, User user) throws SQLException{
    preparedStatement.setString(1, user.getLogin());
    preparedStatement.setString(2, user.getPassword());
    preparedStatement.setString(3, user.getRole().toString());
    preparedStatement.setString(4, user.getName());
    setPreparedValueOrNull(preparedStatement,5,user.getCourtId());
    preparedStatement.setBoolean(6, user.getIsActive());
  };

  @Override
  public boolean update(User entity){
//    TODO: update for User
    throw new RuntimeException("Unready!!");
  };

  @Override
  public boolean remove(long id){
//    TODO: remove for Use
    throw new RuntimeException("Unready!!");
  };
}
