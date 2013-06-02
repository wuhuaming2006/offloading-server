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
		
		String CLASSES_DIR = getServletContext().getRealPath("/") + "WEB-INF/classes/";

		ServletContext servletContext = this.getServletConfig().getServletContext();
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);

		try {
			boolean theFileAlreadyExists;
			List<FileItem> fields = upload.parseRequest(request);
			Iterator<FileItem> it = fields.iterator();
			FileItem fileItem = it.next();
			//TODO when uploading
			//File uploadedFile = new File(servletContext.getRealPath("/")+ "/WEB-INF/classes/",fileItem.getName());
			String fileName = fileItem.getName();
			String packageName = fileName.split("\\.")[0];
			String extension = fileName.split("\\.")[1];
			if (extension.equals("zip")) {
				File exists = new File(CLASSES_DIR+packageName);
				theFileAlreadyExists = exists.exists();
				if (theFileAlreadyExists) System.out.println("The file " + CLASSES_DIR + fileItem.getName() + " exists");
				else System.out.println("The file " + CLASSES_DIR + fileItem.getName() + " does not exists");
				File uploadedFile = new File(CLASSES_DIR,fileItem.getName());
				fileItem.write(uploadedFile);
				//Unzip the file
				Unzip unzip = new Unzip();
				//TODO when uploading
				System.out.println("going to unzip the filename" + fileName);
				//FIXME aixo es el que detecta la carpeta i el que potser ha de canviar!
				unzip.unzipToFile(CLASSES_DIR+fileName, CLASSES_DIR);
				//this.unZipIt(fileItem.getName(), "servletContext.getRealPath("/")+ "/WEB-INF/classes/"+fileItem.getName()");
				//out.println("<br>The path with RealPath is" + servletContext.getRealPath("/") + "</br>");
				uploadedFile.delete();
				fileName = packageName;
				//TODO que no es digui serverClasses
				if (!theFileAlreadyExists) writeAlgorithmsFile(fileName, packageName, CLASSES_DIR + "serverClasses/");
				
				out.println("<br>The files where correctly uploaded<br>");
				
				String compRes;
				//TODO ajuntar aquestes vars amb la var general de CLASSES_DIR
				String fileToCompile = getServletContext().getRealPath("/") + "WEB-INF/classes/serverClasses/Algorithms.java";
				String pathToClasses = getServletContext().getRealPath("/") + "WEB-INF/classes";
				JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
				//TODO borrar els logs
				FileOutputStream errorStream = new FileOutputStream(getServletContext().getRealPath("/") + "WEB-INF/classes/serverClasses/CompilationLogs.txt");
				int compilationResult = compiler.run(null, null, errorStream, "-verbose", "-classpath", pathToClasses, fileToCompile);
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

	//FIXME static?
	private void writeAlgorithmsFile(String fileName, String packageName, String classesDir) throws Exception {
		FileUtilities fu = new FileUtilities();
		System.out.println("The package name is " + packageName);
		fu.addPackageAndAlgName(packageName, "ParseAndCall", classesDir);
	}
	
	private static String convertStreamToString(InputStream is) {
	    java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}


}


