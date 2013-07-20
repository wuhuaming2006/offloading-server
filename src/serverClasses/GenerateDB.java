package serverClasses;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
                BufferedReader fileContentReader = new BufferedReader(new InputStreamReader(fileContent));
                
                DbEntry dbEntry;
                String[] firstAndRest;
                String[] inputParams;
                double startTime;
                
                String currentLine = "";
                while ((currentLine = fileContentReader.readLine()) != null) {
                	if (currentLine.contains(csvSepChar)) { //Skip empty lines
                		dbEntry = new DbEntry();
                    	firstAndRest = currentLine.split(csvSepChar, 2);
                    	dbEntry.algInputRep = Double.parseDouble(firstAndRest[0]); //FIXME BLOB...
                    	inputParams = firstAndRest[1].split(csvSepChar);
                    	dbEntry.algName = AlgName.valueOf(algName);
                    	startTime = ((double) System.nanoTime()) / 1000000.0;
                        Algorithms.executeServer(dbEntry.algName, inputParams); //We don't care about the result returned
                        dbEntry.runTimeMs = GenerateDB.round(((double) System.nanoTime()) / 1000000.0 - startTime, 2);
                        dbEntries.add(dbEntry);
                	}
                }
            }
        }
		
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
			
			statement.executeUpdate("CREATE TABLE algCostsTable (_id INTEGER PRIMARY KEY, algName TEXT, inputRep BLOB, runTimeMs REAL)");
			statement.executeUpdate("CREATE TABLE android_metadata (locale TEXT DEFAULT 'en_US')");
			statement.executeUpdate("INSERT INTO android_metadata VALUES ('en_US')");
			
			for (int i = 0; i < dbEntries.size(); i++) {
				statement.executeUpdate("INSERT INTO algCostsTable (algName, inputRep, runTimeMs) VALUES ('" + dbEntries.get(i).algName +  "', " + dbEntries.get(i).algInputRep + ", " + dbEntries.get(i).runTimeMs + ")");
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
		
		request.getSession().setAttribute("dbReady", true);
		response.sendRedirect("/offload/management/menu.jsp");
	}
	
	private static double round(double nD, int nDec) {
	  return Math.round(nD*Math.pow(10,nDec))/Math.pow(10,nDec);
	}
	
}
