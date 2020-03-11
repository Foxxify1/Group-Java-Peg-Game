import java.awt.*;
import java.util.Arrays;
import javax.swing.*;
/**
 * Graphics handler for the Java Peg Game
 *
 * @author Blake Fox
 * @finalVersion 3/11/2020
 */
public class Output
{
    JFrame frame = new JFrame("Peg Game");
    JPanel panel = new JPanel();
    
    public static final int ROW = 5;
    public static final int COL = 9;
    
    public static final Color DEF_COLOR = Color.BLACK;
    public static final Color PEG_COLOR = Color.YELLOW;
    public static final Color CLK_COLOR = Color.WHITE;
    
    public static final int[] ARRAY_TO_2D = new int[]{4, 12, 14, 20, 22, 24, 28, 30, 32, 34, 36, 38, 40, 42, 44};
    private JButton[][] buttons = new JButton[COL][ROW];
    
    private Board board;
    
    public Output(Board inBoard)
    {
        board = inBoard;
        frame.setSize(700, 700);
        frame.add(panel);
    }
    
    public void buildTile()
    {
        int buttonNum = 0;
        panel.setLayout(new GridLayout(ROW, COL));
        for(int r = 0; r < ROW; r++)
        {
            for(int c = 0; c < COL; c++)
            {
                buttons[c][r] = this.getDefButton();
                if (!arrayTo2DContains(buttonNum)) 
                    buttons[c][r].setEnabled(false);
                else {
                    buttons[c][r].setBackground(PEG_COLOR);
                    buttons[c][r].setForeground(buttons[c][r].getBackground());
                }
                panel.add(buttons[c][r]);
                buttonNum ++;
            }
        }
    }
    
    public void toggleVisible()
    {
        frame.setVisible(!frame.isVisible());
    }
    
    public JButton getDefButton()
    {
        JButton temp = new JButton();
        temp.setBackground(DEF_COLOR);
        temp.setForeground(temp.getBackground());
        temp.addActionListener(new TileListener(this, board));
        temp.setBorderPainted(false);
        return temp;
    }
    
    public JButton arrayTo2D()
    {
        for(int i = 0; i < (ROW * COL); i++)
        {
            for(int j = 0; j < (ROW * COL); j++)
                return new JButton();
        }
        return new JButton();
    }
    
    public boolean arrayTo2DContains(int temp){
        for (int element : ARRAY_TO_2D) {
            if (element == temp)
                return true;
        }
        return false;
    }
    
    public void setOutputToArray()
    {
        for(int i = 0; i < 15; i++){
            JButton temp = arrayTo2D();
            if (board.getPosition(i) == false){
                temp.setBackground(this.CLK_COLOR);
            }else{
                temp.setBackground(this.PEG_COLOR);
            }
            temp.setForeground(temp.getBackground());
            
        }
    }
}
