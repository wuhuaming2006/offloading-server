package serverClasses;

import java.io.*;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;

/** 
 Read and write a file using an explicit encoding.
 Removing the encoding from this code will simply cause the 
 system's default encoding to be used instead.  
 */
public class FileUtilities {


	/** Read the contents of the given file. */
	//	public void read() throws Exception {
	//		log("Reading from file.");
	//		StringBuilder text = new StringBuilder();
	//		String NL = System.getProperty("line.separator");
	//		Scanner scanner = new Scanner(new FileInputStream(fFileName), fEncoding);
	//		try {
	//
	//			while (scanner.hasNextLine()){
	//				String line = scanner.nextLine();
	//				if (line.contains("public class")) {
	//					String newAlgName = line.split("class ")[1].split("\\s+")[0];
	//					addAlgNameEnum(newAlgName);
	//				}
	//				//if ((line.contains("public")==true) && (line.contains("class")==false))
	//				//  text.append(line + NL);
	//			}
	//		}
	//		finally{
	//			scanner.close();
	//		}
	//		log("Text read in: " + text);
	//
	//		/*Writer out = new OutputStreamWriter(new FileOutputStream(fFileName), fEncoding);
	//    try {
	//      out.write(text.toString());
	//    }
	//    finally {
	//      out.close();
	//    }*/
	//	}

	public void addAlgNameEnum(String newAlgName) throws Exception {
		File outFile = new File("Temp.tmp");
		int lineno=6;
		// input
		FileInputStream fis  = new FileInputStream("Algorithms.java");
		BufferedReader in = new BufferedReader
				(new InputStreamReader(fis));

		// output         
		FileOutputStream fos = new FileOutputStream(outFile);
		PrintWriter out = new PrintWriter(fos);
		String thisLine = "";
		int i =1;
		while ((thisLine = in.readLine()) != null) {
			if(i == lineno) out.println("\t\t"+newAlgName+",");
			out.println(thisLine);
			i++;
		}
		out.flush();
		out.close();
		in.close();

		File oldAlgs = new File ("Algorithms.java");
		oldAlgs.delete();
		outFile.renameTo(oldAlgs);
	}

	public String getClassFileDir (String pathname) {
//		File f = new File(pathname);
//		File[] matchingFiles = f.listFiles(new FilenameFilter() {
//			public boolean accept(File dir, String name) {
//				return name.endsWith("class");
//			}
//		});
//		System.out.println("The matching files are" + matchingFiles.toString());
//		return matchingFiles.toString();
		return "a";
	}

	public String getPackageFromClassOrDirectory(String pathname) throws IOException {
		File f = new File(pathname);
		if (f.isDirectory()) {
			System.out.println(pathname + "is a Directory");
			pathname = getClassFileDir(pathname);
		}
		else System.out.println(pathname + "is not a Directory");
 
		
		ClassParser cp = new ClassParser (pathname);
		//String packageName = cp.parse().getClass().getPackage().getName();
		JavaClass jc = cp.parse();
		String packageName = jc.getPackageName();
		return packageName;
	}
}