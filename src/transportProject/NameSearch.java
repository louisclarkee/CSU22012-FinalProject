package transportProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

import edu.princeton.cs.algs4.TST;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class NameSearch {
	
	TST<Integer> tst;
	File stopsFile;
	String stopsFilePath;
	
	
	public NameSearch(String filePath) throws FileNotFoundException{
		this.stopsFile = new File(filePath);
		this.stopsFilePath = filePath;
		this.tst = new TST<Integer>();
		Scanner sc = new Scanner(stopsFile);
		int infoLocation = 2;
		String nextLine;
		sc.nextLine();
		
		while(sc.hasNextLine()) {
			nextLine = sc.nextLine();
			String prefix = "";
			String[] stopInfo = nextLine.split(",");
            String  stopName = stopInfo[2];
            String[] stopNameSplit = stopName.split(" ");
            List<String> t = Arrays.asList(stopNameSplit);
            LinkedList<String> temp = new LinkedList<>(t);
            while (temp.get(0).equals("NB") || temp.get(0).equals("SB") || temp.get(0).equals("WB") || temp.get(0).equals("EB") || temp.get(0).equals("FLAGSTOP")){
            	prefix = temp.remove(0);
            }
            stopName = temp.toString();
            stopName = stopName + prefix;
            //Remove all punctuation added by toString().
            stopName = stopName.replaceAll("\\p{P}", "");
            //Puts stop name into TST along with line number.
            System.out.println(stopName);
            tst.put(stopName, infoLocation);
            infoLocation++;			
		}	
		sc.close();
	}
	
	
}
