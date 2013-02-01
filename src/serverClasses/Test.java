package serverClasses;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Test extends HttpServlet {

	private static final long serialVersionUID = 1L;

	
	public int fibonacci(int n) {
		if (n < 2) {
			return n;
		}
		else {
			return fibonacci(n-1)+fibonacci(n-2);
		}
	}
	
	
	@Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        //response.setContentType("text/xml;charset=UTF-8");
        response.setContentType("text/xml");
        PrintWriter out = response.getWriter();
        String fiboNumberStr = request.getParameter("nLoops");
        long startTime = System.currentTimeMillis();
        int fiboNumber = 0;
        if (fiboNumberStr != null) fiboNumber = Integer.parseInt(fiboNumberStr);
		int result = fibonacci (fiboNumber);
		long takenTime = System.currentTimeMillis() - startTime;
		
		out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		out.println("<root>");
		out.println("	<sympathicAnswer>Server says: I have finished looping!</sympathicAnswer>");
		out.println("	<loopsNumber>" + result + "</loopsNumber>");
		out.println("	<executionTime>" + takenTime + "</executionTime>");
		out.println("</root>");
        
    }
    
}
