package serverClasses;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import serverClasses.Algorithms.AlgName;

public class GenerateDB extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private class DbEntry {
		public double algInputRep; //FIXME BLOB...
		public double runTimeMs;
		public AlgName algName;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<body>");
        
        //TODO in progress...
        //<p>Click <a href="algCosts.db">here</a> to download the generated database</p>
        //<p class="error">Work in progress...!</p>
        
		if (!ServletFileUpload.isMultipartContent(request)) { //The form sent does not have the expected contents
			//"Invalid access attempt"
			response.sendRedirect("/offload/management/error.jsp");
			return;
		}
		
        List<FileItem> items = null;
        try {
			items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
		} catch (FileUploadException e1) {
			//"Cannot parse the MultiPart request"
			response.sendRedirect("/offload/management/error.jsp?err=9");
			return;
		}
        
        String csvSepChar = null;
        for (FileItem item : items) {
        	if (item.getFieldName().equals("sepChar")) csvSepChar = item.getString();
        }
        if (csvSepChar == null) csvSepChar = ","; //Should be set if the form was properly sent, but just in case
        
        ArrayList<DbEntry> dbEntries = new ArrayList<DbEntry>();
        
        for (FileItem item : items) {
            if (!item.isFormField()) {
            	
                String fileName = FilenameUtils.getName(item.getName());
                String extension = fileName.substring(fileName.length()-4);
                if (!extension.equals(".csv")) {
        			//"The file is not a .csv file"
        			response.sendRedirect("/offload/management/error.jsp?err=8&filename=" + fileName);
        			return;
        		}
                
                String algName = item.getFieldName(); //It will be an AlgName
                InputStream fileContent = item.getInputStream();
                DbEntry dbEntry;
                String currentLine;
                String[] firstAndRest;
                String[] inputParams;
                double startTime;
                
                for (int i = 0; i < 10; i++) { //TODO for fileContent readLine
                	currentLine = i*1000000 + "," + i*1000000;
                	dbEntry = new DbEntry();
                	firstAndRest = currentLine.split(csvSepChar, 2);
                	dbEntry.algInputRep = Double.parseDouble(firstAndRest[0]);
                	inputParams = firstAndRest[1].split(csvSepChar);
                	dbEntry.algName = AlgName.valueOf(algName);
                	startTime = ((double) System.nanoTime()) / 1000000.0;
                    Algorithms.executeServer(dbEntry.algName, inputParams); //We don't care about the result returned
                    dbEntry.runTimeMs = ((double) System.nanoTime()) / 1000000.0 - startTime;
                    dbEntries.add(dbEntry);
                }
                
            }
        }
        
        for (int i = 0; i < dbEntries.size(); i++) {
        	out.println(dbEntries.get(i).algName + " " + dbEntries.get(i).algInputRep + " " + dbEntries.get(i).runTimeMs);
        }
	 
		
		
	
		
		
		
		
		
		
		
		
		
		/*
		
		String algCostsDbPath = getServletContext().getRealPath(File.separator) + "management" + File.separatorChar + "algCosts.db";
		 
		File algCostsDb = new File(algCostsDbPath);
		if (algCostsDb.exists()) algCostsDb.delete();
        
		Connection connection = null;
		Statement statement = null;
		ResultSet results = null;
		boolean success = false;
		try {
			
			//Avoid loading the SO specific native libraries (which actually were manually removed from the JAR file)
			//(then the JAR file couldn't be placed in the WebApp "WEB-INF/lib" folder)
			System.setProperty("sqlite.purejava", "true");
			
			//Load the SQLite-JDBC driver using the current class loader
			Class.forName("org.sqlite.JDBC");
			
		    connection = DriverManager.getConnection("jdbc:sqlite:" + algCostsDbPath);
			statement = connection.createStatement(); //Default query timeout = 3000 seconds
			statement.executeUpdate("drop table if exists person");
			statement.executeUpdate("create table person (id integer, name string)");
			statement.executeUpdate("insert into person values(1, 'leo')");
			statement.executeUpdate("insert into person values(2, 'yui')");
			results = statement.executeQuery("select * from person");
			while(results.next()) { //Read the result set
				out.println("name = " + results.getString("name"));
				out.println("id = " + results.getInt("id"));
			}
			success = true;
		} catch (SQLException e) {
			//"Database error"
			response.sendRedirect("/offload/management/error.jsp?err=5");
		} catch (ClassNotFoundException e) {
			//"Could not load the SQLite-JDBC driver using the current class loader. The class org.sqlite.JDBC was not found."
			response.sendRedirect("/offload/management/error.jsp?err=6");
		} finally {
			try {
				if (results != null) results.close();
				if (statement != null) statement.close();
				if (connection != null) connection.close();
			} catch (SQLException e) {
				//"Problems releasing database objects"
				response.sendRedirect("/offload/management/error.jsp?err=7");
				success = false;
			}
		}
		if (!success) return;
		
		*/
		
		out.println("</body>");
		out.println("</html>");
		
		//request.getSession().setAttribute("dbReady", true);
		//response.sendRedirect("/offload/management/menu.jsp");
	}
	
}
