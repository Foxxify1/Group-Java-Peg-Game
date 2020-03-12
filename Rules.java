import java.util.*;
/**
 * Checks Rules for legal and illegal moves and winner
 * 
 * @author Jacob Directo and Blake Fox
 * @finalVersion 3/11/2020
 */
public class Rules
{
    private Output output;
    private Board board;
    
    /* {"from", "to", "jumped"} */
    private static int[][] jumpTable = { 
        { 0, 3, 1}, { 0, 5, 2}, { 1, 6, 3}, { 1, 8, 4}, 
        { 2 ,7 ,4}, { 2, 9, 5}, { 3, 5, 4}, { 3, 0, 1}, 
        { 3,10, 6}, { 3,12, 7}, { 4,13, 8}, { 4,11, 7}, 
        { 5, 3, 4}, { 5,12, 8}, { 5,14, 9}, { 5, 0, 2}, 
        { 6, 1, 3}, { 6, 8, 7}, { 7, 2, 4}, { 7, 9, 8}, 
        { 8, 1, 4}, { 8, 6, 7}, { 9, 7, 8}, { 9, 2, 5}, 
        {10,12,11}, {10, 3, 6}, {11, 4, 7}, {11,13,12}, 
        {12,10,11}, {12,14,13}, {12, 5, 8}, {12, 3, 7}, 
        {13,11,12}, {13, 4, 8}, {14,12,13}, {14, 5, 9} };
    
    private String winner;
    private ArrayList<Integer> numberPressed = new ArrayList<Integer>();
    public Rules(Output inOutput, Board inBoard)
    {
        output = inOutput;
        board = inBoard;
    }  
    
    public void addStroke(int buttonNumber)
    {
        numberPressed.add(buttonNumber);
        if(numberPressed.size() >= 2){
            if(canMove(numberPressed.get(0), numberPressed.get(1)) != -1){
                makeMove(numberPressed.get(0), numberPressed.get(1));
            }else{
                numberPressed.remove(numberPressed.size() - 1);
            }
            numberPressed.clear();
        }
    }
    
    public int canMove(int fromPos, int toPos)
    {
        for(int i = 0; i < jumpTable.length; i++)
        {
            if(fromPos == jumpTable[i][0] && toPos == jumpTable[i][1] && board.getPosition(jumpTable[i][0]) == true && board.getPosition(jumpTable[i][1]) == false && board.getPosition(jumpTable[i][2]) == true){
                System.out.println("From: " + fromPos);
                System.out.println(" To : " + toPos);
                
                return jumpTable[i][2]; // returns jumpPos and break
            }
        }
        return -1;
    }
    
    public void makeMove(int fromPos, int toPos)
    {
        board.setPosition(fromPos, false);
        board.setPosition(toPos, true);
        System.out.println("From: " + fromPos);
        System.out.println(" To : " + toPos);
        System.out.println("Skip: " + canMove(toPos, fromPos));
        board.setPosition(canMove(toPos, fromPos), false);
        output.update();
        if(isWinner() == true)
        {
            
        }
    }
    
    public boolean isWinner()
    {
        for(int i = 0; i < 15; i++)
        {
            for(int j = 0; j < 15; j++)
            {
                for(int k = 0; k < 15; k++)
                {
                    if(i != j && i != k && j != k){
                        if(board.getPosition(i) == false && board.getPosition(j) == true && board.getPosition(k) == false){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
}