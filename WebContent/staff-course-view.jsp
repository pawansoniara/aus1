<%@page import="com.psedb.model.UserBean" %>
<%@page import="com.psedb.model.Staff" %>
<%@page import="com.psedb.ejb.StaffEjbBean" %>
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
<% 
    UserBean user=(UserBean)request.getSession().getAttribute("user"); 
	Context context = new InitialContext();
	StaffEjbBean staffEjbBean = (StaffEjbBean) context.lookup("java:module/StaffEjbBean");
	Byte tid = Byte.valueOf(request.getParameter("tid"));
	Staff teacher = staffEjbBean.getStaff(tid);
%>
<body>		
    <div class="col-md-12">
        <span style="float: left"><font style="size: 7px">User : <%=user.getUserName()%></font></span>
             <input class="backbutton"  style="float: right"   type="button" value="Back" onclick="window.history.back()">
    </div>
	<div class="col-md-10 col-md-offset-1">
	<h3><%=teacher.getFname()+" "+teacher.getSurname() %> : Courses</h3>	
        <div class="InputDiv" style="text-align: right;" >
            <a href="staff-course-edit.jsp?tid=<%=teacher.getStaffId()%>" >
                <input type="button" value="Add New" style="width: 20%">
            </a>
        </div>
  <table class="heavytables">
      <thead>
        <tr>
          <th>Course Id</th>
          <th>Course</th>
          <th>Semester</th>
        </tr>
      </thead>
      <tbody>
       
         <%for(Staff staff:staffEjbBean.getStaffList()){%> 
          <tr>
                <td><%=staff.getFname()%></td>
                <td><%=staff.getSurname()%></td>
                <td>
                    <a href="staff-edit.jsp?tid=<%=staff.getStaffId()%>&action=edit">
                        <%=staff.getLoginname()%>
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