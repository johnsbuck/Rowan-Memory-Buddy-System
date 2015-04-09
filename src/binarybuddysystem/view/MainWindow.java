package binarybuddysystem.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame implements ActionListener
{
	static
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e)
		{
			System.err.println("Unable to set system Look & Feel");
		}
	}
	
	MemoryView mv;
	ProcessView pv;
	JPanel detailPanel = new JPanel();
	JButton addBtn = new JButton("Add Process");
	JButton remBtn = new JButton("Remove Process");
	
	public MainWindow(int numBlocks)
	{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setSize(1024, 480);
		setTitle("MMU View Window");
		
		mv = new MemoryView(numBlocks);
		pv = new ProcessView(numBlocks);
		
		add(pv, BorderLayout.CENTER);
		add(mv, BorderLayout.NORTH);
		add(detailPanel, BorderLayout.SOUTH);
		
		addBtn.setActionCommand("AddProcess");
		remBtn.setActionCommand("RemProcess");
		
		detailPanel.add(addBtn);
		detailPanel.add(remBtn);
		
		addBtn.addActionListener(this);
		remBtn.addActionListener(this);
		
		revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals("AddProcess"))
		{
			/* TEST CASES */
			
			Block b1 = new Block("Process 1", 0xFF0000, 3);
			Block b2 = new Block("Process 2", 0xFFFF00, 1);
			Block b3 = new Block("Process 3", 0x00FF00, 2);
			
			mv.addProcess(b1, 0);
			pv.addProcess(b1, 0);
			mv.addProcess(b2, 4);
			pv.addProcess(b2, 4);
			mv.addProcess(b3, 5);
			pv.addProcess(b3, 5);
			/* END TEST CAES */
		}
		if(e.getActionCommand().equals("RemProcess"))
		{
			mv.removeProcess(5);
			pv.removeProcess(5);
		}
	}
}
