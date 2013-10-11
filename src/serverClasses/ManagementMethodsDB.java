package serverClasses;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ManagementMethodsDB implements ServletContextListener {
	
	public static Connection connection = null;
	static String libsDir = null;

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		libsDir = arg0.getServletContext().getRealPath(File.separator) + "WEB-INF" + File.separatorChar + "lib" + File.separatorChar;
		
		//Avoid loading the SO specific native libraries, which actually were manually removed from the JAR file
		//If not, the JAR file should be placed in the general "lib" folder of Tomcat, which we can not access
		//(instead, we place it in the WebApp "WEB-INF/lib" folder)
		System.setProperty("sqlite.purejava", "true");
		
		//Load the SQLite-JDBC driver using the current class loader
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		//If it did not exist, creates the database
	    try {
			connection = DriverManager.getConnection("jdbc:sqlite:" + arg0.getServletContext().getRealPath(File.separator) + "uploadedMethodsDB" + File.separatorChar + "methods.db");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		    
	}
			
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static String addMethod(String methodName, String methodPath) {
		
		String returnString = "OK";
		Statement statement = null;
		
		try {
			
			statement = ManagementMethodsDB.connection.createStatement(); //Default query timeout = 3000 seconds
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS methodsTable (methodName TEXT PRIMARY KEY,  methodPath TEXT)");
			statement.executeUpdate("INSERT INTO methodsTable VALUES ('" + methodName + "', '" + methodPath + "')");
			
		} catch (SQLException e) {
			//"Database error"
			returnString = "error.jsp?err=5";
		} finally {
			try {
				if (statement != null) statement.close();
			} catch (SQLException e) {
				//"Problems releasing database objects"
				returnString = "error.jsp?err=7";
			}
		}
		
		return returnString;
		
	}

	public static String removeMethod(String methodName) {
	
		String returnString = "OK";
		Statement statement = null;
		
		try {
			
			statement = ManagementMethodsDB.connection.createStatement(); //Default query timeout = 3000 seconds
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS methodsTable (methodName TEXT PRIMARY KEY,  methodPath TEXT)");
			statement.executeUpdate("DELETE FROM methodsTable WHERE methodName='" + methodName + "'");
			
		} catch (SQLException e) {
			//"Database error"
			returnString = "error.jsp?err=5";
		} finally {
			try {
				if (statement != null) statement.close();
			} catch (SQLException e) {
				//"Problems releasing database objects"
				returnString = "error.jsp?err=7";
			}
		}
		
		return returnString;
		
	}
	
	public static String[] getMethodNames() {
		
		Statement statement = null;
		ResultSet resultSet = null;
		String[] methodNames = null;
		
		try {
			
			statement = ManagementMethodsDB.connection.createStatement(); //Default query timeout = 3000 seconds
			resultSet = statement.executeQuery("SELECT COUNT(*) AS count FROM methodsTable");
			int numRows = 0;
			while (resultSet.next()) numRows = resultSet.getInt("count");
			resultSet.close();
			resultSet = statement.executeQuery("SELECT methodName FROM methodsTable");
			methodNames = new String[numRows];
			int i = 0;
			while (resultSet.next()) {
				methodNames[i] = resultSet.getString("methodName");
				i++;
			}
		} catch (SQLException e) {
			//"Database error"
		} finally {
			try {
				if (resultSet != null) resultSet.close();
				if (statement != null) statement.close();
			} catch (SQLException e) {
				//"Problems releasing database objects"
			}
		}
		
		return methodNames;
		
	}
	
}
