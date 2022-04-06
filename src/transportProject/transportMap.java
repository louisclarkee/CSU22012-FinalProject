package transportProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import edu.princeton.cs.algs4.AdjMatrixEdgeWeightedDigraph;
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
	
	public transportMap() {
		V = 0;
		E = 0;
	}

	//Graph Constructor
	public transportMap(String transferFilePath, String stopsFilePath, String stopTimesFilePath ) throws FileNotFoundException
	{
		V = 0;
		E = 0;
		File transfersFile = new File(transferFilePath);
		File stopsFile = new File(stopsFilePath);
		File stopTimesFile = new File(stopTimesFilePath);
		IDtoVertex = new HashMap<Integer, Integer>();
		VertextoID = new HashMap<Integer, Integer>();
		VertextoName = new HashMap<Integer, String>();

		Scanner sc = new Scanner(stopsFile);
		sc.nextLine();
		String[] lineArr = null;

		//reading in vertexes (stops) from stops.txt
		while(sc.hasNextLine()) {
			lineArr = sc.nextLine().split(",");
			IDtoVertex.put(Integer.parseInt(lineArr[0]),V);	
			VertextoID.put(V,Integer.parseInt(lineArr[0]));
			VertextoName.put(V, lineArr[2]);	
			V++;	
		}
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


