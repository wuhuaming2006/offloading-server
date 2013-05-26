package serverClasses;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginCheck extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        
        if (userName.equals("joe")&&password.equals("indian")){
        	request.getRequestDispatcher("/management/uploadfile.jsp").forward(request, response);
        }
        else {
        	request.setAttribute("wrongPass", true);
        	getServletContext().getRequestDispatcher("/management/index.jsp").forward(request, response);
         }
    	
	}

}
