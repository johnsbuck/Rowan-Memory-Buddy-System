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
	
	Process[] blocks;
	
	private int[] colors = {0xFF0000, 0xFF8800, 0xFFFF00, 0x00FF00, 0x00FFFF, 0x0000FF};
	
	public MemoryView(int size)
	{
		blocks = new Process[size];
		
		setPreferredSize(new Dimension(640, 64));
		setBorder(BorderFactory.createLoweredBevelBorder());
		
		/* TEST CASES */
		
		Process p1 = new Process("Process 1");
		Process p2 = new Process("Process 2");
		Process p3 = new Process("Process 3");
		
		blocks[0] = p1;
		blocks[1] = p1;
		blocks[2] = p1;

		blocks[4] = p3;
		
		blocks[5] = p2;
		blocks[6] = p2;
		blocks[7] = p3;
		
		/* END TEST CAES */
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Process prev = blocks[0];
		
		g.setColor(new Color(colors[0]));
		
		for(int i = 0; i < blocks.length; i ++)
		{
			float blockSize = ((float)getWidth() / blocks.length);
			
			if(blocks[i] == null)
			{
				g.setColor(getBackground());
			}
			else if(!blocks[i].equals(prev))
			{
				g.setColor(new Color(colors[(i) % colors.length]));
			}
			g.fillRect(i * (int)blockSize, 0, (int) blockSize, getHeight());
			prev = blocks[i];
		}
	}
	
	public int addProcess(Process p, Color c)
	{
		blocks[procCnt] = p;
		// Return process pid and increment for next created process
		
		repaint();
		
		return procCnt ++;
	}
	
	public void removeProcess(int pNum)
	{
		
	}
}
