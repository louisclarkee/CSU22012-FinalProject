package transportProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class transportMap {
	private int V;           // number of vertices in this digraph
	private int E;                 // number of edges in this digraph


	// define adjacency list 
	private ArrayList<ArrayList<Edge>> adj_list = new ArrayList<>();
	//Way to match stop ID to vertex Index
	private Map<Integer, Integer> IDtoVertex;

	//Graph Constructor
	public transportMap(String transferFilePath, String stopsFilePath, String stopTimesFilePath ) throws FileNotFoundException
	{
		V = 0;
		E = 0;
		File transfersFile = new File(transferFilePath);
		File stopsFile = new File(stopsFilePath);
		File stopTimesFile = new File(stopTimesFilePath);
		IDtoVertex = new HashMap<Integer, Integer>();

		Scanner sc = new Scanner(stopsFile);
		sc.nextLine();
		String[] lineArr = null;

		//reading in vertexes (stops) from stops.txt
		while(sc.hasNextLine()) {
			lineArr = sc.nextLine().split(",");
			IDtoVertex.put(Integer.parseInt(lineArr[0]),V);
			V++;	
		}

		// adjacency list memory allocation
		for (int i = 0; i < V; i++)
			adj_list.add(i, new ArrayList<>());
		
		sc.close();
		sc = new Scanner(transfersFile);
		sc.nextLine();
		int VindexFrom, VindexTo;
		double edgeDist;

		//Adding edges to graph from transfers.txt
		while(sc.hasNextLine()) {
			lineArr = sc.nextLine().split(",");
			VindexFrom = IDtoVertex.get(Integer.parseInt(lineArr[0]));
			VindexTo = IDtoVertex.get(Integer.parseInt(lineArr[1]));
			edgeDist = (Integer.parseInt(lineArr[2]) == 0) ? 2.0 : Double.parseDouble(lineArr[3])/100;
			adj_list.get(VindexFrom).add(new Edge(VindexFrom, VindexTo, edgeDist));
		}
		
		sc.close();
		sc = new Scanner(stopTimesFile);
		sc.nextLine();
		int prevTripID = 0;
		int currentTripID = 0; 
		int fromStopID = 0;
		int toStopID = 0;

		while(sc.hasNextLine()) {
			lineArr = sc.nextLine().split(",");
			toStopID =Integer.parseInt(lineArr[3]);
			currentTripID = Integer.parseInt(lineArr[0]);
			if(prevTripID == currentTripID) {
				VindexFrom = IDtoVertex.get(fromStopID);
				VindexTo = IDtoVertex.get(toStopID);
				adj_list.get(VindexFrom).add(new Edge(VindexFrom, VindexTo, 1.0));
			}
			prevTripID = currentTripID;
			fromStopID = toStopID; 	
		}		
		sc.close();  
	}

	/**
	 * Returns the number of vertices in this digraph.
	 *
	 * @return the number of vertices in this digraph
	 */
	public int V() {
		return V;
	}

	/**
	 * Returns the number of edges in this digraph.
	 *
	 * @return the number of edges in this digraph
	 */
	public int E() {
		return E;
	}
}


