<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Postgraduate Student Progress Database Application.</title>
        <link href="css/layout.css" rel="stylesheet" type="text/css" media="all">
        <link href="css/bootstrap.css" rel="stylesheet" type="text/css" media="all" />
    </head>
    
    <body>
        <div class="col-md-4 col-md-offset-4">
            <h3>Login </h3>
            <%if(request.getSession().getAttribute("errorMessage")!=null){%>
            <h5 style="color: red"><%=request.getSession().getAttribute("errorMessage")%></h5>
            <%}%>
        </div>
        <div class="col-md-4 col-md-offset-4">
            <div class="InputDiv">
                <form action="login" method="post">
                    <select name="userType" onfocus="this.value = '';" onblur="if (this.value == '') {
                                this.value = 'User Type';
                            }">
                        <option value="">User Type</option>
                        <option value="student">Student</option>
                        <option value="staff">Staff</option>
                    </select>
                    <input type="text" name="userName" value="Username" onFocus="this.value = '';" onBlur="if (this.value == '') {
                                this.value = 'Username';
                            }"/>
                    <input type="password" name="userPassword" />
                    <input type="submit" value="SUBMIT" >
                    <input type="reset" value="CLEAR" >
                </form>
            </div>
        </div>
        <div class="clearfix"></div>			
    </body>
    <%
        request.getSession().invalidate();
    %>
</html>