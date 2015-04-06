package binarybuddysystem.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * Displays color-coded memory mapping of processes
 * @author Aaron N. Rudolph
 *
 */
public class MemoryView extends JPanel
{
	private int blocks = 0;
	private String[] processes;
	
	public MemoryView(int blocks)
	{
		this.setBlocks(blocks);
		
		setPreferredSize(new Dimension(640, 64));
		setBorder(BorderFactory.createLoweredBevelBorder());
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	public int getBlocks()
	{
		return blocks;
	}

	public void setBlocks(int blocks)
	{
		this.blocks = blocks;
	}
}
