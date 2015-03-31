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
		return "Process name: \"" + name + "\" | Size: " + size + " byte(s).";
	}

	public String getName()
	{
		return name;
	}

	public int size()
	{
		return size;
	}
}