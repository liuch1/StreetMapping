/* Name: Christina Liu
   NetID: cliu61	
   Class: CSC 172  
   Assignment: Project 2
 */

public class LinkedList {
	
	//Variables for LinkedList
	public int size;
	public Node head;
	
	//Constructor for LinkedList
	public LinkedList() {
		head = new Node();
		size = 0;
	}
	
	//Method to return the size of LinkedList
	public int size() {
		return size;
	}
	
	//Method to find cost of intersection
	public double findCost(Intersection int2) {
		Edge temp2 = head.edge;
		while(temp2 != null) {
			if(temp2.road.intersect1.equals(int2.IntersectionID) || temp2.road.intersect2.equals(int2.IntersectionID)) {
				return temp2.road.distance;
			}
			temp2 = temp2.next;
		}
		return -1;
	}
	
	//Method to add road to LinkedList
	public void insert(Road road) {
		Edge tempEdge = new Edge();
		tempEdge.road = road;
		tempEdge.next = head.edge;
		head.edge = tempEdge;
	}
		
	//Method to add intersection to LinkedList when empty
	public void insert(Intersection intersect) {
		if(head.intersection == null) {
			head.intersection = intersect;
		}
		size++;
	}
	
	//Method to check intersection is connected within LinkedList
	public boolean contains(Intersection i1) {
		Node temp = head;
		while(temp != null) {
			if(temp.intersection.equals(i1)) {
				return true;
			}
			temp = temp.next;
		}
		return false;
	}
	//Method to check intersection is connected within LinkedList
	public boolean connected(Intersection i2) {
		Edge temp2 = head.edge;
		while(temp2 != null) {
			if(temp2.road.intersect1.equals(i2.IntersectionID) || temp2.road.intersect2.equals(i2.IntersectionID)) {
				return true;
			}
			temp2 = temp2.next;
		}
		return false;
	}
}
