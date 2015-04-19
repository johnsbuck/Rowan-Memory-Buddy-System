package binarybuddysystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFrame;

import binarybuddysystem.view.MainWindow;

public class Driver {
	// static MMU manager;
	private static BufferedReader stdin = new BufferedReader(
			new InputStreamReader(System.in));
	private static boolean run = true;
	private static MMU memory;
	private static int chunk;
	private static int memorySize;

	public static void main(String[] args) throws IOException
	{
		try
		{
			if(args[0].equals("showgui"))
			{
				MMU memory = new MMU(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
				MainWindow f = new MainWindow(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
				f.setVisible(true);
			}
		}
		catch(IndexOutOfBoundsException e)
		{
			run();
		}
		// MMU memory = new MMU(16, 2);
		// memory.allocate(new Process("a", 8));
		// memory.allocate(new Process("b", 2));
		// System.out.println(memory);
		// memory.deallocate("b");
		// System.out.println(memory);
	}

	/*
	 * Exit function will be part of printMenu as a switch option.
	 * 
	 * If wanted, make Exit function to clear MMU.
	 */
	public static void run() throws IOException {
		while (run == true) {
			while (run == true) {
				System.out
						.println("Please enter the number for one of the following options: ");
				System.out.println("1.) Initialize.");
				System.out.println("2.) Add process.");
				System.out.println("3.) Remove process.");
				System.out.println("4.) Print memory.");
				System.out.println("5.) Exit.");
				String userInput = stdin.readLine();
				int choice = Integer.parseInt(userInput);
				switch (choice) {
				case 1:
					initalize();
					break;
				case 2:
					addProcess();
					break;
				case 3:
					removeProcess();
					break;
				case 4:
					printMemory();
					break;
				case 5:
					System.out.println("Thank you and good bye.");
					run = false;
					break;
				default:
					System.out.println("Invalid choice");
				}
			}
		}
	}

	public static void initalize() throws IOException {
		System.out.println("Specify a size of memory. Only use powers of 2: ");
		String userInputSize = stdin.readLine();
		memorySize = Integer.parseInt(userInputSize);
		System.out
				.println("Specify the minimum chunk size of memory. Only use powers of 2: ");
		String userInputChunk = stdin.readLine();
		chunk = Integer.parseInt(userInputChunk);
		memory = new MMU(memorySize, chunk);
		System.out.println("Memory has been created with size " + memorySize
				+ " and chunk size of " + chunk);
	}

	static public void addProcess() throws IOException {
		System.out.println("Specify a name for the process: ");
		String userInputName = stdin.readLine();
		System.out.println("Specify a size for the process: ");
		String userInputSize = stdin.readLine();
		int size = Integer.parseInt(userInputSize);
		// Code to check that process is size between 1 and memory Size
		boolean sizeCheck = false;
		while (sizeCheck == false) {
			if (size < memorySize && size >= chunk) {
				sizeCheck = true;
			} else {
				System.out.println("Invalid choice. Choose between " + chunk
						+ " and " + memorySize + ".");
				userInputSize = stdin.readLine();
				size = Integer.parseInt(userInputSize);
			}
		}
		if (memory.allocate(userInputName, size)[0] == -1) {
			System.out
					.println("The process could not be allocated into memory.");
		} else {
			System.out.println("The Process '" + userInputName
					+ "' was allocated into memory.");
		}

	}

	static public void removeProcess() throws IOException {
		System.out.println("Specify the process you wish to remove: ");
		String userInputName = stdin.readLine();
		boolean nameCheck = false;
		while(nameCheck == false){
			if(memory.deallocate(userInputName) == false){
				System.out.println("The process '" + userInputName + "' was not found.");
				System.out.println("Specify the process you wish to remove or type quit: ");
				userInputName = stdin.readLine();
				if (userInputName.equals("quit")){
					break;
				}
			}else{
				System.out.println("The process '" + userInputName + "' was removed from memory.");
				break;
			}
		}
	}

	static public void printMemory() {
		System.out.println(memory.toString());
	}
}