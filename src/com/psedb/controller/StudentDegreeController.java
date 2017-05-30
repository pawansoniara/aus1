
package com.psedb.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.psedb.ejb.StudentEjbBean;
import com.psedb.model.LuDegreeType;
import com.psedb.model.LuThesisStatus;
import com.psedb.model.Student;
import com.psedb.model.StudentDegree;
import com.psedb.model.UserBean;

@WebServlet( urlPatterns = { "/studentdegree" })
public class StudentDegreeController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Inject
	StudentEjbBean studentBean;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UserBean user = (UserBean) req.getSession().getAttribute("user");
		Byte degreeTypeId = Byte.valueOf(req.getParameter("degreeTypeId"));
		Byte thesisStatusId = Byte.valueOf(req.getParameter("thesisStatusId"));
		Integer studentId = Integer.parseInt(req.getParameter("studentId"));
		Integer staffId = Integer.parseInt(req.getParameter("staffId"));
		Student student = new Student();
		student.setStudentId(studentId);
		LuDegreeType luDegreeType = new LuDegreeType();
		luDegreeType.setDegreeTypeId(degreeTypeId);
		LuThesisStatus luThesisStatus = new LuThesisStatus();
		luThesisStatus.setThesisStatusId(thesisStatusId);
		Integer studentDegreeId = null;
		if (req.getParameter("studentDegreeId") != null) {
			studentDegreeId = Integer.parseInt(req.getParameter("studentDegreeId"));
		}
		SimpleDateFormat dateFormate = new SimpleDateFormat("MM/dd/yyyy");
		String comments = req.getParameter("comments");
		try {
			StudentDegree studentDegree = new StudentDegree(student, luDegreeType, luThesisStatus,
					dateFormate.parse(req.getParameter("dateEnrolled")),
					dateFormate.parse(req.getParameter("dateCompleted")), req.getParameter("scholarship"),
					dateFormate.parse(req.getParameter("dateThesisIntendSubmit")),
					dateFormate.parse(req.getParameter("dateThesisSubmit")), req.getParameter("thesisTitle"),
					dateFormate.parse(req.getParameter("dateConfirmationIntended")),
					dateFormate.parse(req.getParameter("dateConfirmationCompleted")), null, null);

			if (studentDegreeId == null) {
				studentBean.saveStudentDegree(studentDegree, staffId, user, comments);
			} else {
				studentBean.updateStudentDegree(studentDegree, staffId, user, comments, studentDegreeId);
			}
		} catch (ParseException ex) {
			Logger.getLogger(StudentDegreeController.class.getName()).log(Level.SEVERE, null, ex);
		}
		resp.sendRedirect("student-degree-view.jsp");
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

}
