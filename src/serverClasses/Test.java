package serverClasses;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Test extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doGet(request, response);
	}

	@Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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
		out.println("	<osName>" + System.getProperty("os.name") + "</osName>");
		out.println("	<osArch>" + System.getProperty("os.arch") + "</osArch>");
		out.println("	<javaVersion>" + System.getProperty("java.version") + "</javaVersion>");
		out.println("	<loopsNumber>" + i + "</loopsNumber>");
		out.println("	<executionTime>" + takenTime + "</executionTime>");
		out.println("</root>");
    }
    
}
