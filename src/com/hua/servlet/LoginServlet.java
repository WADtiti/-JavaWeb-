package com.hua.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.hua.dao.AdminDao;
import com.hua.dao.DaoFactory;
import com.hua.dao.StudentDao;
import com.hua.dao.TeacherDao;
import com.hua.entity.Admin;
import com.hua.entity.Student;
import com.hua.entity.Teacher;
import com.hua.utils.MD5;

@WebServlet("/login")
public class LoginServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		String userName = req.getParameter("userName");
		String password = req.getParameter("password");
		String type = req.getParameter("type");
		if(StringUtils.isBlank(userName)||StringUtils.isBlank(password)||StringUtils.isBlank(type)) {
			req.setAttribute("error","录入信息不能为空");
			req.getRequestDispatcher("login.jsp").forward(req,resp);
			return;
		}
		HttpSession session=req.getSession();
		try {
			if(StringUtils.isNotBlank(type)) {
				if("0".equals(type)) {
					//学生登录验证
					Student student =DaoFactory.getInstance().getStudentDao().login(userName, MD5.encrypByMd5(MD5.encrypByMd5(password)));
					if(student !=null) {
						session.setAttribute("user", student);
						session.setAttribute("type", type);
						resp.sendRedirect("index.jsp");
					}else {
						req.setAttribute("error","用户名或密码错误");
						req.getRequestDispatcher("login.jsp").forward(req,resp);
					}
					
				}else if("1".equals(type)) {
					//老师登录验证
					Teacher teacher =DaoFactory.getInstance().getTeacherDao().login(userName, MD5.encrypByMd5(MD5.encrypByMd5(password)));
					if(teacher !=null) {
						session.setAttribute("user", teacher);
						session.setAttribute("type", type);
						resp.sendRedirect("index.jsp");
						
					}else {
						req.setAttribute("error","用户名或密码错误");
						req.getRequestDispatcher("login.jsp").forward(req,resp);
					}
				}
				else {
//				Admin admin = new Admin();
//				admin.setUserName(userName);
//				admin.setPwd(MD5.encrypByMd5(MD5.encrypByMd5(password)));

					Admin entity=DaoFactory.getInstance().getAdminDao().login(userName,MD5.encrypByMd5(MD5.encrypByMd5(password)));
					if (entity!=null) {
						//执行跳转
						session.setAttribute("user", entity);
						session.setAttribute("type", type);
						resp.sendRedirect("index.jsp");
					}else {
						//用户名或密码错误
						req.setAttribute("error","用户名或密码错误");
						req.getRequestDispatcher("login.jsp").forward(req,resp);
					}
			}
		} }catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setContentType("text/html");
//        String name = req.getParameter("name");
//        if (name == null) {
//            name = "world";
//        }
//        PrintWriter pw = resp.getWriter();
//        pw.write("<h1>Hello, " + name + "!</h1>");
//        pw.flush();
//    }

}
