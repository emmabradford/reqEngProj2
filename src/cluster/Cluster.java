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
	double repData = 0.0;
	
	public Cluster(/*HashMap<Integer, Double> m*/)
	{
		//info = m;
	}
	
	public double getRepData() 
	{
		return info.get(rep);
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
		Object[] k = info.keySet().toArray();
		Integer m = (Integer)(k[0]);
		double max = info.get(m);
		for (Map.Entry<Integer, Double> set : info.entrySet()) 
		{
			if(set.getValue() > max) 
			{
				max = set.getValue();
				m = set.getKey();
			}
		}
		rep = m;
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
	
	public String toString() 
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
