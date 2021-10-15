package main;
import readFile.ReadFile;
import java.io.*;

public class Display
{
	public static void main(String[] args) throws FileNotFoundException{
		ReadFile f = new ReadFile("test.txt");
		//f.printNRFID();
		f.printFRID();
	}
}
