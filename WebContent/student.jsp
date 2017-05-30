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
Student student=null;
Context context = new InitialContext();
StudentEjbBean studentEjbBean = (StudentEjbBean) context.lookup("java:module/StudentEjbBean");
Integer studentId=0;
Integer staffId=0;
if(request.getSession().getAttribute("studentId")!=null){
   studentId=(Integer)request.getSession().getAttribute("studentId");
   student=studentEjbBean.getStudentInfo(studentId);
}else if(request.getParameter("studentId")!=null && request.getParameter("staffId")!=null){
   studentId=Integer.valueOf(request.getParameter("studentId"));
   staffId=Integer.valueOf(request.getParameter("staffId"));
   student=studentEjbBean.getStudentInfo(studentId);
}
%>
<body>
    <div class="col-md-12">
         <span style="float: left"><font style="size: 7px">User : <%=user.getUserName()%></font></span>
            
                <%if(request.getParameter("staffId")==null){%>
                    <a  href="index.jsp" style="float: right" >
                        <input class="backbutton"  type="button" value="(!)">
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
                        <th>Degree</th>
                        <th>Enrol Date</th>
                        <th>Date Completed</th>
                        <th>Scholarship</th>
                        <th>Thesis Title</th>
                        <th>Date Thesis Submit</th>
                        <th>Thesis Status</th>
                    </tr>
                </thead>
                <tbody>
                    <%for(StudentDegree degree:studentEjbBean.getStudentDegreeList(studentId,staffId)){%>
                    <tr>
                        <td><%=degree.getLuDegreeType().getDtname()%></td>
                        <td><%=degree.getDateEnrolled()%></td>
                        <td><%=degree.getDateCompleted()%></td>
                        <td><%=degree.getScholarship()%></td>
                        <td><%=degree.getThesisTitle()%></td>
                        <td><%=degree.getDateThesisSubmit()%></td>
                        <td><%=degree.getLuThesisStatus().getStatus()%></td>
                    </tr>
                   <%}%>
                </tbody>
            </table>
        </div>
<div class="clearfix"></div>




</body>
</html>