<%@page import="com.psedb.ejb.StudentEjbBean" %>
<%@page import="com.psedb.ejb.StaffEjbBean" %>
<%@page import="com.psedb.model.LuDegreeType" %>
<%@page import="com.psedb.model.UserBean" %>
<%@page import="com.psedb.model.Student" %>
<%@page import="com.psedb.model.Staff" %>
<%@page import="com.psedb.model.StudentDegree" %>
<%@page import="com.psedb.model.StudentComment" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.naming.Context" %>

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
</script>


</head>
<%
UserBean user=(UserBean)request.getSession().getAttribute("user");
Context context = new InitialContext();
StudentEjbBean studentEjbBean = (StudentEjbBean) context.lookup("java:module/StudentEjbBean");
StaffEjbBean staffEjbBean = (StaffEjbBean)context.lookup("java:module/StaffEjbBean");

StudentDegree degree=null;
StudentComment comment=null;
Integer studentDegreeId=null;
String started="";String assigned="";String completed="";String submited="";String accepted="";
SimpleDateFormat dateFormate=new SimpleDateFormat("MM/dd/yyyy");
if(request.getParameter("studentDegreeId")!=null){
    studentDegreeId=Integer.parseInt(request.getParameter("studentDegreeId"));
    request.getSession().setAttribute("studentDegreeId",null);
    degree=studentEjbBean.getdegreeInfo(studentDegreeId);
    if(degree.getStudentComments()!=null && degree.getStudentComments().size()>0){
        comment=(StudentComment)degree.getStudentComments().iterator().next();
    }
    if(degree.getLuThesisStatus().getThesisStatusId()==1){
        started="selected";
    }else if(degree.getLuThesisStatus().getThesisStatusId()==2){
        assigned="selected";
    }else if(degree.getLuThesisStatus().getThesisStatusId()==3){
        completed="selected";
    }else if(degree.getLuThesisStatus().getThesisStatusId()==4){
        submited="selected";
    }else if(degree.getLuThesisStatus().getThesisStatusId()==5){
        accepted="selected";
    }
}
%>
<body>
    <div class="col-md-12">
        <span style="float: left"><font style="size: 7px">User : <%=user.getUserName()%></font></span>
             <input class="backbutton"  style="float: right"   type="button" value="Back" onclick="window.history.back()">
    </div>
<div class="col-md-8 col-md-offset-2">
<h3>Student Degree Detail</h3>	
<div class="InputDiv" style="text-align: center">
            <form action="studentdegree" method="post">
                <%if(studentDegreeId!=null){%>
                <input type="text" name="studentDegreeId" value="<%=studentDegreeId%>" style="display: none">
                <%}%>
			<table>
			<tbody>
                            <tr>
				<td>
					<span class="labelClass">Student</span>
				</td>
                                <td style="text-align: center">
					<%if(degree==null){%>
                                            <select name="studentId"  data-role="slider">
                                                <% for(Student student:studentEjbBean.getStudentList(0)){%>
                                                    <option value="<%=student.getStudentId()%>"  ><%=student.getFname()%> <%=student.getSurname()%></option>
                                                <%}%>
                                            </select> 
                                        <%}else{%>
                                            <input type="text" name="studentId" value="<%=degree.getStudent().getStudentId()%>" style="display: none">
                                            <span class="labelClass" style="text-align: center"><%=degree.getStudent().getFname()%></span>
                                        <%}%>
				</td>
                            </tr>
                            
			<tr>
				<td>
					<span class="labelClass">Staff</span>
				</td>
				<td>
					<select name="staffId"  data-role="slider">
                                            <%for(Staff staff:staffEjbBean.getStaffList()){
                                                String selectedStaff="";
                                                if(degree!=null && degree.getStaffId()==staff.getStaffId()){
                                                    selectedStaff="selected";
                                                }
                                            %>
                                                <option value="<%=staff.getStaffId()%>" <%=selectedStaff%>><%=staff.getFname()%></option>
                                            <%}%>
					</select> 
				</td>
			</tr>
                        
                        <tr>
				<td>
					<span class="labelClass">Degree Type</span>
				</td>
				<td>
					<select name="degreeTypeId"  data-role="slider">
                                            <% for(LuDegreeType degreeType:studentEjbBean.getDegreeTypes()){
                                                String selectedDegree="";
                                                if(degree!=null && degree.getLuDegreeType().getDegreeTypeId()==degreeType.getDegreeTypeId()){
                                                    selectedDegree="selected";
                                                }
                                            %>
                                                <option value="<%=degreeType.getDegreeTypeId()%>" <%=selectedDegree%>><%=degreeType.getDtname()%></option>
                                            <%}%>
					</select> 
				</td>
			</tr>
			
			<tr>
				<td>
					<span class="labelClass">Date Enrolled</span>
				</td>
				<td>
                                    <input type="text" name="dateEnrolled" id="datEnroll" value="<%=(degree!=null)?dateFormate.format(degree.getDateEnrolled()):""%>" />
				</td>
			</tr>
			
			<tr>
				<td>
					<span class="labelClass">Date Completed</span>
				</td>
				<td>
                                    <input type="text" name="dateCompleted" id="datComplete" value="<%=(degree!=null)?dateFormate.format(degree.getDateCompleted()):""%>"/>
				</td>
			</tr>
			
			<tr>
				<td>
					<span class="labelClass">Scholarship</span>
				</td>
				<td>
                                    <input type="text" name="scholarship" value="<%=(degree!=null)?degree.getScholarship():""%>"/>
				</td>
			</tr>
                        
                        <tr>
				<td>
					<span class="labelClass">Thesis Title</span>
				</td>
				<td>
                                    <input type="text" name="thesisTitle" value="<%=(degree!=null)?degree.getThesisTitle():""%>"/>
				</td>
			</tr>
                        
                        <tr>
				<td>
					<span class="labelClass">Date Thesis Intend Submit</span>
				</td>
				<td>
					<input type="text" name="dateThesisIntendSubmit" id="datThesisIntend" value="<%=(degree!=null)?dateFormate.format(degree.getDateThesisIntendSubmit()):""%>"/>
				</td>
			</tr>
                        
                        <tr>
				<td>
					<span class="labelClass">Date Thesis Submit</span>
				</td>
				<td>
					<input type="text" name="dateThesisSubmit" id="dateThesisSubmit" value="<%=(degree!=null)?dateFormate.format(degree.getDateThesisSubmit()):""%>"/>
				</td>
			</tr>
                        
                        <tr>
				<td>
					<span class="labelClass">Date Confirmation Intended</span>
				</td>
				<td>
					<input type="text" name="dateConfirmationIntended" id="datConfirmIntend" value="<%=(degree!=null)?dateFormate.format(degree.getDateConfirmationIntended()):""%>"/>
				</td>
			</tr>

                        <tr>
				<td>
					<span class="labelClass">Date Confirmation Completed</span>
				</td>
				<td>
                                    <input type="text" name="dateConfirmationCompleted" id="datConfirmComplete" value="<%=(degree!=null)?dateFormate.format(degree.getDateConfirmationCompleted()):""%>"/>
				</td>
			</tr>
                        
			<tr>
				<td>
					<span class="labelClass">Thesis Status</span>
				</td>
				<td>
					<select name="thesisStatusId"  data-role="slider">
						<option value="1" <%=started%>>Started</option>
						<option value="2" <%=assigned%>>Assigned</option>
                                                <option value="3" <%=completed%>>Completed</option>
						<option value="4" <%=submited%>>Submitted</option>
                                                <option value="5" <%=accepted%>>Accepted</option>
					</select> 
				</td>
			</tr>
                        <tr>
				<td>
					<span class="labelClass">Comments</span>
				</td>
				<td>
                                    <input type="text" name="comments" id="cmmnt" value="<%=(degree!=null && comment!=null)?comment.getAcomment():""%>"/>
				</td>
			</tr>
                        
			</tbody>
			</table>
			
			   <input type="submit" value="SUBMIT" >
			   <input type="reset" value="CLEAR" >
			
		</form>
	</div>
</div>
<div class="clearfix"></div>




</body>
</html>