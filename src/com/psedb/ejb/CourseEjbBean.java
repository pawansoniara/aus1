
package com.psedb.ejb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;

import com.psedb.model.Course;
import com.psedb.service.BaseJdbcService;

@Stateless
public class CourseEjbBean extends BaseJdbcService{

    public void save(Course course ) {
        Connection conn=null;
        PreparedStatement pstm=null;
        try{
          conn=getDbConnection();
          pstm=conn.prepareStatement("INSERT INTO COURSE(DESCRIPTION)VALUES(?)");
          pstm.setString(1, course.getDescription());
          pstm.execute();
        }catch(Exception e){
            Logger.getLogger(BaseJdbcService.class.getName()).log(Level.SEVERE, null, e);
        }finally{
             sqlCleanup(null, pstm, conn);
        }
    }
    
    public List<Course> getList() {
        Connection conn=null;
        PreparedStatement pstm=null;
        ResultSet rs=null;
        List<Course> courses=new ArrayList<>(0);
        try{
          conn=getDbConnection();
          String query="SELECT * FROM COURSE ";
          pstm=conn.prepareStatement(query);
          rs=pstm.executeQuery();
          while(rs.next()){
             courses.add(new Course(rs.getByte("CID"),rs.getString("DESCRIPTION")));
          }
        }catch(Exception e){
            Logger.getLogger(BaseJdbcService.class.getName()).log(Level.SEVERE, null, e);
        }finally{
             sqlCleanup(rs, pstm, conn);
        }
        
        return courses;
    }
    
    public Course get(Byte cid) {
        Connection conn=null;
        PreparedStatement pstm=null;
        ResultSet rs=null;
        Course course=null;
        try{
          conn=getDbConnection();
          String query="SELECT * FROM COURSE WHERE CID=?";
          pstm=conn.prepareStatement(query);
          pstm.setByte(1, cid);
          rs=pstm.executeQuery();
          if(rs.next()){
        	  course= new Course(rs.getByte("CID"),rs.getString("DESCRIPTION"));
          }
        }catch(Exception e){
            Logger.getLogger(BaseJdbcService.class.getName()).log(Level.SEVERE, null, e);
        }finally{
             sqlCleanup(rs, pstm, conn);
        }
        return course;
    }
    
   

    public void update(Course course) {
        Connection conn=null;
        PreparedStatement pstm=null;
        try{
          conn=getDbConnection();
          pstm=conn.prepareStatement("UPDATE COURSE SET DESCRIPTION=? WHERE CID=?");
          pstm.setString(1, course.getDescription());
          pstm.setByte(2, course.getCid());
          pstm.execute();
        }catch(Exception e){
            Logger.getLogger(BaseJdbcService.class.getName()).log(Level.SEVERE, null, e);
        }finally{
             sqlCleanup(null, pstm, conn);
        }
     }

    public void delete(Byte cid) {
        Connection conn=null;
        PreparedStatement pstm=null;
        try{
          conn=getDbConnection();
          pstm=conn.prepareStatement("DELETE FROM COURSE WHERE CID="+cid);
          pstm.execute();
        }catch(Exception e){
            Logger.getLogger(BaseJdbcService.class.getName()).log(Level.SEVERE, null, e);
        }finally{
             sqlCleanup(null, pstm, conn);
        }
    }

	public Byte getCourseByName(String desc) {
        Connection conn=null;
        PreparedStatement pstm=null;
        ResultSet rs=null;
        try{
          conn=getDbConnection();
          String query="SELECT * FROM COURSE WHERE DESCRIPTION=?";
          pstm=conn.prepareStatement(query);
          pstm.setString(1, desc);
          rs=pstm.executeQuery();
          if(rs.next()){
        	 return rs.getByte("CID");
          }
        }catch(Exception e){
            Logger.getLogger(BaseJdbcService.class.getName()).log(Level.SEVERE, null, e);
        }finally{
             sqlCleanup(rs, pstm, conn);
        }
        return null;
    }
    
}

