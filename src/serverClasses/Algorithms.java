package serverClasses;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Algorithms {
	
	public static final int MAX_REPETITIONS = 20;
	public static double lastExecutionTime = 0.0;
	public static double lastExecutionOverhead = 0.0;

	public static String executeServer(String methodName, String... parameters) {
		
		lastExecutionOverhead = 0.0;
		
		if (methodName.equals("doSomeLoops")) {
			long nLoops = Long.parseLong(parameters[0]); //Parsing of the input parameters
			double startTime = ((double) System.nanoTime()) / 1000000.0;
			String resultString =  doSomeLoops(nLoops); //No casting needed of the output result, it is already a String
			lastExecutionTime = ((double) System.nanoTime()) / 1000000.0 - startTime;
			return resultString;
		}
		else if (methodName.equals("fileAndLoops")) {
			long nLoops = Long.parseLong(parameters[0]); //Parsing of the input parameters
			//Parameters[1] is a file encoded as a String with Base64
			//We only want to test that it was correctly received by returning its size, so no parsing is needed
			int fileLength = fileAndLoops(nLoops, parameters[1]);
			double startTime = ((double) System.nanoTime()) / 1000000.0;
			String resultString = Integer.toString(fileLength); //In this case, the output parameter is an Integer so casting to String is needed
			lastExecutionTime = ((double) System.nanoTime()) / 1000000.0 - startTime;
			return resultString;
		}
		else if (methodName.equals("fibonacciIterative")) {
			double startTime = ((double) System.nanoTime()) / 1000000.0;
			String resultString = de.fuberlin.enginetesting.SimpleAlgs.fibonacciIterative(parameters);
			lastExecutionTime = ((double) System.nanoTime()) / 1000000.0 - startTime;
			return resultString;
		}
		else if (methodName.equals("fibonacciRecursive")) {
			double startTime = ((double) System.nanoTime()) / 1000000.0;
			String resultString = de.fuberlin.enginetesting.SimpleAlgs.fibonacciRecursive(parameters);
			lastExecutionTime = ((double) System.nanoTime()) / 1000000.0 - startTime;
			return resultString;
		}
		
		else if (methodName.equals("randomArraySelectionSort")) {
			double startTime = ((double) System.nanoTime()) / 1000000.0;
			String resultString = de.fuberlin.enginetesting.SimpleAlgs.randomArraySelectionSort(parameters);
			lastExecutionTime = ((double) System.nanoTime()) / 1000000.0 - startTime;
			return resultString;
		}
		else if (methodName.equals("isPrime")) {
			double startTime = ((double) System.nanoTime()) / 1000000.0;
			String resultString = de.fuberlin.enginetesting.SimpleAlgs.isPrime(parameters);
			lastExecutionTime = ((double) System.nanoTime()) / 1000000.0 - startTime;
			return resultString;
		}
		else {
			double startTimeOverhead = ((double) System.nanoTime()) / 1000000.0;
			boolean allRight = true;
			Statement statement = null;
			ResultSet resultSet = null;
			String pathMethod = null;
			try {
				statement = ManagementMethodsDB.connection.createStatement(); //Default query timeout = 3000 seconds
				resultSet = statement.executeQuery("SELECT methodPath FROM methodsTable WHERE methodName='" + methodName + "'");
				while (resultSet.next()) { //Should iterate only once
					pathMethod = resultSet.getString("methodPath");
				}
			} catch (SQLException e) {
				allRight = false;
			} finally {
				try {
					if (resultSet != null) resultSet.close();
					if (statement != null) statement.close();
				} catch (SQLException e) {
					allRight = false;
				}
			}
			if (!allRight) return "Error";
			else {
				Class<?> classWithMethods = null;
				try {
					classWithMethods = Class.forName(pathMethod);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					return "Error";
				}
				Method[] methods = classWithMethods.getMethods(); //TODO do not retrieve all methods but only 1!!!
				for (int i = 0; i < methods.length; i++) {
					if (methods[i].getName().equals(methodName)) {
						try {
							Object[] parametersAsObjects = new Object[parameters.length];
							for (int j = 0; j < parameters.length; j++) parametersAsObjects[j] = (Object) parameters[j];
							lastExecutionOverhead = ((double) System.nanoTime()) / 1000000.0 - startTimeOverhead;
							double startTime = ((double) System.nanoTime()) / 1000000.0;
							Object returnObject = methods[i].invoke(null, parametersAsObjects);
							lastExecutionTime = ((double) System.nanoTime()) / 1000000.0 - startTime;
							return (String) returnObject;
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
							return "Error";
						} catch (IllegalAccessException e) {
							e.printStackTrace();
							return "Error";
						} catch (InvocationTargetException e) {
							e.printStackTrace();
							return "Error";
						}
					}
				}
				return "Error"; //This should be unreachable
			}
		}
			
	}

	private static String doSomeLoops(long nLoops) {
		long i = 0;
		while (i < nLoops) i++;
		return "Done";
	}
	
	private static int fileAndLoops(long nLoops, String fileContents) {
		long i = 0;
		while (i < nLoops) i++;
		return fileContents.length();
	}

}
