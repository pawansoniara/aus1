
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

import com.psedb.ejb.CourseEjbBean;
import com.psedb.model.Course;

@WebServlet(urlPatterns = { "/courses" })
public class CoursesController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Inject
	CourseEjbBean courseEjbBean;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			if (req.getParameter("cid") == null) {
				courseEjbBean.save(new Course(null,req.getParameter("description")));
			} else  {
				courseEjbBean.update(new Course(Byte.valueOf(req.getParameter("cid")),req.getParameter("description")));
			}
			resp.sendRedirect("courses-view.jsp");
		} catch (Exception ex) {
			Logger.getLogger(CoursesController.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getParameter("action") != null && req.getParameter("action").equals("isExist")) {
			Byte cid = courseEjbBean.getCourseByName(req.getParameter("description"));
			if (cid != null) {
				resp.getWriter().write("true");
			} else {
				resp.getWriter().write("no");
			}
		} else if (req.getParameter("action") != null && req.getParameter("action").equals("delete")) {
			Byte cid = Byte.valueOf(req.getParameter("cid"));
			courseEjbBean.delete(cid);
			resp.sendRedirect("courses-view.jsp");
		}
	}

}
