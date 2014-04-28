package DataSplit;
import java.io.*;	
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

import uk.ac.wlv.sentistrength.SentiStrength;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class dataExtractagain {
	
	
	public static String parse(String tweet_text) {
		  tweet_text = tweet_text.toLowerCase();
		  tweet_text=tweet_text.replaceAll("[\\W]|_", " ");;
		  while(true){
		    if(tweet_text.contains("http")){ 	   	            	   
		       int indexOfHttp = tweet_text.indexOf("http");
		       int endPoint = (tweet_text.indexOf(" ", indexOfHttp) != -1) ? tweet_text.indexOf(" ", indexOfHttp) : tweet_text.length();
		       try{
		         String url = tweet_text.substring(indexOfHttp, endPoint);	
		         tweet_text = tweet_text.replace(url,"" );
		       }catch(Exception e) {  
		    	   System.out.println(tweet_text);
		    	   break;
		       }
		                
		    }
		    else{
		    	break;
		    }
		  } 
		    
		  String patternStr = "(?:\\s|\\A)[##]+([A-Za-z0-9-_]+)";
		  Pattern pattern = Pattern.compile(patternStr);
		  Matcher matcher = pattern.matcher(tweet_text);
		  String result = "";
		//Search for Hashtags
		  while (matcher.find()) {
		      result = matcher.group();
		      result = result.replace(" ", "");
		      String search = result.replace("#", "");
		      String searchHTML="" + search;
		      tweet_text = tweet_text.replace(result,searchHTML); 
		  }
		//Search for Users
		  patternStr = "(?:\\s|\\A)[@]+([A-Za-z0-9-_]+)";
		  pattern = Pattern.compile(patternStr);
		  matcher = pattern.matcher(tweet_text);
		  while (matcher.find()) {
			  result = matcher.group();
			  result = result.replace(" ", "");
			  String rawName = result.replace("@", "");
			  String userHTML="" + rawName;
			  tweet_text = tweet_text.replace(result,userHTML);
		
		  }
		
		
		  tweet_text=tweet_text.replaceAll("-","");
		  
		  return tweet_text;

	}
	
	public static int sentimentValue(String tweet_text) {
         SentiStrength sentiStrength = new SentiStrength(); 
         String ssthInitialisation[] = {"sentidata", "C:/Users/Ronchy/Desktop/Twitter/Sentiment_Tools/SentiStrength/Java_version/SentStrength_Data_Sept2011/", "explain"};
         sentiStrength.initialise(ssthInitialisation); //Initialise
         String senti_sentence = sentiStrength.computeSentimentScores(tweet_text);
		 String [] senti_split =  senti_sentence.split(" ");		 
		 int sent_score = Integer.parseInt(senti_split[0])+Integer.parseInt(senti_split[1]);
		 
		 return sent_score;
	}
	
	
	public static void main(String[] args) {
		 long startTime = System.currentTimeMillis();
		 String last_filename="";
		if (args.length != 1) {
			System.out.println("usage: [input-path]");
	       	System.exit(-1);
		}
		try {
			
			File dir = new File(args[0]);
			int count =0;
		   
		    for (File child : dir.listFiles()) {
		    	
    			if (".".equals(child.getName()) || "..".equals(child.getName())) {
    				continue; // Ignore the self and parent aliases.
    			}
    			last_filename = child.toString();
        		String filename0 = "F:/Twitter/Company/IBM.txt";
        		String filename1 = "F:/Twitter/Company/Goldman_Sachs.txt";
        		String filename2 = "F:/Twitter/Company/General_Electric.txt";
        		String filename3 = "F:/Twitter/Company/Intel.txt";
        		String filename4 = "F:/Twitter/Company/Home_Depot.txt";
	            String line = null;
				FileWriter writer = new FileWriter(filename0,true); 
	            PrintWriter pw=new PrintWriter(writer);
				FileWriter writer1 = new FileWriter(filename1,true); 
	            PrintWriter pw1=new PrintWriter(writer1);
				FileWriter writer2 = new FileWriter(filename2,true); 
	            PrintWriter pw2=new PrintWriter(writer2);
	              
				FileWriter writer3 = new FileWriter(filename3,true); 
	            PrintWriter pw3=new PrintWriter(writer3);
	              
				FileWriter writer4 = new FileWriter(filename4,true); 
	            PrintWriter pw4=new PrintWriter(writer4);
	            String jsonString="";   
        	
    			FileInputStream dataFile = null;
    			dataFile = new FileInputStream(child.toString());
    			// Read in the file
    			DataInputStream resultsStream = new DataInputStream(dataFile);
    			BufferedReader results = new BufferedReader(new InputStreamReader(resultsStream));
                count++;
                //int line_num=0;
                while((line = results.readLine())!=null)
               {   // line_num++;
                    //System.out.println(line_num);
	                   jsonString = line;
	    	           JSON jsonObject = JSONSerializer.toJSON(jsonString);

    	            	
    	               JSONObject jsonObj = (JSONObject)jsonObject;
    	               String tweet_text = jsonObj.getString("text");
    	               
    	               if (jsonObj.has("lang")){
    	                 if(jsonObj.getString("lang").equals("en")){

    	                   tweet_text = parse(tweet_text);//parse the tweet
    	                   if(tweet_text.contains("ibm")){
    	                	   
    	                	   int sentiScore = sentimentValue(tweet_text);
    	                       String create_time = jsonObj.getString("created_at");
    	                       String retweet_count = jsonObj.getString("retweet_count");
    	                       String favorite_count = jsonObj.getString("favorite_count");
    	                       
    	                       String user = jsonObj.getString("user");   	                       
    	       	               JSON userObject = JSONSerializer.toJSON(user);
	    	       	           JSONObject userObj = (JSONObject)userObject;
	    	       	           String followers_count = userObj.getString("followers_count");
	    	       	           String verified = userObj.getString("verified");
	    	       	           if (jsonObj.has("retweeted_status")){
	    	                       String retweet_states = jsonObj.getString("retweeted_status");   	                       
	    	       	               JSON retweetObject = JSONSerializer.toJSON(retweet_states);
		    	       	           JSONObject retweetObj = (JSONObject)retweetObject;
		    	       	           retweet_count = retweetObj.getString("retweet_count");
		    	       	           favorite_count = retweetObj.getString("favorite_count");
	    	       	        	   
	    	       	           }
	    	       	           
    	       	                   
    	       	               tweet_text= create_time+","+sentiScore+","+retweet_count+","+favorite_count+","+followers_count+","+verified;
 
    	                       
    	                       
    	                       pw.println(tweet_text);
    	                   }
    	                   else if(tweet_text.contains("goldman sachs")){

    	                	   int sentiScore = sentimentValue(tweet_text);
    	                       String create_time = jsonObj.getString("created_at");
    	                       String retweet_count = jsonObj.getString("retweet_count");
    	                       String favorite_count = jsonObj.getString("favorite_count");
    	                       
    	                       String user = jsonObj.getString("user");   	                       
    	       	               JSON userObject = JSONSerializer.toJSON(user);
	    	       	           JSONObject userObj = (JSONObject)userObject;
	    	       	           String followers_count = userObj.getString("followers_count");
	    	       	           String verified = userObj.getString("verified");
	    	       	           if (jsonObj.has("retweeted_status")){
	    	                       String retweet_states = jsonObj.getString("retweeted_status");   	                       
	    	       	               JSON retweetObject = JSONSerializer.toJSON(retweet_states);
		    	       	           JSONObject retweetObj = (JSONObject)retweetObject;
		    	       	           retweet_count = retweetObj.getString("retweet_count");
		    	       	           favorite_count = retweetObj.getString("favorite_count");
	    	       	        	   
	    	       	           }
	    	       	           
    	       	                   
    	       	               tweet_text= create_time+","+sentiScore+","+retweet_count+","+favorite_count+","+followers_count+","+verified;
                       
    	                       pw1.println(tweet_text);
    	                   }
    	                   else if(tweet_text.contains("general electric")){
    	                	   int sentiScore = sentimentValue(tweet_text);
    	                       String create_time = jsonObj.getString("created_at");
    	                       String retweet_count = jsonObj.getString("retweet_count");
    	                       String favorite_count = jsonObj.getString("favorite_count");
    	                       
    	                       String user = jsonObj.getString("user");   	                       
    	       	               JSON userObject = JSONSerializer.toJSON(user);
	    	       	           JSONObject userObj = (JSONObject)userObject;
	    	       	           String followers_count = userObj.getString("followers_count");
	    	       	           String verified = userObj.getString("verified");
	    	       	           if (jsonObj.has("retweeted_status")){
	    	                       String retweet_states = jsonObj.getString("retweeted_status");   	                       
	    	       	               JSON retweetObject = JSONSerializer.toJSON(retweet_states);
		    	       	           JSONObject retweetObj = (JSONObject)retweetObject;
		    	       	           retweet_count = retweetObj.getString("retweet_count");
		    	       	           favorite_count = retweetObj.getString("favorite_count");
	    	       	        	   
	    	       	           }
	    	       	           
    	       	                   
    	       	               tweet_text= create_time+","+sentiScore+","+retweet_count+","+favorite_count+","+followers_count+","+verified;
 
    	                       
    	                       
    	                       pw2.println(tweet_text);
    	                   }
    	                   else if(tweet_text.contains("intel")){
    	                	   int sentiScore = sentimentValue(tweet_text);
    	                       String create_time = jsonObj.getString("created_at");
    	                       String retweet_count = jsonObj.getString("retweet_count");
    	                       String favorite_count = jsonObj.getString("favorite_count");
    	                       
    	                       String user = jsonObj.getString("user");   	                       
    	       	               JSON userObject = JSONSerializer.toJSON(user);
	    	       	           JSONObject userObj = (JSONObject)userObject;
	    	       	           String followers_count = userObj.getString("followers_count");
	    	       	           String verified = userObj.getString("verified");
	    	       	           if (jsonObj.has("retweeted_status")){
	    	                       String retweet_states = jsonObj.getString("retweeted_status");   	                       
	    	       	               JSON retweetObject = JSONSerializer.toJSON(retweet_states);
		    	       	           JSONObject retweetObj = (JSONObject)retweetObject;
		    	       	           retweet_count = retweetObj.getString("retweet_count");
		    	       	           favorite_count = retweetObj.getString("favorite_count");
	    	       	        	   
	    	       	           }
	    	       	           
    	       	                   
    	       	               tweet_text= create_time+","+sentiScore+","+retweet_count+","+favorite_count+","+followers_count+","+verified;
 
    	                       
    	                       pw3.println(tweet_text); 
    	                   }
    	                   else if(tweet_text.contains("home depot")){
    	                	   int sentiScore = sentimentValue(tweet_text);
    	                       String create_time = jsonObj.getString("created_at");
    	                       String retweet_count = jsonObj.getString("retweet_count");
    	                       String favorite_count = jsonObj.getString("favorite_count");
    	                       
    	                       String user = jsonObj.getString("user");   	                       
    	       	               JSON userObject = JSONSerializer.toJSON(user);
	    	       	           JSONObject userObj = (JSONObject)userObject;
	    	       	           String followers_count = userObj.getString("followers_count");
	    	       	           String verified = userObj.getString("verified");
	    	       	           if (jsonObj.has("retweeted_status")){
	    	                       String retweet_states = jsonObj.getString("retweeted_status");   	                       
	    	       	               JSON retweetObject = JSONSerializer.toJSON(retweet_states);
		    	       	           JSONObject retweetObj = (JSONObject)retweetObject;
		    	       	           retweet_count = retweetObj.getString("retweet_count");
		    	       	           favorite_count = retweetObj.getString("favorite_count");
	    	       	        	   
	    	       	           }
	    	       	           
    	       	                   
    	       	               tweet_text= create_time+","+sentiScore+","+retweet_count+","+favorite_count+","+followers_count+","+verified;
 
    	                       
    	                       
    	                       pw4.println(tweet_text);
    	                   }
    	                   
    	                   
    	               }//"lang"="en"
    	              }//json.has("lang")
    	               
    	               
    	               
    	            
         		}

                
       		    pw.flush();
       		    pw1.flush();
       		    pw2.flush();
       		    pw3.flush();
       		    pw4.flush();
                writer.close();
                writer1.close();
                writer2.close();
                writer3.close();
                writer4.close();
                
                System.out.println("Last file is "+last_filename);
    			System.out.println(count+" file processed");
    			
    			
    			if (dataFile != null)
    				dataFile.close();
        	}
          



		}catch(Exception e){
		    e.printStackTrace();           
		}
		
		System.out.println("Job Finished in " + (System.currentTimeMillis() - startTime) / 1000.0+"s");
		
		
		
		
		
    }
}
