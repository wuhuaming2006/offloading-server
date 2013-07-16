package serverClasses;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class FileUtilities {

	public static void addAlgorithm(String packageName, String algorithmsPath) {
		File outFile = new File("Temp.tmp");
		try {
			FileInputStream fis = new FileInputStream(algorithmsPath);
			BufferedReader in = new BufferedReader(new InputStreamReader(fis));
			FileOutputStream fos = new FileOutputStream(outFile);
			PrintWriter out = new PrintWriter(fos);
			String thisLine = "";
			while ((thisLine = in.readLine()) != null) {
				out.println(thisLine);
				//TODO This is for testing!!!!
				//TODO We need to detect the names of the algorithms in whatever class file the user indicates
				if (thisLine.contains("public static enum AlgName {")) out.println("\t\tdoSomeLoops2,");
				if (thisLine.contains("switch (algName) {")) {
					out.println("\t\tcase doSomeLoops2:");
					out.println("\t\t\treturn vegetables.Zucchini.doSomeLoops2(parameters);");
				}
			}		
			out.flush();
			out.close();
			in.close();
		} catch (FileNotFoundException e) { 
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		File oldAlgs = new File(algorithmsPath);
		oldAlgs.delete();
		outFile.renameTo(oldAlgs);
	}
	
}