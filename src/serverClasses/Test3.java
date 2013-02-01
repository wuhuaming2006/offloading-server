package serverClasses;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Test3 extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        //response.setContentType("text/xml;charset=UTF-8");
        response.setContentType("text/xml");
        PrintWriter out = response.getWriter();
        String nLoopsStr = request.getParameter("nLoops");
        long startTime = System.currentTimeMillis();
        long nLoops = 0;
        if (nLoopsStr != null) nLoops = Long.parseLong(nLoopsStr) * 1000000;
		long i = 0;
		while (i < nLoops) i++;
		long takenTime = System.currentTimeMillis() - startTime;
		
		out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		out.println("<root>");
		out.println("	<sympathicAnswer>Server says: I have finished looping!</sympathicAnswer>");
		out.println("	<loopsNumber>" + i + "</loopsNumber>");
		out.println("	<executionTime>" + takenTime + "</executionTime>");
		out.println("</root>");
        
    }
    
}
