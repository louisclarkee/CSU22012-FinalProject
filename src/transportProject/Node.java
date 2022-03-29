package transportProject;

import java.util.Comparator;

//node of adjacency list 
class Node implements Comparator<Node>{
	int value; 
	double weight;
	public Node() { }
	Node(int value, double weight)  {
		this.value = value;
		this.weight = weight;
	}
	public int compare(Node node1, Node node2) 
	{ 
		if (node1.weight < node2.weight) 
			return -1; 
		if (node1.weight > node2.weight) 
			return 1; 
		return 0; 
	} 
};
