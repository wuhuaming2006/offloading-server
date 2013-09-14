package serverClasses;

public class Algorithms {
	
	public static final int MAX_REPETITIONS = 20;

	public static enum AlgName {
		doSomeLoops,
		fileAndLoops,
		fibonacciRecursive,
		fibonacciIterative,
		randomArraySelectionSort,
		isPrime
	}

	public static String executeServer(AlgName algName, String... parameters) {
		switch (algName) {
		case doSomeLoops:
			long nLoops = Long.parseLong(parameters[0]); //Parsing of the input parameters
			return doSomeLoops(nLoops); //No casting needed of the output result, it is already a String
		case fileAndLoops:
			long nLoops2 = Long.parseLong(parameters[0]); //Parsing of the input parameters
			//Parameters[1] is a file encoded as a String with Base64
			//We only want to test that it was correctly received by returning its size, so no parsing is needed
			int fileLength = fileAndLoops(nLoops2, parameters[1]);
			return Integer.toString(fileLength); //In this case, the output parameter is an Integer so casting to String is needed
		case fibonacciIterative:
			return de.fuberlin.enginetesting.SimpleAlgs.fibonacciIterative(parameters);
		case fibonacciRecursive:
			return de.fuberlin.enginetesting.SimpleAlgs.fibonacciRecursive(parameters);
		case randomArraySelectionSort:
			return de.fuberlin.enginetesting.SimpleAlgs.randomArraySelectionSort(parameters);
		case isPrime:
			return de.fuberlin.enginetesting.SimpleAlgs.isPrime(parameters);
		default:
			return "Error";
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
