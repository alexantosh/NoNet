import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Random;


import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import Algorithms.Point;
import Algorithms.Draw;

public class DrawNetwork extends JPanel 
{
	String feedback="";
	int met = 0;
	Color color;
	JPanel labelFields = new JPanel(new BorderLayout(2, 3));
	JPanel networkField = new JPanel();
	JPanel buttonConstrain = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel labels = new JPanel(new GridLayout(0, 1, 1, 3));
	JPanel fields = new JPanel(new GridLayout(0, 1, 1, 3));
	JPanel guiCenter = new JPanel(new BorderLayout(2, 2));
	JPanel gui = new JPanel(new GridLayout(0,1,1,3));
	JButton		startButton = new JButton("Reset");
	JButton sendButton = new JButton("Send");
	JLabel		sRangeLabel	= new JLabel("SenseRange", SwingConstants.LEFT);
	JLabel		nodeNoLabel	= new JLabel("Nodes", SwingConstants.LEFT);
	JLabel		fovLabel	= new JLabel("FOV", SwingConstants.LEFT);
	JLabel		tRangeLabel	= new JLabel("tRange", SwingConstants.LEFT);
	JLabel		iRangeLabel	= new JLabel("iRange", SwingConstants.LEFT); //Interference Range
	JLabel 		sEnergyLabel= new JLabel("Energy(Joules)", SwingConstants.LEFT);
	JLabel 		numberOfClusters = new JLabel("NumberOfClusters", SwingConstants.LEFT);
	JLabel 		gateWays = new JLabel("gateWays", SwingConstants.LEFT);
	JLabel 		algorithm = new JLabel("algorithm", SwingConstants.LEFT);


	JTextField	nodeNoField	= new JTextField("10", 5);
	JTextField	sRangeField	= new JTextField("75", 5);
	JTextField	fovField	= new JTextField("75", 5);
	JTextField	tRangeField	= new JTextField("75", 5);
	JTextField	iRangeField	= new JTextField("75", 5);
	JTextField  sEnergyField= new JTextField("1000",5 );
	JTextField  numClusters = new JTextField("",5);
	JTextField  gateWaysField = new JTextField("",5);
	JComboBox algos; 
	JTextArea progressInfoTextField = new JTextArea(5,20);
	public int tRange = 75;
	public int fovAngle = 45;
	public int startAngle;
	int fHeight, fWidth;
	int n;
	public int iRange;
	public int sRange = 70;
	Node[ ] nodes;
	String[] algorithms = {"Normal", "Voronoi", "LEACH", "Ours" }; 
	Graphics gl;
	
	// My main method is right here
	public static void main( String[] args )
	{
		DrawNetwork net = new DrawNetwork( 10 , 65 );
	}
	
	public DrawNetwork( int n )
	{
		setN( n );
		init();
	}
	
	public DrawNetwork( int n, int fov )
	{
		setN( n );
		setFovAngle( fov );
		init();
	}
	
	
	public DrawNetwork()
	{
		init();
	}
	
	public void init()
	{
		
		setfHeight( 900 );
		setfWidth( 700 );
//		frame = new JFrame();
//		frame.setLayout( new BorderLayout(3,3));
		
		color = new Color(1.0f, 1.0f, 0.0f, 0.7f);
		
		//Handle the algorithms
		//Add clustering Algorithms to Combo Box
		algos = new JComboBox( algorithms );
		algos.addItemListener( new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				if( e.getStateChange() == ItemEvent.SELECTED )
				{
					int selected = algos.getSelectedIndex(); 
					if( selected == 0 )
					{
						setMet( selected );
						feedback += "\nGo Random. Selected: Random==" + algorithms[selected];
						progressInfoTextField.setText( feedback );
						repaint();
					}
					// Set to Voronoi
					else if( selected == 1 )
					{
						setMet( selected );
						feedback += "\nGo Voronoi. Selected: Voronoi==" + algorithms[selected];
						progressInfoTextField.setText( feedback );
						//drawVoronoi( g );
						repaint();
					}
					else if( selected == 2 )
					{
						setMet( selected );
						feedback += "\nGo LEACH. Selected: LEACH==" + algorithms[selected];
						progressInfoTextField.setText( feedback );
						repaint();
					}
					else 
					{
						setMet( selected );
						feedback += "\nGo Us. Selected: Ours==" + algorithms[selected];
						progressInfoTextField.setText( feedback );
						repaint();
					}
				}
			}
		});
				
		// get on with fields
		labelFields.setBorder(new TitledBorder("The Parameters"));

		// add the labels
		//nodeNoField.setPreferredSize( new Dimension(20,20));
		//nodeNoField.setAlignmentX(CENTER_ALIGNMENT);
		labels.add( nodeNoLabel );
		labels.add( sRangeLabel );
		labels.add( fovLabel );
		labels.add( tRangeLabel );
		labels.add( iRangeLabel );
		labels.add( sEnergyLabel );
		labels.add( numberOfClusters );
		labels.add( gateWays );
		labels.add( algorithm );
		// add the fields
		fields.add( nodeNoField );
		fields.add( sRangeField );
		fields.add( fovField );
		fields.add( tRangeField );
		fields.add( iRangeField );
		fields.add( sEnergyField );
		fields.add( numClusters );
		fields.add(gateWaysField);
		fields.add( algos );
		
		
		
		// add fields, labels and algorithms to the side panel
		labelFields.add(labels, BorderLayout.CENTER); // Change to west
		labelFields.add(fields, BorderLayout.EAST);
		//labelFields.add( algos, BorderLayout.SOUTH );

		guiCenter.setBorder(new TitledBorder("Click To Reset"));
		//buttonConstrain.setBorder(new TitledBorder("FlowLayout"));
		startButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
					int nods = Integer.parseInt( nodeNoField.getText() );
					int fovNew = Integer.parseInt( fovField.getText() );
					customizeNode(nods, fovNew );
					feedback += "\nNumber of nodes: " + nodeNoField.getText() + "\n" + "Fov: " + fovField.getText() + "\n"
							+ "Sensor range: " + sRangeField.getText();
					progressInfoTextField.setText( feedback );
					if( !(nodes[0] == null) )
						lexSquare(nodes[0].getxCoor(), nodes[0].getyCoor(), nodes[0].getsRange());
					repaint();
			}
		});
		
		buttonConstrain.add( startButton );
		//buttonConstrain.add( sendButton );
		guiCenter.add(buttonConstrain, BorderLayout.NORTH);
		
		// add the progressInfo Text Area
		progressInfoTextField.setText( "Relevant info to be displayed here" );
		progressInfoTextField.setEditable( false );
		guiCenter.add(new JScrollPane( progressInfoTextField ));
		gui.add( labelFields ); // changed From BorderLayout.NORTH AND SOUTH. To  GridLayout For Even Distribution of components
		gui.add( guiCenter );
		this.setBorder( new TitledBorder("Network"));
//		this.setBackground( Color.BLUE );                     //Set panel Color
//		frame.add( this, BorderLayout.CENTER );
//		frame.add( gui, BorderLayout.EAST );
//		frame.setSize( getfHeight(),  getfWidth() );
//		frame.setVisible( true );
//		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
	}
	
	
	public void customizeNode( int n, int fov )
	{		
		setN( n );
		setFovAngle( fov );
	}
	
	void lexSquare( int x, int y, int r )
	{
		//Later on
		//gl.drawRect(x, y, r, r);
	}
	
	public void drawNode( Graphics g, int x, int y, int width, int height, int startAngle, int arcAngle )
	{
		//super.paintComponent( g );		
//		g.setColor( Color.BLACK );
//		g.fillArc(15, 15, 200, 200, 45, 45);	
		//g.setColor( Color.YELLOW );
		g.setColor( this.color );
		g.fillArc(x, y, width, height, startAngle, arcAngle);		
	}
	
	public void drawRandom( Graphics g )
	{
		populateNodes( getN(), getFovAngle() );
		for( int i= 0; i < nodes.length; i++ )
			drawNode(g, nodes[i].getxCoor(), nodes[i].getyCoor(), nodes[i].getsRange(), this.sRange, nodes[i].getStartAngle(), nodes[i].getFovAngle() );

	}
	
	public void drawVoronoi( Graphics g ) // get Graphics later
	{
		//populateNodes( getN(), getFovAngle() );
		// initialise network to size of the Network
		g.setColor( Color.YELLOW );
		for( int i= 0; i < nodes.length; i++ )
			drawNode(g, nodes[i].getxCoor(), nodes[i].getyCoor(), nodes[i].getsRange(), this.sRange, nodes[i].getStartAngle(), nodes[i].getFovAngle() );
		Point[][] nearest = new Point[ getWidth()][getHeight()];
		Point q = new Point(1,3);
		//from rows
		Point p = new  Point( nodes[0].getxCoor(), nodes[0].getyCoor() );
		for( int i = 0; i < getWidth(); i++ )
		{
			for(int j=0; j<getHeight(); j++ )
			{
				if( j < nodes.length - 1 )
				{
					q = new Point( nodes[j+1].getxCoor(), nodes[j+1].getyCoor() );
					if (i < nodes.length )
						if ((nearest[i][j] == null) || (q.distanceTo(p) < q.distanceTo(nearest[i][j])))
						{
							nearest[i][j] = new Point(nodes[i].getxCoor(),nodes[i].getyCoor());
							this.color = new Color(0.3f, 0.2f, 0.1f, 0.23f );
							g.setColor(this.color);
							g.fillRect(nodes[i].getxCoor() + 1, nodes[j].getxCoor() + 1, gettRange(), gettRange() );  //draw.filledSquare
						}
				}
			}
		}
		
	}
	
	// put nodes onto panel
	public void paintComponent( Graphics g)
	{
		super.paintComponent( g );
		int todo = getMet();
		switch( todo )
		{
			case 0:
				drawRandom( g );
				System.out.println("Deployed Randomly");
				break;
			case 1:
				drawVoronoi( g );
				System.out.println("Voronoi working now...");
				break;
			case 2:
				System.out.println("Leach");
				break;
			case 3:
				System.out.println("Ours");
				break;
				default:
					drawRandom( g );
		}
		
	}
	
	public void populateNodes()
	{		
		// generate new nodes
		nodes = new Node[ getN() ];
		Random generator = new Random();
		int x1, y1;   // avoid deploying them in same position
		
		for( int counter = 0; counter < nodes.length; counter++ )
		{
			x1 = generator.nextInt( getfWidth() - ( tRange * 4 ) );
			y1 = generator.nextInt( getfHeight() - ( tRange * 4 ) );
			startAngle = generator.nextInt( 360 );
			int sR = gettRange();		
			nodes[ counter ] = new Node( x1, y1, startAngle,  getFovAngle(), sR, sR, 15, "node"+ ( counter + 1 ) );	
		}		
	}
	
	public void populateNodes( int numNods, int fov)
	{
		setN( numNods );
		setFovAngle( fov );
		populateNodes();
	}
	public int getfHeight()
	{
		return fHeight;
	}

	public void setfHeight(int fHeight)
	{
		this.fHeight = fHeight;
	}

	public int getfWidth()
	{
		return fWidth;
	}

	public void setfWidth(int fWidth)
	{
		this.fWidth = fWidth;
	}

	public int getiRange()
	{
		return iRange;
	}

	public void setiRange(int iRange)
	{
		this.iRange = iRange;
	}

	public int getsRange()
	{
		return sRange;
	}

	public void setsRange(int sRange)
	{
		this.sRange = sRange;
	}
	
	public int getMet()
	{
		return met;
	}

	public void setMet(int met)
	{
		this.met = met;
	}
	
	public int gettRange()
	{
		return tRange;
	}

	public void settRange(int tRange)
	{
		this.tRange = tRange;
	}

	public int getFovAngle()
	{
		return fovAngle;
	}

	public void setFovAngle(int fovAngle)
	{
		this.fovAngle = fovAngle;
	}
	public int getN()
	{
		return n;
	}

	public void setN(int n)
	{
		this.n = n;
	}
}
