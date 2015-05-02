package binarybuddysystem.view;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import binarybuddysystem.MMU;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

/**
 * This class is used as a viewer of the MMU in the binarybuddysystem package.
 * 
 * @author Aaron Rudolph, John Bucknam
 *
 */
public class MainWindow extends JFrame implements ActionListener
{
	//Look and feel of OS
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
	
	//Memory Management Unit used for storage
	private MMU memory;
	
	//Custom views
	private JScrollPane mvScroll;
	private MemoryView mv;
	private JScrollPane pvScroll;
	private ProcessView pv;
	
	//Used to add buttons and textfields for manual mode
	private JPanel detailPanel;
	
	//Buttons to be added in manual mode
	private JButton addBtn = new JButton("Add Process");
	private JButton rmBtn = new JButton("Remove Process");
	
	//Labels and textfields for manual mode
	private JLabel pNameLabel = new JLabel("Process Name: ");
	private JTextField pName = new JTextField(16);
	private JLabel pSizeLabel = new JLabel("Process Size: ");
	private JFormattedTextField pSize = new JFormattedTextField();
	
	//Chunk size
	private int blockSize;
	
	//Possible colors in viewer
	private int[] colors = {0xFF0000, 0xFF8800, 0xFFFF00, 0x00FF00, 0x00FFFF, 0x0000FF};
	
	/**
	 * This constructor checks whether it is auto mode (AllocatorDeallocator controls)
	 * and sets the memory size and chunk size of MMU being viewed.
	 * @param auto
	 * @param memSize
	 * @param blkSize
	 */
	public MainWindow(boolean auto, int memSize, int blkSize)
	{
		//Set MMU
		memory = new MMU(memSize, blkSize);
		blockSize = blkSize;
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setTitle("MMU View Window || Memory Size: " + memSize + " | Min Chunk Size: " + blkSize);
		
		//Memory and process viewer are set
		mv = new MemoryView(memSize/blkSize);
		pv = new ProcessView(memSize/blkSize);
		
		//Default detailPanel is set
		detailPanel = new JPanel();
		detailPanel.setPreferredSize(new Dimension(0, 0));
		
		//Create scroll panes in preference to viewers
		mvScroll = new JScrollPane(mv, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pvScroll = new JScrollPane(pv, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mvScroll.setPreferredSize(mv.getPreferredSize());
		pvScroll.setPreferredSize(pv.getPreferredSize());
		add(pvScroll, BorderLayout.CENTER);
		add(mvScroll, BorderLayout.NORTH);
		
		//Number formatting
		DefaultFormatterFactory format = new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("########")));
		
		pSize.setFormatterFactory(format);
		pSize.setColumns(8);
		
		//If in manual mode
		if(!auto)
		{
			//Set new detailPanel size
			detailPanel.setPreferredSize(new Dimension(600, 80));

			//Add panels for buttons and textfields
			JPanel buttonPanel = new JPanel();
			JPanel textFieldPanel = new JPanel();
			
			detailPanel.setLayout(new GridLayout(2,1));
			
			//Set actionListener commands
			addBtn.setActionCommand("AddProcess");
			addBtn.addActionListener(this);
			
			rmBtn.setActionCommand("RemoveProcess");
			rmBtn.addActionListener(this);
			
			//Add all labels and textfields
			textFieldPanel.add(pNameLabel);
			textFieldPanel.add(pName);
			textFieldPanel.add(pSizeLabel);
			textFieldPanel.add(pSize);
			textFieldPanel.setPreferredSize(new Dimension(600, 20));
			
			//Add buttons
			buttonPanel.add(addBtn);
			buttonPanel.add(rmBtn);
			buttonPanel.setPreferredSize(new Dimension(600, 20));
			
			//Add panels in correlating order
			detailPanel.add(textFieldPanel);
			detailPanel.add(buttonPanel);
			add(detailPanel, BorderLayout.SOUTH);
		}
		
		//Set sizes
		setPreferredSize(new Dimension(660, mv.getPreferredSize().height +
										detailPanel.getPreferredSize().height/2 + 115));
		setMinimumSize(new Dimension(580, mv.getPreferredSize().height +
										detailPanel.getPreferredSize().height/2 + 90));
		
		//Have it go on its way
		pack();
		revalidate();
		setVisible(true);
	}
	
	/**
	 * This constructor checks whether it is auto mode (AllocatorDeallocator controls)
	 * and sets the memory size and chunk size of MMU being viewed. It also has a custom
	 * colorSize for the Memory View in case the view sizes are too small
	 * @param auto
	 * @param memSize
	 * @param blkSize
	 * @param colorSize
	 */
	public MainWindow(boolean auto, int memSize, int blkSize, int colorSize)
	{
		//Set MMU
		memory = new MMU(memSize, blkSize);
		blockSize = blkSize;
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setTitle("MMU View Window || Memory Size: " + memSize + " | Min Chunk Size: " + blkSize);
		
		//Memory and process viewer are set
		mv = new MemoryView(memSize/blkSize, colorSize);
		pv = new ProcessView(memSize/blkSize);
		
		//Set default detailPanel
		detailPanel = new JPanel();
		detailPanel.setPreferredSize(new Dimension(0, 0));
		
		//Create and add scroll panes in preference to viewers
		mvScroll = new JScrollPane(mv, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pvScroll = new JScrollPane(pv, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mvScroll.setPreferredSize(mv.getPreferredSize());
		pvScroll.setPreferredSize(pv.getPreferredSize());
		add(pvScroll, BorderLayout.CENTER);
		add(mvScroll, BorderLayout.NORTH);
		
		//Number formatting
		DefaultFormatterFactory format = new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("########")));
		
		pSize.setFormatterFactory(format);
		pSize.setColumns(8);
		
		//If in manual mode
		if(!auto)
		{
			//Set actual detailPanel size
			detailPanel.setPreferredSize(new Dimension(600, 80));
			
			//Add panels for textfields and buttons
			JPanel buttonPanel = new JPanel();
			JPanel textFieldPanel = new JPanel();
			
			//Set layout
			detailPanel.setLayout(new GridLayout(2,1));
			
			//Set actionListener commands
			addBtn.setActionCommand("AddProcess");
			addBtn.addActionListener(this);
			
			rmBtn.setActionCommand("RemoveProcess");
			rmBtn.addActionListener(this);
			
			//Add textfields and labels
			textFieldPanel.add(pNameLabel);
			textFieldPanel.add(pName);
			textFieldPanel.add(pSizeLabel);
			textFieldPanel.add(pSize);
			textFieldPanel.setPreferredSize(new Dimension(600, 20));
			
			//Add buttons
			buttonPanel.add(addBtn);
			buttonPanel.add(rmBtn);
			buttonPanel.setPreferredSize(new Dimension(600, 20));
			
			//Add panels in correlating order
			detailPanel.add(textFieldPanel);
			detailPanel.add(buttonPanel);
			add(detailPanel, BorderLayout.SOUTH);
		}
		
		//Set sizes
		setPreferredSize(new Dimension(660, mv.getPreferredSize().height +
				detailPanel.getPreferredSize().height/2 + 115));
		setMinimumSize(new Dimension(580, mv.getPreferredSize().height +
				detailPanel.getPreferredSize().height/2 + 90));
		
		//Have it go on its way
		pack();
		revalidate();
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals("AddProcess"))
		{
			try
			{
				if(!allocate(pName.getText(), Integer.parseInt(pSize.getText())))
				{
					//Replace with Dialog
					System.err.println("Unable to allocate");
				}
			}
			catch(NumberFormatException e1)
			{
				//Replace with Dialog
				System.err.println("Size must be a number");
			}
		}
		else if(e.getActionCommand().equals("RemoveProcess"))
		{
			if(!deallocate(pName.getText()))
			{
				//Replace with Dialog
				System.err.println("Unable to deallocate");
			}
		}
	}
	
	/**
	 * This method attempts to allocate a process into the MMU and then
	 * adds the process to the memory viewer and process viewer.
	 * 
	 * Will fail if the process name is already used or if the memory size is too large
	 * to fit any chunks.
	 * @param name
	 * @param size
	 * @return true is successful, false if there is no chunk that can fit
	 */
	public boolean allocate(String name, int size)
	{
		int[] result = memory.allocate(name, size);
		
		if(result != null)
		{
			Block b = new Block(name, colors[result[0] % colors.length], result[1]);
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
	
	/**
	 * This method attempts to deallocate any process currently in the MMU and
	 * removes the process in the memory viewer and process viewer if successful.
	 * 
	 * Will fail if the process name does not exist.
	 * @param name
	 * @return true if successful, false if there is no process by that name
	 */
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
