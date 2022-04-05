package transportProject;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import edu.princeton.cs.algs4.DijkstraSP;
import edu.princeton.cs.algs4.DirectedEdge;

public class Main {
	public static final String lineSeparator = "-----------------------------------------------------------------------------------------------------------------------";

	public static void main(String[] args) throws FileNotFoundException {
		boolean runSystem = true;
		String userInputString;
		int userInputInt;
		Scanner scanner = new Scanner(System.in);

		System.out.println(lineSeparator);
		System.out.println(" .----------------. .----------------. .----------------.   .----------------. .-----------------..----------------. .----------------. \n"
				+ "| .--------------. | .--------------. | .--------------. | | .--------------. | .--------------. | .--------------. | .--------------. |\n"
				+ "| |   ______     | | | _____  _____ | | |    _______   | | | |     _____    | | | ____  _____  | | |  _________   | | |     ____     | |\n"
				+ "| |  |_   _ \\    | | ||_   _||_   _|| | |   /  ___  |  | | | |    |_   _|   | | ||_   \\|_   _| | | | |_   ___  |  | | |   .'    `.   | |\n"
				+ "| |    | |_) |   | | |  | |    | |  | | |  |  (__ \\_|  | | | |      | |     | | |  |   \\ | |   | | |   | |_  \\_|  | | |  /  .--.  \\  | |\n"
				+ "| |    |  __'.   | | |  | '    ' |  | | |   '.___`-.   | | | |      | |     | | |  | |\\ \\| |   | | |   |  _|      | | |  | |    | |  | |\n"
				+ "| |   _| |__) |  | | |   \\ `--' /   | | |  |`\\____) |  | | | |     _| |_    | | | _| |_\\   |_  | | |  _| |_       | | |  \\  `--'  /  | |\n"
				+ "| |  |_______/   | | |    `.__.'    | | |  |_______.'  | | | |    |_____|   | | ||_____|\\____| | | | |_____|      | | |   `.____.'   | |\n"
				+ "| |              | | |              | | |              | | | |              | | |              | | |              | | |              | |\n"
				+ "| '--------------' | '--------------' | '--------------' | | '--------------' | '--------------' | '--------------' | '--------------' |\n"
				+ " '.----------------.'.----------------.'.----------------. .----------------.'.----------------.'.----------------.' '----------------' \n"
				+ " | .--------------. | .--------------. | .--------------. | .--------------. | .--------------. | .--------------. |                    \n"
				+ " | |    _______   | | |  ____  ____  | | |    _______   | | |  _________   | | |  _________   | | | ____    ____ | |                    \n"
				+ " | |   /  ___  |  | | | |_  _||_  _| | | |   /  ___  |  | | | |  _   _  |  | | | |_   ___  |  | | ||_   \\  /   _|| |                    \n"
				+ " | |  |  (__ \\_|  | | |   \\ \\  / /   | | |  |  (__ \\_|  | | | |_/ | | \\_|  | | |   | |_  \\_|  | | |  |   \\/   |  | |                    \n"
				+ " | |   '.___`-.   | | |    \\ \\/ /    | | |   '.___`-.   | | |     | |      | | |   |  _|  _   | | |  | |\\  /| |  | |                    \n"
				+ " | |  |`\\____) |  | | |    _|  |_    | | |  |`\\____) |  | | |    _| |_     | | |  _| |___/ |  | | | _| |_\\/_| |_ | |                    \n"
				+ " | |  |_______.'  | | |   |______|   | | |  |_______.'  | | |   |_____|    | | | |_________|  | | ||_____||_____|| |                    \n"
				+ " | |              | | |              | | |              | | |              | | |              | | |              | |                    \n"
				+ " | '--------------' | '--------------' | '--------------' | '--------------' | '--------------' | '--------------' |                    \n"
				+ "  '----------------' '----------------' '----------------' '----------------' '----------------' '----------------' ");
		System.out.println(lineSeparator);
		System.out.println(lineSeparator);


		while (runSystem) {
			System.out.print("*	Please enter a query (1,2 or 3): ");
			String queryInput = scanner.next();

			switch (queryInput) {
			case "1":
				System.out.println("Case 1 selected.");
				try {
					transportMap map = new transportMap("input/transfers.txt", "input/stops.txt", "input/stop_times.txt");
					System.out.print("Please enter an origin stop number: ");
					userInputInt = scanner.nextInt();

					DijkstraSP DSP = new DijkstraSP(map.graph, map.IDtoVertex.get(userInputInt));

					System.out.print("Please enter an destination stop number: ");
					userInputInt = scanner.nextInt();
					if(DSP.hasPathTo(map.IDtoVertex.get(userInputInt))) {
						Iterable<DirectedEdge> list = DSP.pathTo(userInputInt);
						double cost = 0;
						for(DirectedEdge e : list) {
							System.out.println("stop:" + map.VertextoID.get(e.from()) + " -> " +map.VertextoID.get(e.to()) );
							cost = cost + e.weight();
						}
						System.out.println("Cost:  "+ cost);
					}
					else {
						System.out.print("There is no route between those two stops :(");
					}




				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(lineSeparator);
				break;

			case "2":
				System.out.println("Case 2 selected.");
				NameSearch nameSearch = new NameSearch("input/stops.txt");
				System.out.println("Please enter the name of the bus stop that you are searching for:");
				userInputString = scanner.next();
				Iterable<String> nameList = nameSearch.tst.keysWithPrefix(userInputString);
				for(String e : nameList) {
					System.out.println(e);
				}
				
				
				
				
				
				queryInput = scanner.nextLine();
				System.out.println(lineSeparator);

				break;

			case "3":
				System.out.println("Case 3 selected.");
				System.out.println(lineSeparator);

				break;

			case "4":
				System.out.println("Application quit....");
				System.out.println(lineSeparator);
				System.out.println(lineSeparator);
				runSystem = false;
				break;

			default:
			}



		}
		scanner.close();
	}
}
