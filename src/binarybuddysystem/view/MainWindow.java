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
		pv = new ProcessView();
		
		add(pv, BorderLayout.CENTER);
		add(mv, BorderLayout.NORTH);
		add(detailPanel, BorderLayout.SOUTH);
		
		addBtn.setActionCommand("AddProcess");
		remBtn.setActionCommand("RemProcess");
		
		detailPanel.add(addBtn);
		detailPanel.add(remBtn);
		
		addBtn.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals("AddProcess"))
		{
			mv.addProcess(new Process("Test Process"), Color.blue);
		}
		else if(e.getActionCommand().equals("RemProcess"))
		{
			
		}
	}
}
