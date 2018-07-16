package com.ts.manpower.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import com.ts.manpower.dto.*;
import com.ts.manpower.service.*;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 20)

public class ManPowerController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// response.getWriter().append("Served at:
		// ").append(request.getContextPath());

		System.out.println("In Get");

		String action = request.getParameter("action");

		if (action == null) {

			request.setAttribute("categories", new ManPowerService().getCategories());

			request.setAttribute("subcategories", new ManPowerService().getSubCategories());

			request.getRequestDispatcher("home.jsp").forward(request, response);
		}

		if (action.equals("delete_category")) {
			if (new ManPowerService().deleteCategory(Integer.parseInt(request.getParameter("categoryId"))))
				request.setAttribute("deleted", "successful");
			else
				request.setAttribute("deleted", "unsuccessful");

			request.getRequestDispatcher("adminhome.jsp").forward(request, response);

		}

		if (action.equals("delete_subcategory")) {
			if (new ManPowerService().deleteSubCategory(Integer.parseInt(request.getParameter("subCategoryId"))))
				request.setAttribute("deleted", "successful");
			else
				request.setAttribute("deleted", "unsuccessful");

			request.getRequestDispatcher("adminhome.jsp").forward(request, response);

		}

		if (action.equals("delete_service")) {
			if (new ManPowerService().deleteServiceType(Integer.parseInt(request.getParameter("serviceTypeId"))))
				request.setAttribute("deleted", "successful");
			else
				request.setAttribute("deleted", "unsuccessful");

			request.getRequestDispatcher("adminhome.jsp").forward(request, response);

		}

		if (action.equals("logout")) {

			HttpSession session = request.getSession(false);
			if (session != null)
				session.invalidate();

			request.setAttribute("categories", new ManPowerService().getCategories());

			request.getRequestDispatcher("home.jsp").forward(request, response);
		}

		if (action.equals("approve")) {
			if (new ManPowerService().approveForce(Integer.parseInt(request.getParameter("userId"))))
				request.setAttribute("approved", "Y");
			else
				request.setAttribute("approved", "N");

			request.getRequestDispatcher("adminhome.jsp").forward(request, response);
		}

		if (Integer.parseInt(action) > 100 && Integer.parseInt(action) < 200) {

			request.setAttribute("category", new ManPowerService().getCategory(Integer.parseInt(action)));

			request.setAttribute("subcategories", new ManPowerService().getSubCategories(Integer.parseInt(action)));
			request.getRequestDispatcher("subcategories.jsp").forward(request, response);

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("I am in Controller Post method");

		String formType = request.getParameter("formType");
		System.out.println(formType + ": <br/>");

		if ("Login As User".equalsIgnoreCase(formType)) {

			String email = request.getParameter("email");
			String password = request.getParameter("password");

			User user = new ManPowerService().loginUser(email, password);

			if (user != null)
				request.setAttribute("user", user);
			else
				request.setAttribute("login", "unsuccessful");

			request.setAttribute("categories", new ManPowerService().getCategories());
			request.setAttribute("subcategories", new ManPowerService().getSubCategories());
			request.getRequestDispatcher("userhome.jsp").forward(request, response);

		} else if ("Login As Force".equalsIgnoreCase(formType)) {

			String email = request.getParameter("email");
			String password = request.getParameter("password");

			Force force = new ManPowerService().loginForce(email, password);

			if (force != null)
				request.setAttribute("force", force);
			else
				request.setAttribute("login", "unsuccessful");

			request.getRequestDispatcher("forcehome.jsp").forward(request, response);

		} else if ("signUpUser".equalsIgnoreCase(formType)) {

			User user = new User();
			user.setName(request.getParameter("uname"));
			user.setPassword(request.getParameter("upwd"));
			user.setEmailId(request.getParameter("uemail"));
			user.setMobileNumber(Long.parseLong(request.getParameter("phone")));

			if (new ManPowerService().registerUser(user)) {
				request.setAttribute("registerstatus", "success");
				request.setAttribute("categories", new ManPowerService().getCategories());
				request.setAttribute("subcategories", new ManPowerService().getSubCategories());
				request.getSession().setAttribute("user", user);
				request.getRequestDispatcher("userhome.jsp").forward(request, response);
			} else {
				request.setAttribute("registerstatus", "unsuccessful");
			}

		} else if ("signUpForce".equalsIgnoreCase(formType)) {

			Force force = new Force();
			force.setName(request.getParameter("fname"));
			force.setPassword(request.getParameter("fpwd"));
			force.setEmailId(request.getParameter("femail"));
			force.setMobileNumber(Long.parseLong(request.getParameter("fphone")));

			File fileSaveDir = new File("/uploads");

			if (!fileSaveDir.exists()) {
				fileSaveDir.mkdirs();
			}

			System.out.println("Upload File Directory=" + fileSaveDir.getAbsolutePath());

			System.out.println(fileSaveDir);

			String fileName = null;
			for (Part part : request.getParts()) {
				fileName = extractFileName(part);
				
				String extension = fileName.substring(fileName.indexOf(".") + 1);
				extension = force.getName() + "." + extension;
				part.write(fileSaveDir + File.separator + force.getName() + extension);				
			}

			force.setAadharImage(fileName);

			if (new ManPowerService().registerForce(force, null, null)) {
				request.setAttribute("message", "Done successfully!");
				request.getRequestDispatcher("/message.jsp").forward(request, response);
			} else {
				request.setAttribute("message", "Registration failed !");
				request.getRequestDispatcher("/message.jsp").forward(request, response);
			}
		}
	}

	
	private String extractFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");

		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				return s.substring(s.indexOf("=") + 2, s.length() - 1);
			}
		}
		return "";
	}
}


