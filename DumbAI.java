/**
 * Realization of AI interface using simplistic random placement strategy.
 *
 * @author William Smith and Jonathan Taylor
 */

import java.util.Random;

public class DumbAI implements AI {
    
    private Random random = new Random();
    private boolean dumbAiIsX;
    private boolean MoveWorks = false;    
    /**
     * Construct a DumbAI.
     * 
     * @param aiIsX Indicates whether the AI player's piece is
     *              the 'X'.
     */
    public DumbAI(boolean aiIsX) {
        
        dumbAiIsX = aiIsX;
        
    }

    public Move chooseMove(Board board) {
        int randomI = 0;
        int randomJ = 0;
        MoveWorks = false;
        
    	while (MoveWorks == false) // only continue the program once the AI randomly produces a valid move
        {
            randomI = random.nextInt(3);
            randomJ = random.nextInt(3);

            if (board.get(randomI,randomJ ) == ' ')
            {
                MoveWorks = true;
            }
        }
        if (dumbAiIsX == true) //determines whether the AI will place an X or and O
        {
            Move move = new Move(randomI, randomJ,'X');
            return move;
        }
        else 
        {
            Move move = new Move(randomI, randomJ, 'O');
            return move;
        }
        
    }
}
