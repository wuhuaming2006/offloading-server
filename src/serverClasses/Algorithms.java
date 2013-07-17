package serverClasses;

public class Algorithms {

	public static enum AlgName {
		doublingOf,
		doSomeLoops2,
		doSomeLoops,
		fibonacciRecursive,
		fibonacciIterative,
		randomArraySelectionSort,
		isPrime
	}

	public static String executeServer(AlgName algName, String... parameters) {
		switch (algName) {
		case doublingOf:
			return vegetables.Zucchini.doublingOf(parameters);
		case doSomeLoops2:
			return vegetables.Zucchini.doSomeLoops2(parameters);
		case doSomeLoops:
			long nLoops = Long.parseLong(parameters[0]); //Parsing of the input parameters
			return doSomeLoops(nLoops); //No casting needed of the output result, it is already a String
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

}
