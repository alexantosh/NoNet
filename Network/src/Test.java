import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Test
{
	public Test()
	{
		DrawNetwork netPanel = new DrawNetwork( 10, 65  );
		frame.setLayout( new BorderLayout(3,3));
		frame.add( netPanel, BorderLayout.CENTER );
		frame.add( netPanel.gui, BorderLayout.EAST );
		frame.setSize( netPanel.getfHeight(),  netPanel.getfWidth() );
		frame.setVisible( true );
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
	}
	static JFrame frame = new JFrame();
	public static void main(String[] args)
	{
		Test tst = new Test();
	}
}
