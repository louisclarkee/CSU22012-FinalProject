package transportProject;

import java.util.Scanner;

public class Main {
	public static final String lineSeparator = "---------------------------------------------------------------------------------------------";

	public static void main(String[] args) {
		boolean runSystem = true;
		Scanner scanner = new Scanner(System.in);

		System.out.println(lineSeparator);
		System.out.println("Welcome to a bus route browser for Vancouver?");


		while (runSystem) {
			System.out.print("Please enter a query (1,2 or 3): ");
			String queryInput = scanner.next();

			switch (queryInput) {
			case "1":
				System.out.println("Case 1 selected.");
				System.out.println(lineSeparator);
				break;

			case "2":
				System.out.println("Case 2 selected.");
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
	}
}
