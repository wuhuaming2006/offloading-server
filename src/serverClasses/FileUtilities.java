package serverClasses;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class FileUtilities {

	public void addPackageAndAlgName(String packageName, String newAlgName, String classesDir) throws Exception {
		
		File outFile = new File("Temp.tmp");
		
		// input
		FileInputStream fis  = new FileInputStream(classesDir + "Algorithms.java");
		BufferedReader in = new BufferedReader (new InputStreamReader(fis));

		// output         
		FileOutputStream fos = new FileOutputStream(outFile);
		PrintWriter out = new PrintWriter(fos);
		String thisLine = "";
		while ((thisLine = in.readLine()) != null) {
			out.println(thisLine);
			if (thisLine.contains("public static enum AlgName {")) out.println("\t\t"+packageName+",");
			if (thisLine.contains("switch (algName)")) {
				out.println("\t\tcase "+ packageName + ":");
				out.println("\t\t\treturn "+ packageName + "." + newAlgName + ".parseAndCall(parameters);");
			}
		}
		out.flush();
		out.close();
		in.close();

		File oldAlgs = new File (classesDir + "Algorithms.java");
		oldAlgs.delete();
		outFile.renameTo(oldAlgs);
	}
}