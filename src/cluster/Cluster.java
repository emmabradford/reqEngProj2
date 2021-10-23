package cluster;
import java.util.*;

public class Cluster 
{
	private int rep = 0;
	private ArrayList<Integer> frs = new ArrayList<Integer>();
	private HashMap<Integer, Double> info = new HashMap();
	private String pass = "";
	int rank = 0;
	int clusterNumber = 10;
	
	public Cluster(/*HashMap<Integer, Double> m*/)
	{
		//info = m;
	}
	
	public HashMap<Integer, Double> getHashMap()
	{
		return info;
	}
	
	public void setHashMapp(HashMap<Integer, Double> m) 
	{
		info = m;
	}
	
	public void addHashMapItem(int i, double d) 
	{
		info.put(i,d);
	}
	
	public void setClusterNumber(int i) 
	{
		clusterNumber = i;
	}
	
	public int getClusterNumber() 
	{
		return clusterNumber;
	}
	
	public ArrayList<Integer> getFRs() 
	{
		return frs;
	}
	
	public void addFR(int fr) 
	{
		frs.add(fr);
	}
	
	public int getRepresentative() 
	{
		return rep;
	}
	
	public void findRepresentative() 
	{
		
	}
	
	public int getRank() 
	{
		return rank;	
	}
	
	public void setRank(int r) 
	{
		rank = r;
	}
	
	public void setPass(boolean t) 
	{
		if(t) 
		{
			pass = "1";
		}
		else 
		{
			pass = "0";
		}
	}
	
	public String getPass() 
	{
		return pass;
	}
	
	public boolean inCluster(int fr) 
	{
		return frs.contains(fr);
	}
	
	public void merge(Cluster other) 
	{
		ArrayList<Integer> a = other.getFRs();
		for (int x : a) {
			this.addFR(x);
		}
	}
	
	public String print() 
	{
		String ans = "";
		ans += "cluster number: " + clusterNumber + "\n";
		ans += "pass: " + pass + "\n";
		ans += "rank: " +rank+ "\n";
		ans += "rep id: " + rep + " rep data: " + info.get(rep) + "\n";
		ans += info;
		return ans;
	}
}
