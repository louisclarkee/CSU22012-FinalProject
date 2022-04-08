package transportProject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

import edu.princeton.cs.algs4.DijkstraSP;
import edu.princeton.cs.algs4.DirectedEdge;

public class Main {
	public static final String lineSeparator = "_______________________________________________________________\n"
			+ "---------------------------------------------------------------\n";
	public static final String lineSeparator2 = "_______________________________________________________________\n";
			
	public static final String mainTitle = "   ___              _____        __       \n"
			+ "  / __\\_   _ ___    \\_   \\_ __  / _| ___  \n"
			+ " /__\\// | | / __|    / /\\/ '_ \\| |_ / _ \\ \n"
			+ "/ \\/  \\ |_| \\__ \\ /\\/ /_ | | | |  _| (_) |\n"
			+ "\\_____/\\__,_|___/ \\____/ |_| |_|_|  \\___/ \n"
			+ "                                          \n"
			+ "     __           _____                   \n"
			+ "    / _\\_   _ ___/__   \\___ _ __ ___      \n"
			+ "    \\ \\| | | / __| / /\\/ _ \\ '_ ` _ \\     \n"
			+ "    _\\ \\ |_| \\__ \\/ / |  __/ | | | | |    \n"
			+ "    \\__/\\__, |___/\\/   \\___|_| |_| |_|    \n"
			+ "        |___/                            ";
	public static final String endTitle = " __           _                     ____       _ _               \n"
			+ "/ _\\_   _ ___| |_ ___ _ __ ___     /___ \\_   _(_) |_             \n"
			+ "\\ \\| | | / __| __/ _ \\ '_ ` _ \\   //  / / | | | | __|            \n"
			+ "_\\ \\ |_| \\__ \\ ||  __/ | | | | | / \\_/ /| |_| | | |_   _   _   _ \n"
			+ "\\__/\\__, |___/\\__\\___|_| |_| |_| \\___,_\\ \\__,_|_|\\__| (_) (_) (_)\n"
			+ "    |___/                                                        ";
	public static final String infoTable = "+---+-------------------------------------------------+\n"
			+ "|   | PLEASE SELECT A QUERY BY TYPING 1,2,3 or 4      |\n"
			+ "+---+-------------------------------------------------+\n"
			+ "| 1 | Find the shortest route between two bus stops.  |\n"
			+ "+---+-------------------------------------------------+\n"
			+ "| 2 | Search for a bus stop by name.                  |\n"
			+ "+---+-------------------------------------------------+\n"
			+ "| 3 | Search for all trips with a given arrival time. |\n"
			+ "+---+-------------------------------------------------+\n"
			+ "| 4 | Quit Application...                             |\n"
			+ "+---+-------------------------------------------------+\n"
			+ "INPUT: ";

	public static void main(String[] args) throws IOException {
		boolean runSystem = true;
		boolean query1RunPrev = false;
		boolean query2RunPrev = false;
		boolean query3RunPrev = false;
		boolean validInput = false;
		
		HashMap<String, ArrayList<arrivalTime>> arrivalTimeMap = null;
		NameSearch nameSearch = null;
		DijkstraSP DSP = null;

		//set to user input using scanner
		String userInputString = "";	
		int userInputInt = 0;

		//Initialise transportMap object
		transportMap map = new transportMap();

		Scanner scanner = new Scanner(System.in);

		//Command line user interface
		System.out.println(lineSeparator);
		System.out.println(mainTitle);
		System.out.println(lineSeparator);

		//Main System loop
		while (runSystem) {
			//Show user possible queries
			System.out.print(infoTable);
			String queryInput = scanner.next();
			//Execute a query or quit program depending on what the user selects
			switch (queryInput) {
			//Query 1: Allow user to type in two stop ID's and 
			case "1":
				try {
					//Only read in text files and populate WeightedDiGraph if query has not been run previously
					if(!query1RunPrev) {
						System.out.println("Query 1 Loading...\n"+ lineSeparator2);
						map = new transportMap("input/transfers.txt", "input/stops.txt", "input/stop_times.txt");
						System.out.println("Loading Complete!\n"+ lineSeparator2);
						query1RunPrev= true;
					}
					//Ensuring user inputs a valid origin stop ID
					while(!validInput) {	
						System.out.println("Please enter a valid origin stop ID: ");
						userInputString = scanner.next();
						try {		
							//Create a DijkstraSP object with the origin stop as source vertex
							DSP = new DijkstraSP(map.graph, map.IDtoVertex.get(Integer.parseInt(userInputString)));	
							validInput = true;
						} catch (Exception e) {		
							System.out.println("* Invalid Input: no stop exists with that stop ID.\n");
						}
					}
					validInput = false;

					//Ensuring user inputs a valid destination stop ID
					while(!validInput) {

						System.out.print("Please enter a valid destination stop ID: ");
						userInputString = scanner.next();

						try {
							userInputInt = Integer.parseInt(userInputString);
							if(DSP.hasPathTo(map.IDtoVertex.get(userInputInt))) 
							{
								Iterable<DirectedEdge> list = DSP.pathTo(map.IDtoVertex.get(userInputInt));
								int stopCount = 1;
								System.out.println("\n***** SHORTEST ROUTE ******");
								for(DirectedEdge e : list) {
									System.out.println(stopCount + "." +map.VertextoName.get(e.from()) + "  (Stop ID: " + map.VertextoID.get(e.from())+")" + " -> "); 
									stopCount++;	
								}
								System.out.println(stopCount + "." +map.VertextoName.get(map.IDtoVertex.get(userInputInt)) + "  (Stop ID: " + userInputInt +")" );
								System.out.println("\n* Cost:  "+ DSP.distTo(userInputInt));
							}
							else 
							{
								System.out.print("There is no route between those two stops");
							}
							validInput = true;
						}catch(Exception e) {
							System.out.println("\n* Invalid Input: no stop exists with that stop ID.\n");
						}
					}
					validInput = false;				

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				System.out.println(lineSeparator);
				break;
				
				//Query 2: Search for a bus stop by name
			case "2":
				//Read in file and populate TST only if query has not been previously run
				if(!query2RunPrev) {
					System.out.println("Query 2 Loading...\n"+ lineSeparator2);
					nameSearch = new NameSearch("input/stops.txt");			
					System.out.println("Loading Complete!\n"+ lineSeparator2);
					query2RunPrev = true;
				}

				System.out.print("\nPlease enter the name of the bus stop that you are searching for:");
				userInputString = scanner.next();
				//Make sure string is all uppercase characters as that is the way they are stored in stops.txt
				userInputString = userInputString.toUpperCase();

				nameSearch.printMatchingStops(userInputString);
				System.out.println(lineSeparator);
				break;


				//Query 3: Searching all trips in system by arrival time
			case "3":

				// Only read in file and populate HashMap if it has not been run previously
				if(!query3RunPrev) {
					System.out.println("Query 3 Loading....\n"+ lineSeparator2);
					arrivalTimeMap = arrivalTime.storeArrivalTimesHashMap("input/stop_times.txt");
					System.out.println("Loading Complete!\n"+ lineSeparator2);
					query3RunPrev = true;
				}

				System.out.print("\nPlease enter an arrival time in the format HH:mm:ss:");
				userInputString = scanner.next();

				try
				{
					//Check if user input is in the correct format
					LocalTime.parse(userInputString, DateTimeFormatter.ofPattern("H:mm:ss"));
					arrivalTime.printArrivalTimesFromHashMap(arrivalTimeMap, userInputString);
				}
				catch (Exception e) 
				{
					System.out.println("\n** No trips match that arrival time!\n");
				}
				break;

			//User quits the program and is presented with a final message
			case "4":
				System.out.println(lineSeparator);
				System.out.println(endTitle);
				System.out.println(lineSeparator);
				runSystem = false;
				break;
			//If the user types in anything that is not in the set {1,2,3,4}
			default:
				System.out.println("\n*Invalid Input: Please type in 1,2,3 or 4 to select a query!");
			}
		}
		scanner.close();
	}
}
