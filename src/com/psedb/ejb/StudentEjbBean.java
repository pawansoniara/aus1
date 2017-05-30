
package com.psedb.ejb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;

import com.psedb.model.LuDegreeType;
import com.psedb.model.LuThesisStatus;
import com.psedb.model.Student;
import com.psedb.model.StudentComment;
import com.psedb.model.StudentDegree;
import com.psedb.model.UserBean;
import com.psedb.service.BaseJdbcService;

import java.util.HashSet;
import java.util.Set;

@Stateless
public class StudentEjbBean extends BaseJdbcService {
    
    public ArrayList<LuDegreeType> getDegreeTypes(){
        Connection conn=null;
        ResultSet rs=null;
        PreparedStatement pstm=null;
        ArrayList<LuDegreeType> degreeTypes=new ArrayList<LuDegreeType>();
        LuDegreeType degreeType=null;
        try{
          conn=getDbConnection();
          pstm=conn.prepareStatement("SELECT degree_type_id, dtName  FROM lu_degree_type");
          rs=pstm.executeQuery();
          while(rs.next()){
              degreeType=new LuDegreeType();
              degreeType.setDegreeTypeId(rs.getByte("degree_type_id"));
              degreeType.setDtname(rs.getString("dtName"));
              degreeTypes.add(degreeType);
          }
        }catch(Exception e){
            System.err.println(BaseJdbcService.class.getName()+"    :   "+ e.getMessage());
        }finally{
             sqlCleanup(rs, pstm, conn);
        }
        
        return degreeTypes;
    }
    
    public void saveStudent(Student student){
        Connection conn=null;
        ResultSet rs=null;
        PreparedStatement pstm=null;
        String studentQuery="insert into student(fname,surname,email,passwd) "
                + "values(?,?,?,?);";
        try{
          conn=getDbConnection();
          pstm=conn.prepareStatement(studentQuery,Statement.RETURN_GENERATED_KEYS);
          pstm.setString(1, student.getFname());
          pstm.setString(2, student.getSurname());
          pstm.setString(3, student.getStudentEmail());
          pstm.setString(4, student.getPasswd());
          //pstm.setBoolean(5, student.getAccountLock());
          pstm.execute();
          pstm.close();
        }catch(Exception e){
            System.err.println(BaseJdbcService.class.getName()+"    :   "+ e.getMessage());
        }finally{
             sqlCleanup(rs, pstm, conn);
        }
        
    }
    
    public Integer getStudentId(String email) {
        Connection conn=null;
        PreparedStatement pstm=null;
        ResultSet rs=null;
        Integer studentId=null;
        try{
          conn=getDbConnection();
          String query="SELECT SID FROM STUDENT WHERE EMAIL=?";
          pstm=conn.prepareStatement(query);
          pstm.setString(1, email);
          rs=pstm.executeQuery();
          while(rs.next()){
             studentId=rs.getInt("SID");
          }
        }catch(Exception e){
            Logger.getLogger(BaseJdbcService.class.getName()).log(Level.SEVERE, null, e);
        }finally{
             sqlCleanup(rs, pstm, conn);
        }
        
        return studentId;
    }
    
    public ArrayList<Student> getStudentList(Integer staffId){
        Connection conn=null;
        ResultSet rs=null;
        PreparedStatement pstm=null;
        ArrayList<Student> studentList=new ArrayList<>(0);
        Student student=null;
        String query="SELECT SID,FNAME, SURNAME,EMAIL  FROM STUDENT";
        if(staffId!=null && staffId>0){
            query="SELECT DISTINCT S.SID,FNAME, S.SURNAME,S.EMAIL  "
                    + "FROM STUDENT S "
                    + "INNER JOIN STUDENT_DEGREE SD ON SD.STUDENT_ID=S.STUDENT_ID "
                    + "INNER JOIN STUDENT_SUPERVISOR SS ON SS.STUDENT_DEGREE_ID=SD.STUDENT_DEGREE_ID WHERE SS.STAFF_ID="+staffId;
        }
        try{
          conn=getDbConnection();
          pstm=conn.prepareStatement(query);
          rs=pstm.executeQuery();
          while(rs.next()){
              student=new Student();
              student.setStudentId(rs.getInt("STUDENT_ID"));
              student.setFname(rs.getString("FNAME"));
              student.setSurname(rs.getString("SURNAME"));
              student.setStudentEmail(rs.getString("STUDENT_EMAIL"));
              student.setAccountLock(rs.getBoolean("ACCOUNT_LOCK"));
              studentList.add(student);
          }
        }catch(Exception e){
            System.err.println(BaseJdbcService.class.getName()+"    :   "+ e.getMessage());
        }finally{
             sqlCleanup(rs, pstm, conn);
        }
        
        return studentList;
    }
    
    public Student getStudentInfo(Integer studentId){
        Student student=new Student();
        Connection conn=null;
        ResultSet rs=null;
        PreparedStatement pstm=null;
        String query="SELECT SID,FNAME,SURNAME,EMAIL,PASSWD FROM STUDENT S WHERE S.SID=?";
        try{
          conn=getDbConnection();
          pstm=conn.prepareStatement(query);
          pstm.setInt(1, studentId);
          rs=pstm.executeQuery();
          while(rs.next()){
              student.setStudentId(rs.getInt("SID"));
              student.setFname(rs.getString("FNAME"));
              student.setSurname(rs.getString("SURNAME"));
              student.setStudentEmail(rs.getString("EMAIL"));
              student.setAccountLock(rs.getBoolean("ACCOUNT_LOCK"));
              student.setPasswd(rs.getString("PASSWD"));
          }
        }catch(Exception e){
            System.err.println(BaseJdbcService.class.getName()+"    :   "+ e.getMessage());
        }finally{
             sqlCleanup(rs, pstm, conn);
        }
        
        return student;
    }
    
    public void saveStudentDegree(StudentDegree studentDegree,Integer staffId,UserBean user,String comment){
        Connection conn=null;
        ResultSet rs=null;
        PreparedStatement pstm=null;
        String studentDegreeQuery="INSERT INTO STUDENT_DEGREE(STUDENT_ID,DATE_ENROLLED,DATE_COMPLETED,SCHOLARSHIP,"
                + "DATE_THESIS_INTEND_SUBMIT,DATE_THESIS_SUBMIT,THESIS_TITLE,DEGREE_TYPE_ID,DATE_CONFIRMATION_INTENDED,"
                + "DATE_CONFIRMATION_COMPLETED,THESIS_STATUS_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        String studentSupervisorQuery="INSERT INTO STUDENT_SUPERVISOR (STAFF_ID, STUDENT_DEGREE_ID, SUPERVISOR_TYPE) VALUES(?,?,?);";
        String studentCommentQuery="insert into  student_comment(staff_id,student_degree_id,cdate,acomment) "
                + "values(?,?,now(),?);";
        try{
          conn=getDbConnection();
          pstm=conn.prepareStatement(studentDegreeQuery,Statement.RETURN_GENERATED_KEYS);
          pstm.setInt(1, studentDegree.getStudent().getStudentId());
          pstm.setDate(2,new java.sql.Date(studentDegree.getDateEnrolled().getTime()));
          pstm.setDate(3, new java.sql.Date(studentDegree.getDateCompleted().getTime()));
          pstm.setString(4, studentDegree.getScholarship());
          pstm.setDate(5, new java.sql.Date(studentDegree.getDateThesisIntendSubmit().getTime()));
          pstm.setDate(6, new java.sql.Date(studentDegree.getDateThesisSubmit().getTime()));
          pstm.setString(7, studentDegree.getThesisTitle());
          pstm.setByte(8, studentDegree.getLuDegreeType().getDegreeTypeId());
          pstm.setDate(9, new java.sql.Date(studentDegree.getDateConfirmationIntended().getTime()));
          pstm.setDate(10, new java.sql.Date(studentDegree.getDateConfirmationCompleted().getTime()));
          pstm.setByte(11, studentDegree.getLuThesisStatus().getThesisStatusId());
          pstm.execute();
          rs=pstm.getGeneratedKeys();
          Integer studentDegreeId=0;
          while(rs.next()){
              studentDegreeId=rs.getInt(1);
          }
          rs.close();
          pstm.close();
          pstm=conn.prepareStatement(studentSupervisorQuery);
          pstm.setInt(1, staffId);
          pstm.setInt(2,  studentDegreeId);
          pstm.setString(3, "P");
          pstm.execute();
          pstm.close();
          pstm=conn.prepareStatement(studentCommentQuery);
          pstm.setInt(1, getStaffId(user.getLoginName()));
          pstm.setInt(2,  studentDegreeId);
          pstm.setString(3, comment);
          pstm.execute();
        }catch(Exception e){
            System.err.println(BaseJdbcService.class.getName()+"    :   "+ e.getMessage());
        }finally{
             sqlCleanup(rs, pstm, conn);
        }
    }
    
    public ArrayList<StudentDegree> getStudentDegreeList(Integer studentId,Integer staffId){
        StudentDegree studentDegree;
        Student student;
            Connection conn=null;
        ResultSet rs=null;
        PreparedStatement pstm=null;
        ArrayList<StudentDegree> studentDegreeList=new ArrayList<StudentDegree>();
        LuDegreeType luDegreeType;
        LuThesisStatus status;
        StudentComment studentComment;
        Set<StudentComment> comments;
        String query="SELECT DISTINCT SD.STUDENT_ID,FNAME,SURNAME,STUDENT_EMAIL,ACCOUNT_LOCK,PASSWD,DATE_ENROLLED,DATE_COMPLETED,"
                    + "SCHOLARSHIP,DATE_THESIS_INTEND_SUBMIT,DATE_THESIS_SUBMIT,THESIS_TITLE,LDT.DEGREE_TYPE_ID,"
                    + "DATE_CONFIRMATION_INTENDED,DATE_CONFIRMATION_COMPLETED,SD.THESIS_STATUS_ID,LDT.DTNAME,LTS.STATUS,"
                    + "SC.CDATE,SC.ACOMMENT,SD.STUDENT_DEGREE_ID  "
                    + "FROM STUDENT_DEGREE SD "
                    + "INNER JOIN STUDENT S ON  SD.STUDENT_ID=S.STUDENT_ID "
                    + "INNER JOIN LU_DEGREE_TYPE LDT ON LDT.DEGREE_TYPE_ID=SD.DEGREE_TYPE_ID "
                    + "INNER JOIN LU_THESIS_STATUS LTS ON SD.THESIS_STATUS_ID=LTS.THESIS_STATUS_ID "
                    + "LEFT JOIN STUDENT_COMMENT SC ON SD.STUDENT_DEGREE_ID=SC.STUDENT_DEGREE_ID "
                    + "INNER JOIN STUDENT_SUPERVISOR SS ON SS.STUDENT_DEGREE_ID=SD.STUDENT_DEGREE_ID ";
        if(studentId!=null && studentId>0){
           query+= " WHERE  SD.STUDENT_ID="+studentId;
        }
        
        if(staffId!=null && staffId>0){
           query+= " AND SS.STAFF_ID="+staffId;
        }
        try{
          conn=getDbConnection();
          pstm=conn.prepareStatement(query);
          rs=pstm.executeQuery();
          while(rs.next()){
              luDegreeType=new LuDegreeType();
              status=new LuThesisStatus();
              student=new Student();
              student.setFname(rs.getString("fname"));
              student.setSurname(rs.getString("surname"));
              student.setStudentEmail(rs.getString("student_email"));
              student.setAccountLock(rs.getBoolean("account_lock"));
              luDegreeType.setDtname(rs.getString("dtname"));
              
              studentDegree=new StudentDegree();
              studentDegree.setStudent(student);
              studentDegree.setStudentDegreeId(rs.getInt("student_degree_id"));
              studentDegree.setDateCompleted(rs.getDate("date_completed"));
              studentDegree.setDateConfirmationCompleted(rs.getDate("date_confirmation_completed"));
              studentDegree.setDateConfirmationIntended(rs.getDate("date_confirmation_intended"));
              studentDegree.setDateEnrolled(rs.getDate("date_enrolled"));
              studentDegree.setDateThesisIntendSubmit(rs.getDate("date_thesis_intend_submit"));
              studentDegree.setDateThesisSubmit(rs.getDate("date_thesis_submit"));
              studentDegree.setScholarship(rs.getString("scholarship"));
              studentDegree.setThesisTitle(rs.getString("thesis_title"));
              studentDegree.setLuDegreeType(luDegreeType);
              status.setStatus(rs.getString("status"));
              studentDegree.setLuThesisStatus(status);
              studentComment=new StudentComment();
              studentComment.setAcomment(rs.getString("acomment"));
              studentComment.setCdate(rs.getDate("cdate"));
              comments=new HashSet<StudentComment>();
              comments.add(studentComment);
              studentDegree.setStudentComments(comments);
              studentDegreeList.add(studentDegree);
          }
        }catch(Exception e){
            System.err.println(BaseJdbcService.class.getName()+"    :   "+ e.getMessage());
        }finally{
             sqlCleanup(rs, pstm, conn);
        }
        
        return studentDegreeList;
    }

    public void updateStudent(Student student, Integer studentId) {
        Connection conn=null;
        ResultSet rs=null;
        PreparedStatement pstm=null;
        String studentQuery="UPDATE STUDENT SET FNAME=?,SURNAME=?,EMAIL=?,PASSWD=?,ACCOUNT_LOCK=? WHERE SID=?";
        try{
          conn=getDbConnection();
          pstm=conn.prepareStatement(studentQuery);
          pstm.setString(1, student.getFname());
          pstm.setString(2, student.getSurname());
          pstm.setString(3, student.getStudentEmail());
          pstm.setString(4, student.getPasswd());
          pstm.setBoolean(5, student.getAccountLock());
          pstm.setInt(6, studentId);
          pstm.execute();
        }catch(Exception e){
            System.err.println(BaseJdbcService.class.getName()+"    :   "+ e.getMessage());
        }finally{
             sqlCleanup(rs, pstm, conn);
        }
        
        
    }
    
    public Integer getStaffId(String loginName){
        Connection conn=null;
        ResultSet rs=null;
        PreparedStatement pstm=null;
        String query="SELECT TID FROM teacher where email='"+loginName+"';";
        try{
          conn=getDbConnection();
          pstm=conn.prepareStatement(query);
          rs=pstm.executeQuery();
          while(rs.next()){
              return rs.getInt("staff_id");
          }
        }catch(Exception e){
            System.err.println(BaseJdbcService.class.getName()+"    :   "+ e.getMessage());
        }finally{
             sqlCleanup(rs, pstm, conn);
        }
        
        return 0;
    }
    
    public StudentDegree getdegreeInfo(Integer studentDegreeId){
        Connection conn=null;
        ResultSet rs=null;
        PreparedStatement pstm=null;
        StudentDegree studentDegree=new StudentDegree();
         LuDegreeType luDegreeType;
        LuThesisStatus status;
        StudentComment studentComment;
        Set<StudentComment> comments;
        Student student=new Student();
        String query="SELECT SS.STAFF_ID,SD.STUDENT_ID,FNAME,SURNAME,DATE_ENROLLED,DATE_COMPLETED,SCHOLARSHIP,"
                    + "DATE_THESIS_INTEND_SUBMIT,DATE_THESIS_SUBMIT,THESIS_TITLE,LDT.DEGREE_TYPE_ID,DATE_CONFIRMATION_INTENDED,"
                    + "DATE_CONFIRMATION_COMPLETED,SD.THESIS_STATUS_ID,LDT.DTNAME,LTS.STATUS,SC.CDATE,SC.ACOMMENT  "
                    + "FROM STUDENT_DEGREE SD "
                    + "INNER JOIN STUDENT S ON SD.STUDENT_ID=S.STUDENT_ID "
                    + "LEFT JOIN LU_DEGREE_TYPE LDT ON LDT.DEGREE_TYPE_ID=SD.DEGREE_TYPE_ID "
                    + "LEFT JOIN LU_THESIS_STATUS LTS ON  SD.THESIS_STATUS_ID=LTS.THESIS_STATUS_ID "
                    + "LEFT JOIN STUDENT_COMMENT SC ON SD.STUDENT_DEGREE_ID=SC.STUDENT_DEGREE_ID "
                    + "LEFT JOIN STUDENT_SUPERVISOR SS ON SD.STUDENT_DEGREE_ID=SS.STUDENT_DEGREE_ID "
                    + "WHERE SD.STUDENT_DEGREE_ID="+studentDegreeId;
        try{
          conn=getDbConnection();
          pstm=conn.prepareStatement(query);
          rs=pstm.executeQuery();
          while(rs.next()){
              luDegreeType=new LuDegreeType();
              status=new LuThesisStatus();
              student=new Student();
              student.setStudentId(rs.getInt("student_id"));
              student.setFname(rs.getString("fname"));
              student.setSurname(rs.getString("surname"));
              luDegreeType.setDtname(rs.getString("dtname"));
              luDegreeType.setDegreeTypeId(rs.getByte("degree_type_id"));
              
              studentDegree=new StudentDegree();
              studentDegree.setStudent(student);
              studentDegree.setStudentDegreeId(studentDegreeId);
              studentDegree.setDateCompleted(rs.getDate("date_completed"));
              studentDegree.setDateConfirmationCompleted(rs.getDate("date_confirmation_completed"));
              studentDegree.setDateConfirmationIntended(rs.getDate("date_confirmation_intended"));
              studentDegree.setDateEnrolled(rs.getDate("date_enrolled"));
              studentDegree.setDateThesisIntendSubmit(rs.getDate("date_thesis_intend_submit"));
              studentDegree.setDateThesisSubmit(rs.getDate("date_thesis_submit"));
              studentDegree.setScholarship(rs.getString("scholarship"));
              studentDegree.setThesisTitle(rs.getString("thesis_title"));
              studentDegree.setLuDegreeType(luDegreeType);
              status.setStatus(rs.getString("status"));
              status.setThesisStatusId(rs.getByte("thesis_status_id"));
              studentDegree.setLuThesisStatus(status);
              if(rs.getString("acomment")!=null){
                studentComment=new StudentComment();
                studentComment.setAcomment(rs.getString("acomment"));
                studentComment.setCdate(rs.getDate("cdate"));
                comments=new HashSet<StudentComment>();
                comments.add(studentComment);
                studentDegree.setStudentComments(comments);
              }
              studentDegree.setStaffId(rs.getByte("staff_id"));
          }
        }catch(Exception e){
            System.err.println(BaseJdbcService.class.getName()+"    :   "+ e.getMessage());
        }finally{
             sqlCleanup(rs, pstm, conn);
        }
        
        return studentDegree;
    }

    public void updateStudentDegree(StudentDegree studentDegree, Integer staffId, UserBean user, String comments, Integer studentDegreeId) {
        
        Connection conn=null;
        ResultSet rs=null;
        PreparedStatement pstm=null;
        String studentDegreeQuery="UPDATE STUDENT_DEGREE SET STUDENT_ID=?,DATE_ENROLLED=?,DATE_COMPLETED=?,SCHOLARSHIP=?,"
                + "DATE_THESIS_INTEND_SUBMIT=?,DATE_THESIS_SUBMIT=?,THESIS_TITLE=?,DEGREE_TYPE_ID=?,DATE_CONFIRMATION_INTENDED=?,"
                + "DATE_CONFIRMATION_COMPLETED=?,THESIS_STATUS_ID=? WHERE STUDENT_DEGREE_ID=?";
        
        String studentSupervisorQuery="UPDATE STUDENT_SUPERVISOR SET STAFF_ID=? WHERE STUDENT_DEGREE_ID=?";
        
        String selectComment="SELECT STUDENT_COMMENT_ID FROM STUDENT_COMMENT WHERE STUDENT_DEGREE_ID=?";
        String studentUpdateCommentQuery="UPDATE STUDENT_COMMENT SET STAFF_ID=?,CDATE=now(),ACOMMENT=? "
                                 + "WHERE STUDENT_COMMENT_ID=?";
        String studentInsertCommentQuery="INSERT INTO STUDENT_COMMENT (STAFF_ID,CDATE,ACOMMENT,STUDENT_DEGREE_ID) values(?,now(),?,?) ";
       
        Integer studentCommentId=null;
        try{
          conn=getDbConnection();
          pstm=conn.prepareStatement(studentDegreeQuery);
          pstm.setInt(1, studentDegree.getStudent().getStudentId());
          pstm.setDate(2,new java.sql.Date(studentDegree.getDateEnrolled().getTime()));
          pstm.setDate(3, new java.sql.Date(studentDegree.getDateCompleted().getTime()));
          pstm.setString(4, studentDegree.getScholarship());
          pstm.setDate(5, new java.sql.Date(studentDegree.getDateThesisIntendSubmit().getTime()));
          pstm.setDate(6, new java.sql.Date(studentDegree.getDateThesisSubmit().getTime()));
          pstm.setString(7, studentDegree.getThesisTitle());
          pstm.setByte(8, studentDegree.getLuDegreeType().getDegreeTypeId());
          pstm.setDate(9, new java.sql.Date(studentDegree.getDateConfirmationIntended().getTime()));
          pstm.setDate(10, new java.sql.Date(studentDegree.getDateConfirmationCompleted().getTime()));
          pstm.setByte(11, studentDegree.getLuThesisStatus().getThesisStatusId());
          pstm.setInt(12,studentDegreeId);
          pstm.execute();
          pstm.close();

          pstm=conn.prepareStatement(studentSupervisorQuery);
          pstm.setInt(1, staffId);
          pstm.setInt(2,  studentDegreeId);
          pstm.execute();
          pstm.close();
          
          pstm=conn.prepareStatement(selectComment);
          pstm.setInt(1, studentDegreeId);
          rs=pstm.executeQuery();
          if(rs.next()){
              studentCommentId=rs.getInt("STUDENT_COMMENT_ID");
          }
          pstm.close();
          
          if(studentCommentId!=null){
            pstm=conn.prepareStatement(studentUpdateCommentQuery);
            pstm.setInt(1, staffId);
            pstm.setString(2, comments);
            pstm.setInt(3,  studentCommentId);
            pstm.execute();
          }else{
            pstm=conn.prepareStatement(studentInsertCommentQuery);
            pstm.setInt(1, staffId);
            pstm.setString(2, comments);
            pstm.setInt(3,  studentDegreeId);
            pstm.execute();
          }
        }catch(Exception e){
            System.err.println(BaseJdbcService.class.getName()+"    :   "+ e.getMessage());
        }finally{
             sqlCleanup(rs, pstm, conn);
        }
    }
    
}