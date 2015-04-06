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
	private Block[] blocks;
	
	private int[] colors = {0xFF0000, 0xFF8800, 0xFFFF00, 0x00FF00, 0x00FFFF, 0x0000FF};
	
	public MemoryView(int numBlocks)
	{
		blocks = new Block[numBlocks];
		
		setPreferredSize(new Dimension(640, 64));
		setBorder(BorderFactory.createLoweredBevelBorder());
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Block prev = blocks[0];
		for(int i = 0; i < blocks.length; i ++)
		{
			float blockSize = ((float)getWidth() / blocks.length);
			
			if(blocks[i] == null)
			{
				g.setColor(getBackground());
			}
			else if(blocks[i] != prev)
			{
				g.setColor(new Color(colors[i % colors.length]));
			}
			g.fillRect(i * (int)blockSize, 0, (int) blockSize, getHeight());
			prev = blocks[i];
		}
	}
}
