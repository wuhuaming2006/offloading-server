package serverClasses;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final int BUFSIZE = 4096;
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		if (request.getSession().getAttribute("loginDone") == null) {
			getServletContext().getRequestDispatcher("/management/error.jsp").forward(request, response);
			return;
		}
		
		String filesPath = getServletContext().getRealPath(File.separator) + "WEB-INF" + File.separatorChar + "files" + File.separatorChar;
		String fileName = request.getParameter("fileName");
		File file = null;
		
		if (fileName == null) {
			getServletContext().getRequestDispatcher("/management/error.jsp").forward(request, response);
			return;
		}
		
		file = new File(filesPath + fileName);
		
		if (!file.exists()) {
			getServletContext().getRequestDispatcher("/management/error.jsp").forward(request, response);
			return;
		}
		
		int length   = 0;
	    ServletOutputStream outStream = response.getOutputStream();
	    ServletContext context  = getServletConfig().getServletContext();
	    String mimeType = context.getMimeType(filesPath + fileName);

	    //Sets the response content type
	    if (mimeType == null) mimeType = "application/octet-stream";
	    
	    response.setContentType(mimeType);
	    response.setContentLength((int)file.length());

	    //Sets the HTTP header
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

	    byte[] byteBuffer = new byte[BUFSIZE];
	    DataInputStream in = new DataInputStream(new FileInputStream(file));

	    //Reads the file's bytes and writes them to the response stream
	    while ((in != null) && ((length = in.read(byteBuffer)) != -1)) outStream.write(byteBuffer,0,length);

	    in.close();
	    outStream.close();
		
	}
	
}
