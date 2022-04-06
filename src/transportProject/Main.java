package transportProject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import edu.princeton.cs.algs4.DijkstraSP;
import edu.princeton.cs.algs4.DirectedEdge;

public class Main {
	public static final String lineSeparator = "---------------------------------------------------------------------------------\n"
			+ "---------------------------------------------------------------------------------";
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

		//set to user input using scanner
		String userInputString;
		int userInputInt;

		//Initialise transportMap object
		transportMap map = new transportMap();

		Scanner scanner = new Scanner(System.in);

		//Command line user interface
		System.out.println(lineSeparator);
		System.out.println(mainTitle);
		System.out.println(lineSeparator);

		//Main System loop
		while (runSystem) {

			System.out.print(infoTable);
			String queryInput = scanner.next();

			switch (queryInput) {

			case "1":
				System.out.println("Case 1 Loading....\n" + lineSeparator);

				try {

					if(!query1RunPrev) {
						map = new transportMap("input/transfers.txt", "input/stops.txt", "input/stop_times.txt");
						query1RunPrev= true;
					}

					System.out.print("Please enter an origin stop number: ");
					userInputInt = scanner.nextInt();

					DijkstraSP DSP = new DijkstraSP(map.graph, map.IDtoVertex.get(userInputInt));

					System.out.print("Please enter an destination stop number: ");
					userInputInt = scanner.nextInt();

					if(DSP.hasPathTo(map.IDtoVertex.get(userInputInt))) 
					{
						Iterable<DirectedEdge> list = DSP.pathTo(map.IDtoVertex.get(userInputInt));

						for(DirectedEdge e : list) {
							System.out.println("stop:" +map.VertextoName.get(e.from()) + "  (" + map.VertextoID.get(e.from())+")" + " -> " 
									+map.VertextoName.get(e.to()) + "  (" + map.VertextoID.get(e.to())+")" );

						}

						System.out.println("Cost:  "+ DSP.distTo(userInputInt));
					}
					else 
					{
						System.out.print("There is no route between those two stops");
					}

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(lineSeparator);
				break;


			case "2":
				System.out.println("Case 2 selected.\n"+ lineSeparator);

				NameSearch nameSearch = new NameSearch("input/stops.txt");

				System.out.print("Please enter the name of the bus stop that you are searching for:");
				userInputString = scanner.next();

				nameSearch.printMatchingStops(userInputString);

				System.out.println(lineSeparator);
				break;



			case "3":
				System.out.println("Case 3 selected.");
				System.out.println(lineSeparator);

				break;


			case "4":
				System.out.println(lineSeparator);
				System.out.println(endTitle);
				System.out.println(lineSeparator);
				runSystem = false;
				break;
			default:
				System.out.println("Invalid Input: Please type in 1,2,3 or 4 to select a query!");
			}
		}
		scanner.close();
	}
}
