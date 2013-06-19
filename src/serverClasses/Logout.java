package serverClasses;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Logout extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	HttpSession session = request.getSession();
    	// As we don't invalidate the session, we remove all the attributes that the session contains
    	// removeAttribute will do nothing if an attribute doesn't exist
        session.removeAttribute("loginDone");
        session.removeAttribute("userName");
        session.removeAttribute("password");
        session.removeAttribute("uploadDone");
        
        request.setAttribute("loggedOut", true);
        getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
        
	}
    
}
