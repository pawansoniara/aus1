
package com.psedb.controller;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.psedb.ejb.CourseEjbBean;
import com.psedb.ejb.StaffEjbBean;
import com.psedb.model.CourseConduction;
import com.psedb.model.LuAccessLevel;
import com.psedb.model.Staff;

@WebServlet( urlPatterns = { "/staff-course" })
public class StaffCourseController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Inject
	StaffEjbBean staffBean;
	
	@Inject
	CourseEjbBean courseEjbBean;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String ccid = req.getParameter("ccid");
		String tid = req.getParameter("tid");
		String cid = req.getParameter("cid");
		String semester = req.getParameter("semester");
		if (ccid == null) {
			staffBean.saveCourse(tid,cid,semester);
		} else {
			
			staffBean.updateCourse(ccid,tid,cid,semester);
		}
		resp.sendRedirect("staff-course-view.jsp?tid="+tid);
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if (req.getParameter("action") != null && req.getParameter("action").equals("delete")) {
			Byte ccid = Byte.valueOf(req.getParameter("ccid"));
			CourseConduction cc=staffBean.getCourse(ccid);
			staffBean.deleteStaffCourse(ccid);
			resp.sendRedirect("staff-course-view.jsp?tid="+cc.getStaff().getStaffId());
		} 
	}

}
