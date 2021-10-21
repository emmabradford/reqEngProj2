package main;
import readFile.ReadFile;
import java.io.*;
import java.util.*;
import cluster.Cluster;

public class Display
{
	public static String[][] matrix;
	private static ReadFile f;
	private static ArrayList<ArrayList<String>> FRS;
	public static void main(String[] args) throws FileNotFoundException{
		f = new ReadFile("test.txt");
		//f.printNRFID();
		//f.printFRID();
		//f.printFRInfo();
		FRS = f.getFRsInfo();
		clustering(FRS);
		Cluster c1;
		Cluster c2;
		Cluster c3;
		Cluster c4;
		Cluster c5;
		Cluster c6;
		Cluster c7;
		Cluster c8;
		
		//sim(f, f.getFRsInfo().get(0), f.getFRsInfo().get(1));
		
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
		//write2File(rows, cols);
	}
	
	//finish this method
	private static String calculate01(ArrayList<String> f, ArrayList<String> n) 
	{
		return "0";
	}
	
	//source for next 3 methods: https://guendouz.wordpress.com/2015/02/17/implementation-of-tf-idf-in-java/
	private static double tf(ArrayList<String> x, String term) 
	{
		double ans = 0;
		for(String word: x) 
		{
			if(term.equalsIgnoreCase(word)) 
			{
				ans++;
			}
		}
		return ans / x.size();
	}
	
	private static double idf(ArrayList<ArrayList<String>> docs, String term) 
	{
		double ans  = 0;
		for(ArrayList<String> doc: docs) 
		{
			for(String w: doc) 
			{
				if(term.equalsIgnoreCase(w)) 
				{
					ans++;
					break;
				}
			}
		}
		return Math.log(docs.size() / ans);
	}
	
	private static double tfidf(ArrayList<String> x, ArrayList<ArrayList<String>> docs, String term) 
	{
		return tf(x, term) * idf(docs, term);
	}
	
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//ASK PROF IF MY DOCS SHOULD INCLUDE FRS AND NFRS!!!!!!!!!!
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	private static double sim(ArrayList<String> a, ArrayList<String> b) 
	{
		double ans = 0;
		ArrayList<ArrayList<String>> docs = f.getFRsInfo();
		
		ArrayList<String> words = new ArrayList<String>();
		
		for(String w: a) 
		{
			if(!words.contains(w)) 
			{
				words.add(w);
			}
		}
		
		for(String w: b) 
		{
			if(!words.contains(w)) 
			{
				words.add(w);
			}
		}
		
		for(String w: words) 
		{
			ans += tfidf(a, docs, w) * tfidf(b, docs, w);
		}
		
		return ans;
	}
	
	private static double[][] calcSimFRS(ArrayList<ArrayList<String>> frs)
	{
		double[][] m = new double[frs.size()][frs.size()];
		for(int r = 0; r< m.length; r++) 
		{
			for(int c = 0; c < m[r].length; c++) 
			{
				if(r>=c) 
				{
					m[r][c] = 1.0;
				}
				else 
				{
					m[r][c] = sim(frs.get(r), frs.get(c));
				}
			}
		}
		return m;
	}
	
	
	
	
	//clustering
	private static ArrayList<Cluster> clustering(ArrayList<ArrayList<String>> frs)
	{
		int clusterCount = frs.size();
		ArrayList<Cluster> c = new ArrayList<Cluster>();
		double[][] m = new double[clusterCount][clusterCount]; 
		m = calcSimFRS(frs);
		printM(m);
		while (clusterCount> 8) 
		{
			
			//cacluate distances of clusters
			//merge closest clusters
		}
		
		return null;
	}
	
	
	private static void printM(double[][] m) 
	{
		for(int r = 0; r< m.length; r++) 
		{
			String ans = "";
			for(int c = 0; c < m[r].length; c++) 
			{
				ans += m[r][c] + " ";
			}
			System.out.println(ans);
		}
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
