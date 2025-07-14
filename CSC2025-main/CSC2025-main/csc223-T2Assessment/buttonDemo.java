/**
 * Demo buttons
 *
 * @author Bill Viggers
 * @version 24-June-2022
 */

import javax.swing.JFrame;   
import javax.swing.JButton;   
import java.awt.Dimension;
import java.awt.event.*; // ActionEvents

//import java.awt.edvent.*;
public class buttonDemo extends JFrame implements ActionListener{

    JButton myButton;  // global so can be seem om actionPerformed.
    int clicks=0;

    public buttonDemo()
    {

        myButton = new JButton();
        myButton.setText("Click me.");
        myButton.setBounds (100,100,100,20);  //x,y,width,height.
        myButton.setFocusable(false); // remove box around text in button
        myButton.addActionListener(this); // we are implmenting the ActionListener in this interface.
        this.add(myButton);                    // add to frame

        setTitle("Button Demo");  //Whateveryou want the window to be called.
        this.getContentPane().setPreferredSize(new Dimension(1050,1050));  
        this.getContentPane().setLayout(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);    

        this.pack();
        this.toFront();  // Not too sure what this does, commenting out makes no apparent difference
        this.setVisible(true);

    } // constructor.

    public void actionPerformed(ActionEvent e){
        if (e.getSource()==myButton){
            System.out.println("Hello");
            setTitle("Button Demo: Clicked "+clicks+" times");
            clicks++;
        } else System.out.println("Huh?");
    }// actionPerformed

} // Class