package binarybuddysystem.view;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import binarybuddysystem.MMU;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

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
	
	MMU memory;
	
	MemoryView mv;
	ProcessView pv;
	JPanel detailPanel = new JPanel();
	JButton addBtn = new JButton("Add Process");
	JTextField pName = new JTextField(16);
	JFormattedTextField pSize = new JFormattedTextField();
	
	int blockSize;
	
	private int[] colors = {0xFF0000, 0xFF8800, 0xFFFF00, 0x00FF00, 0x00FFFF, 0x0000FF};
	
	public MainWindow(int memSize, int blkSize)
	{
		memory = new MMU(memSize, blkSize);
		blockSize = blkSize;
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setSize(1024, 480);
		setTitle("MMU View Window");
		
		mv = new MemoryView(memSize/blkSize);
		pv = new ProcessView(memSize/blkSize);
		
		add(pv, BorderLayout.CENTER);
		add(mv, BorderLayout.NORTH);
		add(detailPanel, BorderLayout.SOUTH);
		
		DefaultFormatterFactory format = new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("########")));
		
		pSize.setFormatterFactory(format);
		pSize.setColumns(8);
		
		addBtn.setActionCommand("AddProcess");
		addBtn.addActionListener(this);
		
		detailPanel.add(pName);
		detailPanel.add(pSize);
		detailPanel.add(addBtn);
		
		revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals("AddProcess"))
		{
			int[] result = memory.allocate(pName.getText(), Integer.parseInt(pSize.getText()));
			
			if(result != null)
			{
				Block b = new Block(pName.getText(), colors[result[0]/2 % colors.length], result[1]);
				mv.addProcess(b, result[0]);
				pv.addProcess(b, result[0]);
			}
			else
			{
				System.err.println("Failed to allocate");
			}
			/* TEST CASES */
			
			/*
			
			Block b1 = new Block("Process 1", 0xFF0000, 3);
			Block b2 = new Block("Process 2", 0xFFFF00, 1);
			Block b3 = new Block("Process 3", 0x00FF00, 2);
			
			mv.addProcess(b1, 0);
			pv.addProcess(b1, 0);
			mv.addProcess(b2, 4);
			pv.addProcess(b2, 4);
			mv.addProcess(b3, 5);
			pv.addProcess(b3, 5);
			
			*/
			
			/* END TEST CAES */
		}
	}
	
	public boolean allocate(String name, int size)
	{
		int[] result = memory.allocate(name, size);
		
		if(result != null)
		{
			Block b = new Block(name, colors[result[0]/2 % colors.length], result[1]);
			mv.addProcess(b, result[0]);
			pv.addProcess(b, result[0]);
			pv.revalidate();
			pv.repaint();
		}
		else
		{
			System.err.println("Failed to allocate - Name: \"" + name + "\"\tSize: " + size);
			return false;
		}
		
		return true;
	}
	
	public boolean deallocate(String name)
	{
		if(memory.getProcess(name) != null)
		{
			int index = mv.getProcessLoc(name);
			
			if(index != -1)
			{
				memory.deallocate(name);
				mv.removeProcess(index);
				pv.removeProcess(index);
			}
			else
			{
				System.err.println("Failed to deallocate");
				return false;
			}
			
			return true;
		}
		else
		{
			System.err.println("Failed to deallocate: Invalid name");
			return false;
		}
	}
}
