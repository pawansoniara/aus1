package com.psedb.ejb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ejb.Stateless;

import com.psedb.model.UserBean;
import com.psedb.service.BaseJdbcService;

@Stateless
public class AuthenticationEjbBean extends BaseJdbcService{

    public UserBean authenticateStaff(UserBean user, String userPassword) {
        Connection conn=null;
        ResultSet rs=null;
        PreparedStatement pstm=null;
        try{
          conn=getDbConnection();
          pstm=conn.prepareStatement("SELECT FNAME, ACCESS_ID  FROM teacher WHERE email=? AND PASSWD=?");
          pstm.setString(1, user.getUserName());
          pstm.setString(2, userPassword);
          rs=pstm.executeQuery();
          if(rs.next()){
              user.setAccessId(rs.getInt("ACCESS_ID"));
              user.setUserName(rs.getString("FNAME"));
          }else{
              user=null;
          }
        }catch(Exception e){
            System.err.println(BaseJdbcService.class.getName()+"    :   "+ e.getMessage());
        }finally{
             sqlCleanup(rs, pstm, conn);
        }
        
        return user;
    }
    
    public UserBean authenticateStudent(UserBean user, String userPassword) {
        Connection conn=null;
        ResultSet rs=null;
        PreparedStatement pstm=null;
        try{
          conn=getDbConnection();
          pstm=conn.prepareStatement("SELECT FNAME FROM STUDENT WHERE EMAIL=? AND PASSWD=?");
          pstm.setString(1, user.getUserName());
          pstm.setString(2, userPassword);
          rs=pstm.executeQuery();
          if(rs.next()){
              user.setUserName(rs.getString("FNAME"));
          }else{
              user=null;
          }
        }catch(Exception e){
            System.err.println(BaseJdbcService.class.getName()+"    :   "+ e.getMessage());
        }finally{
             sqlCleanup(rs, pstm, conn);
        }
        
        return user;
    }
    
}
