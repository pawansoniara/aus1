
package com.psedb.controller;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.psedb.ejb.CourseEjbBean;
import com.psedb.ejb.StudentEjbBean;
import com.psedb.model.CourseConduction;
import com.psedb.model.Enrollment;
import com.psedb.model.LuAccessLevel;
import com.psedb.model.Staff;

@WebServlet( urlPatterns = { "/student-enrollment" })
public class StudentEnrollmentController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Inject
	StudentEjbBean studentBean;
	
	@Inject
	CourseEjbBean courseEjbBean;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String eid = req.getParameter("eid");
		String sid = req.getParameter("sid");
		String cid = req.getParameter("cid");
		String semester = req.getParameter("semester");
		if (eid == null) {
			studentBean.saveCourse(sid,cid,semester);
		} else {
			
			studentBean.updateCourse(eid,sid,cid,semester);
		}
		resp.sendRedirect("student-enrollment-view.jsp?sid="+sid);
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if (req.getParameter("action") != null && req.getParameter("action").equals("delete")) {
			Byte eid = Byte.valueOf(req.getParameter("eid"));
			Enrollment enrollment=studentBean.getCourse(eid);
			studentBean.deleteStudentEnrollment(eid);
			resp.sendRedirect("student-enrollment-view.jsp?sid="+enrollment.getStudent().getStudentId());
		} 
	}

}
