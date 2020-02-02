/**
 * Represents the logic of the game in terms of detecting wins or draws.  Also
 * places new pieces for the human player or AI.
 *
 * @author William Smith and Jonathan Taylor
 */

public class Game {
    private Board board = new Board();
    private GameStatus status;
    private AI ai;
    private boolean playerIsX_;

    /**
     * Construct a new Game according to the given parameters.
     */
    public Game(boolean playerIsX, boolean challenging) {

        status = GameStatus.IN_PROGRESS;
        this.playerIsX_ = playerIsX;
        this.ai = new DumbAI(!playerIsX);
     }
    
    /*
     * Return a copy of the board's current contents.
     */
    public Board getBoard() {
        
        return board; 
    }

    /**
     * Get the game's status.
     */
    public GameStatus getStatus() {
        
        if (this.board.xWin() == true)
        {
            status = GameStatus.X_WON;
        }
        
        else if (this.board.oWin() == true)
        {
            status = GameStatus.O_WON;
        }
        else if (this.board.isFull() == true)
        {
            status = GameStatus.DRAW;
        }
        else 
        {
            status = GameStatus.IN_PROGRESS;
        }
        return status;
    }
    
    /**
     * Place a piece for the player on the board.
     * @param i i-coordinate of desired position.
     * @param j j-coordinate of desired position
     * @return true only if the coordinates of the desired position are in
     * range and the corresponding cell is empty.
     *
     * @precondition status == IN_PROGRESS
     *
     */ 
    public boolean placePlayerPiece(int i, int j) {
        
    	//flag any invalid inputs - not between (0-2)
    	if (i < 0 || i > 2)
    	{
    		return false;
    	}
    	else if (j < 0 || j > 2)
    	{
    		return false;
    	}
    	
    	if (board.isFull() == true)
        {
        	return false;
        }
    	
    	if(this.playerIsX_ == true)
        {	
    		if (board.get(i, j) == 'X' ||board.get(i, j) == 'O')
    		{
    			return false;
    		}
            Move move = new Move(i,j,'X');
            Board newBoard = new Board(board, move);
            this.board = newBoard;
            return true;
        }
        else 
        {
        	if (board.get(i, j) == 'X' || board.get(i, j) == 'O')
    		{
    			return false;
    		}
            Move move = new Move(i,j,'O');
            Board newBoard = new Board(board, move);
            this.board = newBoard;
            return true;
        }
     }

    /**
     * @precondition status == IN_PROGRESS
     */
    public void aiPlacePiece() {
       
    	Move newMove = this.ai.chooseMove(board);
    	Board newBoard = new Board(board, newMove);
    	this.board = newBoard;
    }
}
