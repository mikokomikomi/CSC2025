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
public class ComSciAssessmentTwoVerFive extends JFrame implements ActionListener,MouseListener{
    private Scanner keyboard;

    //-Class Finals
    final int BUTTONHEIGHT= 125;
    final int BUTTONWIDTH = 355;
    final int CRITCHANCECOST = 0;//final int responsible for cost if the crit upgrade within the upgrade costs array
    final int PRODUCTIONCOST = 1;//final int responsible for cost if the Production upgrade within the upgrade costs array
    final int AUTOCOST = 2;//final int responsible for cost if the Auto upgrade within the upgrade costs array
    final double COSTMULTIPLIER = 1.75;//final double responsible for the multiplier on the cost of upgrades when buying them
    final double CRITCHANCEDIMINISHINGRETURNS = 0.8;//final double responsible for the diminishing returns of upgrading crit
    final int XSIZE = 1000;//final variable holding the X dimensions of the program
    final int YSIZE = 1000;//final variable holding the Y dimensions of the program

    //-GUI class variables
    JMenuBar menuBar; //the menu bar
    JMenu menu; // the menu in the menu bar
    JMenuItem menuItem;//the item in the menu in the menu bar
    JButton theButton;//The primary button used to gain money
    JButton upgradeCritChance;//an upgrade used to get crit chance when bought
    JButton upgradeProduction;//an upgrade used to increase the amount of money gained when the button is clicked
    JButton upgradeAuto;//an upgrade used to increase the speed at which you automatically gain money
    JButton prestige;//a button to prestige the game. Prestiging is when you reset the game in return for permanent buffs
    JLabel moneyLabel;//a piece of text holding the current money count
    JLabel critLabel;//a piece of text holding the current level of the crit upgrade
    JLabel prodLabel;//a piece of text holding the current level of the production upgrade
    JLabel autoLabel;//a piece of text holding the current level of the auto upgrade
    JLabel levelLabel;//a piece of text holding the current level of player used when prestiging
    JLabel untilNextLevelLabel;//a piece of text holding the current amount of money needed until you level up
    Timer allTimer;//timer in which all automatically done things are in
    //-money cost and generation related class variables
    int [] upgradeCosts = {10,40,200};//array holding the costs of all the upgrades
    double currentMoneyCount = 0;//double holding the value of the current amount of money held
    double rawIncreasePerClick = 1;//double holding the increase you get to your money when the button is clicked
    double productionIncrease = 1;//double holding the value of the amount of increase to your raw increase per click when the production upgrade is brought
    double prodStart = 1;//double holding the starting value of production used when prestiging 
    double autoSpeed = 600;//the amount the production is multiplied by when auto
    double critChance = 0;//holds the current crit chance
    double critChanceIncrease = 6;//variable responsible for the amount crit rate is increased by when upgraded
    double critChecker;//The variable responsible for deciding whether the money gained is a crit or not, using math.random
    double prestigeMultiplier = 1;//The multiplier given to all income when prestiged
    //-Level related variable
    double pointsUntilNextLevel;//Holds the value of the amount of money to be gained until one levels up
    double levelProgression;//holds the value for the total amount of point's currently gained
    double nextLevel = 1500;//holds the value for the points required for the next level up
    int currentLevel = 1;//holds the value for the current player level
    int prestigeLevel;//holds the value for the current prestige level
    int autoLevel = 1;//holds the value for the current level of the auto upgrade
    int critLevel = 1;//holds the value for the current level of the crit upgrade
    int prodLevel = 1;//holds the value for the current level of the production upgrade
    public ComSciAssessmentTwoVerFive()
    {
        keyboard = new Scanner(System.in);
        //sets the name of the page
        String nameOfWindow = "Incremental game";
        setTitle(nameOfWindow);

        //sets the size of the page
        this.getContentPane().setPreferredSize(new Dimension(XSIZE,YSIZE));  
        this.getContentPane().setLayout(null);

        //specifies what happens when the window is closed
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.pack(); //sizes window so contents are above preferred size.
        this.toFront(); //says whether the window wants to be in the front.  Won’t force it to be the active window though.
        this.setVisible(true);
        this.add(new GamePanel());
        menuBar=new JMenuBar(); //bar at the top with the menu's on it
        this.setJMenuBar(menuBar);

        menu =  new JMenu("file");//file menu 
        menuBar.add(menu);

        menuItem=new JMenuItem("quit");//when clicked quits game
        menuItem.addActionListener(this);
        menu.add(menuItem);

        theButton = new JButton();//Main button
        theButton.setText("CLICK ME");
        theButton.setBounds(100,100,700,300);//x,y,width,height
        theButton.setFocusable(false);
        theButton.addActionListener(this); //adds actionlistener
        this.add(theButton);// add to frame

        upgradeCritChance = new JButton();//Crit upgrade
        upgradeCritChance.setText("CRIT $"+upgradeCosts[CRITCHANCECOST]);
        upgradeCritChance.setBounds(100,500,200,100);//x,y,width,height
        upgradeCritChance.setFocusable(false); 
        upgradeCritChance.addActionListener(this);//adds actionlistener
        upgradeCritChance.setBackground(Color.RED); // Sets the colour to red
        upgradeCritChance.setForeground(Color.WHITE); // Sets the text color to white
        this.add(upgradeCritChance);// add to frame

        upgradeProduction = new JButton();//Production upgrade
        upgradeProduction.setText("PRODUCTION $"+upgradeCosts[PRODUCTIONCOST]);
        upgradeProduction.setBounds(100,800,200,100);//x,y,width,height
        upgradeProduction.setFocusable(false);
        upgradeProduction.addActionListener(this); //adds actionlistener
        upgradeProduction.setBackground(Color.BLUE);// Sets the colour to blue
        upgradeProduction.setForeground(Color.WHITE); // Sets the text color to white
        this.add(upgradeProduction);// add to frame

        upgradeAuto = new JButton();//Auto upgrade
        upgradeAuto.setText("AUTO $"+upgradeCosts[AUTOCOST]);
        upgradeAuto.setBounds(700,800,200,100);//x,y,width,height
        upgradeAuto.setFocusable(false);
        upgradeAuto.addActionListener(this); //adds actionlistener
        upgradeAuto.setBackground(Color.YELLOW); // Sets the colour to yellow
        this.add(upgradeAuto);// add to frame
        this.pack();

        prestige = new JButton();
        prestige.setText("Unlock at level 3");
        prestige.setBounds(730,650,200,50);//x,y,width,height
        prestige.setFocusable(false);
        prestige.addActionListener(this); 
        this.add(prestige);// add to frame
        this.pack();
        addMouseListener(this);

        moneyLabel = new JLabel("$0:00");//adds a piece of text holding the current money count
        moneyLabel.setBounds(400, 50, 200, 50);//x,y,width,height
        add(moneyLabel);//add to frame

        critLabel = new JLabel("LVL 1");//adds a piece of text holding the current level of crit
        critLabel.setBounds(100, 600, 200, 50);//x,y,width,height
        add(critLabel);//add to frame

        prodLabel = new JLabel("LVL 1");//adds a piece of text holding the current level of production
        prodLabel.setBounds(100, 900, 200, 50);//x,y,width,height
        add(prodLabel);//add to frame

        autoLabel = new JLabel("LVL 1");//adds a piece of text holding the current level of auto
        autoLabel.setBounds(700, 900, 200, 50);//x,y,width,height
        add(autoLabel);//add to frame

        levelLabel = new JLabel("PLAYER LVL 1");//adds a piece of text holding the current level of the player
        levelLabel.setBounds(700, 500, 200, 50);//x,y,width,height
        add(levelLabel);//add to frame

        untilNextLevelLabel = new JLabel("2500");//adds a piece of text holding the current level of the player
        untilNextLevelLabel.setBounds(700, 520, 200, 50);//x,y,width,height
        add(untilNextLevelLabel);//add to frame

        allTimer = new Timer ((int)autoSpeed, new ActionListener() {//sets timer perameters
                @Override
                public void actionPerformed(ActionEvent a){
                    currentMoneyCount += rawIncreasePerClick ;//auto generation
                    levelProgression +=rawIncreasePerClick;
                    if (levelProgression >= nextLevel){//level up mechanics
                        currentLevel++;
                        nextLevel *= 1.5;
                        levelProgression = 0;
                        levelLabel.setText(String.format("PLAYER LVL %d", currentLevel));
                    }
                    if(currentLevel>=3){
                        prestige.setText("Reset? Gain 1."+currentLevel+" Mult");
                    }

                    moneyLabel.setText(String.format("$%.2f", currentMoneyCount));
                    untilNextLevelLabel.setText(String.format("%.0f / %.0f", levelProgression, nextLevel));
                }
            });
        allTimer.start();//Starts the time

        setVisible(true);
    }
    public class GamePanel extends JPanel {//jpanel removes buffering
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
    }

    public void actionPerformed(ActionEvent e){
        String cmd=e.getActionCommand();//Cmd holds actions made
        switch(cmd){
            case "quit":System.exit(0);
                break;
        }
        if (e.getSource()==theButton){//The mechanics of the primary button
            critChecker = (double) (Math.random() * 101);//checks for crits
            if(critChecker<=critChance){//deals with the results of crits
                currentMoneyCount+=rawIncreasePerClick;
                levelProgression +=rawIncreasePerClick;
            }
            currentMoneyCount+=rawIncreasePerClick;
            levelProgression+=rawIncreasePerClick;

            moneyLabel.setText(String.format("$%.2f", currentMoneyCount));
            untilNextLevelLabel.setText(String.format("%.0f / %.0f", levelProgression, nextLevel));
        }
        if (e.getSource()==upgradeCritChance){//the mechanics of the crit upgrade
            if(currentMoneyCount>=upgradeCosts[CRITCHANCECOST]){
                currentMoneyCount-=upgradeCosts[CRITCHANCECOST];
                upgradeCosts[CRITCHANCECOST]*=COSTMULTIPLIER;//multiplies cost once bought
                critChance += critChanceIncrease;//increases crit rate
                critChanceIncrease *= CRITCHANCEDIMINISHINGRETURNS;//diminishing returns to stop from getting 100% crit rate
                upgradeCritChance.setText("CRIT $"+upgradeCosts[CRITCHANCECOST]);
                critLevel ++;
                critLabel.setText(String.format("LVL "+ critLevel));
            }

            moneyLabel.setText(String.format("$%.2f", currentMoneyCount));
        }
        if (e.getSource()==upgradeProduction){//the mechanics of the production upgrade
            if(currentMoneyCount>=upgradeCosts[PRODUCTIONCOST]){
                currentMoneyCount-=upgradeCosts[PRODUCTIONCOST];
                upgradeCosts[PRODUCTIONCOST]*=COSTMULTIPLIER;
                rawIncreasePerClick += productionIncrease;
                upgradeProduction.setText("PRODUCTION $"+upgradeCosts[PRODUCTIONCOST]);
                prodLevel ++;
                prodLabel.setText(String.format("LVL "+ prodLevel));
                levelProgression +=rawIncreasePerClick;
            }

            moneyLabel.setText(String.format("$%.2f ", currentMoneyCount));
        }
        if (e.getSource()==prestige){//the mechanics of prestiging
            if(currentLevel>=3){//must be above level 3 to prestige
                //Resets everything
                upgradeCosts [CRITCHANCECOST] = 10;
                upgradeCosts [PRODUCTIONCOST] = 40;
                upgradeCosts [AUTOCOST] = 200;
                currentMoneyCount = 0;
                autoSpeed = 600;
                critChance = 0;
                critChanceIncrease = 6;
                prestigeMultiplier = currentLevel / 10.0 + 1;
                rawIncreasePerClick = 1;
                currentLevel = 1;
                rawIncreasePerClick = prodStart;
                productionIncrease *= prestigeMultiplier;
                rawIncreasePerClick *= prestigeMultiplier;
                prodStart = rawIncreasePerClick;//holds value in order to stop very high scaling 
                autoLevel = 1;
                critLevel = 1;
                prodLevel = 1;
                nextLevel = 1500;
                levelProgression = 0;
                //resets labels
                moneyLabel.setText(String.format("$%.2f", currentMoneyCount));
                levelLabel.setText(String.format("PLAYER LVL %d", currentLevel));
                upgradeAuto.setText("AUTO $"+upgradeCosts[AUTOCOST]);
                upgradeProduction.setText("PRODUCTION $"+upgradeCosts[PRODUCTIONCOST]);
                upgradeCritChance.setText("CRIT $"+upgradeCosts[CRITCHANCECOST]);
                prodLabel.setText(String.format("LVL "+ prodLevel));
                autoLabel.setText(String.format("LVL "+ autoLevel));
                critLabel.setText(String.format("LVL "+ critLevel));
                //Resets Main timer
                allTimer.stop();
                allTimer = new Timer((int)autoSpeed, new ActionListener() {
                        public void actionPerformed(ActionEvent a) {
                            currentMoneyCount += rawIncreasePerClick;
                            levelProgression += rawIncreasePerClick;

                            if (levelProgression >= nextLevel){
                                currentLevel++;
                                nextLevel *= 1.5;
                                levelProgression = 0;
                                levelLabel.setText(String.format("PLAYER LVL %d", currentLevel));
                            }

                            if(currentLevel >= 3){
                                prestige.setText("Reset? Gain 1." + currentLevel + " Mult");
                            }

                            moneyLabel.setText(String.format("$%.2f", currentMoneyCount));
                            untilNextLevelLabel.setText(String.format("%.0f / %.0f", levelProgression, nextLevel));
                        }
                    });
                allTimer.start();//Restarts main timer
                prestige.setText("Unlock at level 3");//resets text on prestige box
            }
        }
        if (e.getSource() == upgradeAuto) {//the mechanics of the auto upgrade
            if (currentMoneyCount >= upgradeCosts[AUTOCOST]) {
                currentMoneyCount -= upgradeCosts[AUTOCOST];
                upgradeCosts[AUTOCOST] *= COSTMULTIPLIER;
                autoSpeed *= 0.75;//Multiplies speed to stop bad scaling
                upgradeAuto.setText("AUTO $"+upgradeCosts[AUTOCOST]);

                // Restarts timer with new speed
                allTimer.stop();
                allTimer = new Timer((int)autoSpeed, new ActionListener() {
                        public void actionPerformed(ActionEvent a) {
                            currentMoneyCount += rawIncreasePerClick ;
                            levelProgression +=rawIncreasePerClick;
                            if (levelProgression >= nextLevel){
                                currentLevel++;
                                nextLevel *= 1.5;
                                levelProgression = 0;
                                levelLabel.setText(String.format("PLAYER LVL %d", currentLevel));

                            }
                            if(currentLevel>=3){
                                prestige.setText("Reset? Gain 1."+currentLevel+" Mult");
                            }

                            moneyLabel.setText(String.format("$%.2f", currentMoneyCount));
                            untilNextLevelLabel.setText(String.format("%.0f / %.0f", levelProgression, nextLevel));
                        }
                    });
                allTimer.start();
                autoLevel ++;
                autoLabel.setText(String.format("LVL "+ autoLevel));
            }
            moneyLabel.setText(String.format("$%.2f", currentMoneyCount));
        }
    }
}
