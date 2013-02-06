package serverClasses;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import serverClasses.Algorithms.AlgName;

public class RunAlgs extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Algorithms algorithms = new Algorithms();

	@Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        response.setContentType("text/xml");
        PrintWriter out = response.getWriter();
        String algNameStr = request.getParameter("algName");
        ArrayList<String> params = new ArrayList<String>();
        int i = 1;
        while (request.getParameter("param" + i) != null) {
        	params.add(request.getParameter("param" + i));
        	i++;
        }
        
        String[] parameters = null;
        if (params.size() > 0) parameters = params.toArray(new String[params.size()]);
        String result = "";
        
        long startTime = System.currentTimeMillis();
        if (algNameStr != null) {
        	AlgName algName = AlgName.valueOf(algNameStr);
        	result = algorithms.executeServer(algName, parameters);
        }
		long takenTime = System.currentTimeMillis() - startTime;
		
		out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		out.println("<root>");
		out.println("	<result>" + result + "</result>");
		out.println("	<runtime>" + takenTime + "</runtime>");
		out.println("</root>");
    }
    
}
