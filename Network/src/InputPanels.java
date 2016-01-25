import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

public class InputPanels
{
	JPanel labelFields = new JPanel(new BorderLayout(2, 3));
	JPanel buttonConstrain = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel labels = new JPanel(new GridLayout(0, 1, 1, 3));
	JPanel fields = new JPanel(new GridLayout(0, 1, 1, 3));
	JPanel guiCenter = new JPanel(new BorderLayout(2, 2));
	JPanel gui = new JPanel(new BorderLayout(2, 3));
	JButton		startButton		= new JButton("Start");
	JLabel		sRangeLabel	= new JLabel("SensorRange", SwingConstants.LEFT);
	JLabel		nodeNoLabel	= new JLabel("Nodes", SwingConstants.LEFT);
	JLabel		fovLabel	= new JLabel("FOV", SwingConstants.LEFT);
	JLabel		tRangeLabel	= new JLabel("tRange", SwingConstants.LEFT);
	JLabel		iRangeLabel	= new JLabel("iRange", SwingConstants.LEFT);
	JTextField	nodeNoField	= new JTextField("100", 5);
	JTextField	sRangeField	= new JTextField("75", 5);
	JTextField	fovField	= new JTextField("75", 5);
	JTextField	tRangeField	= new JTextField("75", 5);
	JTextField	iRangeField	= new JTextField("75", 5);
	JTextArea progressInfoTextField = new JTextArea(5,20); 
	int counter = 0;
	Simul sim;
	

	public void drawSidePanels()
	{
		// takes care of the labels and textfields
		labelFields.setBorder(new TitledBorder("The Parameters"));

		// add the labels
		nodeNoField.setPreferredSize( new Dimension(100,50));
		labels.add( nodeNoLabel );
		labels.add( sRangeLabel );
		labels.add( fovLabel );
		labels.add( tRangeLabel );
		labels.add( iRangeLabel );
		
		// add the fields
		fields.add( nodeNoField );
		fields.add( sRangeField );
		fields.add( fovField );
		fields.add( tRangeField );
		fields.add( iRangeField );
		
//		for (int ii = 1; ii <= 4; ii++)
//		{
//			labels.add(new JLabel("Label " + ii));
//			// if these were of different size, it would be necessary to
//			// constrain them using another panel
//			fields.add(new JTextField(10));
//		}

		labelFields.add(labels, BorderLayout.CENTER); // Change to west
		labelFields.add(fields, BorderLayout.EAST);

		guiCenter.setBorder(new TitledBorder("Click To Start"));
		//buttonConstrain.setBorder(new TitledBorder("FlowLayout"));
		buttonConstrain.add( startButton );
		
		//Add action listener to button
		startButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				
				int nodes = Integer.parseInt( nodeNoField.getText() );
				int sensorRange = Integer.parseInt( sRangeField.getText() );
				int fov = Integer.parseInt( fovField.getText() );
				int tRange = Integer.parseInt( tRangeField.getText() );
				int iRange = Integer.parseInt( iRangeField.getText() );
				progressInfoTextField.setText("Clicked me: "+ (++counter) + " times!\nNew Number of Nodes: " + nodes );
				
				sim = new Simul( nodes );
				sim.deploy();
				sim.nodeDraw = new DrawNode( sim.nodes );
				sim.panels.drawSidePanels();
				sim.frame.add( sim.nodeDraw, BorderLayout.CENTER );
				sim.frame.add( sim.panel, BorderLayout.EAST );
				
				sim.frame.revalidate();
				sim.frame.repaint();
				//frame.pack();
				
				//gui.repaint();
			}
		});
		
		guiCenter.add(buttonConstrain, BorderLayout.NORTH);
		
		// add the progressInfo Text Area
		progressInfoTextField.setText( "Whatever is happening in the network \nWill be displayed here" );
		progressInfoTextField.setEditable( false );
		guiCenter.add(new JScrollPane( progressInfoTextField ));
		gui.add(labelFields, BorderLayout.NORTH);
		gui.add(guiCenter, BorderLayout.CENTER);

	}

	public static void main(String[] args)
	{

		InputPanels panels = new InputPanels();
		panels.drawSidePanels();
		JPanel pans = panels.gui;
		JFrame frame = new JFrame("The Side Panels To Come Later");
		frame.setSize(500, 500);
		frame.add(pans);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

}
