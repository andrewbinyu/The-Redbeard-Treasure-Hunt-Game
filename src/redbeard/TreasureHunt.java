package redbeard;

import java.awt.GraphicsDevice;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.omg.CosNaming.NamingContextPackage.NotEmpty;

import heap.HeapEmptyException;
import heap.HeapFullException;
import world.Grid;
import world.Node;

public class TreasureHunt {

	private final int DEFAULT_SONARS = 3; // default number of available sonars
	private final int DEFAULT_RANGE = 200; // default range of a sonar 200
	protected Grid islands; // the world where the action happens!
	protected int height, width, landPercent;
	protected int sonars, range; // user defined number of sonars and range
	protected String state; // state of the game (STARTED, OVER)
	protected ArrayList<Node> path; // the path to the treasure!

	public TreasureHunt() {
		// The default constructor
	    islands = new Grid();
	    this.height = islands.getHeight();
	    this.width = islands.getWidth();
	    this.landPercent = islands.getPercent();
	    this.sonars = DEFAULT_SONARS;
	    this.range = DEFAULT_RANGE;
	    this.state = "STARTED";
	    
	}

	public TreasureHunt(int height, int width, int landPercent, int sonars,
			int range) {
		// The constructor that uses parameters
	    islands = new Grid(width, height, landPercent);
	    this.height = height;
	    this.width = width;
	    this.landPercent = landPercent;
	    this.sonars = sonars;
	    this.range = range;
	    this.state = "STARTED";
	}

	private void processCommand(String command) throws HeapFullException,
			HeapEmptyException {
		// The allowed commands are: 
		// SONAR to drop the sonar in hope to detect treasure
		// GO direction to move the boat in some direction
		// For example, GO NW means move the boat one cell up left (if the cell is navigable; if not simply ignore the command)
	    if (command.equals("SONAR")){
	        sonars -= 1;
	        Node treasure = islands.getTreasure(range);
	        if (treasure != null){
	            islands.findPath(islands.getBoat(), treasure);
	            path = islands.retracePath(islands.getBoat(), treasure);
	            state = "OVER";
	        }
	        if (treasure == null && sonars == 0)
	            state = "OVER";
	    }
	    if (command.substring(0, 2).equals("GO")){
	        islands.move(command.substring(3));
	    }
	    
	}

	public int pathLength() {
		if (path == null)
			return 0;
		else return path.size();
	}

	public String getMap() {
		return islands.drawMap();
	}

	public void play(String pathName) throws FileNotFoundException,
			HeapFullException, HeapEmptyException {
		// Read a batch of commands from a text file and process them.
	     Scanner getData = new Scanner(new File(pathName));
	     while(getData.hasNextLine() && state == "STARTED"){
	            String line = getData.nextLine();
	            processCommand(line);
	     }
	     getData.close();
	}
	
	public void playByKeyboard() throws HeapFullException, HeapEmptyException{
        Scanner s = new Scanner(System.in);
        while (state == "STARTED" ){
            String command = s.nextLine();
            processCommand(command);
            System.out.println(getMap());
        }
        s.close();
	    
	}
	
	
}
