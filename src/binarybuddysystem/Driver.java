package binarybuddysystem;

public class Driver
{
	static MMU manager;
	
	public static void main(String[] args)
	{
		MMU memory = new MMU(16, 2);
		//memory.allocate(new Process("a", 4);
		System.out.println(memory);
	}
	
	/*
	 * Exit function will be part of printMenu
	 * as a switch option.
	 * 
	 * If wanted, make Exit function to clear
	 * MMU.
	 */
	
	static public void printMenu()
	{
		
	}
	
	static public boolean removeProcess()
	{
		return false;
	}
	
	static public boolean addProcess()
	{
		
		//Code to check that process is size between 1 and memory Size
		
		return false;
	}
	
	static public void showMemory()
	{
		
	}
}