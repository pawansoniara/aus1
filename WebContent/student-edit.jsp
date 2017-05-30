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
    $(function() {
        $("#dateThesisSubmit").datepicker();
        $("#datEnroll").datepicker();
        $("#datComplete").datepicker();
        $("#datThesisIntend").datepicker();
        $("#datConfirmIntend").datepicker();
        $("#datConfirmComplete").datepicker();
        
    });
    
function loadXMLDoc(){
    var xmlhttp;
    if (window.XMLHttpRequest){// code for IE7+, Firefox, Chrome, Opera, Safari
      xmlhttp=new XMLHttpRequest();
    }else{// code for IE6, IE5
      xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange= function (){
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200){
            if(xmlhttp.responseText==='true'){
                alert("email already exist!");
                document.getElementsByName("studentEmail")[0].value='';
            }
        }
    };
    xmlhttp.open("GET", "Student?studentEmail="+document.getElementsByName("studentEmail")[0].value+"&action=isExist", true);
    xmlhttp.send();
}
</script>
</head>
<%
Student student=null;
String on="";
String off="";
UserBean user=(UserBean)request.getSession().getAttribute("user"); 
if(request.getParameter("studentId")!=null){
   Integer studentId=Integer.valueOf(request.getParameter("studentId"));
   Context context = new InitialContext();
   StudentEjbBean studentEjbBean = (StudentEjbBean) context.lookup("java:module/StudentEjbBean");
   student=studentEjbBean.getStudentInfo(studentId);
   
}
%>
<body>

    <div class="col-md-12">
        <span style="float: left"><font style="size: 7px">User : <%=user.getUserName()%></font></span>
             <input class="backbutton"  style="float: right"   type="button" value="Back" onclick="window.history.back()">
    </div>

<div class="col-md-8 col-md-offset-2">
<h3>Student Detail</h3>	
	<div class="InputDiv">
            <form action="Student" method="post">
			<table>
			<tbody>
                            
                            <%if(student != null){%>
                                <tr>
                                    <td>
                                            <span class="labelClass">Student ID</span>
                                    </td>
                                    <td>
                                        <input type="text" name="studentId" value="<%=student.getStudentId()%>" readonly="true" />
                                    </td>
                                </tr>
                            <%}%>
			<tr>
				<td>
					<span class="labelClass">First Name</span>
				</td>
				<td>
                                    <input type="text" name="fname" value="<%=(student != null)?student.getFname():""%>" />
				</td>
			</tr>
			
			<tr>
				<td>
					<span class="labelClass">Surname</span>
				</td>
				<td>
					<input type="text" name="surname" value="<%=(student != null)?student.getSurname():""%>"/>
				</td>
			</tr>
			
			<tr>
				<td>
					<span class="labelClass">Email</span>
				</td>
				<td>
                                     <%if(student != null){%>
                                        <input type="text" name="studentEmail" readonly="true" value="<%=student.getStudentEmail()%>"/>
                                     <%}else{%>
                                        <input type="text" name="studentEmail" onblur="loadXMLDoc()"/>
                                    <%}%>
				</td>
			</tr>
			
			<tr>
				<td>
					<span class="labelClass">password</span>
				</td>
				<td>
					<input type="password" name="passwd" value="<%=(student != null)?student.getPasswd():""%>"/>
				</td>
			</tr>
			
			
			
			
			</tbody>
			</table>
			
			
                        <input type="submit" value="SUBMIT" onclick="if(document.getElementsByName('studentEmail')[0].value.trim()===''){alert('Please enter email');return false;}"  >
			<input type="reset" value="CLEAR" >
		</form>
	</div>
</div>
<div class="clearfix"></div>




</body>
</html>