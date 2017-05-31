
package com.psedb.ejb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.psedb.model.Assessment;
import com.psedb.model.Course;
import com.psedb.model.CourseConduction;
import com.psedb.model.Enrollment;
import com.psedb.model.LuDegreeType;
import com.psedb.model.LuThesisStatus;
import com.psedb.model.Staff;
import com.psedb.model.Student;
import com.psedb.model.StudentComment;
import com.psedb.model.StudentDegree;
import com.psedb.model.UserBean;
import com.psedb.service.BaseJdbcService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Stateless
public class StudentEjbBean extends BaseJdbcService {
    
	@Inject
	CourseEjbBean courseEjbBean;
	
	@Inject
	StaffEjbBean staffEjbBean;
	
	
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
            System.err.println(this.getClass().getName()+"    :   "+ e.getMessage());
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
            System.err.println(this.getClass().getName()+"    :   "+ e.getMessage());
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
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }finally{
             sqlCleanup(rs, pstm, conn);
        }
        
        return studentId;
    }
    
    public ArrayList<Student> getStudentList(Byte staffId){
        Connection conn=null;
        ResultSet rs=null;
        PreparedStatement pstm=null;
        ArrayList<Student> studentList=new ArrayList<>(0);
        Student student=null;
       // Course course=new Course();
        String query="SELECT s.SID,s.FNAME, s.SURNAME,s.EMAIL  FROM STUDENT s left join  enrollment e on s.SID=e.SID left"
+" join course c on e.CID=c.CID;";
        /*if(staffId!=null && staffId>0){
            query="SELECT DISTINCT S.SID,FNAME, S.SURNAME,S.EMAIL  "
                    + "FROM STUDENT S "
                    + "INNER JOIN STUDENT_DEGREE SD ON SD.STUDENT_ID=S.STUDENT_ID "
                    + "INNER JOIN STUDENT_SUPERVISOR SS ON SS.STUDENT_DEGREE_ID=SD.STUDENT_DEGREE_ID WHERE SS.STAFF_ID="+staffId;
        }*/
        try{
          conn=getDbConnection();
          pstm=conn.prepareStatement(query);
          rs=pstm.executeQuery();
          while(rs.next()){
              student=new Student();
              student.setStudentId(rs.getInt("SID"));
              student.setFname(rs.getString("FNAME"));
              student.setSurname(rs.getString("SURNAME"));
              student.setStudentEmail(rs.getString("EMAIL"));
              //student.setAccountLock(rs.getBoolean("ACCOUNT_LOCK"));
            /*  if(rs.getString("title")!=null){
            	  course=new Course();
            	  
              }*/
              
              
              studentList.add(student);
          }
        }catch(Exception e){
            System.err.println(this.getClass().getName()+"    :   "+ e.getMessage());
        }finally{
             sqlCleanup(rs, pstm, conn);
        }
        
        return studentList;
    }
    
    public ArrayList<Student> getEnrollStudentList(CourseConduction courseConduction){
        Connection conn=null;
        ResultSet rs=null;
        PreparedStatement pstm=null;
        ArrayList<Student> studentList=new ArrayList<>(0);
        Student student=null;
        String query="SELECT S.*,E.EID FROM STUDENT S "
        		   + "INNER JOIN ENROLLMENT E USING(SID) "
        		   + "WHERE E.CID="+courseConduction.getCourse().getCid()+" AND SEMESTER='"+courseConduction.getSemester()+"'";
        try{
          conn=getDbConnection();
          pstm=conn.prepareStatement(query);
          rs=pstm.executeQuery();
          while(rs.next()){
              student=new Student();
              student.setStudentId(rs.getInt("SID"));
              student.setFname(rs.getString("FNAME"));
              student.setSurname(rs.getString("SURNAME"));
              student.setStudentEmail(rs.getString("EMAIL"));
              student.setEid(rs.getInt("EID"));
              studentList.add(student);
          }
        }catch(Exception e){
            System.err.println(this.getClass().getName()+"    :   "+ e.getMessage());
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
              //student.setAccountLock(rs.getBoolean("ACCOUNT_LOCK"));
              student.setPasswd(rs.getString("PASSWD"));
          }
        }catch(Exception e){
            System.err.println(this.getClass().getName()+"    :   "+ e.getMessage());
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
            System.err.println(this.getClass().getName()+"    :   "+ e.getMessage());
        }finally{
             sqlCleanup(rs, pstm, conn);
        }
    }
    

    public void updateStudent(Student student, Integer studentId) {
        Connection conn=null;
        ResultSet rs=null;
        PreparedStatement pstm=null;
        String studentQuery="UPDATE STUDENT SET FNAME=?,SURNAME=?,EMAIL=?,PASSWD=? WHERE SID=?";
        try{
          conn=getDbConnection();
          pstm=conn.prepareStatement(studentQuery);
          pstm.setString(1, student.getFname());
          pstm.setString(2, student.getSurname());
          pstm.setString(3, student.getStudentEmail());
          pstm.setString(4, student.getPasswd());
          //pstm.setBoolean(5, student.getAccountLock());
          pstm.setInt(5, studentId);
          pstm.execute();
        }catch(Exception e){
            System.err.println(this.getClass().getName()+"    :   "+ e.getMessage());
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
            System.err.println(this.getClass().getName()+"    :   "+ e.getMessage());
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
            System.err.println(this.getClass().getName()+"    :   "+ e.getMessage());
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
            System.err.println(this.getClass().getName()+"    :   "+ e.getMessage());
        }finally{
             sqlCleanup(rs, pstm, conn);
        }
    }
    
    /*public ArrayList<Course> getStudentCourseList(Integer studentId){
    	ArrayList<Course> studentCourseList=new ArrayList<>();
    	Connection conn=null;
        ResultSet rs=null;
        PreparedStatement pstm=null;
        Course course;
        String query="select e.CID, title,semester from courses c, enrollment e where e.CID=c.CID and e.SID=? ";
        try{
          conn=getDbConnection();
          pstm=conn.prepareStatement(query);
          pstm.setInt(1, studentId);
          rs=pstm.executeQuery();
          while(rs.next()){
             course=new Course();
             course.setCid(Byte.parseByte(rs.getString("CID")));
             course.setDescription(rs.getString("title"));
             course.setSemester(rs.getString("semester"));
             studentCourseList.add(course);
          }
        }catch(Exception e){
            System.err.println(this.getClass().getName()+"    :   "+ e.getMessage());
        }finally{
             sqlCleanup(rs, pstm, conn);
        }
    	
    	
    	return studentCourseList;
    }*/
    
    public List<Enrollment> getStudentCourseList(Integer studentId) {
        Connection conn=null;
        PreparedStatement pstm=null;
        ResultSet rs=null;
        List<Enrollment> studentCourseList=new ArrayList<>(0);
        Staff staff;
        try{
          conn=getDbConnection();
          String query="select e.eid,e.CID, description,cc.semester,t.fname as name,t.email from course c, enrollment e,"
          		+ "course_conduction cc,teacher t where e.CID=c.CID and c.CID=cc.CID and cc.TID = t.TID and "
          		+ "e.semester=cc.semester and e.SID=?";
          pstm=conn.prepareStatement(query);
          pstm.setInt(1, studentId);
          rs=pstm.executeQuery();
          while(rs.next()){
        	  Enrollment enrollment=new Enrollment();
        	  enrollment.setEid(rs.getInt("eid"));
        	  enrollment.setSemester(rs.getString("SEMESTER"));
        	  enrollment.setCourse(new Course(rs.getByte("CID"), rs.getString("DESCRIPTION")));
        	  enrollment.setAssessment(getAssesment(rs.getInt("eid")));
        	  staff=new Staff();
        	  staff.setLoginname(rs.getString("email"));
        	  staff.setFname(rs.getString("name"));
        	  enrollment.setStaff(staff);
        	  studentCourseList.add(enrollment);
          }
        }catch(Exception e){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }finally{
             sqlCleanup(rs, pstm, conn);
        }
        
        return studentCourseList;
    }
    
    public Enrollment getCourse(Byte eid) {
        Connection conn=null;
        PreparedStatement pstm=null;
        ResultSet rs=null;
        Integer studentId=null;
        Byte courseId=null;
        Enrollment enrollment=new Enrollment();
        try{
          conn=getDbConnection();
          String query="SELECT EID,SID,CID,SEMESTER FROM ENROLLMENT WHERE EID=?";
          pstm=conn.prepareStatement(query);
          pstm.setByte(1, eid);
          rs=pstm.executeQuery();
          while(rs.next()){
        	  enrollment.setEid(rs.getInt("eid"));
        	  enrollment.setSemester(rs.getString("SEMESTER"));
        	  studentId=rs.getInt("sid");
        	  courseId=rs.getByte("CID");
          }
        }catch(Exception e){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }finally{
             sqlCleanup(rs, pstm, conn);
        }
        enrollment.setStudent(getStudentInfo(studentId));
        enrollment.setCourse(courseEjbBean.get(courseId));
        return enrollment;
    }
    
    public void saveCourse(String sid, String cid, String semester) {
        Connection conn=null;
        PreparedStatement pstm=null;
        try{
          conn=getDbConnection();
          pstm=conn.prepareStatement("	INSERT INTO enrollment(sid, CID, SEMESTER)VALUES(?,?,?)");
          pstm.setByte(1, Byte.valueOf(sid));
          pstm.setByte(2, Byte.valueOf(cid));
          pstm.setString(3, semester);
          pstm.execute();
        }catch(Exception e){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }finally{
             sqlCleanup(null, pstm, conn);
        }
    }

	public void updateCourse(String eid, String sid, String cid, String semester) {
        Connection conn=null;
        PreparedStatement pstm=null;
        try{
          conn=getDbConnection();
          pstm=conn.prepareStatement("UPDATE  enrollment SET sid=?,CID=?,SEMESTER=? WHERE eid="+eid);
          pstm.setByte(1, Byte.valueOf(sid));
          pstm.setByte(2, Byte.valueOf(cid));
          pstm.setString(3, semester);
          pstm.execute();
        }catch(Exception e){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }finally{
             sqlCleanup(null, pstm, conn);
        }
    }
	
	public void deleteStudentEnrollment(Byte eid) {
        Connection conn=null;
        PreparedStatement pstm=null;
        try{
          conn=getDbConnection();
          pstm=conn.prepareStatement("DELETE FROM enrollment WHERE eid="+eid);
          pstm.execute();
        }catch(Exception e){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }finally{
             sqlCleanup(null, pstm, conn);
        }
    }

	public void saveAssessment(String eid, String a1, String a2, String sid) {
        Connection conn=null;
        PreparedStatement pstm=null;
        try{
          conn=getDbConnection();
          pstm=conn.prepareStatement("INSERT INTO ASSESSMENT(SID, EID, A1, A2)VALUES(?,?,?,?)");
          pstm.setInt(1, Integer.valueOf(sid));
          pstm.setInt(2, Integer.valueOf(eid));
          pstm.setInt(3, Integer.valueOf(a1));
          pstm.setInt(4, Integer.valueOf(a2));
          pstm.execute();
        }catch(Exception e){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }finally{
             sqlCleanup(null, pstm, conn);
        }
    }

	public Assessment getAssesment(Integer eid) {
        Connection conn=null;
        PreparedStatement pstm=null;
        ResultSet rs=null;
        Integer studentId=null;
        Byte enrollmentId=null;
        Assessment assessment=new Assessment();
        try{
          conn=getDbConnection();
          String query="SELECT AID, SID, EID, A1, A2 FROM ASSESSMENT WHERE EID=?";
          pstm=conn.prepareStatement(query);
          pstm.setInt(1, eid);
          rs=pstm.executeQuery();
          if(rs.next()){
        	  assessment.setAid(rs.getInt("AID"));
        	  assessment.setA1(rs.getInt("A1"));
        	  assessment.setA2(rs.getInt("A2"));
        	  studentId=rs.getInt("SID");
        	  enrollmentId=rs.getByte("EID");
          }else{
        	  return null;
          }
        }catch(Exception e){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }finally{
             sqlCleanup(rs, pstm, conn);
        }
        assessment.setStudent(getStudentInfo(studentId));
        assessment.setEnrollment(getCourse(enrollmentId));
        return assessment;
    }

	public void updateAssessment(String aid, String a1, String a2) {
        Connection conn=null;
        PreparedStatement pstm=null;
        try{
          conn=getDbConnection();
          pstm=conn.prepareStatement("UPDATE  ASSESSMENT SET A1=?,A2=? WHERE AID="+aid);
          pstm.setInt(1, Integer.valueOf(a1));
          pstm.setInt(2, Integer.valueOf(a2));
          pstm.execute();
        }catch(Exception e){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }finally{
             sqlCleanup(null, pstm, conn);
        }
    }
}
