package serverClasses;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UploadFile extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		boolean isMultipartContent = ServletFileUpload.isMultipartContent(request);
		if (!isMultipartContent) {
			response.sendRedirect("/offload/management/error.jsp?err=0");
			//"The form you sent does not have the expected contents"
			return;
		}
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		//FIXME String classesDir = "/home/joan/PFC/git-offloading-server/src/";
		String classesDir = getServletContext().getRealPath("/") + "WEB-INF/classes/";
		String algorithmsPath = classesDir + "serverClasses/Algorithms.java";

		ServletContext servletContext = this.getServletConfig().getServletContext();
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);
		
		List<FileItem> fields = null;
		try {
			fields = upload.parseRequest(request);
		} catch (FileUploadException e1) {
			e1.printStackTrace();
		}
		Iterator<FileItem> it = fields.iterator();
		FileItem fileItem = it.next();
		String fileName = fileItem.getName();
		String packageName = fileName.split("\\.")[0];
		String extension = fileName.split("\\.")[1];
		if (packageName.equals("serverClasses")) {
			//This package name is not allowed
			response.sendRedirect("/offload/management/error.jsp?err=1");
			return;
		}
		if (!extension.equals("zip")) {
			//"The file is not a .zip file"
			response.sendRedirect("/offload/management/error.jsp?err=2&filename="+fileName);
			return;
		}
		
		File packageDir = new File(classesDir + packageName);
		boolean theFileAlreadyExists = packageDir.exists();
	
		File uploadedFile = new File(classesDir, fileName);
		try {
			fileItem.write(uploadedFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Unzip unzip = new Unzip();
		unzip.unzipToFile(classesDir + fileName, classesDir);
		uploadedFile.delete();
		
		if (!theFileAlreadyExists) FileUtilities.addAlgorithm(packageName, algorithmsPath);
		
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		FileOutputStream errorStream = new FileOutputStream(classesDir + "serverClasses/CompilationLogs.txt");
		int compilationResult = compiler.run(null, null, errorStream, "-verbose", "-classpath", classesDir.substring(0, classesDir.length() - 1), algorithmsPath);
		if (compilationResult != 0) {
			//"Compiling Algorithms.java failed (after adding the new algorithm case corresponding to your package)"
			response.sendRedirect("/offload/management/error.jsp?err=3");
			return;
		}
		
		URL url;
		//TODO Leave only the first option of this if-else once local testing is not needed anymore
		if (request.getServerName().contains("fu-berlin")) url = new URL("http://localhost:8180/manager/reload?path=/offload");
		else url = new URL("http://localhost:8080/manager/text/reload?path=/offload");
		URLConnection urlConn = url.openConnection();

		String userPass = "martigriera:m4rT.n1;"; //username:password
		String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userPass.getBytes());
		urlConn.setRequestProperty ("Authorization", basicAuth);
		
		InputStream inSt = urlConn.getInputStream();
		String reloadAnswer = UploadFile.convertStreamToString(inSt);
		inSt.close();
		
		if (!reloadAnswer.contains("OK")) {
			//"The webapp could not be reloaded"
			response.sendRedirect("/offload/management/error.jsp?err=4");
			return;
		}
		
		//TODO 
		//si tot be... depen alreadyExists dir uploaded i prou o afegir updated
		
	}
	
	private static String convertStreamToString(InputStream is) {
	    java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}

}