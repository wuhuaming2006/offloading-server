package serverClasses;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import serverClasses.Algorithms.AlgName;



public class UploadFile extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String SRC_DIRECTORY = "/home/joan/PFC/git-offloading-server/src/";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<br>The files where correctly uploaded</br>");
		boolean isMultipartContent = ServletFileUpload.isMultipartContent(request);
		if (!isMultipartContent) {
			out.println("<br>You are not trying to upload<br>");
			return;
		}
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);

		ServletContext servletContext = this.getServletConfig().getServletContext();
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);

		try {
			List<FileItem> fields = upload.parseRequest(request);
			Iterator<FileItem> it = fields.iterator();
			FileItem fileItem = it.next();
			//TODO when uploading
			File uploadedFile = new File(SRC_DIRECTORY,fileItem.getName());
			//File uploadedFile = new File(servletContext.getRealPath("/")+ "/WEB-INF/classes/",fileItem.getName());
			String fileName = fileItem.getName();
			String newAlgName = fileName.split("\\.")[0];
			String extension = fileName.split("\\.")[1];
			fileItem.write(uploadedFile);
			if(extension.equals("zip")) {
				//Unzip the file
				Unzip unzip = new Unzip();
				//TODO when uploading
				System.out.println("going to unzip the filename" + fileName);
				//FIXME aixo es el que detecta la carpeta i el que potser ha de canviar!
				unzip.unzipToFile(SRC_DIRECTORY+fileName, SRC_DIRECTORY+newAlgName);
				//this.unZipIt(fileItem.getName(), "servletContext.getRealPath("/")+ "/WEB-INF/classes/"+fileItem.getName()");
				//out.println("<br>The path with RealPath is" + servletContext.getRealPath("/") + "</br>");
				uploadedFile.delete();
				fileName = newAlgName;
			}
			writeAlgorithmsFile(fileName, newAlgName);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		/*while (it.hasNext()) {
				out.println("<tr>");
				FileItem fileItem = it.next();
				boolean isFormField = fileItem.isFormField();
				if (isFormField) {
					out.println("<td>regular form field</td><td>FIELD NAME: " + fileItem.getFieldName() + 
							"<br/>STRING: " + fileItem.getString()
							);
					out.println("</td>");
				} else {
					out.println("<td>file form field</td><td>FIELD NAME: " + fileItem.getFieldName() +
							"<br/>STRING: " + fileItem.getString() +
							"<br/>NAME: " + fileItem.getName() +
							"<br/>CONTENT TYPE: " + fileItem.getContentType() +
							"<br/>SIZE (BYTES): " + fileItem.getSize() +
							"<br/>TEMP DIRECTORY : " + System.getProperty("java.io.tmpdir") +
							"<br/>TO STRING: " + fileItem.toString()
							);
					out.println("</td>");
				}
				File uploadedFile = new File("/home/joan/Proves/","lol2.java");
			    fileItem.write(uploadedFile);
				out.println("</tr>");
			}
			out.println("</table>");
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

	private void writeAlgorithmsFile(String fileName, String newAlgName) throws IOException {
//		FileUtilities fu = new FileUtilities();
//		String packageName = fu.getPackageFromClassOrDirectory(SRC_DIRECTORY + fileName);
//		System.out.println("The package name is" + packageName);
	}


}


