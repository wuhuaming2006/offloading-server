package serverClasses;

public class Algorithms {

	public static enum AlgName {
		doSomeLoops,
		fibonacciRecursive,
		fibonacciIterative,
		factorial,
		isPrime
	}

	public static String executeServer(AlgName algName, String... parameters) {
		switch (algName) {
		case doSomeLoops:
			long nLoops = Long.parseLong(parameters[0]); //Parsing of the input parameters
			return doSomeLoops(nLoops); //No casting needed of the output result, it is already a String
		case fibonacciRecursive:
			int fiboSeqRecElem = Integer.parseInt(parameters[0]); //Parsing of the input parameters
			int resultFibRec = fibonacciRecursive(fiboSeqRecElem);
			return Integer.toString(resultFibRec); //In this case, the output parameter is an Integer so casting to String is needed
		case fibonacciIterative:
			int fiboSeqItElem = Integer.parseInt(parameters[0]); //Parsing of the input parameters
			int resultFibIt = fibonacciIterative(fiboSeqItElem);
			return Integer.toString(resultFibIt); //In this case, the output parameter is an Integer so casting to String is needed
		case factorial:
			long factorialNum = Long.parseLong(parameters[0]); //Parsing of the input parameters
			long resultFact = factorial(factorialNum);
			return Long.toString(resultFact); //In this case, the output parameter is a Long so casting to String is needed
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

	private static int fibonacciIterative(int n)
	{
		if (n == 0) return 0;
		if (n == 1) return 1;

		int prevPrev = 0;
		int prev = 1;
		int result = 0;

		for (int i = 2; i <= n; i++)
		{
			result = prev + prevPrev;
			prevPrev = prev;
			prev = result;
		}
		return result;
	}

	public static long factorial(long number) {
		if (number <= 1) return 1;
		else return number * factorial(number - 1);
	}

	private static boolean isPrime(int n) {
		for (int i = 2; i < n; i++) {
			if (n % i == 0) return false;
		}
		return true;
	}

}