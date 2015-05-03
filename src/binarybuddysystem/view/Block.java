package binarybuddysystem.view;

/**
 * Represents a Block to the GUI. Used as a placeholder for various values
 * @author Aaron N. Rudolph
 *
 */
public class Block
{
	public String procName;
	public int color;
	public int chunkSize;
	
	// Convenience Constructor
	public Block(String pName, int col, int sz)
	{
		color = col;
		chunkSize = sz;
		procName = pName;
	}
	
	public String getProcName()
	{
		return procName;
	}
	
	public int getProcSize()
	{
		return chunkSize;
	}
}
