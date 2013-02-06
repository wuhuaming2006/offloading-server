package serverClasses;

public class Algorithms {
	
	public enum AlgName {
		doSomeLoops,
		fibonacci
	}
	
	public String executeServer(AlgName algName, String... parameters) {
		switch (algName) {
		case doSomeLoops:
			long nLoops = Long.parseLong(parameters[0]); //Parsing of the input parameters
			return doSomeLoops(nLoops); //No casting needed of the output result, it is already a String
		case fibonacci:
			int fiboSeqElem = Integer.parseInt(parameters[0]); //Parsing of the input parameters
			int result = fibonacci(fiboSeqElem);
			return Integer.toString(result); //In this case, the output parameter is an Integer so casting to String is needed
		default:
			return "Error";
		}
	}

	private String doSomeLoops(long nLoops) {
		long i = 0;
		while (i < nLoops) i++;
		return "Done";
	}

	private int fibonacci(int n) {
		if (n < 2) {
			return n;
		}
		else {
			return fibonacci(n-1)+fibonacci(n-2);
		}
	}

}