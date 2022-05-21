

import com.sun.java.swing.plaf.motif.MotifLookAndFeel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUIDemo implements ActionListener{
	public static String LABEL_TEXT = "Hello Mr. K!";
	JFrame frame;
	JPanel contentPane;
	JLabel label;
	public static JTextField input;
	
	public GUIDemo(){
		//Create and set up the frame
		frame = new JFrame("GUIDemo"); //New frame with name
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Close when X is pressed
		
		//Create content pane with BoxLayout and empty borders
		contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS)); //creates layout
		contentPane.setBorder(BorderFactory.createEmptyBorder(20,20,20,20)); //adds border
		
		//Create and add label that is centered with empty border
		label = new JLabel(LABEL_TEXT);
		label.setAlignmentX(JLabel.CENTER_ALIGNMENT); // set it in the middle of the x axis
		label.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50)); //Set invisible border
		contentPane.add(label);

		//Create input box
		input = new JTextField("");
		input.setMaximumSize(new Dimension(300, 25)); //Maximum input box size
		input.setHorizontalAlignment(JTextField.CENTER); //set text to be centered
		input.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		input.addActionListener( this );
		contentPane.add(input);
		
		
		//Add content pane to frame
		frame.setContentPane(contentPane);
		
		//size and display the frame
		frame.pack();
		frame.setVisible(true);
	}
	
	//Create button click action event
	public void actionPerformed(ActionEvent e) {
		System.out.println(input.getText());
		label.setText(input.getText());
		input.setText("");
	}

	//Create and show the GUI
	public static void runGUI(){
		
	
		try {
			UIManager.setLookAndFeel(
			UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e){
			System.out.println();
		}
		
		GUIDemo greeting = new GUIDemo();
	}
	
	public static void main(String[] args){
		//Methods that create and show GUI should be run from an event-dispatching thread
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				runGUI();
			}
		});
	}
}
