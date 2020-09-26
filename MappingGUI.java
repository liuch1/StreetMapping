/* Name: Christina Liu
   NetID: cliu61	
   Class: CSC 172  
   Assignment: Project 2
 */

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MappingGUI extends JPanel{
	//Variables for MappingGUI
	public static double xScale;
	public static double yScale;
	public static double maxLon;
	public static double minLon;
	public static double maxLat;
	public static double minLat;
	public static ArrayList<Road> roads;
	public static HashMap<String, Intersection> iMap;
	
	//Constructor for MappingGUI 
	public MappingGUI(ArrayList<Road> roads, HashMap<String, Intersection> iMap, double minimumLat, double maximumLat, double minimumLon, double maximumLon) {
		MappingGUI.roads = roads;
		MappingGUI.iMap = iMap;
		minLat = minimumLat;
		maxLat = maximumLat;
		minLon = minimumLon;
		maxLon = maximumLon;
		setPreferredSize(new Dimension(800, 800));
		
	}
    //Method for changing scale
	public void scale() {
		xScale = this.getWidth() / (maxLon - minLon);
		yScale = this.getHeight() / (maxLat - minLat);
	}
	
	//Method for Paint Component
	public void paintComponent(Graphics page) {
		//Set the 2D Graphics
		Graphics2D page2 = (Graphics2D) page;
		super.paintComponent(page2);
		page2.setColor(Color.BLACK);
		
		//Create scales
		xScale = this.getWidth() / (maxLon - minLon);
		yScale = this.getHeight() / (maxLat - minLat);
		Intersection int1;
		Intersection int2;
		double x1;
		double y1; 
		double x2; 
		double y2;
		
		//Graph roads using the class
		for(Road r : roads) {
			scale();
			int1 = iMap.get(r.intersect1);
			int2 = iMap.get(r.intersect2);
			x1 = int1.longitude;
			y1 = int1.latitude;
			x2 = int2.longitude;
			y2 = int2.latitude;
			page2.draw(new Line2D.Double((x1-minLon) * xScale, getHeight() - ((y1 - minLat) * yScale), (x2-minLon) * xScale, getHeight() - ((y2 - minLat) * yScale)));
		}
		
		//Graph paths using Dijstrka's algorithm
		if(Graph.dPath != null) {
			page2.setColor(Color.CYAN);
			for(int i = 0; i < Graph.dPath.length - 1; i++) {
				x1 = Graph.dPath[i].longitude;
				y1 = Graph.dPath[i].latitude;
				x2 = Graph.dPath[i+1].longitude;
				y2 = Graph.dPath[i+1].latitude;
				page2.draw(new Line2D.Double((x1-minLon) * xScale, getHeight() - ((y1 - minLat) * yScale), (x2-minLon) * xScale, getHeight() - ((y2 - minLat) * yScale)));
			}
		}
		
		//Graph meridian map using the minimum spanning tree
		if(Graph.mst != null) {
			for(Road r : Graph.mst) {
				page2.setColor(Color.RED);
				int1 = iMap.get(r.intersect1);
				int2 = iMap.get(r.intersect2);
				x1 = int1.longitude;
				y1 = int1.latitude;
				x2 = int2.longitude;
				y2 = int2.latitude;
				page2.draw(new Line2D.Double((x1-minLon) * xScale, getHeight() - ((y1 - minLat) * yScale), (x2-minLon) * xScale, getHeight() - ((y2 - minLat) * yScale)));
			}
		}
	}
}