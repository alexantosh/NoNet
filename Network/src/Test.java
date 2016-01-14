import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Test
{

	public static void main(String[] args)
	{
		DrawNetwork netPanel = new DrawNetwork( 10, 65  );
		JFrame frame;
		frame = new JFrame();
		frame.setLayout( new BorderLayout(3,3));
		frame.add( netPanel, BorderLayout.CENTER );
		frame.add( netPanel.gui, BorderLayout.EAST );
		frame.setSize( netPanel.getfHeight(),  netPanel.getfWidth() );
		frame.setVisible( true );
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
	}

}
