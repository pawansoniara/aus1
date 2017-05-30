
package com.psedb.ejb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;

import com.psedb.model.LuAccessLevel;
import com.psedb.model.Staff;
import com.psedb.service.BaseJdbcService;

@Stateless
public class StaffEjbBean extends BaseJdbcService{

    public void save(Staff staff ) {
        Connection conn=null;
        PreparedStatement pstm=null;
        try{
          conn=getDbConnection();
          pstm=conn.prepareStatement("INSERT INTO teacher(FNAME,SURNAME,ACCESS_ID,email,PASSWD)VALUES(?,?,?,?,?)");
          pstm.setString(1, staff.getFname());
          pstm.setString(2, staff.getSurname());
          pstm.setInt(3, staff.getLuAccessLevel().getAccessId());
          pstm.setString(4, staff.getLoginname());
          pstm.setString(5, staff.getPasswd());
          pstm.execute();
        }catch(Exception e){
            Logger.getLogger(BaseJdbcService.class.getName()).log(Level.SEVERE, null, e);
        }finally{
             sqlCleanup(null, pstm, conn);
        }
    }
    
    public List<Staff> getStaffList() {
        Connection conn=null;
        PreparedStatement pstm=null;
        ResultSet rs=null;
        List<Staff> staffList=new ArrayList<>(0);
        try{
          conn=getDbConnection();
          String query="SELECT S.TID, S.FNAME, S.SURNAME, S.ACCESS_ID, S.email, S.PASSWD,AL.ACESS_DESCRIPTION "
                  + "FROM teacher S "
                  + "INNER JOIN ACCESS_LEVEL AL ON S.ACCESS_ID=AL.ACCESS_ID "
                  + "WHERE TID<>1";
          pstm=conn.prepareStatement(query);
          rs=pstm.executeQuery();
          while(rs.next()){
             LuAccessLevel luAccessLevel=new LuAccessLevel(rs.getByte("ACCESS_ID"), rs.getString("ACESS_DESCRIPTION"));
             Staff staff=new Staff(luAccessLevel, rs.getByte("TID"), rs.getString("FNAME"),  rs.getString("SURNAME"),  rs.getString("email"));
             staffList.add(staff);
          }
        }catch(Exception e){
            Logger.getLogger(BaseJdbcService.class.getName()).log(Level.SEVERE, null, e);
        }finally{
             sqlCleanup(rs, pstm, conn);
        }
        
        return staffList;
    }
    
    public Staff getStaff(Byte staffId) {
        Connection conn=null;
        PreparedStatement pstm=null;
        ResultSet rs=null;
        Staff staff=new Staff();
        try{
          conn=getDbConnection();
          String query="SELECT S.TID, S.FNAME, S.SURNAME, S.ACCESS_ID, S.email, S.PASSWD,AL.ACESS_DESCRIPTION "
                  + "FROM teacher S "
                  + "INNER JOIN ACCESS_LEVEL AL ON S.ACCESS_ID=AL.ACCESS_ID "
                  + "WHERE TID="+staffId;
          pstm=conn.prepareStatement(query);
          rs=pstm.executeQuery();
          if(rs.next()){
             LuAccessLevel luAccessLevel=new LuAccessLevel(rs.getByte("ACCESS_ID"), rs.getString("ACESS_DESCRIPTION"));
             staff=new Staff(luAccessLevel, rs.getByte("TID"), rs.getString("FNAME"),  rs.getString("SURNAME"),  rs.getString("email"));
          }
        }catch(Exception e){
            Logger.getLogger(BaseJdbcService.class.getName()).log(Level.SEVERE, null, e);
        }finally{
             sqlCleanup(rs, pstm, conn);
        }
        return staff;
    }
    
    public String getStaffPasswd(Byte staffId) {
        Connection conn=null;
        PreparedStatement pstm=null;
        ResultSet rs=null;
        try{
          conn=getDbConnection();
          String query="SELECT PASSWD FROM teacher  WHERE TID="+staffId;
          pstm=conn.prepareStatement(query);
          rs=pstm.executeQuery();
          if(rs.next()){
              return rs.getString("PASSWD");
          }
        }catch(Exception e){
            Logger.getLogger(BaseJdbcService.class.getName()).log(Level.SEVERE, null, e);
        }finally{
             sqlCleanup(rs, pstm, conn);
        }
        return null;
    }

    public Integer getStaffId(String loginname) {
        Connection conn=null;
        PreparedStatement pstm=null;
        ResultSet rs=null;
        Integer staffId=null;
        try{
          conn=getDbConnection();
          String query="SELECT TID FROM teacher WHERE email=?";
          pstm=conn.prepareStatement(query);
          pstm.setString(1, loginname);
          rs=pstm.executeQuery();
          while(rs.next()){
             staffId=rs.getInt("TID");
          }
        }catch(Exception e){
            Logger.getLogger(BaseJdbcService.class.getName()).log(Level.SEVERE, null, e);
        }finally{
             sqlCleanup(rs, pstm, conn);
        }
        
        return staffId;
    }

    public void update(Staff staff, String staffId) {
        Connection conn=null;
        PreparedStatement pstm=null;
        try{
          conn=getDbConnection();
          pstm=conn.prepareStatement("UPDATE  teacher SET FNAME=?,SURNAME=?,ACCESS_ID=?,email=?,PASSWD=? WHERE TID="+staffId);
          pstm.setString(1, staff.getFname());
          pstm.setString(2, staff.getSurname());
          pstm.setInt(3, staff.getLuAccessLevel().getAccessId());
          pstm.setString(4, staff.getLoginname());
          pstm.setString(5, staff.getPasswd());
          pstm.execute();
        }catch(Exception e){
            Logger.getLogger(BaseJdbcService.class.getName()).log(Level.SEVERE, null, e);
        }finally{
             sqlCleanup(null, pstm, conn);
        }//To change body of generated methods, choose Tools | Templates.
    }

    public void deleteStaff(Byte staffId) {
        Connection conn=null;
        PreparedStatement pstm=null;
        try{
          conn=getDbConnection();
          pstm=conn.prepareStatement("DELETE FROM teacher WHERE TID="+staffId);
          pstm.execute();
        }catch(Exception e){
            Logger.getLogger(BaseJdbcService.class.getName()).log(Level.SEVERE, null, e);
        }finally{
             sqlCleanup(null, pstm, conn);
        }
    }
    
}

