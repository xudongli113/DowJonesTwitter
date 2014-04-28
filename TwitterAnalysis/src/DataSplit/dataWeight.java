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
public class dataWeight {

	
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
    		    String filename="C:/Users/Ronchy/Desktop/Twitter/dataWeight_i.txt";
    			// Read in the file
    			DataInputStream resultsStream = new DataInputStream(dataFile);
    			BufferedReader results = new BufferedReader(new InputStreamReader(resultsStream));
				FileWriter writer = new FileWriter(filename); 
	            PrintWriter pw=new PrintWriter(writer);
	            while ((line = results.readLine()) != null) {
	                
	    			String[] data = line.split(",");
	    			String[] time = data[0].split(" ");
	    			
	                if( time[1].equals("Jan")){
	             	   time[1]="01";
	                }
	                else if( time[1].equals("Feb")){
	             	   time[1]="02";
	                }
	                else if( time[1].equals("Mar")){
	             	   time[1]="03";
	                }
	    			String created_time = "2014-"+time[1]+"-"+time[2];
	    			     
	    			     
	    			double sentiScore = Double.parseDouble(data[1]);	
	    			
	    			double followers_count = 1.0+Double.parseDouble(data[4]);
	    			int verified=0;
	                if( data[5].equals("false")){
	                	verified=0;
		            }
		            else if( data[5].equals("true")){
		            	verified=1;
		            }
	    				    			
	    			if (followers_count>=1.0){
	    			double score = sentiScore*Math.log(followers_count)*Math.pow(2,verified);
	    			pw.println(created_time+","+score+","+data[2]+","+data[3]);
	    			}
	                
	            }

    					
    				
       		    pw.flush();
                writer.close();
    			
                System.out.println(count+" file processed");
    			
    			
    			
    			
    			
		    }
		}catch(Exception e){}
	
		
		System.out.println("Job Finished in " + (System.currentTimeMillis() - startTime) / 1000.0+"s");
		
	
	
	
	
	
	
	}
	
	
	
	
}
