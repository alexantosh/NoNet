import java.awt.Color;
import java.awt.Graphics;


public class Node 
{
	/**
	 * cluster size must be less than the tRange/ transmission range
	 * 
	 * */
	public int fovAngle;
	public int iRange;
	public int sRange;
	public int tRange;
	public int startAngle;
	String name;
	public int xCoor, yCoor;
	String clusterName;
	boolean isClustered = false;
	//Cluster[] clusters;
	//JPanel nodePanel = new JPanel();
	//JFrame frame = new JFrame();
	
	public Node(  int x, int y, int stAngle,  int fov, int tR, int sR, int iR, String name )
	{
		this.setxCoor( x );
		this.setyCoor( y );
		this.setStartAngle( stAngle );
		this.setFovAngle( fov );
		this.setTRange( tR );
		this.setsRange( sR );
		this.setIRange( iR );
		this.setName( name );
		
	}
	
	public void setIRange( int ir )
	{
		this.iRange = ir;
	}
	
	public int getIRange()
	{
		return this.iRange;
	}
	
	public void setTRange( int tr )
	{
		this.tRange = tr;
	}
	
	public int getTRange()
	{
		return this.tRange;
	}
	
	public double distFr(int x1, int y1 )
	{
		return Math.sqrt( ( x1-xCoor )*( x1-xCoor ) + ( y1-yCoor )*( y1-yCoor ) );
	}
	
	public int getFovAngle()
	{
		return fovAngle;
	}

	public void setFovAngle(int fovAngle)
	{
		this.fovAngle = fovAngle;
	}

	public int getsRange()
	{
		return sRange;
	}

	public void setsRange(int sRange)
	{
		this.sRange = sRange;
	}

	public int getStartAngle()
	{
		return startAngle;
	}

	public void setStartAngle(int startAngle)
	{
		this.startAngle = startAngle;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getxCoor()
	{
		return xCoor;
	}

	public void setxCoor(int xCoor)
	{
		this.xCoor = xCoor;
	}

	public int getyCoor()
	{
		return yCoor;
	}

	public void setyCoor(int yCoor)
	{
		this.yCoor = yCoor;
	}

	public String getClusterName()
	{
		return clusterName;
	}

	public void setClusterName(String clusterName)
	{
		this.clusterName = clusterName;
	}
	
	public void drawNode( Graphics g, int x, int y, int width, int height, int startAngle, int arcAngle )
	{
	//	super.paintComponent( g );		
//		g.setColor( Color.BLACK );
//		g.fillArc(15, 15, 200, 200, 45, 45);	
		g.setColor( Color.YELLOW );
		g.fillArc(getxCoor(), getyCoor(), width, height, startAngle, getFovAngle() );
			
	}	
}
