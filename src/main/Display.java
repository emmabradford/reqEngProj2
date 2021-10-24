package main;
import readFile.ReadFile;
import java.io.*;
import java.util.*;
import cluster.Cluster;

public class Display
{
	public static String[][] matrix;
//	private static ReadFile f;
	//private static ArrayList<ArrayList<String>> FRS;
	//private static ArrayList<ArrayList<String>> NFRS;
	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		ReadFile f = new ReadFile("test.txt");
		
		//f = new ReadFile("run 1");
		//f = new ReadFile("run 2");
		//f = new ReadFile("run 3");
		//f.printNRFID();
		//f.printFRID();
		//f.printNFRInfo();
		//f.printFRInfo();
		//FRS = f.getFRsInfo();
		//NFRS = f.getNFRsInfo();
		//System.out.println(NFRS.size());
		int s = f.getFRsInfo().size();
		int ns = f.getNFRsInfo().size();
		int rows = s;
		int cols = ns+1;
		matrix = new String[rows][cols];
		for(int r = 0; r< rows; r++) 
		{
			System.out.println(f.getFRsID());
			matrix[r][0] = f.getFRsID().get(r);
		}
		ArrayList<ArrayList<String>> nfrsInfo = f.getNFRsInfo();
		ArrayList<ArrayList<String>> frsInfo = f.getFRsInfo();
		for(int i = 0; i< ns; i++) 
		{
			//System.out.println(FRS.size());
			//System.out.println(f.getFRsInfo().size());
			//f.printFRInfo();
			System.out.println(ns);
			//System.out.println(frsInfo);
			//System.out.println("0");
			//clusteringForANFR(0, s);
			System.out.println(i);
			ArrayList<Cluster> cl = clusteringForANFR(i, s, ns, nfrsInfo, frsInfo);
			
			for (int x = 0; x< 8; x++) 
			{
				Object[] keys = (cl.get(x).getHashMap().keySet().toArray());
				for(int k = 0; k< keys.length; k++) 
				{
					matrix[(Integer)(keys[k])][i+1] = cl.get(x).getPass();
				}
				//create a function to calculate the 0 or 1
				//matrix[r][i+1] = 			
			}
		}		
		//sim(f, f.getFRsInfo().get(0), f.getFRsInfo().get(1));
		/*
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
		*/
		write2File(s, ns+1);
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
	
	private static double sim(ArrayList<String> a, ArrayList<String> b, ArrayList<ArrayList<String>> frsInfo, ArrayList<ArrayList<String>> nfrsInfo, int s, int ns) 
	{
		//System.out.println(s);
		double ans = 0;
		//add nfrs to docs
		ArrayList<ArrayList<String>> docs = new ArrayList<ArrayList<String>>();
		
		for (int i = 0; i< s; i++) 
		{
			docs.add(frsInfo.get(i));
		}
		
		for (int i = 0; i< ns; i++) 
		{
			docs.add(nfrsInfo.get(i));
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
	
	private static double[][] calcSim(int s, int ns, ArrayList<ArrayList<String>> frsInfo, ArrayList<ArrayList<String>> nfrsInfo)
	{
		System.out.println(s);
		double[][] m = new double[ns][s];
		for(int r = 0; r< m.length; r++) 
		{
			for(int c = 0; c < m[r].length; c++) 
			{
				m[r][c] = sim(nfrsInfo.get(r), frsInfo.get(c), frsInfo, nfrsInfo, s, ns);
			}
		}
		return m;
	}
	
	//clustering
	private static ArrayList<Cluster> clusteringForANFR(int nfr, int s, int ns, ArrayList<ArrayList<String>> nfrsInfo, ArrayList<ArrayList<String>> frsInfo)
	{
		///DONT COMPARE FR TO FR 
		//COMPARE FR TO NFR
		//CLUSTER BASED ON SIM OF FRS TO NFR1
		
		System.out.println(s);
		//System.out.println(frsInfo);
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
		m1 = calcSim(s, ns, frsInfo, nfrsInfo);
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
					cl.get(x).findRepresentative();
					break;
				}
				else 
				{
					if(num == 10) 
					{
						cl.get(x).setClusterNumber(clusters[i]);
						cl.get(x).addHashMapItem(i, data[i]);
						cl.get(x).findRepresentative();
						break;
					}
				}
			}
		}
		rankClusterse(cl);
		printClusters(cl);
		return cl;
	}
	
	private static void rankClusterse(ArrayList<Cluster> cl) 
	{
		double[] arr = new double[8];
		int n = arr.length;
		for (int x = 0; x< cl.size(); x++) 
		{
			//arr[i] = c.getRepData();
			arr[x] = cl.get(x).getRepData();
			//System.out.println(cl.get(x).getRepData());
		}

		for(int i=0; i < n; i++) 
		{
			System.out.println(arr[i]);
		}
		
		System.out.println("highest to lowest");
		//smallest to greatest
		double temp = arr[0];
		for(int i=0; i < n; i++) 
		{
			for(int j=1; j < (n-i); j++) 
			{
				if(arr[j-1] > arr[j])
				{  
					temp = arr[j-1];  
					arr[j-1] = arr[j];  
					arr[j] = temp;  
				}
			}
		}
		
		for(int i=0; i < n; i++) 
		{
			System.out.println(arr[i]);
		}
		
		for(Cluster c: cl) 
		{
			for(int i=0; i < 8; i++) 
			{
				if(c.getRepData() == arr[i]) 
				{
					c.setRank(i);
				}
			}
		}
		
		setClusterRank(cl);
	}
	
	private static void setClusterRank(ArrayList<Cluster> cl) 
	{
		for(int x = 0; x< cl.size(); x++) 
		{
			if(cl.get(x).getRank()<=2) 
			{
				cl.get(x).setPass(false);
			}
			else 
			{
				cl.get(x).setPass(true);
			}
		}
	}
	
	private static void printClusters(ArrayList<Cluster> x) 
	{
		for (Cluster m: x) 
		{
			System.out.println(m.toString());
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
	private static void write2File(int rows, int cols) throws IOException
	{
		File run = new File("reults.txt");
		FileWriter w = new FileWriter("results.txt");
		
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
			w.write(ans+"\n");
		}
		w.close();
	}

}