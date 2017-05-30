
package com.psedb.controller;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.psedb.ejb.StaffEjbBean;
import com.psedb.model.LuAccessLevel;
import com.psedb.model.Staff;

@WebServlet( urlPatterns = { "/staff" })
public class StaffController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Inject
	StaffEjbBean staffBean;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fname = req.getParameter("fname");
		String surname = req.getParameter("surname");
		String loginname = req.getParameter("loginname");
		String passwd = req.getParameter("passwd");
		Byte accessId = Byte.valueOf(req.getParameter("accessId"));
		String staffId = req.getParameter("staffId");

		LuAccessLevel luAccessLevel = new LuAccessLevel(accessId);
		Staff staff = new Staff(luAccessLevel, fname, surname, loginname, passwd);

		if (staffId == null && staffBean.getStaffId(loginname) == null) {
			staffBean.save(staff);
		} else if (staffId != null) {
			if (staff.getPasswd().trim().isEmpty()) {
				staff.setPasswd(staffBean.getStaffPasswd(Byte.valueOf(staffId)));
			}
			staffBean.update(staff, staffId);
		}
		req.getSession().setAttribute("staff", null);
		resp.sendRedirect("staff-view.jsp");
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if (req.getParameter("action") != null && req.getParameter("action").equals("edit")) {
			Byte staffId = Byte.valueOf(req.getParameter("staffId"));
			Staff staff = staffBean.getStaff(staffId);
			req.getSession().setAttribute("staff", staff);

			resp.sendRedirect("staff-edit.jsp");
		} else if (req.getParameter("action") != null && req.getParameter("action").equals("delete")) {
			Byte staffId = Byte.valueOf(req.getParameter("staffId"));
			staffBean.deleteStaff(staffId);
			req.getSession().setAttribute("staff", null);
			resp.sendRedirect("staff-view.jsp");
		} else {
			String loginname = req.getParameter("loginname");
			Integer staffId = staffBean.getStaffId(loginname);
			if (staffId != null) {
				resp.getWriter().write("true");
			} else {
				resp.getWriter().write("no");
			}
		}
	}

}
