/* Name: Christina Liu
   NetID: cliu61	
   Class: CSC 172  
   Assignment: Project 2
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.PriorityQueue;

public class Graph {
	//Variables for Graph
	public static int numIntersections;
	public static double minLat;
	public static double maxLat;
	public static double minLon;
	public static double maxLon;
	public static ArrayList<Road> roads;
	public static ArrayList<Road> mst;
	public HashMap<String, LinkedList> graph;
	public static HashMap<String, Intersection> iMap;
	public static HashMap<String, HashSet<String>> iSets;
	public static PriorityQueue<Intersection> unknownIntersections;
	public static PriorityQueue<Road> k;
	public static Intersection [] dPath;
	
	//Constructor for Graph 
	public Graph(int numVertices) {
		//Set names for implementations we will be using with a comparator being used for the intersection IDs compiled in Hashmap
		numIntersections = numVertices;
		graph = new HashMap<String, LinkedList>();
		roads = new ArrayList<Road>();
		iMap = new HashMap<String, Intersection>();
		Comparator<Intersection> comparator = new Comparator<Intersection>() {
        	@Override
        	public int compare(Intersection i1, Intersection i2) {
            	
            	if(i1.distance < i2.distance) {
            		return -1;
            	}
            	else {
            		return 1;
            	}
            }
		};
		
		//Create a priority queue for any unknown intersections using heap
		unknownIntersections = new PriorityQueue<Intersection>(numVertices, comparator);
		
		//Heap using comparator and priority queue
		Comparator<Road> comparator2 = new Comparator<Road>() {
        	@Override
        	public int compare(Road r1, Road r2) {
            	
            	if(r1.distance < r2.distance) {
            		return -1;
            	}
            	else {
            		return 1;
            	}
            }
		};
		k = new PriorityQueue<Road>(numVertices*3, comparator2);
		minLat = minLon = Integer.MAX_VALUE;
		maxLat = maxLon = Integer.MIN_VALUE;
	}
	
	//Method for the size of graph, or number of intersections
	public int size() {
		return graph.size();
	}
	
	//Method to get corresponding intersection ID
	public static Intersection intersectLookup(String intersectID) {
		
		return iMap.get(intersectID);
		
	}
	
	//Method to form path for Dijstrka's algorithm
	public static String formPath(String endID) {
		
		//Find endID intersection
		Intersection temp = iMap.get(endID);
		String [] path = new String[iMap.size()];
		int counter = 0;
		
		//Use while loop to add until nothing is left
		while(temp.path != null) {
			path[counter] = temp.IntersectionID;
			temp = temp.path;
			counter++;
		}
		path[counter] = temp.IntersectionID;
		int totalPath = 0;
		
		//Use for loop to calculate total length of path, breaking when nothing is left
		for(int i = 0; i < path.length; i++) {
			if(path[i] == null) {
				totalPath = i;
				break;
			}
		}
		
		//dPath is used to graph the directions
		dPath = new Intersection [totalPath];
		for(int i = 0; i < totalPath; i++) {
			dPath[i] = iMap.get(path[i]);
		}
		String finalPath = "";
		
		//Using for loop create string
		for(int i = counter ; i > -1; i--) {
			finalPath = finalPath + path[i] + "\n";
		}
		return finalPath;
	}
	
	//Method to determine total distance in miles to travel 
		public static double dPathLength() {
			return dPath[0].distance * 0.000621371;
		}
	
	//Method to find smallest know vertex for dijkstra method
	public static Intersection smallestUnknownVertex() {
		Intersection temp = unknownIntersections.remove();
		return iMap.get(temp.IntersectionID);
		
	}
	
	//Method to determine the paths needed to get from beginning to end
	public void dijkstra(String intersectionID) {
		//Variables
		double cost;
		int numUnknownVertices = iMap.size();
		//Find starting intersection then remove from heap, changing distance to 0 before adding it back
		Intersection start = iMap.get(intersectionID);
		unknownIntersections.remove(start);
		start.distance = 0;
		unknownIntersections.add(start);
		
		//Decrement vertices when known
		while(numUnknownVertices > 0) {
			Intersection temp = smallestUnknownVertex();
			temp.known = true;
			numUnknownVertices--;
			LinkedList currentVertex = graph.get(temp.IntersectionID);
			Edge currentRoad = currentVertex.head.edge;
			Intersection currentIntersection;
			
			//While there are still edges in the linked lest then get intersection that is not the same then find cost to move vertices
			while(currentRoad != null) {
				if(currentRoad.road.intersect1.equals(temp.IntersectionID)) {
					currentIntersection = iMap.get(currentRoad.road.intersect2);
				}
				else {
					currentIntersection = iMap.get(currentRoad.road.intersect1);
				}
				
				if(currentIntersection.known == false) {
					cost = findCost(temp, currentIntersection);
					if(temp.distance + cost < currentIntersection.distance) {
						unknownIntersections.remove(currentIntersection);
						currentIntersection.distance = temp.distance + cost;
						currentIntersection.path = temp;
						unknownIntersections.add(currentIntersection);
					}
				}
				currentRoad = currentRoad.next;
			}
		}
	}
	
	//Method to find cost between the first and second intersection using LinkedList
	public double findCost(Intersection int1, Intersection int2) {
		LinkedList temp = graph.get(int1.IntersectionID);
		return temp.findCost(int2);
	}
	
	//Method to check if first and second intersections are connected using LinkedList
	public boolean connected(Intersection int1, Intersection int2) {
		LinkedList temp = graph.get(int1.IntersectionID);
		return temp.connected(int2);
		
	}
	
	//Method to enter intersection
	public void insert(Intersection i) {
		//Updating for min/min latitude/longitude
		if(i.latitude < minLat) {
			minLat = i.latitude;
		}
		if(i.latitude > maxLat) {
			maxLat = i.latitude;
		}
		if(i.longitude < minLon) {
			minLon = i.longitude;
		}
		if(i.longitude > maxLon) {
			maxLon = i.longitude;
		}
		
		//Add intersections to all necessary areas
		iMap.put(i.IntersectionID, i);
		unknownIntersections.add(i);
		LinkedList l = new LinkedList();
		l.insert(i);
		graph.put(i.IntersectionID, l);
	}
	//Method using Kruskal's to create sets
		public void createSet() {
			
		//Create Hashmap for intersections
		iSets = new HashMap<String, HashSet<String>>();
		HashSet<String> intersections;
		
		//Iterate through graph
		Iterator<Entry<String, LinkedList>> iterator = graph.entrySet().iterator();
		while (iterator.hasNext()) {
	        HashMap.Entry<String, LinkedList> pair = (HashMap.Entry<String, LinkedList>) iterator.next();
	        intersections = new HashSet<String>();
	        intersections.add(pair.getKey());
	        iSets.put(pair.getKey(), intersections);   
		}
	}
	 	
	//Methods to find roads making up minimum spanning tree
	public void kruskals() {
		//Method from earlier
		createSet();
		//Variables
		mst = new ArrayList<Road>();
		Road currentRoad;
		HashSet<String> a;
		HashSet<String> b;
		
		//While loop when there still more in heap
		while(k.size() > 0) {
			currentRoad = k.remove();
			a = iSets.get(currentRoad.intersect1);
			b = iSets.get(currentRoad.intersect2);
			//Add set so long as intersections aren't equal
			if(!a.equals(b)) {
				mst.add(currentRoad);
				a.addAll(b);
				for(String intersectionID: a) {
					iSets.put(intersectionID, a);
				}
			}
		}
	}
	//Method to insert road into graph using linked list, heap, and arraylist
	public void insert(Road r) {
		LinkedList int1 = graph.get(r.intersect1);
		LinkedList int2 = graph.get(r.intersect2);
		int1.insert(r);
		int2.insert(r);
		
		k.add(r);
		
		roads.add(r);
	}
	
	//Using road class, calculates the distance between intersection pairs
	public static double roadDist(Intersection int1, Intersection int2) {
		
		return Road.calcDist(int1.latitude, int1.longitude, int2.latitude, int2.longitude);
		
	}
}

