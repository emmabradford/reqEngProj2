package main;
import readFile.ReadFile;
import java.io.*;
import java.util.*;
import cluster.Cluster;

public class Display
{
	public static String[][] matrix;
//	private static ReadFile f;
	private static ArrayList<ArrayList<String>> FRS;
	private static ArrayList<ArrayList<String>> NFRS;
	public static void main(String[] args) throws FileNotFoundException{
		ReadFile f = new ReadFile("test.txt");
		//f = new ReadFile("run 1");
		//f = new ReadFile("run 2");
		//f = new ReadFile("run 3");
		//f.printNRFID();
		//f.printFRID();
		//f.printNFRInfo();
		FRS = f.getFRsInfo();
		NFRS = f.getNFRsInfo();
		//System.out.println(NFRS.size());
		int s = f.getFRsInfo().size();
		int ns = f.getNFRsInfo().size();
		for(int i = 0; i< ns; i++) 
		{
			System.out.println(FRS.size());
			System.out.println(f.getFRsInfo().size());
			
			//System.out.println("0");
			//clusteringForANFR(0, s);
			System.out.println(i);
			clusteringForANFR(i, s, ns, f);
		}		
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
		double ans = 0;
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
	
	private static double sim(ArrayList<String> a, ArrayList<String> b, ReadFile f) 
	{
		double ans = 0;
		//add nfrs to docs
		ArrayList<ArrayList<String>> docs = f.getFRsInfo();
		for (ArrayList<String> q: f.getNFRsInfo()) 
		{
			docs.add(q);
		}
		
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
	
	private static double[][] calcSim(int s, int ns, ReadFile f)
	{
		double[][] m = new double[ns][s];
		for(int r = 0; r< m.length; r++) 
		{
			for(int c = 0; c < m[r].length; c++) 
			{
				m[r][c] = sim(f.getNFRsInfo().get(r), f.getFRsInfo().get(c), f);
			}
		}
		return m;
	}
	
	//clustering
	private static ArrayList<Cluster> clusteringForANFR(int nfr, int s, int ns, ReadFile f)
	{
		///DONT COMPARE FR TO FR 
		//COMPARE FR TO NFR
		//CLUSTER BASED ON SIM OF FRS TO NFR1
		
		System.out.println(nfr);
		
		int clusterCount = s;
		//System.out.println(clusterCount);
		//System.out.println(FRS);
		double minSim=1.0;
		int minR = 0;
		int minC = 0;
		//ArrayList<Cluster> cl = new ArrayList<Cluster>();
		//CHANGE TO 1 D MATRIX OR 2D OF SIZE CLUSTER COUNT x 3 OR 4
		double[][] m1 = new double[s][ns];
		int[] clusterNum = new int[s];
		m1 = calcSim(s, ns, f);
		//System.out.println(FRS);
		//printM(m1);
		double[] m = m1[nfr];
		for (int x = 0; x < clusterNum.length; x++) 
		{
			clusterNum[x] = x;
		}
		int iter = 0;
		//while(iter < 17) 
		while(clusterCount > 8)
		{
			minSim = 1.0;
			
			//System.out.println("iter " + iter);

			for(int r = 0; r< m.length; r++) 
			{
				for(int c = r+1; c < m.length; c++) 
				{
					//if c and r are in diffent clusters
					if(clusterNum[r] != clusterNum[c]) 
					{
						
						//if Math.abs(m[r] -m[c]) < minSim
						if(Math.abs(m[r] - m[c]) < minSim)
						{
							//System.out.println("minSim: "+ minSim);
							minSim = Math.abs(m[r] - m[c]);
							minR = r;
							minC = c;
							//System.out.println("new min SIm: " + minSim);
							//System.out.println("min r: " + minR);
							//System.out.println("min c: " + minC);
						}
					}
				}	
			}
			
			int temp = clusterNum[minC];
			//merge cluster r with cluster c
			for (int x = 0; x < clusterNum.length; x++) 
			{
				//System.out.println("c[" + x + "]: " + clusterNum[x] + "==" + "c[c]: " + temp);
				if(clusterNum[x] == temp) 
				{
					clusterNum[x] = clusterNum[minR];
				}
			}
			
			clusterNum[minC] = clusterNum[minR];
			//printClusterNum(clusterNum);
			clusterCount--;
			//System.out.println("cluster count " + clusterCount);
			//iter++;
			minSim = 1.0;
			minR = 0;
			minC = 0;
			//break;
		}
		//printClusterNum(clusterNum);
		return createClusters(m, clusterNum);
	}
	
	private static ArrayList<Cluster> createClusters(double[] data, int[]clusters) 
	{
		ArrayList<Cluster> cl = new ArrayList<Cluster>();
		cl.add(new Cluster());
		cl.add(new Cluster());
		cl.add(new Cluster());
		cl.add(new Cluster());
		cl.add(new Cluster());
		cl.add(new Cluster());
		cl.add(new Cluster());
		cl.add(new Cluster());
		
		for(int i = 0; i < clusters.length; i++) 
		{
			for(int x = 0; x< cl.size(); x++) 
			{
				int num = cl.get(x).getClusterNumber();
				if(num == clusters[i]) 
				{
					//cl.get(x).setClusterNumber(clusters[i]);
					cl.get(x).addHashMapItem(i, data[i]);
					break;
				}
				else 
				{
					if(num == 10) 
					{
						cl.get(x).setClusterNumber(clusters[i]);
						cl.get(x).addHashMapItem(i, data[i]);
						break;
					}
				}
			}
		}
		printClusters(cl);
		return cl;
	}
	
	private static void printClusters(ArrayList<Cluster> x) 
	{
		for (Cluster m: x) 
		{
			System.out.println(m.print());
		}
	}
	
	private static void printClusterNum(int[] x) 
	{
		for(int c = 0; c < x.length; c++) 
		{
			System.out.println(c + ": "+ x[c]);
		}
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
