package binarybuddysystem;

public class Process
{
	private String name;
	private int size;
	
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
		return size;
	}
}