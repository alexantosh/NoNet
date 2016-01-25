import java.awt.BorderLayout;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Simul      
{
	public int n;
	public int iRange;
	public int sRange = 45;
	public int tRange = 75;
	public int fovAngle;
	public int startAngle;
	Node[ ] nodes;
	InputPanels panels = new InputPanels();
	JPanel panel = panels.gui;
	DrawNode nodeDraw; 
	JFrame frame = new JFrame();
	
	public Simul()
	{
		setN( 10 );
		nodes = new Node[ 10 ];
	}
	
	public Simul( int n )
	{
		setN( n );
		nodes = new Node[ n ];
	}
	
	public void drawNetwork( Node[] nodeArray )
	{
		nodeDraw = new DrawNode( nodeArray );
		panels.drawSidePanels();
		frame.setLayout( new BorderLayout(3,3));
		frame.add( nodeDraw, BorderLayout.CENTER );
		frame.add( panel, BorderLayout.EAST );
		//frame.pack();
		frame.setSize( 700, 500 );
		frame.setVisible( true );
		frame.setDefaultCloseOperation( frame.EXIT_ON_CLOSE );
	}	
	
	public int getN()
	{
		return n;
	}

	public void setN(int n)
	{
		this.n = n;
	}


	
	public void deploy()
	{		
		// generate new nodes
		Random generator = new Random();
		int x1, y1;   // avoid deploying them in same position
		
		for( int counter = 0; counter < n; counter++ )
		{
			x1 = generator.nextInt( 500 - sRange * 2 );
			y1 = generator.nextInt( 500 - sRange * 2 );
			startAngle = generator.nextInt( 360 );
			int sR = sRange;
			
			nodes[ counter ] = new Node( x1, y1, startAngle,  45, sR, 35, 15, "node"+ ( counter + 1 ) );	
		}
			
	}
	
	
	
	public static void main( String[] args )
	{
		//int numNodes=10;
		Simul sim = new Simul( 15 );
		sim.deploy();		
		sim.drawNetwork( sim.nodes );		
	}
	
	
}
