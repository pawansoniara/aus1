<!DOCTYPE html>
<%@page import="com.psedb.model.UserBean" %>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <link href="css/layout.css" rel="stylesheet" type="text/css" media="all">
        <link href="css/bootstrap.css" rel="stylesheet" type="text/css" media="all" />
        <link href="css/table.css" rel="stylesheet" type="text/css" media="all" /> 


    </head>
    <% UserBean user = (UserBean) request.getSession().getAttribute("user");%>
    <body>		
        <div class="col-md-12">
            <span style="float: left"><font style="size: 7px">User : <%=user.getUserName()%></font></span>
            <a  href="index.jsp" style="float: right" >
                    <input  class="backbutton"  type="button" value="Logout">
             </a>
        </div>
        <div class="col-md-4 col-md-offset-4">
            <div class="InputDiv" style="margin-top: 100px">
                <a href="staff-view.jsp">
                    <input type="button" value="Staff Section"  style="width: 100%"/>
                </a>
            </div>
        </div>
        
        <div class="col-md-4 col-md-offset-4">
            <div class="InputDiv" >
                 <a href="student-view.jsp">
                    <input type="button" value="Student Section"  style="width: 100%" >
                 </a>
            </div>
        </div>
        
        <div class="col-md-4 col-md-offset-4">
            <div class="InputDiv" >
                 <a href="courses-view.jsp">
                    <input type="button" value="Manage Courses"  style="width: 100%">
                 </a>
            </div>
        </div>
        
     
        <div class="clearfix"></div>				
    </body>
</html>