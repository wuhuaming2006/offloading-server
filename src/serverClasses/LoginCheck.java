package serverClasses;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCheck extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doPost(request, response);
	}

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        if (userName == null) userName = "";
        if (password == null) password = "";
        HttpSession session = request.getSession();
        if (userName.equals("katinka") && password.equals("admin")) {
        	session.setAttribute("loginDone", true);
        	response.sendRedirect("/offload/management/uploadFile.jsp");
        }
        else {
        	session.setAttribute("userName", userName);
        	session.setAttribute("password", password);
        	response.sendRedirect("/offload/management/index.jsp?invUserPass=1");
        }
    }
    
}
