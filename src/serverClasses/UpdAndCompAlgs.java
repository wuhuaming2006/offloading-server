package serverClasses;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.jar.JarFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
	
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
		
		String selectedClass = (request.getParameter("classNamesSelect"));
		String jarName = (String) request.getSession().getAttribute("jarName");
		
		String classesDir = getServletContext().getRealPath(File.separator) + "WEB-INF" + File.separatorChar + "classes" + File.separatorChar;
		//String classesDir = "/home/joan/PFC/git-offloading-server/src/";
		String libsDir = getServletContext().getRealPath(File.separator) + "WEB-INF" + File.separatorChar + "lib" + File.separatorChar;
		//String libsDir = "/home/joan/PFC/git-offloading-server/WebContent/WEB-INF/lib/";
		
		String algorithmsPath = classesDir + "serverClasses" + File.separatorChar + "Algorithms.java";
		
		
		File uploadedFile = new File(libsDir, jarName);
		JarFile jFile = new JarFile ( uploadedFile);
			
		ArrayList<String> methods= null;
		try {
			methods = JarUtilities.getMethodsFromClassInJarFile(jFile, selectedClass.replace(".", "/")+".class");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for(int i =1 ; i<methods.size();i++){
			FileUtilities.removeAlgorithm(methods.get(i), algorithmsPath);
			
		}		

		for(int i =1 ; i<methods.size();i++){
			FileUtilities.addAlgorithm(methods.get(i),selectedClass, algorithmsPath);
			
		}
			
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		//The -classpath option of this compiler needs the paths to all the .jar files one by one
		String jarPaths = classesDir.substring(0, classesDir.length() - 1); //Probably not needed, the classes directory, but then we can just add ":" + "/path/to/file.jar" again and again
		File[] listOfFiles = (new File(libsDir)).listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) jarPaths += ":" + libsDir + listOfFiles[i].getName();
		}
		FileOutputStream errorStream = new FileOutputStream(classesDir + "serverClasses" + File.separatorChar + "CompilationLogs.txt");
		//Even with successful compilations, the verbose output is obtained through the OutputStrean err, the third parameter
		int compilationResult = compiler.run(null, null, errorStream, "-verbose", "-classpath", jarPaths, algorithmsPath);
		if (compilationResult != 0) {
			//"Compiling Algorithms.java failed (after adding the new algorithm case corresponding to your package)"
			response.sendRedirect("/offload/management/error.jsp?err=4");
			return;
		}
		
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
			response.sendRedirect("/offload/management/error.jsp?err=5");
			return;
		}
		
		request.getSession().setAttribute("uploadDone", true);
		request.getSession().setAttribute("newAlgs",methods);
		response.sendRedirect("/offload/management/correctlyUploadedAlgs.jsp");
		
	}
	
	private static String convertStreamToString(InputStream is) {
	    java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
	
}
