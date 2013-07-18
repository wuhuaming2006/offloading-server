package serverClasses;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

public class JarUtilities {

	public static ArrayList<String> getClassNamesInJar(JarFile jarFile) throws Exception {
		ArrayList<String> classNames = new ArrayList<String>();
		Enumeration<JarEntry> entries = jarFile.entries();
		while (entries.hasMoreElements()) {
			JarEntry entry = entries.nextElement();
			String entryName = entry.getName();
			if (entryName.endsWith(".class")) {
				ClassNode classNode = new ClassNode();
				InputStream classFileInputStream = jarFile.getInputStream(entry);
				try {
					ClassReader classReader = new ClassReader(classFileInputStream);
					classReader.accept(classNode, 0);
				} finally {
					classFileInputStream.close();
				}
				classNames.add(Type.getObjectType(classNode.name).getClassName());
			}
		}
		return classNames;
	}
	
	
	public static ArrayList<String> getMethodsFromClassInJar(JarFile jarFile, String classEntryName) throws Exception {
	  	Enumeration<JarEntry> entries = jarFile.entries();
	    while (entries.hasMoreElements()) {
	        JarEntry entry = entries.nextElement();
	        String entryName = entry.getName();
	        if (entryName.equals(classEntryName)) {
	            ClassNode classNode = new ClassNode();
	            InputStream classFileInputStream = jarFile.getInputStream(entry);
	            try {
	                ClassReader classReader = new ClassReader(classFileInputStream);
	                classReader.accept(classNode, 0);
	            } finally {
	                classFileInputStream.close();
	            }
	           return getMethodsFromClass(classNode);
	         }
	    }
	    return null;
	}
	
	
	public static ArrayList<String> getMethodsFromClass(ClassNode classNode) {
		ArrayList<String> listOfMethods = new ArrayList<String>();
		// The method signatures (e.g. - "public static void main(String[]) throws Exception")
		@SuppressWarnings("unchecked")
		List<MethodNode> methodNodes = classNode.methods;
		for (MethodNode methodNode : methodNodes) {
			listOfMethods.add(methodNode.name);
		}
		listOfMethods.remove(0);
		return listOfMethods;
	}
}
