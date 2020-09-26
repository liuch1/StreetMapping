/* Name: Christina Liu
   NetID: cliu61	
   Class: CSC 172  
   Assignment: Project 2
 */

public class Road {
	
	//Variables for Road
	public double distance;
	public String roadID;
	public String intersect1;
	public String intersect2;
	
	//Constructor for Rode
	public Road(String road, String int1, String int2, double dist) {
		roadID = road;
		intersect1 = int1;
		intersect2 = int2;
		distance = dist;
	}
	//Adjusted code to calculate distance between intersection pair based on https://rosettacode.org/wiki/Haversine_formula#Java
	public static double calcDist(double lat1, double lon1, double lat2, double lon2) {
		
		int R = 6372800;
		double dLat = lat2-lat1;
		double dLon = lon2-lon1;
		lat1 = Math.toRadians(lat1);
		lon1 = Math.toRadians(lon1);
		lat2 = Math.toRadians(lat2);
		lon2 = Math.toRadians(lon2);

		double a = (Math.sin(dLat/2) * Math.sin(dLon/2)) + (Math.cos(lat1) * Math.cos(lat2) * Math.sin(dLon/2) * Math.sin(dLon/2));
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		return R * c;
		
	}

}