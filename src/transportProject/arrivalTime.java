package transportProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

//Class to store information in stop_times.txt and allow user to search for all trips with a given arrival time.
public class arrivalTime {
	protected int trip_id; 
	protected int stop_id;
	protected int stop_sequence;
	protected int pickup_type;
	protected int drop_off_type;
	protected String arrival_time;
	protected String departure_time;
	protected String stop_headsign;
	protected String shape_dist_traveled;

	

	//Constructor
	arrivalTime(int trip_id, int stop_id, int stop_sequence, int pickup_type, int drop_off_type, String arrival_time, String departure_time, String stop_headsign, String shape_dist_traveled){
		this.trip_id = trip_id;
		this.stop_id = stop_id;
		this.stop_sequence = stop_sequence;
		this.pickup_type = pickup_type;
		this.drop_off_type = drop_off_type;
		this.arrival_time = arrival_time;
		this.departure_time = departure_time;
		this.stop_headsign = stop_headsign;
		this.shape_dist_traveled = shape_dist_traveled;	
	}

	
	
	/**
	 * This method scans the stop_times.txt file, creates arrivalTime objects from the information conatined in the file 
	 * and stores them in a HashMap of ArrayLists. 
	 * 
	 * @param stop_times_Filepath : A string that conatins the filepath leading to stop_times.txt where all information needed is stored
	 * @return HashMap of ArrayLists containing arrivalTime objects using the arrival time variable of each object as the key
	 */ 
	public static HashMap<String, ArrayList<arrivalTime>> storeArrivalTimesHashMap(String stop_times_FilePath) throws FileNotFoundException{
		//Initialise HashMap
		HashMap<String, ArrayList<arrivalTime>> arrivalTimes = new HashMap<String, ArrayList<arrivalTime>>();

		//Initialise scanner with file information
		File stopTimesFile = new File(stop_times_FilePath);
		Scanner sc = new Scanner(stopTimesFile);

		//First line aint shit
		sc.nextLine();

		arrivalTime temp;

		while(sc.hasNextLine()) {	
			String textLine = sc.nextLine();
			// Parse whole line in the file into an array of strings, separated by commas
			String[] textArr = textLine.split(",");
			temp = new arrivalTime(0,0,0,0,0,"","","","");
			// Store information from textLine in arrivalTime object
			temp.trip_id = Integer.parseInt(textArr[0]);
			temp.arrival_time = textArr[1].replaceAll("\\s+", "0");;
			temp.departure_time = textArr[2].replaceAll("\\s+", "0");;
			temp.stop_id =  Integer.parseInt(textArr[3]);
			temp.stop_sequence =  Integer.parseInt(textArr[4]);
			temp.stop_headsign =  textArr[5];	
			temp.pickup_type =  Integer.parseInt(textArr[6]);
			temp.drop_off_type =  Integer.parseInt(textArr[7]);
			if(textArr.length > 8) {
				temp.shape_dist_traveled =  textArr[8];
			}
			if (arrivalTimes.get(temp.arrival_time) == null) {
				arrivalTimes.put(temp.arrival_time, new ArrayList<arrivalTime>());
			}
			// If temp.arrival_time and temp.departure time are valid (i.e. not greater than 23:59:59) then
			// add the object to the hashmap
			try
			{
				LocalTime.parse(temp.arrival_time, DateTimeFormatter.ofPattern("H:mm:ss"));
				LocalTime.parse(temp.departure_time, DateTimeFormatter.ofPattern("H:mm:ss"));
			}
			catch (Exception e) 
			{
				continue;
			}
			// Add new arrivalTime objec to HashMap using it's arrival_time member variable as key
			arrivalTimes.get(temp.arrival_time).add(temp);
		}	
		sc.close();
		return arrivalTimes;
	}



	/**
	 * This method gets and ArrayList of arrivalTimes from the HashMap using userArrivaltime String as key.
	 * And then prints all the matching arrivalTimes form their query.
	 * 
	 * @param  map : HashMap of ArrayLists of arrivalTime objects, userArrivalTime : Time that the user is looking for mathcin times
	 */ 
	public static void printArrivalTimesFromHashMap(HashMap<String, ArrayList<arrivalTime>> map, String userArrivalTime) {

		// Get ArrayList of arrivalTimes from hashmap using userArrivaltime String as key
		ArrayList<arrivalTime> arrivalTimeList = map.get(userArrivalTime);
		//Sort ArrayList by trip ID
		arrivalTimeList = mergSortArrivalTime.mergeSort(arrivalTimeList);

		System.out.println(Main.lineSeparator + Main.lineSeparator +
				"\n** There are " + arrivalTimeList.size() +" trips matching your selected arrival time! **\n");
		int tripCount = 1;

		// Print all arrivalTime objects in arrivalTimeList
		for(arrivalTime a : arrivalTimeList) {
			System.out.println("* Matching Trip: " + tripCount);
			tripCount++;
			printArrivalTimeInfo(a);
		}
	}


	/**
	 * This method prints all member variables contained in an arrivalTime object
	 * 
	 * @param a : an arrivalTime object
	 */ 
	public static void printArrivalTimeInfo(arrivalTime a) {	
		System.out.println("--  Trip ID: " + a.trip_id);
		System.out.println("--  Arrival Time: " + a.arrival_time);
		System.out.println("--  Departure Time: " + a.departure_time);
		System.out.println("--  Stop ID: " + a.stop_id);
		System.out.println("--  Stop Sequence: " + a.stop_sequence);
		System.out.println("--  Stop Headsign: " + a.stop_headsign);
		System.out.println("--  Pickup Type: " + a.pickup_type);
		System.out.println("--  Drop Off Type: " + a.drop_off_type);
		System.out.println("--  Shape Distance Travelled: " + a.shape_dist_traveled + "\n" + Main.lineSeparator2);	
	}
}