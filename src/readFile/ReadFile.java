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
	ArrayList<String> frsInfo = new ArrayList<String>();
	ArrayList<String> nfrsInfo = new ArrayList<String>();
	Scanner scan;
	
	public ReadFile(String p) throws FileNotFoundException
	{
		path = p;
		file = new File(path);
	    scan = new Scanner(file);
	    this.readFRsAndNFRs();
	    this.setFRIDs();
	    this.setNFRIDs();
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
	
	private void setNFRsInfo() 
	{
		for(int i = 0; i < nfrs.size(); i++) 
		{
			
			nfrsInfo.add(nfrs.get(i).substring(0,4));
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
	
	public String toString() 
	{
		String ans = "nfrs: \n";
		for(int i = 0; i < nfrs.size(); i++) 
		{
			ans += nfrs.get(i) + "\n";
		}
		ans = "frs: /n";
		for(int i = 0; i < frs.size(); i++) 
		{
			ans += frs.get(i) + "\n";
		}
		return ans;
	}
	
}
