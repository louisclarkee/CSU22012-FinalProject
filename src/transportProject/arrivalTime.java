package transportProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
 
	public static ArrayList<arrivalTime> getArrayListOfArrivalTimes(String stop_times_FilePath) throws FileNotFoundException{
		ArrayList<arrivalTime> arrivalTimeList = new ArrayList();
		File stopTimesFile = new File(stop_times_FilePath);
		Scanner sc = new Scanner(stopTimesFile);
		
		//First line aint shit
		sc.nextLine();
	 
		//trip_id,arrival_time,departure_time,stop_id,stop_sequence,stop_headsign,pickup_type,drop_off_type,shape_dist_traveled
		arrivalTime temp = new arrivalTime(0,0,0,0,0,"","","","");
		
		//sc.useDelimiter(","); 
		while(sc.hasNextLine()) {	
			String textLine = sc.nextLine();
			String[] textArr = textLine.split(",");
			
			temp.trip_id = Integer.parseInt(textArr[0]);
			temp.arrival_time = textArr[1];
			temp.departure_time = textArr[2];
			System.out.println(textArr[1]);
			temp.stop_id =  Integer.parseInt(textArr[3]);
			temp.stop_sequence =  Integer.parseInt(textArr[4]);
			temp.stop_headsign =  textArr[5];	
			temp.pickup_type =  Integer.parseInt(textArr[6]);
			temp.drop_off_type =  Integer.parseInt(textArr[7]);
			if(textArr.length > 8) {
				temp.shape_dist_traveled =  textArr[8];
			}
			arrivalTimeList.add(temp);
		}	
		sc.close();
		return arrivalTimeList;
	}
	
	public static Map<String , ArrayList<arrivalTime>> arrivalTimeListToHashmap(ArrayList<arrivalTime> aList){
		Map<String, ArrayList<arrivalTime>> arrivalTimeHashMap = new HashMap<>();
		
		for(int i = 0; i < aList.size(); i++ ) {
			arrivalTime arrivalToAdd = aList.get(i);	
			//System.out.println(arrivalToAdd.arrival_time);
			 ArrayList<arrivalTime> listFromMap = arrivalTimeHashMap.getOrDefault(arrivalToAdd.arrival_time, new ArrayList<>());
			 listFromMap.add(arrivalToAdd);
			 arrivalTimeHashMap.put(arrivalToAdd.arrival_time, listFromMap);
		}
		return arrivalTimeHashMap;
	}
	
	
	
	public static void printArrivalTimeInfo(arrivalTime a) {	
        System.out.println("--  Trip ID: " + a.trip_id);
        System.out.println("--  Arrival Time: " + a.arrival_time);
        System.out.println("--  Departure Time: " + a.departure_time);
        System.out.println("--  Stop ID: " + a.stop_id);
        System.out.println("--  Stop Sequence: " + a.stop_sequence);
        System.out.println("--  Stop Headsign: " + a.stop_headsign);
        System.out.println("--  Pickup Type: " + a.pickup_type);
        System.out.println("--  Drop Off Type: " + a.drop_off_type);
        System.out.println("--  Shape Distance Travelled: " + a.shape_dist_traveled);	
	}
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		try {
			ArrayList<arrivalTime> list = getArrayListOfArrivalTimes("input/stop_times.txt");
			Map<String , ArrayList<arrivalTime>> fack = arrivalTimeListToHashmap(list);
			list = fack.get(" 5:37:05");
			for(int i = 0; i < list.size(); i++ ) {
				printArrivalTimeInfo(list.get(i));
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

}