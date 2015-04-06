package binarybuddysystem.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.BorderLayout;

public class MainWindow extends JFrame
{
	static
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e)
		{
			System.err.println("Unable to set system Look & Feel");
		}
	}
	
	MemoryView v;
	JPanel detailPanel = new JPanel();
	JButton addBtn = new JButton("Add Process");
	JButton remBtn = new JButton("Remove Process");
	
	public MainWindow(int numBlocks)
	{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setSize(640, 480);
		setTitle("MMU View Window");
		
		v = new MemoryView(numBlocks);
		
		add(v, BorderLayout.NORTH);
		add(detailPanel, BorderLayout.CENTER);
		
		detailPanel.add(addBtn);
		detailPanel.add(remBtn);
		
		pack();
	}
}
