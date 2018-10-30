package redbeard;

import heap.HeapEmptyException;
import heap.HeapFullException;

import java.io.FileNotFoundException;
import java.util.Scanner;

// This class is not part of your evaluation. You may use it for testing if you want.
public class GameTest {

	/**
	 * @param args
	 * @throws HeapEmptyException 
	 * @throws HeapFullException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, HeapFullException, HeapEmptyException {
		// TODO Auto-generated method stub
		TreasureHunt game = new TreasureHunt(10, 20, 40, 10, 10000);
        game.play("src/game.txt");
		System.out.println(game.getMap());
		//game.playByKeyboard();
		/*Scanner s = new Scanner(System.in);
		while(game.state == "STARTED"){
    		//game.playByKeyboard();             	    
	        String command = s.nextLine();
	        game.processCommand(command);
	        System.out.println(game.getMap());  
	        
		}
		s.close();*/
		System.out.println(game.state);
        System.out.println(game.pathLength());
        
	}

}
