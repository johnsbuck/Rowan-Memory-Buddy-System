package binarybuddysystem.test;

import static org.junit.Assert.*;
import binarybuddysystem.MMU;
import binarybuddysystem.Process;

import org.junit.Before;
import org.junit.Test;

public class MMUTest
{
	MMU testMMU;
	Process p1;
	Process p2;
	Process p3;
	Process p4;
	
	@Before
	public void setUp()
	{
		testMMU = new MMU(1024, 64);
		p1 = new Process("Process 1", 2);
		p2 = new Process("Process 2", 63);
		p3 = new Process("Process 3", 129);
	}
	
	@Test
	public void test()
	{
		fail("Not yet implemented");
	}

}
