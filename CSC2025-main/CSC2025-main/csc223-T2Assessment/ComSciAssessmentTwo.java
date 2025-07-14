
/**
 * This is an Incremental game, made for COM223 assessment AS91897
 *
 * @author Miko Peszynski
 * @version 26/05/2025
 */
//libraries for GUI
import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.ImageIcon;
//Keyboard
import java.util.Scanner;
public class ComSciAssessmentTwo extends JFrame implements ActionListener,MouseListener{
    private Scanner keyboard;

    //-Class Finals
    final int CENTERX;
    final int CENTERY;
    final int BUTTONHEIGHT= 125;
    final int BUTTONWIDTH = 355;
    final String MAINBUTTONIMAGE="RedButton.png";
    final String PRESSEDBUTTONIMAGE="RedButtonPress.png";
    final int CRITCHANCECOST = 0;
    final int PRODUCTIONCOST = 1;
    final int AUTOCOST = 2;
    //-GUI class variables
    JMenuBar menuBar;
    JMenu menu;
    JMenuItem menuItem;
    Canvas myGraphic;
    JButton theButton;
    JButton upgradeCritChance;
    JButton upgradeProduction;
    JButton upgradeAuto;
    //-Location and size related class variables
    int xSize;
    int ySize;
    int buttonx;
    int buttony;
    //-money related class variables
    int [] upgradeCosts = {10,40,200};
    double currentMoneyCount = 0;
    int increasePerClick = 1;
    double costMultiplier = 1.75;
    double critChance = 0;
    //Boolean variables
    boolean isButtonPressed = false;
    boolean gameOngoing = true;
    public ComSciAssessmentTwo()
    {
        keyboard = new Scanner(System.in);
        //sets the name of the page
        String nameOfWindow = "Incremental game"; 
        xSize = 1000;
        ySize = 1000;
        CENTERX = xSize/2;
        CENTERY = ySize/2;
        setTitle(nameOfWindow);
    
        //sets the size of the page
        this.getContentPane().setPreferredSize(new Dimension(xSize,ySize));  
        this.getContentPane().setLayout(null);
        
        //specifies what happens when the window is closed
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.pack(); //sizes window so contents are above preferred size.
        this.toFront(); //says whether the window wants to be in the front.  Won’t force it to be the active window though.
        this.setVisible(true);
        this.add(new GamePanel());
        menuBar=new JMenuBar(); //bar at the top with the menu's on it
        this.setJMenuBar(menuBar);

        menu =  new JMenu("file");
        menuBar.add(menu);

        menuItem=new JMenuItem("quit");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        theButton = new JButton();
        theButton.setText("BUTTON");
        theButton.setBounds(100,100,700,300);//x,y,width,height
        theButton.setFocusable(false);
        theButton.addActionListener(this); // we are implmenting the ActionListener in this interface.
        this.add(theButton);// add to frame
        
        upgradeCritChance = new JButton();
        upgradeCritChance.setText("CRIT $"+upgradeCosts[CRITCHANCECOST]);
        upgradeCritChance.setBounds(100,500,200,100);//x,y,width,height
        upgradeCritChance.setFocusable(false);
        upgradeCritChance.addActionListener(this); // we are implmenting the ActionListener in this interface.
        this.add(upgradeCritChance);// add to frame
        
        upgradeProduction = new JButton();
        upgradeProduction.setText("PRODUCTION $"+upgradeCosts[PRODUCTIONCOST]);
        upgradeProduction.setBounds(100,800,200,100);//x,y,width,height
        upgradeProduction.setFocusable(false);
        upgradeProduction.addActionListener(this); // we are implmenting the ActionListener in this interface.
        this.add(upgradeProduction);// add to frame
        this.pack();
        
        addMouseListener(this);
    }
    int mousex;
    int mousey;
    public class GamePanel extends JPanel {
        public void paintComponent (Graphics g) {
            super.paintComponent(g);
            repaint();
        }
    }

    public void mouseExited(MouseEvent e){}

    public void mouseEntered(MouseEvent e){}

    public void mouseReleased(MouseEvent e){
    }

    public void mousePressed(MouseEvent e){}

    public void mouseClicked(MouseEvent e){
        mousex=e.getX();
        mousey=e.getY();
        System.out.println(currentMoneyCount);
    }

    public void actionPerformed(ActionEvent e){
        String cmd=e.getActionCommand();
        System.out.println(cmd);
        switch(cmd){
            case "quit":System.exit(0);
                break;
        }
         if (e.getSource()==theButton){
             currentMoneyCount+=increasePerClick;
             setTitle("you have "+currentMoneyCount+" money");
        }
                 if (e.getSource()==upgradeCritChance){
            if(currentMoneyCount>=upgradeCosts[CRITCHANCECOST]){
                currentMoneyCount-=upgradeCosts[CRITCHANCECOST];
                upgradeCosts[CRITCHANCECOST]*=costMultiplier;
                upgradeCritChance.setText("CRIT $"+upgradeCosts[CRITCHANCECOST]);
            }
            
            setTitle("you have "+currentMoneyCount+" money");
        }
                 if (e.getSource()==upgradeProduction){
            if(currentMoneyCount>=upgradeCosts[PRODUCTIONCOST]){
                currentMoneyCount-=upgradeCosts[PRODUCTIONCOST];
                upgradeCosts[PRODUCTIONCOST]*=costMultiplier;
                increasePerClick += 1;
                upgradeProduction.setText("PRODUCTION $"+upgradeCosts[PRODUCTIONCOST]);
            }
            
            setTitle("you have "+currentMoneyCount+" money");
        }
    }
}
