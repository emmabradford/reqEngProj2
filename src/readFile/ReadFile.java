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
	Scanner scan;
	
	public ReadFile(String p) throws FileNotFoundException
	{
		path = p;
		file = new File(path);
	    scan = new Scanner(file);
	    this.readFRsAndNFRs();
	//    this.readNFRs();
	}
	
	public void readFRsAndNFRs()
	{
		boolean foundBlank = false;
		while(scan.hasNextLine()) 
		{
			if(foundBlank) 
			{
				frs.add("");
			}
			else 
			{
				if("".equals(""))//check for blank line 
				{
					foundBlank = true;
				}
				else 
				{
					nfrs.add("");		
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
	public ArrayList<String> getFRs()
	{
		return frs;
	}	
	
	public ArrayList<String> getNFRs() 
	{
		return nfrs;	
	}
	
}
