<%@page import="com.psedb.model.Course"%>
<%@page import="com.psedb.util.Tokens"%>
<%@page import="com.psedb.model.Staff"%>
<%@page import="com.psedb.model.UserBean"%>
<%@ page import="javax.naming.InitialContext"%>
<%@ page import="javax.naming.Context"%>
<%@page import="com.psedb.ejb.CourseEjbBean"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/layout.css" rel="stylesheet" type="text/css" media="all">
<link href="css/bootstrap.css" rel="stylesheet" type="text/css"
	media="all" />
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
                alert("Course already exist!");
                document.getElementsByName("description")[0].value='';
            }
        }
    }
    xmlhttp.open("GET", "courses?action=isExist&description="+document.getElementsByName("description")[0].value, true);
    xmlhttp.send();
}
</script>
<% 
UserBean user=(UserBean)request.getSession().getAttribute("user");

Course course=null;
String admin="";
String sup="";
if(request.getParameter("cid")!=null){
	Context context = new InitialContext();
	CourseEjbBean courseEjbBean = (CourseEjbBean) context.lookup("java:module/CourseEjbBean");
	course=courseEjbBean.get(Byte.valueOf(request.getParameter("cid")));
  
}
%>

<body>
	<div class="col-md-12">
		<span style="float: left"><font style="size: 7px">User :
				<%=user.getUserName()%></font></span> <input class="backbutton"
			style="float: right" type="button" value="Back"
			onclick="window.history.back()">
	</div>

	<div class="col-md-8 col-md-offset-2">
		<h3>Course Detail</h3>
		<%if(course!=null){%>
		<div class="InputDiv" style="text-align: right;">
			<a href="course?cid=<%=course.getCid()%>&action=delete"> <input
				type="button" value="Delete"
				style="width: 20%; background-color: #ff0000">
			</a>
		</div>
		<%}%>
		<form action="courses" method="post">
			<div class="InputDiv" style="margin-bottom: 30px; text-align: center">

				<table>
					<tbody>

						<%if(course!=null){%>
						<tr>
							<td><span class="labelClass">Course ID</span></td>
							<td><input type="text" name="cid"
								value="<%=course.getCid()%>" readonly="true" /></td>
						</tr>
						<%}%>

						<tr>
							<td><span class="labelClass">Course</span></td>
							<td>
								<%if(course!=null){%> <input type="text" name="description"
								value="<%=course.getDescription() %>" /> <%}else{%> <input
								type="text" name="description" onblur="loadXMLDoc()" /> <%}%>
							</td>
						</tr>



					</tbody>
				</table>

				<input type="submit" value="SUBMIT"
					onclick="if(document.getElementsByName('description')[0].value.trim()===''){alert('Please enter Course Name');return false;}">
				<input type="reset" value="CLEAR">




			</div>
		</form>
	</div>
	<div class="clearfix"></div>




</body>
</html>