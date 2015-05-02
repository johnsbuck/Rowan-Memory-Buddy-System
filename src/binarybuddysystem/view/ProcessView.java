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

public class ProcessView extends JPanel
{
	ProcessLabel[] blocks;
	
	public ProcessView(int size)
	{
		blocks = new ProcessLabel[size];
		
		setPreferredSize(new Dimension(0, size*13));
		
		setLayout(new GridLayout(size, 0));
		//setBorder(BorderFactory.createEmptyBorder());
		
		revalidate();
		repaint();
	}
	
	public void addProcess(Block blk, int blkIdx)
	{
		ProcessLabel tmp = new ProcessLabel(blk.procName, new Color(blk.color));
		
		add(tmp);
		blocks[blkIdx] = tmp;
		
		revalidate();
		repaint();
	}
	
	public void removeProcess(int blkIdx)
	{
		remove(blocks[blkIdx]);
		
		revalidate();
		repaint();
	}
}
