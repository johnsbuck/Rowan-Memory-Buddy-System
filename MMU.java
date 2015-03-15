package binarybuddysystem;

public class MMU
{
	Process[] memory; 	//Might make wrapper class (Chunk)
						//Should it be a LinkedList instead?
	
	public MMU()
	{
		
	}
	
	public boolean allocate(Process p)
	{
		return false;
	}
	
	public boolean deallocate(String name)
	{
		return false;
	}
	
	private boolean partition(int index)	//index or hole
	{
		return false;
	}
	
	private boolean merge(int index)		//index or hole
	{
		return false;
	}
	
	public String toString()
	{
		String str = "";
		
		return str;
	}
}