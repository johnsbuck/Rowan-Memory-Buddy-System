package binarybuddysystem.view;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class ProcessView extends JPanel
{
	ProcessLabel[] procs;
	
	public ProcessView()
	{
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		add(new ProcessLabel("PROCESS 1", Color.BLUE));
	}
	
	public void addProcess(Process p)
	{
		
	}
	
	public void removeProcess(Process p)
	{
		
	}
}
