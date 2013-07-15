package vegetables;

import vegetables.potatoes.Fried;

public class Tomato {
	
	public static String doIterations(long nLoops) {
		long i = 0;
		while (i < nLoops) i++;
		return "Done";
	}
	
	public int doSauce(int n) {
		int j = 0;
		for (int i = 0; i < n; i++) j++;
		int barTomas = Fried.doFried(n);   
		return j + barTomas;
	}
	
}
