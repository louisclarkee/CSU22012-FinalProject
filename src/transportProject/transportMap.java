package transportProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;

public class transportMap {
	private int V;           // number of vertices in this digraph
	private int E;                 // number of edges in this digraph
	EdgeWeightedDigraph graph;

	//Way to match stop ID to vertex Index
	Map<Integer, Integer> IDtoVertex;
	Map<Integer, Integer> VertextoID;
	Map<Integer, String> VertextoName;
	
	// Empty constructor
	public transportMap() {
		V = 0;
		E = 0;
	}

	 /**
	 * Constructor for transportMap object.
	 * Reads input files and stores stops as vertices and routes between stops as edges in an EdgeWeightedDigraph.
	 * 
     * @param String, String, String : Which are the filepaths for the input files stops.txt, stop_times.txt and transfers.txt
     * @return 
     */ 
	public transportMap(String transferFilePath, String stopsFilePath, String stopTimesFilePath ) throws FileNotFoundException
	{
		V = 0;
		E = 0;
		File transfersFile = new File(transferFilePath);
		File stopsFile = new File(stopsFilePath);
		File stopTimesFile = new File(stopTimesFilePath);
		
		//Create key-value pairs connecting vertex number to stop id and vertex number to stop name
		IDtoVertex = new HashMap<Integer, Integer>();
		VertextoID = new HashMap<Integer, Integer>();
		VertextoName = new HashMap<Integer, String>();
		
		//Scanner to read thorugh each text file
		Scanner sc = new Scanner(stopsFile);
		
		sc.nextLine();
		
		// String in which each line of the text file can be split up and stored in
		String[] lineArr = null;

		//reading in vertexes (stops) from stops.txt
		while(sc.hasNextLine()) {
			lineArr = sc.nextLine().split(",");
			//Fill in key maps with stop information from file
			IDtoVertex.put(Integer.parseInt(lineArr[0]),V);	
			VertextoID.put(V,Integer.parseInt(lineArr[0]));
			VertextoName.put(V, lineArr[2]);	
			//Count of number of vertices and also used as vertex index
			V++;	
		}
		
		//Initialise graph to which weighted edges will be added
		graph = new EdgeWeightedDigraph(V);
		
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
			graph.addEdge(new DirectedEdge(VindexFrom, VindexTo, edgeDist));
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
			// Edge should be added only between 2 consecutive stops with the same trip_id
			if(prevTripID == currentTripID) {
				VindexFrom = IDtoVertex.get(fromStopID);
				VindexTo = IDtoVertex.get(toStopID);
				graph.addEdge(new DirectedEdge(VindexFrom, VindexTo, 1.0));
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


