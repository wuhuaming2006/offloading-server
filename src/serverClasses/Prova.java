package serverClasses;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Prova extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        //response.setContentType("text/html");
        //response.setContentType("text/xml;charset=UTF-8");
        response.setContentType("text/xml");
        PrintWriter out = response.getWriter();
        String usrIt = request.getParameter("usrIt");
        long startTime = System.currentTimeMillis();
        long iterationsToDo = 0;
        if (usrIt != null) iterationsToDo = Long.parseLong(usrIt) * 1000000;
		long i = 0;
		while (i < iterationsToDo) i++;
		long endTime = System.currentTimeMillis();
		long exeTime = endTime - startTime;
		
		out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		out.println("<root>");
		out.println("	<sympathicAnswer>Server says: I have finished looping!</sympathicAnswer>");
		out.println("	<loopsNumber>" + i + "</loopsNumber>");
		out.println("	<executionTime>" + exeTime + "</executionTime>");
		out.println("</root>");
        
    }
    
}
