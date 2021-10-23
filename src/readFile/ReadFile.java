package readFile;
import java.io.*;
import java.util.*;

public class ReadFile {
	private String path = "";
	private File file;
	int numFr = 0;
	int numNFR = 0;
	ArrayList<String> frs = new ArrayList<String>();
	ArrayList<String> nfrs = new ArrayList<String>();
	ArrayList<String> frsID = new ArrayList<String>();
	ArrayList<String> nfrsID = new ArrayList<String>();
	ArrayList<ArrayList<String>> frsInfo = new ArrayList<ArrayList<String>>();
	ArrayList<ArrayList<String>> nfrsInfo = new ArrayList<ArrayList<String>>();
	Scanner scan;
	
	public ReadFile(String p) throws FileNotFoundException
	{
		path = p;
		file = new File(path);
	    scan = new Scanner(file);
	    this.readFRsAndNFRs();
	    this.setFRIDs();
	    this.setNFRIDs();
	    this.setNFRsInfo();
	    this.setFRsInfo();
	//    this.readNFRs();
	}
	
	public void readFRsAndNFRs()
	{
		boolean isF = false;
		while(scan.hasNextLine()) 
		{
			String line = scan.nextLine();
			
			if(!line.isEmpty()) 
			{
				isF = line.substring(0, 1).equals("F");
				if(isF) 
				{
					frs.add(line);
				}
				else 
				{
					nfrs.add(line);		
				}
			}
		}
		numFr = frs.size();
		numNFR = nfrs.size();
	}
	
/*	public void readNFRs() 
	{
		int count = 0;
		
		numNFR = count;
		
	}
	*/
	
	private void setNFRIDs() 
	{
		for(int i = 0; i < nfrs.size(); i++) 
		{
			nfrsID.add(nfrs.get(i).substring(0,4));
		}
	}
	
	private void setFRIDs() 
	{
		for(int i = 0; i < frs.size(); i++) 
		{
			int x = frs.get(i).indexOf(":");
			frsID.add(frs.get(i).substring(0,x));
		}
	}
	
	public void setNFRsInfo() 
	{
		for(int i = 0; i < nfrs.size(); i++) 
		{
			String[] s  = nfrs.get(i).split(" ");
			ArrayList<String> temp = new ArrayList<String>();
			for (int x = 2; x< s.length; x++) 
			{
				//if you want to remove the,a , etc do this in if statments
				//before adding to array list
				temp.add(s[x]);
			}
			nfrsInfo.add(temp);
			//nfrsInfo.add(nfrs.get(i).substring(0,4));
		}
	}
	
	public void setFRsInfo() 
	{
		for(int i = 0; i < frs.size(); i++) 
		{
			String[] s  = frs.get(i).split(" ");
			ArrayList<String> temp = new ArrayList<String>();
			for (int x = 2; x< s.length; x++) 
			{
				//if you want to remove the,a , etc do this in if statments
				//before adding to array list
				temp.add(s[x]);
			}
			frsInfo.add(temp);
			//nfrsInfo.add(nfrs.get(i).substring(0,4));
		}
	}
	
	public ArrayList<String> getFRs()
	{
		return frs;
	}	
	
	public ArrayList<String> getNFRs() 
	{
		return nfrs;	
	}
	
	public ArrayList<String> getNFRsID() 
	{
		return nfrsID;	
	}
	
	public ArrayList<String> getFRsID()
	{
		return frsID;
	}	
	
	public ArrayList<ArrayList<String>> getNFRsInfo() 
	{
		return nfrsInfo;	
	}
	
	public ArrayList<ArrayList<String>> getFRsInfo()
	{
		return frsInfo;
	}	
	
	public void printNRFID() 
	{
		for(int i = 0; i < nfrsID.size(); i++) 
		{
			System.out.println(nfrsID.get(i));
		}
	}
	
	public void printFRID() 
	{
		for(int i = 0; i < frsID.size(); i++) 
		{
			System.out.println(frsID.get(i));
		}
	}
	
	public void printNFRInfo() 
	{
		for(int i = 0; i < nfrsInfo.size(); i++) 
		{
			for(int x = 0; x < nfrsInfo.get(i).size(); x++) 
			{
				System.out.println(nfrsInfo.get(i).get(x));
			}
			System.out.println();
		}
	}
	
	public void printFRInfo() 
	{
		for(int i = 0; i < frsInfo.size(); i++) 
		{
			for(int x = 0; x < frsInfo.get(i).size(); x++) 
			{
				System.out.print(frsInfo.get(i).get(x)+ ",");
			}
			System.out.println();
		}
	}
	
	public String toString() 
	{
		String ans = "nfrs: \n";
		for(int i = 0; i < nfrs.size(); i++) 
		{
			ans += nfrs.get(i) + "\n";
		}
		ans = "frs: \n";
		for(int i = 0; i < frs.size(); i++) 
		{
			ans += frs.get(i) + "\n";
		}
		return ans;
	}
	
}
