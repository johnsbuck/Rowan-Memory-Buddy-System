package binarybuddysystem.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * Basic JPanel view for displaying process info
 * @author Aaron N. Rudolph
 *
 */
public class ProcessView extends JPanel
{
	ProcessLabel[] blocks;
	
	/**
	 * Creates a new ProcessView
	 * @param size maximum number of minimum-sized blocks
	 */
	public ProcessView(int size)
	{
		blocks = new ProcessLabel[size];
		
		int h = new ProcessLabel("p1", Color.black).getPreferredSize().height;
		int pad = 5;
		
		setPreferredSize(new Dimension(100, size*(h + pad)));
		
		setLayout(new GridLayout(size, 0, 0, pad));
		//setBorder(BorderFactory.createEmptyBorder());
		
		revalidate();
		repaint();
	}
	
	/**
	 * Adds a new process using info contained in Block
	 * @param blk Block whose data is to be used
	 * @param blkIdx Index, as given by MMU
	 */
	public void addProcess(Block blk, int blkIdx)
	{
		ProcessLabel tmp = new ProcessLabel(blk.procName, new Color(blk.color));
		
		add(tmp);
		blocks[blkIdx] = tmp;
		
		revalidate();
		repaint();
	}
	
	/**
	 * Removes a process from the View
	 * @param blkIdx Index of the process to be removed
	 */
	public void removeProcess(int blkIdx)
	{
		remove(blocks[blkIdx]);
		
		revalidate();
		repaint();
	}
}
