<%@page import="com.psedb.util.Tokens"%>
<%@page import="com.psedb.model.Staff" %>
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

Staff staff=null;
String admin="";
String sup="";
if(request.getParameter("tid")!=null){
	Context context = new InitialContext();
	StaffEjbBean staffEjbBean = (StaffEjbBean) context.lookup("java:module/StaffEjbBean");
	Byte tid = Byte.valueOf(request.getParameter("tid"));
	staff = staffEjbBean.getStaff(tid);
	
   if(staff.getLuAccessLevel().getAccessId() == Tokens.ADMINISTRATOR){
       admin="selected";
   }else{
      sup="selected"; 
   }
}
%>

<body>
 <div class="col-md-12">
        <span style="float: left"><font style="size: 7px">User : <%=user.getUserName()%></font></span>
                <input class="backbutton"  style="float: right"   type="button" value="Back" onclick="window.history.back()">
 </div>

<div class="col-md-8 col-md-offset-2">
<h3>Staff Detail</h3>	
 <%if(staff!=null){%>
<div class="InputDiv" style="text-align: right;" >
            <a href="staff?staffId=<%=staff.getStaffId()%>&action=delete">
                <input type="button" value="Delete" style="width: 20%;background-color: #ff0000 ">
            </a>
</div>
<%}%>
<form action="staff" method="post">
    <div class="InputDiv" style="margin-bottom: 30px;text-align: center">
            
			<table>
			<tbody>
			
                        <%if(staff!=null){%>
                        <tr>
				<td>
					<span class="labelClass">Staff ID</span>
				</td>
				<td>
                                    <input type="text" name="staffId" value="<%=staff.getStaffId()%>" readonly="true"/>
				</td>
			</tr>
                        <%}%>
                        
                        <tr>
				<td>
					<span class="labelClass">First Name</span>
				</td>
				<td>
                                    <%if(staff!=null){%>
                                        <input type="text" name="fname" value="<%=staff.getFname()%>"/>
                                    <%}else{%>
                                        <input type="text" name="fname"/>
                                    <%}%>
				</td>
			</tr>
			
			<tr>
				<td>
					<span class="labelClass">Surname</span>
				</td>
				<td>
                                     <%if(staff!=null){%>
                                       <input type="text" name="surname" value="<%=staff.getSurname()%>"/>
                                    <%}else{%>
                                       <input type="text" name="surname" />
                                    <%}%>
				</td>
			</tr>
			
			<tr>
				<td>
					<span class="labelClass">Access Type</span>
				</td>
				<td>
					<select name="accessId" id="flip-1" data-role="slider">
                                            <option value="1" <%=admin%>>Administrator</option>
						<option value="2" <%=sup%>>Supervisor</option>
					</select> 
				</td>
			</tr>
			
                        <tr>
				<td>
					<span class="labelClass">Username/Email</span>
				</td>
				<td>
                                    <%if(staff!=null){%>
                                    <input type="text" name="loginname" readonly="true" value="<%=staff.getLoginname()%>"/>
                                    <%}else{%>
                                        <input type="text" name="loginname" onblur="loadXMLDoc()"/>
                                    <%}%>
				</td>
			</tr>
                        
			<tr>
				<td>
					<span class="labelClass">password</span>
				</td>
				<td>
					<input type="password" name="passwd" />
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