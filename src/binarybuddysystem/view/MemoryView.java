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
	
	/**
	 * Creates a Memory View
	 * @param numBlocks maximum number of minimum-size blocks
	 */
	public MemoryView(int numBlocks)
	{
		blocks = new Block[numBlocks];
		int colorBlockSize = 1;
		int width = numBlocks * colorBlockSize;
		if(width <= 640)
			setPreferredSize(new Dimension(640, 64));
		else
			setPreferredSize(new Dimension(width, 64));
		
		//setBorder(BorderFactory.createLoweredBevelBorder());
	}
	/**
	 * Creates a Memory View with extended parameters
	 * @param numBlocks maximum number of minimum-size blocks
	 * @param colorSize number of color table entries to use
	 */
	public MemoryView(int numBlocks, int colorSize)
	{
		blocks = new Block[numBlocks];
		int colorBlockSize = 1;
		if(colorSize > 0)
			colorBlockSize = colorSize;
		int width = numBlocks * colorBlockSize;
		if(width <= 640)
			setPreferredSize(new Dimension(640, 64));
		else
			setPreferredSize(new Dimension(width, 64));
		
		//setBorder(BorderFactory.createLoweredBevelBorder());
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		g.setColor(getBackground());
		
		for(int i = 0; i < blocks.length; i ++)
		{
			int blockSize = (getWidth() / blocks.length);
			int blockMod = getWidth() % blocks.length;
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
			g.fillRect(i * (int)blockSize, 0, (int) (blockSize * incr) + blockMod, getHeight());
			
			i += incr - 1;
		}
	}
	
	/**
	 * Adds a process to the Memory View
	 * @param b a Block object containing any relevant information
	 * @param blkIdx index (from MMU) to put this Block at.
	 */
	public void addProcess(Block b, int blkIdx)
	{
		blocks[blkIdx] = b;
		repaint();
	}
	
	/**
	 * Gets the process location
	 * @param name name of process whose location is to be retrieved
	 * @return index, or -1 on failure
	 */
	public int getProcessLoc(String name)
	{
		for(int i = 0; i < blocks.length; i++)
		{
			if(blocks[i] != null && blocks[i].getProcName().equals(name))
				return i;
		}
		return -1;
	}
	/**
	 * Removes a block from the memory view
	 * @param blkIdx index (from MMU) of the block to remove
	 */
	public void removeProcess(int blkIdx)
	{
		blocks[blkIdx] = null;
		
		repaint();
	}
}
