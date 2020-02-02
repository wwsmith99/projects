/**
 * ConsoleRunner:  Prompts the user to determine the parameters of the Game
 * constructor.  Creates a Game and manages the alternating calls to the
 * ‘place’ methods in Game.  Prompts the user for inputs and outputs the state
 * of the board to the console.
 *
 * @author William Smith, Jonathan Taylor
 */

import java.util.Scanner;

public class ConsoleRunner {

    private boolean playerIsX;
    private Game game;   
    // Use to process text input from the user.
    private Scanner scanner = new Scanner(System.in);

    private boolean challenging = false; // there is no smart AI implementation, challenging will always be false
    
    public ConsoleRunner() {
    	  	
        System.out.println("Do you want to play as X? (Y/N)");
        String string = scanner.nextLine();
        if (string.contains("Y"))
        {
            playerIsX = true;
        }
        else if(string.contains("N"))
        {
        	playerIsX = false;
        }
        else
        {
        	System.out.println("Incorrect input");
        	return;
        }
        
        System.out.println("Do you want a challenge? (Y/N)");
        String string_ = scanner.nextLine(); //dummy string to hold the result.
        
        game = new Game(playerIsX, challenging);
        
    }

    /**
     * Enter the main control loop which returns only at the end of the game
     * when one party has won or there has been a draw.
     */
    public void mainLoop() {
        
    	boolean validMove = true; // this flag will ensure that the player can not place over it's own/ the ai's pieces
    	
        if (this.playerIsX == true)
        {
             while (game.getStatus() == GameStatus.IN_PROGRESS)
            {
            	validMove = true; // setting the valid move flag to true
                System.out.println(game.getBoard().toString());
                System.out.println("Enter Desired x-coordinate:");
                int x = scanner.nextInt();
                
                System.out.println("Enter Desired y-coordinate:");
                int y = scanner.nextInt();
                
                if (game.placePlayerPiece(y, x) == false) // check that the placement is valid
                {
                	System.out.println("Please enter a valid coordinates");
                	validMove = false;
                }
                if (validMove == true) // we only place the piece should the piece be valid.
                {
                	game.placePlayerPiece(y, x); 
                	System.out.println(game.getBoard().toString());
                	//check game status
                	if (game.getStatus() == GameStatus.X_WON || game.getStatus() == GameStatus.O_WON || game.getStatus() == GameStatus.DRAW)
                	{
                		break;
                	}
                	game.aiPlacePiece(); //AI will always choose a valid placement, no need to check
                	if (game.getStatus() == GameStatus.X_WON || game.getStatus() == GameStatus.O_WON || game.getStatus() == GameStatus.DRAW)
                	{
                		break;
                	}
                }           
            }
        }
        else //player is not x 
        {
            while (game.getStatus() == GameStatus.IN_PROGRESS)
            { 	
            	if (validMove == true) // will always be true on the first iteration. Ensures the AI doesn't place twice following an invalid input
            	{
            		game.aiPlacePiece(); 
            		System.out.println(game.getBoard().toString());
               	}
            	else 
            	{
            		System.out.println("Please enter a valid coordinates");
            	}
            	
            	validMove = true; // sets flag back to true for next check
            	
                if (game.getStatus() == GameStatus.X_WON || game.getStatus() == GameStatus.O_WON || game.getStatus() == GameStatus.DRAW)
                {
                    break;
                }
                
               	System.out.println("Enter Desired x-coordinate:");
                int x = scanner.nextInt();         
                System.out.println("Enter Desired y-coordinate:");
                int y = scanner.nextInt();
                
                if (game.placePlayerPiece(y,x) == false)
                {
                	validMove = false;
                }
                
                if(validMove == true)
                {
                	game.placePlayerPiece(y, x);
                	System.out.println(game.getBoard().toString());
                }
                
                //check game status
                if (game.getStatus() == GameStatus.X_WON || game.getStatus() == GameStatus.O_WON || game.getStatus() == GameStatus.DRAW)
                {
                    break;
                }
               
            }


        }
        // code will begin executing here after the break once a tie or a winner is found.
        if (game.getStatus() == GameStatus.X_WON)
        {
        	System.out.println(game.getBoard().toString());
            System.out.println("Player X wins!");
            return;
        }
        if(game.getStatus() == GameStatus.O_WON)
        {
        	System.out.println(game.getBoard().toString());
            System.out.println("Player O wins!");
            return;
        }
        if (game.getStatus()== GameStatus.DRAW)
        {
        	System.out.println(game.getBoard().toString());
            System.out.println("Draw!");
            return;
        }
    }

}
