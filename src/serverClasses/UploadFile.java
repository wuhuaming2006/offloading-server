package serverClasses;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UploadFile extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		//TODO canviar aquest titol, fer un jsp que informi si tot va be i pregunti pel seguent pas, i un jsp pels errors
		out.println("<html><head><title>ALL RIGHT... ODER NICHT</title></head><body>");
		
		boolean isMultipartContent = ServletFileUpload.isMultipartContent(request);
		if (!isMultipartContent) {
			out.println("<br>You are not trying to upload<br>");
			return;
		}
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		String classesDir = "/home/joan/PFC/git-offloading-server/src/";
		//String classesDir = getServletContext().getRealPath("/") + "WEB-INF/classes/";
		String algorithmsPath = classesDir + "serverClasses/Algorithms.java";

		ServletContext servletContext = this.getServletConfig().getServletContext();
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);

		try {
			boolean theFileAlreadyExists;
			List<FileItem> fields = upload.parseRequest(request);
			Iterator<FileItem> it = fields.iterator();
			FileItem fileItem = it.next();
			String fileName = fileItem.getName();
			String packageName = fileName.split("\\.")[0];
			String extension = fileName.split("\\.")[1];
			if (extension.equals("zip")) {
				File exists = new File(classesDir+packageName);
				theFileAlreadyExists = exists.exists();
				if (theFileAlreadyExists) System.out.println("The file " + classesDir + fileItem.getName() + " exists");
				else System.out.println("The file " + classesDir + fileItem.getName() + " does not exists");
				File uploadedFile = new File(classesDir,fileItem.getName());
				fileItem.write(uploadedFile);
				//Unzip the file
				Unzip unzip = new Unzip();
				unzip.unzipToFile(classesDir+fileName, classesDir);
				uploadedFile.delete();
				if (packageName.equals("serverClasses")) { //TODO error, canvia el nom del package
					
				}
				if (!theFileAlreadyExists) UploadFile.writeAlgorithmsFile(packageName, algorithmsPath);
				
				out.println("<br>The files where correctly uploaded<br>");
				
				String compRes;
				JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
				FileOutputStream errorStream = new FileOutputStream(classesDir + "serverClasses/CompilationLogs.txt");
				int compilationResult = compiler.run(null, null, errorStream, "-verbose", "-classpath", classesDir.substring(0, classesDir.length() - 1), algorithmsPath);
				if (compilationResult == 0) compRes = "Compilation is successful";
				else compRes = "Compilation Failed";
				
				out.println("<br>compRes: " + compRes + "</br>");
				
				URL url;
				//TODO Leave only the first option of this if-else once local testing is not needed anymore
				if (request.getServerName().contains("fu-berlin")) url = new URL("http://localhost:8180/manager/reload?path=/offload");
				else url = new URL("http://localhost:8080/manager/text/reload?path=/offload");
				URLConnection urlConn = url.openConnection();

				String userPass = "martigriera:m4rT.n1;"; //username:password
				String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userPass.getBytes());
				urlConn.setRequestProperty ("Authorization", basicAuth);
				
				InputStream inSt = urlConn.getInputStream();
				String htmlAnswer = UploadFile.convertStreamToString(inSt);
				inSt.close();
				
				out.println("<br>htmlAnswer: " + htmlAnswer + "</br>");
				
			}
			else {
				//TODO 
				System.out.println("Error: The file is not a .zip file");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		out.println("</body></html>");
	}

	private static void writeAlgorithmsFile(String packageName, String classesDir) throws Exception {
		FileUtilities fu = new FileUtilities();
		System.out.println("The package name is " + packageName);
		fu.addPackageAndAlgName(packageName, "ParseAndCall", classesDir);
	}
	
	private static String convertStreamToString(InputStream is) {
	    java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}


}


