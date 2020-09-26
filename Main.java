/* Name: Christina Liu
   NetID: cliu61	
   Class: CSC 172  
   Assignment: Project 2
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JFrame;

public class Main {
	
	public static void main(String [] args) throws FileNotFoundException {
		//Variables used
		double lat;
		double lon;
		double distance;
		int numIntersects = 0;
		String intersectionID;
		String roadID; 
		String int1; 
		String int2;
		Intersection x;
		Intersection y;
		Intersection z;
		
		//get the name of the file
		File mapInfo = new File(args[0]);
			
		//Create new scanner to find intersections
		Scanner i = new Scanner(mapInfo);
		while(i.nextLine().startsWith("i")) {
			numIntersects++;
		}
		i.close();
		
		//Create new scanner to scan through the info
		Scanner d = new Scanner(mapInfo);
		String currentLine = d.nextLine();
		String [] info;
		String name;
				
		//Determine name of the map
		if(args[0].equals("ur.txt")) {
			name = "UR Campus";
		}
		else {
			if(args[0].equals("monroe.txt")) {
				name = "Monroe County";
			}
			else {
				if(args[0].equals("nys.txt")) {
					name = "New York State";
				}
				else {
					name = "Map";
				}
			}
		}
				
		//Create new graph which also creates map
		Graph graph = new Graph(numIntersects);
		//Use while loop to insert intersections
		while(currentLine.startsWith("i")) {
			
			info = currentLine.split("\t");
			
			intersectionID = info[1];
			lat = Double.parseDouble(info[2]);
			lon = Double.parseDouble(info[3]);
			
			//create the new Intersection
			x = new Intersection();
			x.distance = Integer.MAX_VALUE;
			x.IntersectionID = intersectionID;
			x.latitude = lat;
			x.longitude = lon;
			x.known = false;
			
			currentLine = d.nextLine();
			
			//add the new intersection into the map
			graph.insert(x);
		}
		
		//Insert road information using while loop
		while(currentLine.startsWith("r")) {
			info = currentLine.split("\t");
			roadID = info[1];
			int1 = info[2];
			int2 = info[3];
			y = Graph.intersectLookup(int1);
			z = Graph.intersectLookup(int2);
			distance = Graph.roadDist(y, z);
			//Add new road
			graph.insert(new Road(roadID, int1, int2, distance));
			//Break when there is no more info 
			if(d.hasNextLine() == false) {
				break;
			}
			currentLine = d.nextLine();
		}
		
		//Booleans to determine what to execute depending on what command line arguments are given
		boolean showMap = false;
		boolean dijkstras = false;
		boolean mwst = false;
		//Default
		String dStart = "i0";
		String dEnd = "i1";
		//For loop to determine which command line arguments are inputted and to be redetermined as true for execution
		for(int j = 0; j < args.length; j++) {
			if(args[j].equals("-show")) {
				showMap = true;
			}
			if(args[j].equals("-directions")) {
				dijkstras = true;	
				dStart = args[j+1];
				dEnd = args[j+2];
			}
			if(args[j].equals("-meridianmap")) {
				mwst = true;
			}
		}
		
		//If -directions is used then use dijkstras
		if(dijkstras == true) {
			graph.dijkstra(dStart);
			System.out.println("\nShortest path from " + dStart + " to " + dEnd + " is: ");
			System.out.println(Graph.formPath(dEnd));
			System.out.println("Path length from " + dStart + " to " + dEnd + " is: " + Graph.dPathLength() + " miles.");
		}
		//if -meridianmap is used then utilize mwst
		if(mwst == true) {
			graph.kruskals();
			System.out.println("\nThe Roads Taken For Minimum Weight Spanning Tree " + name + ":\n");
			for(Road r : Graph.mst) {
				System.out.println(r.roadID);
			}
		}
		//If -show is used then input GUI
		if(showMap == true) {
			JFrame frame = new JFrame("Map");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().add(new MappingGUI(Graph.roads, Graph.iMap, Graph.minLat, Graph.maxLat, Graph.minLon, Graph.maxLon));
			frame.pack();
			frame.setVisible(true);
		}
		d.close();
	}
}

