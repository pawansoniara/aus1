<%@page import="com.psedb.ejb.CourseEjbBean"%>
<%@page import="com.psedb.model.Enrollment"%>
<%@page import="com.psedb.util.Tokens"%>
<%@page import="com.psedb.model.Student" %>
<%@page import="com.psedb.model.Course" %>
<%@page import="com.psedb.model.UserBean" %>
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

<script>

function loadXMLDoc(){
var xmlhttp;
if (window.XMLHttpRequest){// code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp=new XMLHttpRequest();
}else{// code for IE6, IE5
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
}
xmlhttp.onreadystatechange= function (){
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200){
            if(xmlhttp.responseText=='true'){
                alert("Username already exist!");
                document.getElementsByName("loginname")[0].value='';
            }
        }
    }
    xmlhttp.open("GET", "staff?loginname="+document.getElementsByName("loginname")[0].value, true);
    xmlhttp.send();
}
</script>
<% 
UserBean user=(UserBean)request.getSession().getAttribute("user");
Context context = new InitialContext();
StudentEjbBean studentEjbBean = (StudentEjbBean) context.lookup("java:module/StudentEjbBean");
CourseEjbBean courseEjbBean = (CourseEjbBean) context.lookup("java:module/CourseEjbBean");

Student student=null;
Enrollment enrollment=null;
if(request.getParameter("eid")!=null){
	Byte eid = Byte.valueOf(request.getParameter("eid"));
	enrollment=studentEjbBean.getCourse(eid);
	student=enrollment.getStudent();
}else{
	Integer sid = Integer.valueOf(request.getParameter("sid"));
	student = studentEjbBean.getStudentInfo(sid);
}
%>

<body>
 <div class="col-md-12">
        <span style="float: left"><font style="size: 7px">User : <%=user.getUserName()%></font></span>
                <input class="backbutton"  style="float: right"   type="button" value="Back" onclick="window.history.back()">
 </div>

<div class="col-md-8 col-md-offset-2">
<h3><%=student.getFname()+" "+student.getSurname() %> : Courses</h3>	
 <%if(enrollment!=null){%>
<div class="InputDiv" style="text-align: right;" >
            <a href="student-enrollment?eid=<%=enrollment.getEid()%>&action=delete">
                <input type="button" value="Delete" style="width: 20%;background-color: #ff0000 ">
            </a>
</div>
<%}%>
<form action="student-enrollment" method="post">
    <div class="InputDiv" style="margin-bottom: 30px;text-align: center">
           
            <input type="text" name="sid" value="<%=student.getStudentId()%>" readonly="true" style="display: none;"/>
            
            
			<table>
			<tbody>
			
                        <%if(enrollment!=null){%>
                        <tr>
				<td>
					<span class="labelClass">Enrollment Id</span>
				</td>
				<td>
                                    <input type="text" name="eid" value="<%=enrollment.getEid()%>" readonly="true"/>
				</td>
			</tr>
                        <%}%>
                        
                        <tr>
				<td>
					<span class="labelClass">Course</span>
				</td>
				<td>
				
						<select name="cid" id="flip-1" data-role="slider">
									<%
										for (Course course : courseEjbBean.getList()) {
									%>
											<option value="<%=course.getCid() %>" <%=(enrollment!=null && enrollment.getCourse().getCid().equals(course.getCid()))?"selected":""%>><%=course.getDescription()%></option>
									<%
										}
									%>
							</select></td>
					</tr>
			
			<tr>
				<td>
					<span class="labelClass">Semester</span>
				</td>
				<td>
                                     <%if(enrollment!=null){%>
                                       <input type="text" name="semester" value="<%=enrollment.getSemester()%>"/>
                                    <%}else{%>
                                       <input type="text" name="semester" />
                                    <%}%>
				</td>
			</tr>
			
			 
			</tbody>
			</table>
                                
                                <input type="submit" value="SUBMIT"  onclick="if(document.getElementsByName('loginname')[0].value.trim()===''){alert('Please enter username');return false;}" >
                                <input type="reset" value="CLEAR" >
			
			
			
		
	</div>
                                </form>
</div>
<div class="clearfix"></div>




</body>
</html>