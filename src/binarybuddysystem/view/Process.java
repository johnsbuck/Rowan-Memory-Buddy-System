package binarybuddysystem.view;

public class Process
{
	private int pid;
	private String pName;
	
	public Process(String name)
	{
		pName = name;
	}
	
	public String getName()
	{
		return pName;
	}
	
	public boolean equals(Object other)
	{
		if(other == null)
		{
			return false;
		}
		if(other instanceof Process)
		{
			if(((Process) other).pName.equals(pName))
			{
				return true;
			}
			return false;
		}
		return false;
	}
}
