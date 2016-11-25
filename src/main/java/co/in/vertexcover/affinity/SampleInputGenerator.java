package co.in.vertexcover.affinity;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.apache.commons.io.FileUtils;

public class SampleInputGenerator {

	public static void main(String[] args) throws IOException {
		
		final String inputFileName = "input.txt";
		String inputFilePath = App.getProjectPath() + "data" + File.separator + inputFileName;  
		File f = new File(inputFilePath);
		FileUtils.writeStringToFile(f, "", false);
		
		for(int i = 1; i <= 100; i++) {
			String e = "entity" + i;
			HashSet<String> set = new HashSet<>();
			int setSize = 5 + (int)(Math.random() * 20);
			while(set.size() < setSize) {
				String s = "";
				s += Integer.toString((int)(Math.random() * 10));
				s += Integer.toString((int)(Math.random() * 10));
				set.add(s);
			}
			
			for (String s : set) {
			    int rand100 = (int)(Math.random() * 10000);
			    String line = e + "," + s + "," + (rand100 + 1) + "\n";
			    FileUtils.writeStringToFile(f, line, true);
			    System.out.println(line);
			}
		}
	}

}
