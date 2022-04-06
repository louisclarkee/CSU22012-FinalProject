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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class NameSearch {

	TST<Integer> tst;
	File stopsFile;
	String stopsFilePath;


	// Constructor which 
	public NameSearch(String filePath) throws FileNotFoundException{
		this.stopsFile = new File(filePath);
		this.stopsFilePath = filePath;
		this.tst = new TST<Integer>();

		Scanner sc = new Scanner(stopsFile);

		//infoLocation stores the line number in the file that scanner is currently reading
		int infoLocation = 2;
		String stopName;

		//first line in file is not needed
		sc.nextLine();	
		while(sc.hasNextLine()) {

			stopName = formatTSTkey(sc.nextLine());
			//stopName is a string Key that points to an integer value which stores the lineNumber of the stop information in the file
			tst.put(stopName, infoLocation);
			infoLocation++;			
		}	
		sc.close();
	}	


	//Helper function to take in raw line from stops.txt and return stop name in correct format
	//to be used as a key for the TST
	public String formatTSTkey(String input) {
		String prefix = "";
		String[] stopInfo = input.split(",");

		String  stopName = stopInfo[2];

		String[] stopNameSplit = stopName.split(" ");

		List<String> t = Arrays.asList(stopNameSplit);
		LinkedList<String> temp = new LinkedList<>(t);
		while (temp.get(0).equals("NB") || temp.get(0).equals("SB") || temp.get(0).equals("WB") || temp.get(0).equals("EB") || temp.get(0).equals("FLAGSTOP")){
			prefix = temp.remove(0);
		}
		stopName = temp.toString();
		stopName = stopName + prefix;
		stopName = stopName.replaceAll("\\p{P}", ""); 
		return stopName;
	}

	public String formatStopInfo(String input) {
		String[] stopInfo = input.split(",");
		input = "- Stop ID: " + stopInfo[0]+ "\n" +
				"- Stop Code: " + stopInfo[1] + "\n" +
				"- Stop Name: " + stopInfo[2] + "\n" +
				"- Stop Description: "+ stopInfo[3]+ "\n";
		return input;
	}
	
	
	public void printMatchingStops(String inputString) throws IOException {
		String stopLine;
		
		ArrayList<Integer> linesPrintedAlready = new ArrayList<Integer>();
		StringBuffer inputStringBuffer = new StringBuffer(inputString);
		Iterable<String> keyList = tst.keysWithPrefix(inputStringBuffer.toString());
		Iterator<String> iterator = keyList.iterator();
		
		int stopCount = 1;
		
		if(iterator.hasNext()) {
			
			while(inputStringBuffer.length()>0) {
				keyList = tst.keysWithPrefix(inputStringBuffer.toString());
				iterator = keyList.iterator();


				while(iterator.hasNext()&& stopCount < 11) {
					int lineNumber = tst.get(iterator.next().toString()) -1;
					if(!linesPrintedAlready.contains(lineNumber)) {
						System.out.println("------------------------------\n"
								+ "Matching stop no."+ stopCount);
						stopLine = Files.readAllLines(Paths.get(stopsFilePath)).get(lineNumber);
						System.out.print(formatStopInfo(stopLine));	
						linesPrintedAlready.add(lineNumber);
						stopCount++;
					}
				}	
				inputStringBuffer.deleteCharAt(inputStringBuffer.length()-1);
			}

		}
		else {
			System.out.println("Error: No stops match your search query!");
		}
	}	
}
