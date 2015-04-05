package binarybuddysystem.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * Displays color-coded memory mapping of processes
 * @author Aaron N. Rudolph
 *
 */
public class MemoryView extends JPanel
{
	public MemoryView()
	{
		
	}
	
	public void paint(Graphics g)
	{		
		g.setColor(Color.RED);
		g.fillRect(0, 0, getWidth(), getHeight());;
	}
}
