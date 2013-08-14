package serverClasses;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
	
	//"NO" if not generated yet, "YES" if it was correctly generated
	//Otherwise it contains a relative URL redirection (to be executed by a JS in pleaseWait.jsp) to error.jsp indicating the error as a GET parameter
	public static String dbReady = "NO";
	
	private class DbEntry {
		public long algInputRep;
		public double runTimeMs;
	}
	
	private class DbTable {
		public AlgName algName;
		public ArrayList<DbEntry> dbEntries;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
		dbReady = "NO";
		
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
		
        //Although the doPost method will before this new Thread finishes, items will not be released because it still has references in the Thread
        //However, this does not work if we pass a reference to a HttpServletRequest object like the request to the Thread, apparently it is always released when the doPost finishes
        //(this might have a relation with the destroy() method of the Java Servlets)
		GenerateDbThread generateDbThread = new GenerateDbThread(items);
		generateDbThread.start();
		
		response.sendRedirect("/offload/management/pleaseWait.jsp");
		
	}
	
	private class GenerateDbThread extends Thread {
		
		List<FileItem> items;
		
		GenerateDbThread(List<FileItem> items) {
			this.items = items;
		}
		
		public void run() {

	        String csvSepChar = null;
	        for (FileItem item : items) {
	        	if (item.getFieldName().equals("sepChar")) csvSepChar = item.getString();
	        }
	        if (csvSepChar == null) csvSepChar = ","; //Should be set if the form was properly sent, but just in case
	        
	        ArrayList<DbTable> dbTables = new ArrayList<DbTable>();
	        DbTable dbTable;
	        
	        for (FileItem item : items) {
	            if (!item.isFormField()) {
	            	
	            	dbTable = new DbTable();
	            	dbTable.dbEntries = new ArrayList<DbEntry>();
	            	
	                String fileName = FilenameUtils.getName(item.getName());
	                String extension = fileName.substring(fileName.length()-4);
	                if (!extension.equals(".csv")) {
	        			//"The file is not a .csv file"
	                	dbReady = "error.jsp?err=8&filename=" + fileName;
	        			return;
	        		}
	                
	                String algName = item.getFieldName(); //It will be an AlgName
	                dbTable.algName = AlgName.valueOf(algName);
	                InputStream fileContent = null;
					try {
						fileContent = item.getInputStream();
					//Before executing this in a separate Thread, this was handled by the "throws IOException" of the goGet and goPost methods
					} catch (IOException e) {
						e.printStackTrace();
					}
	                BufferedReader fileContentReader = new BufferedReader(new InputStreamReader(fileContent));
	                
	                DbEntry dbEntry;
	                String[] firstAndRest;
	                String[] inputParams;
	                double startTime;
	                double elapsedTime;
	                
	                String currentLine = "";
	                try {
						while ((currentLine = fileContentReader.readLine()) != null) {
							if (currentLine.contains(csvSepChar)) { //Skip empty lines
								dbEntry = new DbEntry();
						    	firstAndRest = currentLine.split(csvSepChar, 2);
						    	dbEntry.algInputRep = Long.parseLong(firstAndRest[0]);
						    	inputParams = firstAndRest[1].split(csvSepChar);
						    	//Execute 3 times, average only the last 2 times
						    	//(we skip the first because sometimes it has an extra delay because of loading classes)
						    	for (int i = 0; i < 3; i++) {
						    		startTime = ((double) System.nanoTime()) / 1000000.0;
							        Algorithms.executeServer(dbTable.algName, inputParams); //We don't care about the result returned
							        elapsedTime = ((double) System.nanoTime()) / 1000000.0 - startTime;
							        if (i == 1) dbEntry.runTimeMs = elapsedTime;
							        else if (i == 2) dbEntry.runTimeMs = GenerateDB.round((dbEntry.runTimeMs + elapsedTime) / 2.0, 3);
						    	}
						        dbTable.dbEntries.add(dbEntry);
							}
						}
						
						dbTables.add(dbTable);
						
					//Before executing this in a separate Thread, this was handled by the "throws IOException" of the goGet and goPost methods
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
	            }
	        }
			
			String algCostsDbPath = getServletContext().getRealPath(File.separator) + "management" + File.separatorChar + "algCosts.db";
			 
			File algCostsDb = new File(algCostsDbPath);
			if (algCostsDb.exists()) algCostsDb.delete();
	        
			Connection connection = null;
			Statement statement = null;
			PreparedStatement psInsert = null;
			PreparedStatement psDelete = null;
			boolean success = false;
			try {
				
				//Avoid loading the SO specific native libraries (which actually were manually removed from the JAR file)
				//(then the JAR file couldn't be placed in the WebApp "WEB-INF/lib" folder)
				System.setProperty("sqlite.purejava", "true");
				
				//Load the SQLite-JDBC driver using the current class loader
				Class.forName("org.sqlite.JDBC");
				
			    connection = DriverManager.getConnection("jdbc:sqlite:" + algCostsDbPath);
				statement = connection.createStatement(); //Default query timeout = 3000 seconds
				
				statement.executeUpdate("CREATE TABLE android_metadata (locale TEXT DEFAULT 'en_US')");
				statement.executeUpdate("INSERT INTO android_metadata VALUES ('en_US')");
				
				for (int i = 0; i < dbTables.size(); i++) {
					
					statement.executeUpdate("CREATE TABLE " + dbTables.get(i).algName + " (_id INTEGER PRIMARY KEY, inputRep INTEGER NOT NULL, runTimeMs REAL NOT NULL, serverGen INTEGER NOT NULL)");
					statement.executeUpdate("CREATE INDEX inputRepIndex ON algCostsTable (inputRep ASC)");
					
					String subQuery1 = "(SELECT COUNT(*) FROM " + dbTables.get(i).algName + " WHERE inputRep=?)";
					String subQuery2 = "(SELECT _id FROM " + dbTables.get(i).algName + " WHERE inputRep=? ORDER BY RANDOM() LIMIT 1)";
					psDelete = connection.prepareStatement("DELETE FROM " + dbTables.get(i).algName + " WHERE " + Algorithms.MAX_REPETITIONS + "<=" + subQuery1 + " AND _id=" + subQuery2);
					psInsert = connection.prepareStatement("INSERT INTO " + dbTables.get(i).algName + " (inputRep, runTimeMs, serverGen) VALUES (?, ?, ?)");
					
					for (int j = 0; j < dbTables.get(i).dbEntries.size(); j++) {
						psDelete.setLong(1, dbTables.get(i).dbEntries.get(j).algInputRep);
						psDelete.setLong(2, dbTables.get(i).dbEntries.get(j).algInputRep);
						psDelete.executeUpdate(); //If there are more than Algorithms.MAX_REPETITIONS rows with this inputRep, delete one of them randomly
						psInsert.setLong(1, dbTables.get(i).dbEntries.get(j).algInputRep);
						psInsert.setDouble(2, dbTables.get(i).dbEntries.get(j).runTimeMs);
						psInsert.setBoolean(3, true);
						psInsert.executeUpdate();
					}
					
				}
				
				success = true;
				
			} catch (SQLException e) {
				//"Database error"
				dbReady = "error.jsp?err=5";
			} catch (ClassNotFoundException e) {
				//"Could not load the SQLite-JDBC driver using the current class loader. The class org.sqlite.JDBC was not found."
				dbReady = "error.jsp?err=6";
			} finally {
				try {
					if (statement != null) statement.close();
					if (psInsert != null) psInsert.close();
					if (psDelete != null) psDelete.close();
					if (connection != null) connection.close();
				} catch (SQLException e) {
					//"Problems releasing database objects"
					dbReady = "error.jsp?err=7";
					success = false;
				}
			}
			if (!success) return;
			
			dbReady = "YES";
        	
		}
		
	}
	
	private static double round(double nD, int nDec) {
	  return Math.round(nD*Math.pow(10,nDec))/Math.pow(10,nDec);
	}
	
}
