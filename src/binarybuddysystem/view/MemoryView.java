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
	int procCnt = 0;
	
	Block[] blocks;
	
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
		
		g.setColor(new Color(colors[0]));
		g.setXORMode(getBackground());
		
		for(int i = 0; i < blocks.length; i ++)
		{
			float blockSize = ((float)getWidth() / blocks.length);
			Block tmp = blocks[i];
			
			int incr = 1;
			
			if(tmp == null)
			{
				g.setColor(getBackground());
//				System.out.println("Current IDX: " + String.valueOf(i));
			}
			else
			{
				g.setColor(new Color(tmp.color));
				
				incr = tmp.chunkSize;
//				System.out.println("Current IDX: " + String.valueOf(i) + ", BlkSz: " + String.valueOf(tmp.chunkSize));
			}
			g.fillRect(i * (int)blockSize, 0, (int) blockSize * incr, getHeight());
			
			i += incr - 1;
		}
	}
	
	public void addProcess(Block b, int blkIdx)
	{
		blocks[blkIdx] = b;
		repaint();
	}
	
	public void removeProcess(int blkIdx)
	{
		blocks[blkIdx] = null;
		
		repaint();
	}
}
