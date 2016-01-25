import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
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

import Algorithms.LexDraw;
import Algorithms.Point;

public class DrawNetwork extends JPanel 
{
	String feedback="";
	int met = 0;
	public static final Color		WHITE				= Color.WHITE;
	BufferedImage offscreenImage,  onscreenImage;
	Graphics2D offscreen, onscreen;
	private static final Color		DEFAULT_CLEAR_COLOR	= WHITE;
	
	private boolean					defer				= false;
	private Color					penColor;
	private double					xmin, ymin, xmax, ymax;
	
	// boundary of drawing canvas, 0% border
	private static final double		BORDER				= 0.0;
	private static final double		DEFAULT_XMIN		= 0.0;
	private static final double		DEFAULT_XMAX		= 1.0;
	private static final double		DEFAULT_YMIN		= 0.0;
	private static final double		DEFAULT_YMAX		= 1.0;
	
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
		
		setfHeight( 750 );
		setfWidth( 500 );
		//setSize(getfWidth(), getfWidth());
		//Initialising those buffered images
		offscreenImage = new BufferedImage( getfWidth(), getfHeight(), BufferedImage.TYPE_INT_ARGB); //width and height
		onscreenImage = new BufferedImage( getfWidth(), getfHeight(), BufferedImage.TYPE_INT_ARGB);
		offscreen = offscreenImage.createGraphics();
		onscreen = onscreenImage.createGraphics();
//		frame = new JFrame();
//		frame.setLayout( new BorderLayout(3,3));
//		
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
		this.setBackground( DEFAULT_CLEAR_COLOR );                     //Set panel Color
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
		System.out.println("Closing the JFrame of test by force");
		//Test.frame.dispose();
		
		//LexDraw draw = new LexDraw();
		//draw.setCanvasSize(getfWidth(), getfHeight());
		offscreen = offscreenImage.createGraphics();
		onscreen = onscreenImage.createGraphics();
		offscreen.setColor(DEFAULT_CLEAR_COLOR);
		offscreen.fillRect(0, 0, getfWidth(), getfHeight());
		this.setXscale(0, getfWidth());
		this.setYscale(0, getfHeight());
		this.show(20);
		System.out.println("In drawVoronoi now");
		//populateNodes( getN(), getFovAngle() );
		// initialise network to size of the Network
		//g.setColor( Color.YELLOW );
//		for( int i= 0; i < nodes.length; i++ )
//			drawNode(g, nodes[i].getxCoor(), nodes[i].getyCoor(), nodes[i].getsRange(), this.sRange, nodes[i].getStartAngle(), nodes[i].getFovAngle() );
		Point[][] nearest = new Point[ getfWidth()][getfHeight()];
		for (int k = 0; k < nodes.length; k++)
		{
			//from rows
			Point p = new Point(nodes[k].getxCoor(), nodes[k].getyCoor());
			System.out.println("Inserting:       " + p);
			setPenColor(Color.getHSBColor((float) Math.random(), .7f, .7f));
			for (int i = 0; i < getfWidth(); i++)
			{
				for (int j = 0; j < getfHeight(); j++)
				{
					System.out.println("Creating p the: "+i+" th");
						Point q = new Point(i,j);
						if ((nearest[i][j] == null) || (q.distanceTo(p) < q.distanceTo(nearest[i][j])))
						{
							nearest[i][j] = p;
							this.color = new Color((float) Math.random(), (float) Math.random(),
							        (float) Math.random(), .5f);
							//g.setColor(this.color);
							//g.fillRect(i + 1, j + 1, gettRange(), gettRange()); //draw.filledSquare
							System.out.println("drawing square now");
							this.square(i + 0.5, j + 0.5, 0.5);
						}
						System.out.println("Outside if in 2nd for");
				}
			} 
			this.setPenColor(Color.BLACK);
			this.filledCircle( nodes[k].getxCoor(), nodes[k].getyCoor(), 4);
			//g.setColor( Color.YELLOW );
			//drawNode(g, nodes[k].getxCoor(), nodes[k].getyCoor(), nodes[k].getsRange(), this.sRange, nodes[k].getStartAngle(), nodes[k].getFovAngle() );
		}
		
		this.show(20);
		g.drawImage(offscreenImage, 0, 0, null);
		
		System.out.println("Done voronoing");
		return;
		
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
	
	public void setPenColor(Color color)
	{
		penColor = color;
		offscreen.setColor(penColor);
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
	
	/***************************************************************************
	 * User and screen coordinate systems.
	 ***************************************************************************/

	/**
	 * Sets the x-scale to be the default (between 0.0 and 1.0).
	 */
	public void setXscale()
	{
		setXscale(DEFAULT_XMIN, DEFAULT_XMAX);
	}

	/**
	 * Sets the y-scale to be the default (between 0.0 and 1.0).
	 */
	public void setYscale()
	{
		setYscale(DEFAULT_YMIN, DEFAULT_YMAX);
	}

	/**
	 * Sets the x-scale.
	 *
	 * @param min
	 *            the minimum value of the x-scale
	 * @param max
	 *            the maximum value of the x-scale
	 */
	public void setXscale(double min, double max)
	{
		double size = max - min;
		xmin = min - BORDER * size;
		xmax = max + BORDER * size;
	}

	/**
	 * Sets the y-scale.
	 *
	 * @param min
	 *            the minimum value of the y-scale
	 * @param max
	 *            the maximum value of the y-scale
	 */
	public void setYscale(double min, double max)
	{
		double size = max - min;
		ymin = min - BORDER * size;
		ymax = max + BORDER * size;
	}

	// helper functions that scale from user coordinates to screen coordinates
	// and back
	private double scaleX(double x)
	{
		return getWidth() * (x - xmin) / (xmax - xmin);
	}

	private double scaleY(double y)
	{
		return getHeight() * (ymax - y) / (ymax - ymin);
	}

	private double factorX(double w)
	{
		return w * getWidth() / Math.abs(xmax - xmin);
	}

	private double factorY(double h)
	{
		return h * getHeight() / Math.abs(ymax - ymin);
	}

	private double userX(double x)
	{
		return xmin + x * (xmax - xmin) / getWidth();
	}

	private double userY(double y)
	{
		return ymax - y * (ymax - ymin) / getHeight();
	}
	
	private void draw()
	{
		if (defer)
			return;
		onscreen.drawImage(offscreenImage, 0, 0, null);
		repaint();
	}
	
	public void show(int t)
	{
		defer = false;
		draw();
		try
		{
			Thread.sleep(t);
		} catch (InterruptedException e)
		{
			System.out.println("Error sleeping");
		}
		defer = true;
	}
	
	private void pixel(double x, double y)
	{
		offscreen.fillRect((int) Math.round(scaleX(x)), (int) Math.round(scaleY(y)), 1, 1);
	}
	
	public void square(double x, double y, double r)
	{
		if (r < 0)
			throw new IllegalArgumentException("square side length can't be negative");
		double xs = scaleX(x);
		double ys = scaleY(y);
		double ws = factorX(2 * r);
		double hs = factorY(2 * r);
		if (ws <= 1 && hs <= 1)
			pixel(x, y);
		else
			offscreen.draw(new Rectangle2D.Double(xs - ws / 2, ys - hs / 2, ws, hs));
		draw();
	}
	
	/**
	 * Draws a filled square of side length 2r, centered on (x, y).
	 *
	 * @param x
	 *            the x-coordinate of the center of the square
	 * @param y
	 *            the y-coordinate of the center of the square
	 * @param r
	 *            radius is half the length of any side of the square
	 * @throws IllegalArgumentException
	 *             if r is negative
	 */
	public void filledSquare(double x, double y, double r)
	{
		if (r < 0)
			throw new IllegalArgumentException("square side length can't be negative");
		double xs = scaleX(x);
		double ys = scaleY(y);
		double ws = factorX(2 * r);
		double hs = factorY(2 * r);
		if (ws <= 1 && hs <= 1)
			pixel(x, y);
		else
			offscreen.fill(new Rectangle2D.Double(xs - ws / 2, ys - hs / 2, ws, hs));
		draw();
	}
	//circles
	public void circle(double x, double y, double r)
	{
		if (r < 0)
			throw new IllegalArgumentException("circle radius can't be negative");
		double xs = scaleX(x);
		double ys = scaleY(y);
		double ws = factorX(2 * r);
		double hs = factorY(2 * r);
		if (ws <= 1 && hs <= 1)
			pixel(x, y);
		else
			offscreen.draw(new Ellipse2D.Double(xs - ws / 2, ys - hs / 2, ws, hs));
		draw();
	}
	
	public void filledCircle(double x, double y, double r)
	{
		if (r < 0)
			throw new IllegalArgumentException("circle radius can't be negative");
		double xs = scaleX(x);
		double ys = scaleY(y);
		double ws = factorX(2 * r);
		double hs = factorY(2 * r);
		if (ws <= 1 && hs <= 1)
			pixel(x, y);
		else
			offscreen.fill(new Ellipse2D.Double(xs - ws / 2, ys - hs / 2, ws, hs));
		draw();
	}
	
	
}
