<%@page import="com.psedb.ejb.CourseEjbBean"%>
<%@page import="com.psedb.model.CourseConduction"%>
<%@page import="com.psedb.util.Tokens"%>
<%@page import="com.psedb.model.Staff" %>
<%@page import="com.psedb.model.Course" %>
<%@page import="com.psedb.model.UserBean" %>
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
StaffEjbBean staffEjbBean = (StaffEjbBean) context.lookup("java:module/StaffEjbBean");
CourseEjbBean courseEjbBean = (CourseEjbBean) context.lookup("java:module/CourseEjbBean");

Staff teacher=null;
CourseConduction courseConduction=null;
if(request.getParameter("ccid")!=null){
	Byte ccid = Byte.valueOf(request.getParameter("ccid"));
	courseConduction=staffEjbBean.getCourse(ccid);
	teacher=courseConduction.getStaff();
}else{
	Byte tid = Byte.valueOf(request.getParameter("tid"));
	teacher = staffEjbBean.getStaff(tid);
}
%>

<body>
 <div class="col-md-12">
        <span style="float: left"><font style="size: 7px">User : <%=user.getUserName()%></font></span>
                <input class="backbutton"  style="float: right"   type="button" value="Back" onclick="window.history.back()">
 </div>

<div class="col-md-8 col-md-offset-2">
<h3><%=teacher.getFname()+" "+teacher.getSurname() %> : Courses</h3>	
 <%if(courseConduction!=null){%>
<div class="InputDiv" style="text-align: right;" >
            <a href="staff-course?ccid=<%=courseConduction.getCcid()%>&action=delete">
                <input type="button" value="Delete" style="width: 20%;background-color: #ff0000 ">
            </a>
</div>
<%}%>
<form action="staff-course" method="post">
    <div class="InputDiv" style="margin-bottom: 30px;text-align: center">
           
            <input type="text" name="tid" value="<%=teacher.getStaffId()%>" readonly="true" style="display: none;"/>
            
            
			<table>
			<tbody>
			
                        <%if(courseConduction!=null){%>
                        <tr>
				<td>
					<span class="labelClass">Course Conduction Id</span>
				</td>
				<td>
                                    <input type="text" name="ccid" value="<%=courseConduction.getCcid()%>" readonly="true"/>
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
											<option value="<%=course.getCid() %>" <%=(courseConduction!=null && courseConduction.getCourse().getCid().equals(course.getCid()))?"selected":""%>><%=course.getDescription()%></option>
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
                                     <%if(courseConduction!=null){%>
                                       <input type="text" name="semester" value="<%=courseConduction.getSemester()%>"/>
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