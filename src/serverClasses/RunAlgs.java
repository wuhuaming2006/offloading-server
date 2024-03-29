package serverClasses;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RunAlgs extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doPost(request, response);
	}

	@Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        
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
        
        if (algNameStr != null) result = Algorithms.executeServer(algNameStr, parameters);
		
		out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		out.println("<root>");
		out.println("	<result>" + result + "</result>");
		out.println("	<runtime>" + Algorithms.lastExecutionTime + "</runtime>");
		//out.println("<overhead>" + Algorithms.lastExecutionOverhead + "</overhead>");
		out.println("</root>");
    }
    
}
