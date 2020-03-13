import java.awt.*;
import javax.swing.*;
/**
 * Graphics handler for the Java Peg Game
 *
 * @author Blake Fox
 * @finalVersion 3/11/2020 \
 */
public class Output
{
    private JFrame frame = new JFrame("Peg Game");
    private JPanel panel = new JPanel();
    
    private static final int ROW = 5;
    private static final int COL = 9;
    private static final int SIZE = 700;
    
    public static final ImageIcon DEF_COLOR = new ImageIcon("DEF_COLOR.png", "DEF_COLOR");
    public static final ImageIcon PEG_COLOR = new ImageIcon("PEG_COLOR.png", "PEG_COLOR");
    public static final ImageIcon PEG_PRESS = new ImageIcon("PEG_PRESS.jpg", "PEG_PRESS");
    public static final ImageIcon CLK_COLOR = new ImageIcon("CLK_COLOR.jpg", "CLK_COLOR");
    
    private static final int[] ARRAY_TO_2D = new int[]{4, 12, 14, 20, 22, 24, 28, 30, 32, 34, 36, 38, 40, 42, 44};
    private JButton[][] buttons2D = new JButton[ROW][COL];
    private JButton[] buttons;
    
    private Board board;
    private Rules rules;
    /**
     * Sets the size of the game window
     * @param inBoard
     * sets size of board using board class
     */
    public Output(Board inBoard)
    {
        board = inBoard;
        frame.setSize(SIZE, SIZE);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
    }

    /**
     * Changes color for buttons clicked
     * @param inRules 
     * Creates a gridlayout for virtual buttons
     */
    public void buildTile(Rules inRules)
    {
        rules = inRules;
        int buttonNum = 0;
        panel.setLayout(new GridLayout(ROW, COL));
        for(int r = 0; r < ROW; r++)
        {
            for(int c = 0; c < COL; c++)
            {
                buttons2D[r][c] = this.getDefButton(buttonNum, rules);
                if (!arrayTo2DContains(buttonNum)){
                    buttons2D[r][c].setEnabled(false);
                }
                else {
                    buttons2D[r][c].setForeground(buttons2D[r][c].getBackground());
                }
                panel.add(buttons2D[r][c]);
                buttonNum ++;
            }
        }
    }

    /**
     * Sets the window to visible
     */
    public void toggleVisible()
    {
        frame.setVisible(!frame.isVisible());
    }

    /**
     * Changes color for the different tiles
     * @param number
     * Index of button
     * @param rules
     * from Rules class
     */
    public JButton getDefButton(int number, Rules rules)
    {
        JButton temp = new JButton();
        temp.setToolTipText(Integer.toString(number));
        temp.addActionListener(new TileListener(this, board, rules));
        temp.setBorderPainted(false);
        temp.setIcon(DEF_COLOR);
        return temp;
    }

    /**
     * Creates array of buttons
     */
    public void arrayTo2D()
    {
        int buttonNum = 0;
        JButton[] tempArray = new JButton[15];
        
        for(int r = 0; r < ROW; r++)
        {
            for(int c = 0; c < COL; c++)
            {
                if(Integer.parseInt(buttons2D[r][c].getToolTipText()) == ARRAY_TO_2D[buttonNum])
                {
                    tempArray[buttonNum] = buttons2D[r][c];
                    tempArray[buttonNum].setToolTipText(Integer.toString(buttonNum));
                    buttonNum ++;
                }else
                    buttons2D[r][c].setToolTipText(null);
            }
        }
        buttons = tempArray;
    }

    /**
     * @param Creates temporary variable for the virtual array to 2D array
     */
    public boolean arrayTo2DContains(int temp){
        for (int element : ARRAY_TO_2D) {
            if (element == temp)
                return true;
        }
        return false;
    }

    /**
     * @param Sets background color for the peg positions
     */
    public void update()
    {
        for(int i = 0; i < 15; i++)
        {
            JButton temp = buttons[i];
            int text = Integer.parseInt(temp.getToolTipText());
            if (text != rules.getStroke(0) && text != rules.getStroke(1))
                if (board.getPosition(i) == false){
                    buttons[i].setIcon(CLK_COLOR);
                }else{
                    buttons[i].setIcon(PEG_COLOR);
                }
            
            buttons[i].setForeground(buttons[i].getBackground());
        }
    }
    /**
     * @param demonstrates steps on how win
     */
    public void win(int moves)
    {
        UIManager ui = new UIManager();
        ui.put("OptionPane.okButtonText", "Close");
        ui.put("Button.background", Color.YELLOW);
        ui.put("OptionPane.background", Color.BLUE);
        ui.put("Panel.background", Color.BLUE);
        ui.put("OptionPane.messageForeground", Color.WHITE);
        
        JDialog win = new JDialog();
        int dialogButton = JOptionPane.DEFAULT_OPTION;
        if(rules.getScore() >= 50)
            JOptionPane.showConfirmDialog(win, "Great job! You score is " + rules.getScore() + " points!", "Congradulations!", dialogButton, 1);//returns winner dialog
        else
            JOptionPane.showConfirmDialog(win, "Try again. You score is " + rules.getScore() + " points.", "Game Over", dialogButton, 1);//returns winner dialog
        System.exit(0);//Closes the game
    }
    
    public void togglePressed(int i)
    {
        if(i != -1 && board.getPosition(i) == true)
        {
            if(buttons[i].getIcon() == PEG_PRESS){
                buttons[i].setIcon(PEG_COLOR);
            }else{
                buttons[i].setIcon(PEG_PRESS);
            }
            buttons[i].setForeground(buttons[i].getBackground());
        }
    }
}