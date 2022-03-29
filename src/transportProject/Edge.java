package transportProject;

//class to store edges of the weighted graph
class Edge {
	private int src, dest;
	private double weight;
	Edge(int src, int dest, double weight) {
		this.setSrc(src);
		this.setDest(dest);
		this.setWeight(weight);
	}
	public int getSrc() {
		return src;
	}
	public void setSrc(int src) {
		this.src = src;
	}
	public int getDest() {
		return dest;
	}
	public void setDest(int dest) {
		this.dest = dest;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
}
