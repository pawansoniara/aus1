
package com.psedb.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.psedb.ejb.StudentEjbBean;
import com.psedb.model.Student;

@WebServlet( urlPatterns = { "/Student" })
public class StudentController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Inject
	StudentEjbBean studentBean;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String fname = req.getParameter("fname");
			String surname = req.getParameter("surname");
			String studentEmail = req.getParameter("studentEmail");
			String passwd = req.getParameter("passwd");
			Boolean accountLock = Boolean.valueOf(req.getParameter("accountLock"));
			Student student = new Student(fname, surname, studentEmail, passwd, accountLock, null);
			if (req.getParameter("studentId") == null && studentBean.getStudentId(studentEmail) == null) {
				studentBean.saveStudent(student);
			} else if (req.getParameter("studentId") != null) {
				studentBean.updateStudent(student, Integer.valueOf(req.getParameter("studentId")));
			}
			resp.sendRedirect("student-view.jsp");
		} catch (Exception ex) {
			Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getParameter("action") != null && req.getParameter("action").equals("isExist")) {
			String studentEmail = req.getParameter("studentEmail");
			Integer studentId = studentBean.getStudentId(studentEmail);
			if (studentId != null) {
				resp.getWriter().write("true");
			} else {
				resp.getWriter().write("no");
			}
		}
	}

}
