Name: Christina Liu
Class: CSC 172  
Assignment: Project 2

Summary of Code:
StreetMapping is a program that plots a map of roads and intersections from data given, gives shortest distance to travel
between two points using Dijkstra, and generate a minimum weight spanning tree for the entire map.
To run it please look at the OUTPUT.txt

The code reads the formatted data of roads and intersections to make a map that is translated into a graph that can be displayed
using Java GUI. The next parts have methods I used for practicing the lab involving Dijkstra's algorithm, and the algorithm is used to find shortest distance to travel between two points using two HashMaps (ID, ID and intersection) and LinkedList (Node: intersection, Edge: road). This
info is inserted with a priority queue and added to an ArrayList that is then used by the Java GUI. Kruskal's algorithm was used to create the minimum weight spanning trees
for the graph using HashMaps once again (ID to intersections) with the info inserted with a priority and queue and added to an ArrayList that is then
used by Java GUI for the roads. 

Obstacles:
Some obstacles I encountered when writing was I originally used the form of the Haversine from the source fairly true to form, but due to how I implemented and converted my numbers
I ended up having to tweak the numbers or else I would end up with the decimal places being off and my shortest distance incorrect. I also originally had to add in Kruskal's algorithm after a lot of trial and error
as the other methods I had tried to make the minimum weight spanning tree ended up not working with the other parts of my code or ended up in long blocks of code. 
One last obstacle I had was getting my GUI to display correctly, I had to rescale and recode multiple parts of it until it would properly show.

Runtime Analysis:
	V: number of intersections in Graph
	E: average number of edges in each Linked List
	Overall: Linear growth from looking at runtime for everything combined is at most linear. 
Graph Display:
	Everything should be O(1) expect O(E) due to ArrayList iterating through for roads
Shortest Graph Display:
	O(V) due to number of intersections needed to make path
Minimum Weight Spanning Tree:
	O(E) due to ArrayList iterating through for roads
Dijstrka:
Everything should be O(1) except for the method finding the road needed from the appropriate LinkedList in the HashMap including both edges and roads.
Summarized, Dijstrka's algorithm is O(V|E|).


Files:
Edge class 
	Methods for Road and points to next Edge
Graph class with constructor
	Methods for calculating minimum weight spanning tree, Dijstraka's algorithm for shortest path, and inserting Intersections and Edges
Intersection class with variables needed like intersection ID, lat/lon, known/unknown, distance and path
LinkedList class with constructor and methods for inserting Intersection or Edge to list and calculating cost of connected Intersections
Main class to read file, execute GUI, and conditions for what to execute
Mapping GUI class with constructor
	Methods for painting general map and individual routes (shortest path and minimum weight spanning tree)
Node class
	Head for LinkedList. Node with Intersection and points to Edge
Road class
	Constructor for road including road IDs and which intersection ID they pair with
README text file with contact info, summary of code, obstacles, list of files
OUTPUT text file with contact info, instructions for program and examples
ur.txt with formatted data for U of R campus
monroe.txt with formatted data for Monroe County
nys.txt with formatted data for New York State