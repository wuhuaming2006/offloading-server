package serverClasses;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarFile;

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
		String libsDir = getServletContext().getRealPath(File.separator) + "WEB-INF" + File.separatorChar + "lib" + File.separatorChar;
		//String libsDir = "/home/joan/PFC/git-offloading-server/WebContent/WEB-INF/lib/";

		if (request.getSession().getAttribute("loginDone") == null) {
			//"You must log in order to access the management area"
			response.sendRedirect("/offload/management/error.jsp");
			return;
		}
		
		boolean isMultipartContent = ServletFileUpload.isMultipartContent(request);
		if (!isMultipartContent) {
			//"The form you sent does not have the expected contents"
			response.sendRedirect("/offload/management/error.jsp?err=1");
			return;
		}
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		
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
		String extension = fileName.substring(fileName.length()-4);
			
		if(checkValidJarName(fileName)==false) {
			response.sendRedirect("/offload/management/error.jsp?err=2");
			return;
		}
				
		if (!extension.equals(".jar")) {
			//"The file is not a .jar file"
			response.sendRedirect("/offload/management/error.jsp?err=3&filename="+fileName);
			return;
		}
		
		File packageDir = new File(libsDir + fileName);
		boolean theFileAlreadyExists = packageDir.exists();
	
		File uploadedFile = new File(libsDir, fileName);
		try {
			fileItem.write(uploadedFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JarFile jFile = new JarFile(uploadedFile);
		ArrayList<String> classNames = null;
		try {
			classNames = JarUtilities.getClassNames(jFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.getSession().setAttribute("jarName", fileName);
		request.getSession().setAttribute("classNames", classNames);
		
		if (theFileAlreadyExists) response.sendRedirect("/offload/management/selectClass.jsp?newFile=0");
		else response.sendRedirect("/offload/management/selectClass.jsp?newFile=1");
		
	}

	private boolean checkValidJarName(String jarName) {
		
		
		System.out.println("The name is " + jarName);
		if (jarName.equals("asm-4.1.jar") ||
				jarName.equals("asm-tree-4.1.jar") ||
				jarName.equals("commons-fileupload-1.3.jar") ||
				jarName.equals("commons-io-2.4.jar") ||
				jarName.equals("servlet-api.jar"))return false;
		return true;
		
	}

}
