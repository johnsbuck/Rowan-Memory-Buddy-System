package binarybuddysystem;

public class Process
{
	private String name;
	private int size;
	/*
	 * Should we have a buddy reference in Process
	 * or make a wrapper class (Chunk) that would manage that?
	 */
	public Object buddyReference;	//Will change depending on decision
	
	
	public Process()
	{
		name = "";
		size = 0;
	}
	
	public Process(String name, int size)
	{
		this.name = name;
		this.size = size;
	}
	
	public String toString()
	{
		String str = "";
		return str;
	}

	public boolean empty()
	{
		// TODO Auto-generated method stub
		return false;
	}

	public String getName()
	{
		// TODO Auto-generated method stub
		return name;
	}

	public boolean emptyProcess()
	{
		// TODO Auto-generated method stub
		return false;
	}

	public int size()
	{
		// TODO Auto-generated method stub
		return 0;
	}
}