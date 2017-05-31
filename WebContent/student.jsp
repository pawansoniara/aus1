<%@page import="com.psedb.model.Enrollment"%>
<%@page import="com.psedb.model.StudentComment"%>
<%@page import="com.psedb.model.StudentDegree"%>
<%@page import="com.psedb.model.UserBean"%>
<%@page import="com.psedb.model.LuDegreeType" %>
<%@page import="com.psedb.model.Student" %>
<%@page import="com.psedb.ejb.StudentEjbBean" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.naming.Context" %>
<!DOCTYPE html>
<html>
<head>
<link href="css/layout.css" rel="stylesheet" type="text/css" media="all">
<link href="css/bootstrap.css" rel="stylesheet" type="text/css" media="all" />
<link href="css/table.css" rel="stylesheet" type="text/css" media="all" />
<link rel="stylesheet" href="css/jquery-ui.css">
<script src="js/jquery-1.11.1.min.js"></script>
<script src="js/jquery-ui.js"></script>
<script>
    
</script>
</head>
<%

UserBean user=(UserBean)request.getSession().getAttribute("user"); 
Context context = new InitialContext();
StudentEjbBean studentEjbBean = (StudentEjbBean) context.lookup("java:module/StudentEjbBean");
Student student=studentEjbBean.getStudentInfo(Integer.valueOf(user.getId()));
%>
<body>
    <div class="col-md-12">
         <span style="float: left"><font style="size: 7px">User : <%=user.getUserName()%></font></span>
            
                <%if(request.getParameter("staffId")==null){%>
                    <a  href="index.jsp" style="float: right" >
                        <input class="backbutton"  type="button" value="Logout">
                    </a>
                <%}else{%>
                     <input class="backbutton"  style="float: right"   type="button" value="Back" onclick="window.history.back()">
                <%}%>
             
    </div>

<div class="col-md-8 col-md-offset-2">
<h3>Student Detail</h3>	
	<div class="InputDiv">
            <span class="labelClass">First Name</span>
            <input type="text" name="fname" readonly="true" value="<%=(student != null)?student.getFname():""%>" />
            
            <span class="labelClass">Surname</span>
            <input type="text" name="surname" readonly="true" value="<%=(student != null)?student.getSurname():""%>"/>
            
            <span class="labelClass">Email</span>
            <input type="text" name="studentEmail" readonly="true" value="<%=(student != null)?student.getStudentEmail():""%>"/>
				
	</div>
</div>
            
<div class="col-md-12">
            <table class="heavytables">
                <thead>
                    <tr>
                        <th>EID</th>
                        <th>Course</th>
                        <th>Semester</th>
                        <th>Assessment</th>
                        <th>Teacher Name</th>
                        <th>Email</th>
                    </tr>
                </thead>
                <tbody>
                    <%for(Enrollment enrollment:studentEjbBean.getStudentCourseList(student.getStudentId())){%>
                    <tr>
                        <td><%=enrollment.getEid()%></td>
                        <td><%=enrollment.getCourse().getDescription()%></td>
                        <td><%=enrollment.getSemester()%></td>
                        <td >
                        	<%if(enrollment.getAssessment()!=null){ %>
                        		<div style="width: 100%;text-align: center;">
                        			A1: <%=enrollment.getAssessment().getA1() %>
                        			<br>
                        			A2: <%=enrollment.getAssessment().getA2() %>
                        		
                        		</div>
                        	
                        	<%} %>
                        </td>
                         <td><%=enrollment.getStaff().getFname()%></td>
                        <td><%=enrollment.getStaff().getLoginname()%></td>
                    </tr>
                   <%}%>
                </tbody>
            </table>
        </div>
<div class="clearfix"></div>




</body>
</html>