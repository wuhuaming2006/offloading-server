package vegetables;

public class Zucchini {
	
	//Before it was mandatory to name this class ParseAndCall, now its name can be any
	//Each function corresponds to a potentially offloadable algorithm (part of code of an Android application)
	//The functions here actually parse the input parameters (from Strings to whatever) that these algorithms would need, and call them
	
	public static String doSomeLoops2(String... parameters) {
		long nLoops = Long.parseLong(parameters[0]); //Parsing of the input parameters
		return Tomato.doIterations(nLoops); //No casting needed of the output result, it is already a String
	}
	
	public static String doublingOf(String... parameters) {
		int mushroom = Integer.parseInt(parameters[0]); //Parsing of the input parameters
		Tomato tomato = new Tomato();
		int result = tomato.doSauce(mushroom);
		return Integer.toString(result); //In this case, the output parameter is an Integer so casting to String is needed
	}
	
}
