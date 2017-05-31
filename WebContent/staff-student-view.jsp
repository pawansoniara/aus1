<%@page import="com.psedb.model.CourseConduction"%>
<%@page import="com.psedb.ejb.StaffEjbBean"%>
<%@page import="com.psedb.model.UserBean" %>
<%@page import="com.psedb.model.Student" %>
<%@page import="com.psedb.ejb.StudentEjbBean" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.naming.Context" %>

<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/layout.css" rel="stylesheet" type="text/css" media="all">
<link href="css/bootstrap.css" rel="stylesheet" type="text/css" media="all" />
<link href="css/table.css" rel="stylesheet" type="text/css" media="all" /> 


</head>
<% UserBean user=(UserBean)request.getSession().getAttribute("user"); 
Context context = new InitialContext();
StudentEjbBean studentEjbBean = (StudentEjbBean) context.lookup("java:module/StudentEjbBean");
StaffEjbBean staffEjbBean = (StaffEjbBean) context.lookup("java:module/StaffEjbBean"); 
CourseConduction courseConduction=staffEjbBean.getCourse(Byte.valueOf(request.getParameter("ccid")));
%>
<body>		
   <div class="col-md-12">
        <span style="float: left"><font style="size: 7px">User : <%=user.getUserName()%></font></span>
           <input class="backbutton"  style="float: right"   type="button" value="Back" onclick="window.history.back()">
    </div>
	<div class="col-md-10 col-md-offset-1">
	
	<h3>Enrolled Students</h3>
	<h4>Course   :<%=courseConduction.getCourse().getDescription()  %> </h4>
	<h4>Semester  :<%=courseConduction.getSemester()  %> </h4>
		
        <div class="InputDiv" style="text-align: right;" >
            
        </div>
  <table class="heavytables">
      <thead>
        <tr>
          <th>First Name</th>
          <th>Surname</th>
          <th>Email</th>
          <th>Assessment</th>
        </tr>
      </thead>
      <tbody>
          <%for(Student student:studentEjbBean.getEnrollStudentList(courseConduction)){%>
        <tr>
            
          <td><%=student.getFname()%></td>
          <td><%=student.getSurname()%></td>
          <td><%=student.getStudentEmail()%></td>
          <td style="text-align: center;">
                    <a href="assessment.jsp?eid=<%=student.getEid()%>&ccid=<%=courseConduction.getCcid()%>">
                       <img  src="img/courses-icon.png" style="height: 30px">
                    </a>
                </td>
        </tr> 
        <%}%>
      </tbody>
    </table>
	</div>
	</div>
<div class="clearfix"></div>				
</body>
</html>