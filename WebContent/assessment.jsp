<%@page import="com.psedb.model.Assessment"%>
<%@page import="com.psedb.model.Enrollment"%>
<%@page import="com.psedb.ejb.StudentEjbBean"%>
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

	Context context = new InitialContext();
	StudentEjbBean studentEjbBean = (StudentEjbBean) context.lookup("java:module/StudentEjbBean");
	String eid=request.getParameter("eid");
	Enrollment enrollment=studentEjbBean.getCourse(Byte.valueOf(eid));
  	String ccid= request.getParameter("ccid");
  	Assessment assessment=studentEjbBean.getAssesment(Integer.valueOf(eid));
%>

<body>
	<div class="col-md-12">
		<span style="float: left"><font style="size: 7px">User :
				<%=user.getUserName()%></font></span> <input class="backbutton"
			style="float: right" type="button" value="Back"
			onclick="window.history.back()">
	</div>

	<div class="col-md-8 col-md-offset-2">
		<h3>Assessment</h3>
		<h4>Student  :<%=enrollment.getStudent().getFname()  %> </h4>
		<h4>Course   :<%=enrollment.getCourse().getDescription()  %> </h4>
		<h4>Semester :<%=enrollment.getSemester()  %> </h4>
	
		<form action="assessment" method="post">
			<div class="InputDiv" style="margin-bottom: 30px; text-align: center">
					<input type="text" name="sid" value="<%=enrollment.getStudent().getStudentId()%>" readonly="true" style="display: none;"/>
					<input type="text" name="eid" value="<%=eid%>" readonly="true" style="display: none;"/>
					<input type="text" name="ccid" value="<%=ccid%>" readonly="true" style="display: none;"/>
					<%if(assessment!=null){%>
						<input type="text" name="aid" value="<%=assessment.getAid()%>" readonly="true" style="display: none;"/>
					<%} %>
				<table>
					<tbody>
						<tr>
							<td><span class="labelClass">A1</span></td>
							<td>
								<%
									if (assessment != null) {
								%> 
									<input type="text" name="a1" value="<%=assessment.getA1()%>" /> 
								<% } else { %> 
									<input type="text" name="a1"  /> 
								<% } %>
							</td>
						</tr>
						
						<tr>
							<td><span class="labelClass">A2</span></td>
							<td>
								<%
									if (assessment != null) {
								%> 
									<input type="text" name="a2" value="<%=assessment.getA2()%>" /> 
								<% } else { %> 
									<input type="text" name="a2"  /> 
								<% } %>
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