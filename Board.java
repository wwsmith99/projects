/**
 * An immutable class that represents the state of the 3×3 tic-tac-toe board.
 *
 * @author William Smith and Jonathan Taylor
 */

public class Board {

    private char[][] board = new char[3][3];
    /**
     * Construct an empty board (contains all space char's).
     */
    public Board() {
        char[][] emptyBoard = {{ ' ', ' ', ' '},{ ' ', ' ', ' '},{ ' ', ' ', ' '}};
        this.board = emptyBoard;
        
    }

    /**
     * Given the 'other' board as a starting condition, apply the given
     * 'move' to generate this board's state.
     */
    public Board(Board other, Move move) {
    	
        int posI = move.getI();
        int posJ = move.getJ();
        char piece = move.getPiece();

        other.board[posI][posJ] = piece; 
        this.board = other.board;


    }

    /*
     * Convert to a string that shows the board's state.
     */
    public String toString() {

        String dottedLine = "-------------\n";
        String top = "| " + board[0][0] + " | " + board[0][1] + " | " + board[0][2]+ " |\n";    
        String middle = "| " + board[1][0] + " | " + board[1][1] + " | " + board[1][2]+ " |\n";
        String bottom = "| " + board[2][0] + " | " + board[2][1] + " | " + board[2][2]+ " |\n";

        String total = dottedLine + top + dottedLine + middle + dottedLine + bottom + dottedLine;
        return total;
    }

    /**
     * Get the entry of the board at column i, row j.  Both indices should
     * be in the range [0, 2].
     */
    public char get(int i, int j) {
        return this.board[i][j];
    }
    
    /**
     * @return true if there remain no empty spots on the board.
     */
    public boolean isFull() {

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (this.board[i][j] == ' ')
                return false;
            }

        }
        return true;
    }
        
    /* 
     * The following two methods will check 5 tiles for all possible win conditions. 
     * These tiles include: top middle, middle middle, left middle, right middle, and bottom middle.
     */
    public boolean xWin()
    {
        if (this.board[0][1] == 'X')
        {
            if ((this.board[0][0] == 'X') && (this.board[0][2] == 'X')) 
            {
                return true;
            }
        }
        if (this.board[1][0] == 'X')
        {
            if (this.board[0][0] == 'X'  && this.board[2][0] == 'X') 
            {
                return true;
            }
        }
        if (this.board[1][2] == 'X') 
        {
            if (this.board[0][2] == 'X' && this.board[2][2] == 'X') 
            {
                return true;
            }
        }
        if (this.board[1][1] == 'X')
        {
            if (this.board[0][1] == 'X' && this.board[2][1] == 'X') 
            {
                return true;
            }
            else if (this.board[1][0] == 'X' && this.board[1][2] == 'X')
            {
                return true;
            }
            else if (this.board[0][0] == 'X' && this.board[2][2] == 'X')
            {
                return true;
            }
            else if (this.board[0][2] == 'X' && this.board[2][0] == 'X')
            {
                return true;
            }

        }
        if (this.board[2][1] == 'X')
        {
            if (this.board[2][0] == 'X' && this.board[2][2] == 'X') 
            {
                return true;
            }
        }
        return false;
    }
    
    
    
    public boolean oWin()
    {      
    	if (this.board[0][1] == 'O')
        {
            if ((this.board[0][0] == 'O') && (this.board[0][2] == 'O')) 
            {
                return true;
            }
        }
        if (this.board[1][0] == 'O')
        {
            if (this.board[0][0] == 'O'  && this.board[2][0] == 'O') 
            {
                return true;
            }
        }
        if (this.board[1][2] == 'O') 
        {
            if (this.board[0][2] == 'O' && this.board[2][2] == 'O') 
            {
                return true;
            }
        }
        if (this.board[1][1] == 'O')
        {
            if (this.board[0][1] == 'O' && this.board[2][1] == 'O') 
            {
                return true;
            }
            else if (this.board[1][0] == 'O' && this.board[1][2] == 'O')
            {
                return true;
            }
            else if (this.board[0][0] == 'O' && this.board[2][2] == 'O')
            {
                return true;
            }
            else if (this.board[0][2] == 'O' && this.board[2][0] == 'O')
            {
                return true;
            }

        }
        if (this.board[2][1] == 
        		'O')
        {
            if (this.board[2][0] == 'O' && this.board[2][2] == 'O') 
            {
                return true;
            }
        }
        return false;
    }
} 
