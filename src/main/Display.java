package main;
import readFile.ReadFile;
import java.io.*;
import java.util.*;

public class Display
{
	public static String[][] matrix;
	public static void main(String[] args) throws FileNotFoundException{
		ReadFile f = new ReadFile("test.txt");
		//f.printNRFID();
		//f.printFRID();
		//f.printFRInfo();
		int cols = f.getNFRs().size()+1;
		int rows = f.getFRs().size();
		matrix = new String[rows][cols];
		
		for (int r = 0; r< rows; r++) 
		{
			for(int c = 0; c < cols; c++) 
			{
				if(c == 0) {
					matrix[r][c] = f.getFRsID().get(r);
				}
				else 
				{
					//create a function to calculate the 0 or 1
					matrix[r][c] = calculate01(f.getFRsInfo().get(r), f.getFRsInfo().get(c));
				}
			}
		}
		write2File(rows, cols);
	}
	
	//finish this method
	private static String calculate01(ArrayList<String> f, ArrayList<String> n) 
	{
		return "0";
	}
	
	//change this to write to a file
	private static void write2File(int rows, int cols) 
	{
		for (int r = 0; r< rows; r++) 
		{
			String ans = "";
			for(int c = 0; c < cols; c++) 
			{
				if( c == cols-1) 
				{
					ans+= matrix[r][c];
				}
				else 
				{
					ans += matrix[r][c] +",";
				}
			}
			System.out.println(ans);
		}
	}
}
