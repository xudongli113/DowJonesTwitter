package DataSplit;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class splitData {

	
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		if (args.length != 1) {
			System.out.println("usage: [input-path]");
	       	System.exit(-1);
		}
		try {
			
			File dir = new File(args[0]);
			int count = 0;
		    
		    for (File child : dir.listFiles()) {
		    	count++;
		    	
    			if (".".equals(child.getName()) || "..".equals(child.getName())) {
    				continue; // Ignore the self and parent aliases.
    			}
    			FileInputStream dataFile = null;
    			dataFile = new FileInputStream(child.toString());
    		    String line = null;
    		    String filename="F:/Twitter/Proceed/proceed_"+count+".txt";
    			// Read in the file
    			DataInputStream resultsStream = new DataInputStream(dataFile);
    			BufferedReader results = new BufferedReader(new InputStreamReader(resultsStream));
				FileWriter writer = new FileWriter(filename); 
	            PrintWriter pw=new PrintWriter(writer);
	            
    			line = results.readLine();
    			
                String [] data = line.split("}\\{");
                //System.out.println(data.length);
                for(int k=1; k<data.length-1;k++){
                	data[k]="{"+data[k]+"}";
                	pw.println(data[k]);
                	              	
                }
                
                

    					
    				
       		    pw.flush();
                writer.close();
    			
                System.out.println(count+" file processed");
    			
    			
    			
    			
    			
		    }
		}catch(Exception e){}
	
		
		System.out.println("Job Finished in " + (System.currentTimeMillis() - startTime) / 1000.0+"s");
		
	
	
	
	
	
	
	}
	
	
	
	
}
