package binarybuddysystem.view;

import java.awt.Color;
import java.awt.Component;

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
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		blocks = new ProcessLabel[size];
		
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
