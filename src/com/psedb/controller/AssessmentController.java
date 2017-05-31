
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
import com.psedb.model.Assessment;
import com.psedb.model.CourseConduction;
import com.psedb.model.Enrollment;
import com.psedb.model.LuAccessLevel;
import com.psedb.model.Staff;

@WebServlet( urlPatterns = { "/assessment" })
public class AssessmentController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Inject
	StudentEjbBean studentBean;
	
	@Inject
	CourseEjbBean courseEjbBean;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String aid = req.getParameter("aid");
		String sid = req.getParameter("sid");
		String eid = req.getParameter("eid");
		String a1 = req.getParameter("a1");
		String a2 = req.getParameter("a2");
		String ccid = req.getParameter("ccid");
		
		if (aid == null) {
			studentBean.saveAssessment(eid,a1,a2,sid);
		} else {
			studentBean.updateAssessment(aid,a1,a2);
		}
		resp.sendRedirect("staff-student-view.jsp?ccid="+ccid);
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
