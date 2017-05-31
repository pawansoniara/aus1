package com.psedb.controller;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.psedb.ejb.AuthenticationEjbBean;
import com.psedb.ejb.StaffEjbBean;
import com.psedb.ejb.StudentEjbBean;
import com.psedb.model.UserBean;
import com.psedb.util.Tokens;

@WebServlet(urlPatterns = { "/login" })
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Inject
	AuthenticationEjbBean authenticationBean;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userType = req.getParameter("userType");
		String userName = req.getParameter("userName");
		Integer studentId = null;
		Integer staffId = null;
		String userPassword = req.getParameter("userPassword");
		UserBean user = new UserBean();
		user.setUserType(userType);
		user.setUserName(userName);
		System.out.println(user + " pass: " + userPassword);
		user.setLoginName(req.getParameter("userName"));

		if (userType.equalsIgnoreCase("staff")) {
			user = authenticationBean.authenticateStaff(user, userPassword);
		} else {
			user = authenticationBean.authenticateStudent(user, userPassword);
		}

		HttpSession session = req.getSession(true);
		session.setAttribute("user", user);
		if (user == null) {
			session.setAttribute("errorMessage", "Invalid Username/Password");
			resp.sendRedirect("index.jsp");
		} else if (userType.equalsIgnoreCase("staff") && user.getAccessId() == Tokens.ADMINISTRATOR.intValue()) {
			resp.sendRedirect("admin.jsp");
		} else if (userType.equalsIgnoreCase("staff") && user.getAccessId() == Tokens.SUPERVISOR.intValue()) {
			resp.sendRedirect("staff.jsp");
		} else if (userType.equalsIgnoreCase("student")) {
			resp.sendRedirect("student.jsp");
		} else {
			session.setAttribute("errorMessage", "Invalid Username/Password");
			resp.sendRedirect("index.jsp");
		}

	}
}
