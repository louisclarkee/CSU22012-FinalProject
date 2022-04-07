package transportProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import edu.princeton.cs.algs4.TST;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * This class provides functionality to read in all stops form stops.txt, store them in a TST and enable the user
 * to search for a stop by name and be presented with a list of stops matching the name that they are 
 * searching for.
 */

public class NameSearch {

	TST<Integer> tst;
	File stopsFile;
	String stopsFilePath;


	 /**
     * This is the constructor method for the NameSearch class. It reads in all stops from the input file and populates
     * the TST with key-value pairs. Using the stop name as the key and the line number in the file where the rest of the
     * stops information is stored as the value
     * 
     * @param String filePath : This string is the correct filePath to stops.txt which stores info on all stops in the system
     */ 
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
			// Format the line in the text file to be stored in TST
			stopName = formatTSTkey(sc.nextLine());
			//stopName is a string Key that points to an integer value which stores the lineNumber of the stop information in the file
			tst.put(stopName, infoLocation);
			// infoLocation is the line number in stops.txt where information about this stop is stored
			infoLocation++;			
		}	
		sc.close();
	}	


	 /**
     * Helper function to take in raw line from stops.txt and return stop name in correct format
     * to be used as a key for the TST
     * 
     * @param String input : This string is a whole line in stops.txt
     * @return String : Returns the name of the stops formatted to be more search friendly
     */ 
	public String formatTSTkey(String input) {
		String prefix = "";
		// Convert whole line into String array separated by ","
		String[] stopInfo = input.split(",");
		// Isolate name of stop
		String  stopName = stopInfo[2];
		// Convert stop name, containg multiple words into string array, and then convert that array to a LinkedList
		String[] stopNameSplit = stopName.split(" ");
		List<String> stopNameList = Arrays.asList(stopNameSplit);
		LinkedList<String> temp = new LinkedList<>(stopNameList);
		
		//Remove the following prefixes from each stop name and add them to the end of each stop name
		while (temp.get(0).equals("NB") || temp.get(0).equals("SB") || temp.get(0).equals("WB") || temp.get(0).equals("EB") || temp.get(0).equals("FLAGSTOP")){
			prefix = temp.remove(0);
		}
		stopName = temp.toString();
		stopName = stopName + prefix;
		stopName = stopName.replaceAll("\\p{P}", ""); 
		return stopName;
	}
	
	 /**
     * Formats stop info string from line in file and returns a string that can be printed
     * to show user stop information
     * 
     * @param String input : raw line from stops.txt containing stop information
     * @return String : Formatted string, ready to be printed
     */ 
	public String formatStopInfo(String input) {
		String[] stopInfo = input.split(",");
		input = "- Stop ID: " + stopInfo[0]+ "\n" +
				"- Stop Code: " + stopInfo[1] + "\n" +
				"- Stop Name: " + stopInfo[2] + "\n" +
				"- Stop Description: "+ stopInfo[3]+ "\n";
		return input;
	}
	
	/**
	 * Print all stops and their information that match the users query
	 * 
	 * 
	 * @param inputString : This is provided by the user. It is some characters or the full name of the stop that
	 * 						they are searching for.
	 */ 
	public void printMatchingStops(String inputString) throws IOException {
		String stopLine;

		//ArrayList of integers that represent line numbers in stops.txt that have already been printed
		ArrayList<Integer> linesPrintedAlready = new ArrayList<Integer>();
		//StringBuffer to allow character manipulation of inputString
		StringBuffer inputStringBuffer = new StringBuffer(inputString);
		//Get a list strings stored in the TST that have contain inputString as a prefix
		Iterable<String> keyList = tst.keysWithPrefix(inputStringBuffer.toString());
		Iterator<String> iterator = keyList.iterator();

		int stopCount = 1;
		// If there are stops in TST that match
		if(iterator.hasNext()) {
			// After each iteration of this loop, a character is removed from the end of input string
			// so if there are no more stops with matching names. The program continues to print stops
			// with similiar names
			while(inputStringBuffer.length()>0) {
				// Get list of stop names with current stringBuffer
				keyList = tst.keysWithPrefix(inputStringBuffer.toString());
				iterator = keyList.iterator();

				while(iterator.hasNext()&& stopCount < 11) {
					// loops through list of stop names returned from TST and print stop information
					// from file using the line number returned from the TST
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
				// Remove laste character from stringBuffer in order to search for stops with similiar names 
				// to what the user is searching for
				inputStringBuffer.deleteCharAt(inputStringBuffer.length()-1);
			}
		}
		else {
			System.out.println("\n* Error: No stops match your search query!");
		}
	}	
}
