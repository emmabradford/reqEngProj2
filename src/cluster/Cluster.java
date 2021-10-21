package cluster;
import java.util.*;

public class Cluster 
{
	private int rep = 0;
	private ArrayList<Integer> frs = new ArrayList<Integer>();
	private String pass = "";
	int rank = 0;
	
	public Cluster() 
	{
		
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
}
