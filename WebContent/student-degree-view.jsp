<%@page import="com.psedb.model.UserBean" %>
<%@page import="com.psedb.ejb.StudentEjbBean" %>
<%@page import="com.psedb.model.StudentDegree" %>
<%@page import="com.psedb.model.StudentComment" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.naming.Context" %>

<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Postgraduate Student Progress Database Application.</title>
        <link href="css/layout.css" rel="stylesheet" type="text/css" media="all">
        <link href="css/bootstrap.css" rel="stylesheet" type="text/css" media="all" />
        <link href="css/table.css" rel="stylesheet" type="text/css" media="all" /> 


    </head>
    </head>
<% 

UserBean user=(UserBean)request.getSession().getAttribute("user"); 
Context context = new InitialContext();
StudentEjbBean studentEjbBean = (StudentEjbBean)context.lookup("java:module/StudentEjbBean");

%>
    
    <body>	
        <div class="col-md-12">
        <span style="float: left"><font style="size: 7px">User : <%=user.getUserName()%></font></span>
          <input class="backbutton"  style="float: right"   type="button" value="Back" onclick="window.history.back()">
    </div>
	<div class="col-md-12">
	<h3>Student Degree</h3>	
        <div class="InputDiv" style="text-align: right;" >
            <a href="student-degree-edit.jsp" >
                <input type="button" value="Add New" style="width: 20%">
            </a>
        </div>
        
        <div class="col-md-12">
            <table class="heavytables">
                <thead>
                    <tr>
                        <th>Student Name</th>
                        <th>Degree</th>
                        <th>Enrol Date</th>
                        <th>Date Completed</th>
                        <th>Scholarship</th>
                        <th>Thesis Title</th>
                        <th>Date Thesis Submit</th>
                        <th>Thesis Status</th>
                        <th>Comment</th>
                        <th>Comment Date</th>
                        
                    </tr>
                </thead>
                <tbody>
                    <%for(StudentDegree degree:studentEjbBean.getStudentDegreeList(0,0)){%>
                    <tr>
                        <td><%=degree.getStudent().getFname()%> <%=degree.getStudent().getSurname()%></td>
                        <td><a href="student-degree-edit.jsp?studentDegreeId=<%=degree.getStudentDegreeId()%>&action=edit"><%=degree.getLuDegreeType().getDtname()%></a></td>
                        <td><%=degree.getDateEnrolled()%></td>
                        <td><%=degree.getDateCompleted()%></td>
                        <td><%=degree.getScholarship()%></td>
                        <td><%=degree.getThesisTitle()%></td>
                        <td><%=degree.getDateThesisSubmit()%></td>
                        <td><%=degree.getLuThesisStatus().getStatus()%></td>
                        <%StudentComment comment=(StudentComment)degree.getStudentComments().iterator().next();%>
                        <td><%=(comment.getAcomment()!=null)?comment.getAcomment():"N/A"%></td>
                        <td><%=(comment.getCdate()!=null)?comment.getCdate():"N/A"%></td>
                    </tr> 
                    <%}%>
                </tbody>
            </table>
        </div>
        <div class="clearfix"></div>				
    </body>
</html>