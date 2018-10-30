package world;

import heap.Heap;
import heap.HeapEmptyException;
import heap.HeapFullException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.ietf.jgss.GSSException;
import org.omg.CosNaming.NamingContextPackage.NotEmpty;

public class Grid {

	private final int DEFAULT_WIDTH = 60; // default width of the world map - gridX runs from 0 to 59
	private final int DEFAULT_HEIGHT = 15; // default height of the map - gridY runs from 0 to 14
	private final int DEFAULT_PERCENT = 20; // this is the percentageof the map occupied by islands
	protected int width, height; // user defined width and height, if one is not using defaults
	protected int percent; // user defined percentage of islands on the map
	protected Node treasure; // points to the map node where the Redbeard treasure is sunken
	protected Node boat; // points to the current location of our boat on the map

	protected Node[][] map; // the map

	public Grid() {
		width = DEFAULT_WIDTH;
		height = DEFAULT_HEIGHT;
		percent = DEFAULT_PERCENT;
		buildMap();
	}

	public Grid(int width, int height, int percent) {
		this.width = width;
		this.height = height;
		if (percent <= 0 || percent >= 100)
			this.percent = DEFAULT_PERCENT;
		else
			this.percent = percent;
		buildMap();
	}

	private void buildMap() {
		// Your implementation goes here
		// For each map position (i,j) you need to generate a Node with can be navigable or it may belong to an island
		// You may use ideas from Lab3 here.
		// Don't forget to generate the location of the boat and of the treasure; they must be on navigable waters, not on the land!
	    this.map = new Node[this.height][this.width];
	    Random rand = new Random();
	    for (int i = 0; i < this.height; i++)
	        for (int j = 0; j < this.width; j++){
	            double island = rand.nextInt(100);
	            if (island < this.percent)
	                map[i][j] = new Node(false, j, i);
	            else
	                map[i][j] = new Node(true, j, i);	                       
	        }
	    boolean setBoat = false;
	    while (!setBoat) {        
	        int x = rand.nextInt(this.width);
	        int y = rand.nextInt(this.height);	    
	        if (map[y][x].walkable){
	            this.boat = map[y][x];
	            setBoat = true;
	        }
        }
	    boolean setTreasure = false;
        while (!setTreasure) {        
            int x = rand.nextInt(this.width);
            int y = rand.nextInt(this.height);      
            if (map[y][x].walkable){
                this.treasure = map[y][x];
                setTreasure = true;
            }
        }
	            
	}

	public String drawMap() {
		// provided for your convenience
		String result = "";
		String hline = "       ";
		String extraSpace;
		for (int i = 0; i < width / 10; i++)
			hline += "         " + (i + 1);
		result += hline + "\n";
		hline = "       ";
		for (int i = 0; i < width; i++)
			hline += (i % 10);
		result += hline + "\n";
		for (int i = 0; i < height; i++) {
			if (i < 10)
				extraSpace = "      ";
			else
				extraSpace = "     ";
			hline = extraSpace + i;
			for (int j = 0; j < width; j++) {
				if (i == boat.gridY && j == boat.gridX)
					hline += "B";
				else if (i == treasure.gridY && j == treasure.gridX)
					hline += "T";
				else if (map[i][j].inPath)
					hline += "*";
				else if (map[i][j].walkable)
					hline += ".";
				else
					hline += "+";
			}
			result += hline + i + "\n";
		}
		hline = "       ";
		for (int i = 0; i < width; i++)
			hline += (i % 10);
		result += hline + "\n";
		return result;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getPercent() {
		return percent;
	}
	
	public Node getBoat() {
		return boat;
	}
	
	private ArrayList<Node> getNeighbours(Node node) {
		// each node has at most 8 neighbours
		// Lab3 may be useful here as well
	    ArrayList<Node> result = new ArrayList<>();

        int x, y;
        x = node.gridX - 1;
        y = node.gridY - 1;
        if (x >= 0 && y >= 0) 
            result.add(map[y][x]);
        if (x >= 0) 
            result.add(map[node.gridY][x]);
        y = node.gridY + 1;
        if (y < this.height && x >= 0) 
            result.add(map[y][x]);
        y = node.gridY - 1;
        if (y >= 0) 
            result.add(map[y][node.gridX]);
        y = node.gridY + 1;
        if (y < this.height) 
            result.add(map[y][node.gridX]);
        y = node.gridY - 1;
        x = node.gridX + 1;
        if (x < this.width && y >=0 ) 
            result.add(map[y][x]);
        if (x < this.width) 
            result.add(map[node.gridY][x]);
        y = node.gridY + 1;
        if (y <this.height && x < this.width)
            result.add(map[y][x]);
        return result;
	}

	private int getDistance(Node nodeA, Node nodeB) {
		// helper method. Provided for your convenience.
		int dstX = Math.abs(nodeA.gridX - nodeB.gridX);
		int dstY = Math.abs(nodeA.gridY - nodeB.gridY);
		if (dstX > dstY)
			return 14 * dstY + 10 * (dstX - dstY);
		return 14 * dstX + 10 * (dstY - dstX);
	}

	public void findPath(Node startNode, Node targetNode)
			throws HeapFullException, HeapEmptyException {
		Heap<Node> openSet = new Heap<>(width * height); // this where we make use of our heaps
		// The rest of your implementation goes here.
		// This method implements A-star path search algorithm.
		// The pseudocode is provided in the appropriate web links.
		// Make sure to use the helper method getNeighbours
		ArrayList<Node> closeSet = new ArrayList<>(); 
		openSet.add(startNode);
		while (!openSet.isEmpty()){
    		Node q = openSet.removeFirst();
    		closeSet.add(q);
    		ArrayList<Node> neighbours = getNeighbours(q);
    		for (Node n : neighbours){
    		    if (n.walkable == true && !closeSet.contains(n)){
    		        if (!openSet.contains(n)){    		            
    		            n.parent = q;
    		            n.gCost = q.gCost + getDistance(n, q);
    		            n.hCost = getDistance(n, treasure);
    		            openSet.add(n);
    		        }
    		        else if(n.gCost > (q.gCost + getDistance(n, q))) {
    		           n.parent = q;
    		           n.gCost = q.gCost + getDistance(n, q);
    		           openSet.updateItem(n);
    		        }    		           
    		    }
    		 if (q.equals(treasure))
    		     break;
    		}
		}
	}

	public ArrayList<Node> retracePath(Node startNode, Node endNode) {
		Node currentNode = endNode;
	    ArrayList<Node> path = new ArrayList<Node>();
		while (currentNode != startNode && currentNode != null) {
			currentNode.inPath = true;
			path.add(currentNode);
			currentNode = currentNode.parent;
		}
		return path;
	}

	public void move(String direction) {
		// Direction may be: N,S,W,E,NE,NW,SE,SW
		// move the boat 1 cell in the required direction
	    if (direction.equals("N") && boat.gridY > 0 && 
	            map[boat.gridY-1][boat.gridX].walkable == true)
	        boat = map[boat.gridY-1][boat.gridX];
	    if (direction.equals("S") && boat.gridY < (height -1) && 
	            map[boat.gridY + 1][boat.gridX].walkable == true )
	        boat = map[boat.gridY+1][boat.gridX];
	    if (direction.equals("W") && boat.gridX > 0 && 
                map[boat.gridY][boat.gridX - 1].walkable == true )
            boat = map[boat.gridY][boat.gridX - 1];
	    if (direction.equals("E") && boat.gridX < (width -1) && 
                map[boat.gridY][boat.gridX + 1].walkable == true )
            boat = map[boat.gridY][boat.gridX + 1];
	    if (direction.equals("NW") && boat.gridY > 0 && boat.gridX > 0 &&
                map[boat.gridY - 1][boat.gridX - 1].walkable == true )
            boat = map[boat.gridY - 1][boat.gridX - 1];
	    if (direction.equals("NE") && boat.gridY > 0 && boat.gridX < (width -1)
	            && map[boat.gridY - 1][boat.gridX + 1].walkable == true )
            boat = map[boat.gridY - 1][boat.gridX + 1];
	    if (direction.equals("SE") && boat.gridY < (height - 1) && 
	            boat.gridX < (width -1) && 
	            map[boat.gridY + 1][boat.gridX + 1].walkable == true )
            boat = map[boat.gridY + 1][boat.gridX + 1];
	    if (direction.equals("SW") && boat.gridY < (height - 1) && 
	            boat.gridX > 0 && 
	            map[boat.gridY + 1][boat.gridX - 1].walkable == true )
            boat = map[boat.gridY + 1][boat.gridX - 1];
	        
	}
	
	public Node getTreasure(int range) {
		// range is the range of the sonar
		// if the distance of the treasure from the boat is less or equal that the sonar range,
		// return the treasure node. Otherwise return null.
	    int distance = getDistance(boat,treasure);
	    if (distance <= range)
	        return treasure;
	    else 
	        return null;
	}

}
