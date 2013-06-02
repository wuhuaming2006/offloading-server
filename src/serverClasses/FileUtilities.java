package serverClasses;

import java.io.*;

/** 
 Read and write a file using an explicit encoding.
 Removing the encoding from this code will simply cause the 
 system's default encoding to be used instead.  
 */
public class FileUtilities {

	private static final String CLASSES_DIR = "/home/joan/PFC/git-offloading-server/src/serverClasses/";

	public void addPackageAndAlgName(String packageName, String newAlgName) throws Exception{
		File outFile = new File("Temp.tmp");
		// input
		FileInputStream fis  = new FileInputStream(CLASSES_DIR + "Algorithms.java");
		BufferedReader in = new BufferedReader
				(new InputStreamReader(fis));

		// output         
		FileOutputStream fos = new FileOutputStream(outFile);
		PrintWriter out = new PrintWriter(fos);
		String thisLine = "";
		while ((thisLine = in.readLine()) != null) {
			//if(i == 3) out.println("import " + packageName + ";" );
			out.println(thisLine);
			if(thisLine.contains("public static enum AlgName {")) out.println("\t\t"+packageName+",");
			if(thisLine.contains("switch (algName)")) {
				out.println("\t\tcase "+ packageName + ":");
				out.println("\t\t\treturn "+ packageName + "." + newAlgName + ".ParseAndCall(parameters);");
			}
			
		}
		out.flush();
		out.close();
		in.close();

		File oldAlgs = new File (CLASSES_DIR + "Algorithms.java");
		oldAlgs.delete();
		outFile.renameTo(oldAlgs);
	}
}