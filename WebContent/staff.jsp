<%@page import="com.psedb.ejb.StaffEjbBean"%>
<%@page import="com.psedb.model.CourseConduction"%>
<%@page import="com.psedb.model.UserBean" %>
<%@page import="com.psedb.model.Student" %>
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
StaffEjbBean staffEjbBean = (StaffEjbBean) context.lookup("java:module/StaffEjbBean"); 
Byte staffId=user.getId();

%>
<body>		
    <div class="col-md-12">
        <span style="float: left"><font style="size: 7px">User : <%=user.getUserName()%></font></span>
            <a   href="index.jsp" style="float: right" >
                    <input class="backbutton" type="button" value="(!)">
             </a>
    </div>
	<div class="col-md-10 col-md-offset-1">
	<h3>Course Conduction</h3>	
		  <table class="heavytables">
		      <thead>
		        <tr>
		          <th>Course Conduction Id</th>
		          <th>Course</th>
		          <th>Semester</th>
		        </tr>
		      </thead>
		      <tbody>
		       
		         <%for(CourseConduction courseConduction:staffEjbBean.getStaffCourseList(staffId)){%> 
		          <tr>
		                <td>
		                        <%=courseConduction.getCcid()%>
		                </td>
		                <td><%=courseConduction.getCourse().getDescription()%></td>
		                <td>
		                	<a href="staff-student-view.jsp?ccid=<%=courseConduction.getCcid()%>">
		                       <%=courseConduction.getSemester()%>
		                    </a>
		                	
		                
		                </td>
		        </tr> 
			<%}%>	
		      </tbody>
		    </table>
	</div>
<div class="clearfix"></div>				
</body>
</html>