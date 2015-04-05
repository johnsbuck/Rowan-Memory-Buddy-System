package binarybuddysystem.view;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Component;

public class MainWindow extends JFrame implements ComponentListener
{
	MemoryView v;
	JPanel detailPanel = new JPanel();
	
	public MainWindow()
	{
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setSize(640, 480);
		setTitle("MMU View Window");
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		v = new MemoryView();
		v.setAlignmentY(Component.TOP_ALIGNMENT);
		v.setSize(30, 30);
		
		getContentPane().add(v);
		getContentPane().add(detailPanel);
	}

	@Override
	public void componentHidden(ComponentEvent e)
	{
		
	}

	@Override
	public void componentMoved(ComponentEvent e)
	{
		
	}

	@Override
	public void componentResized(ComponentEvent e)
	{
		v.setSize(getWidth(), 35);
	}

	@Override
	public void componentShown(ComponentEvent e)
	{
		
	}
}
