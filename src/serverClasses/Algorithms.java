package serverClasses;

public class Algorithms {

	public static enum AlgName {
		verdures,
		doSomeLoops,
		fibonacciRecursive,
		fibonacciIterative,
		randomArraySelectionSort,
		isPrime
	}

	public static String executeServer(AlgName algName, String... parameters) {
		switch (algName) {
		case verdures:
			return verdures.ParseAndCall.parseAndCall(parameters);
		case doSomeLoops:
			long nLoops = Long.parseLong(parameters[0]); //Parsing of the input parameters
			return doSomeLoops(nLoops); //No casting needed of the output result, it is already a String
		case fibonacciRecursive:
			int fiboSeqRecElem = Integer.parseInt(parameters[0]); //Parsing of the input parameters
			int resultFibRec = fibonacciRecursive(fiboSeqRecElem);
			return Integer.toString(resultFibRec); //In this case, the output parameter is an Integer so casting to String is needed
		case fibonacciIterative:
			int fiboSeqItElem = Integer.parseInt(parameters[0]); //Parsing of the input parameters
			long resultFibIt = fibonacciIterative(fiboSeqItElem);
			return Long.toString(resultFibIt); //In this case, the output parameter is a Long so casting to String is needed
		case randomArraySelectionSort:
			int numOfElements = Integer.parseInt(parameters[0]); //Parsing of the input parameters
			return randomArraySelectionSort(numOfElements); //No casting needed of the output result, it is already a String
		case isPrime:
			int number = Integer.parseInt(parameters[0]); //Parsing of the input parameters
			boolean isPrime = isPrime(number);
			return Boolean.toString(isPrime); //In this case, the output parameter is a Boolean so casting to String is needed
		default:
			return "Error";
		}
	}

	private static String doSomeLoops(long nLoops) {
		long i = 0;
		while (i < nLoops) i++;
		return "Done";
	}

	private static int fibonacciRecursive(int n) {
		if (n < 2) {
			return n;
		}
		else {
			return fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);
		}
	}

	private static long fibonacciIterative(int n) {
		if (n == 0) return 0;
		if (n == 1) return 1;

		long prevPrev = 0;
		long prev = 1;
		long result = 0;

		for (int i = 2; i <= n; i++) {
			result = prev + prevPrev;
			prevPrev = prev;
			prev = result;
		}
		return result;
	}

	private static String randomArraySelectionSort(int n) {
		double[] a = new double[n];
		for (int k = 0 ; k < n ; k++) a[k] = Math.random();
		int i, j;
		int iMin;
		double aux;    
		for (j = 0; j < n-1; j++) {
			iMin = j;
			for ( i = j+1; i < n; i++) {
				if (a[i] < a[iMin]) {
					iMin = i;
				}
			}
			if ( iMin != j ) {
				aux = a[iMin];
				a[iMin] = a[j];
				a[j] = aux;
			}
		}
		return "Done";
	}

	private static boolean isPrime(int n) {
		for (int i = 2; i < n; i++) {
			if (n % i == 0) return false;
		}
		return true;
	}

}
