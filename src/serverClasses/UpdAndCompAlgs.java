package serverClasses;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.jar.JarFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
	
public class UpdAndCompAlgs extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (request.getSession().getAttribute("loginDone") == null) {
			//"You must log in order to access the management area"
			response.sendRedirect("/offload/management/error.jsp");
			return;
		}
		
		String selectedClass = request.getParameter("classNamesSelect");
		Object jarNameObj = request.getSession().getAttribute("jarName");
		if (selectedClass == null || jarNameObj == null) {
			//"Invalid access attempt"
			response.sendRedirect("/offload/management/error.jsp");
			return;
		}
		String jarName = (String) jarNameObj;
		
		File uploadedFile = new File(ManagementMethodsDB.libsDir, jarName);
		JarFile jFile = new JarFile(uploadedFile);
			
		ArrayList<String> methods = null;
		try {
			methods = JarUtilities.getMethodsFromClassInJar(jFile, selectedClass.replace(".", "/") + ".class");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < methods.size(); i++) {
			String returnCode = ManagementMethodsDB.removeMethod(methods.get(i));
			if (!returnCode.equals("OK")) {
				response.sendRedirect("/offload/management/" + returnCode);
				return;
			}
		}
		for (int i = 0; i < methods.size(); i++) {
			String returnCode = ManagementMethodsDB.addMethod(methods.get(i), selectedClass);
			if (!returnCode.equals("OK")) {
				response.sendRedirect("/offload/management/" + returnCode);
				return;
			}
		}
		
		//TODO tot el seguent es podria petar potser amb carregant la classes amb:
		//Class.forName(selectedClass);
		
		URL url;
		//FIXME Leave only the first option of this if-else once local testing is not needed anymore
		if (request.getServerName().contains("fu-berlin")) url = new URL("http://localhost:8180/manager/reload?path=/offload");
		else url = new URL("http://localhost:8080/manager/text/reload?path=/offload");
		URLConnection urlConn = url.openConnection();

		String userPass = "martigriera:m4rT.n1;"; //username:password
		String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userPass.getBytes());
		urlConn.setRequestProperty("Authorization", basicAuth);
		
		InputStream inSt = urlConn.getInputStream();
		String reloadAnswer = UpdAndCompAlgs.convertStreamToString(inSt);
		inSt.close();
		
		if (!reloadAnswer.contains("OK")) {
			//"The WebApp could not be reloaded"
			response.sendRedirect("/offload/management/error.jsp?err=4");
			return;
		}
		
		request.getSession().setAttribute("newMethods", methods);
		response.sendRedirect("/offload/management/menu.jsp");
		
	}
	
	private static String convertStreamToString(InputStream is) {
	    java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
	
}
