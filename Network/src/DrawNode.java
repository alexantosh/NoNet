
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DrawNode extends JPanel
{
	/**
	 *   public void drawArc( int x, int y, int width, int height, 
	 *   int startingAngle, int arcAngle)
	 *   
	 *   public void fillArc( int x, int y, int width, int height,
	 *   int startingAngle, int arcAngle)
	 * */
	
	List <Node> nods;
	int x, y, length, width, height, startAngle, arcAngle; 
	
	public DrawNode( )
	{
		Node[] nodes = new Node[1];
		nods = new ArrayList<>();
		for ( Node nd : nodes )
		{
			nods.add( nd );
		}
	}
	
	public DrawNode( Node[] nodes )
	{
		nods = new ArrayList<>();
		for ( Node nd : nodes )
		{
			nods.add( nd );
		}
	}
	
	
	@Override
    protected void paintComponent( Graphics g )
	{
        super.paintComponent(g);
        for ( Node nod : nods ) 
        {
            nod.drawNode( g, nod.xCoor, nod.yCoor, nod.sRange, nod.tRange, nod.startAngle, nod.fovAngle );
        }
    }
	
//	public static void main( String[] args )
//	{
//		Simul sim = new Simul();
//		DrawNode sector = new DrawNode( sim.nodes );
//		JFrame frame = new JFrame();
//		frame.setSize( 500, 500 );
//		frame.setVisible( true );
//		frame.add( sector );
//	}	
}
