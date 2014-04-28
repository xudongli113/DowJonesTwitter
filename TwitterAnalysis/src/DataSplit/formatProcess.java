package DataSplit;
import java.io.*;	
public class formatProcess {
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("usage: [input-path]");
	       	System.exit(-1);
		}
		try {
		    long startTime = System.currentTimeMillis();
		    BufferedReader in = new BufferedReader(new FileReader(args[0]));
            String line = null;
			FileWriter writer = new FileWriter("C:/Users/Ronchy/Desktop/Output/Goldman_Sachs.txt"); 
            PrintWriter pw=new PrintWriter(writer);
            while((line = in.readLine())!=null)
           {
               String [] lineStore = line.split("	");
               String [] time = lineStore[0].split("-");
               if( time[1].equals("Jan")){
            	   time[1]="01";
               }
               else if( time[1].equals("Feb")){
            	   time[1]="02";
               }
               else if( time[1].equals("Mar")){
            	   time[1]="03";
               }
            
			  pw.println("2014-"+time[1]+"-"+time[2]+" "+lineStore[1]);
	           
	           
	           
	           
	           
	           
	           
	           
		   }    


            System.out.println("Job Finished in " + (System.currentTimeMillis() - startTime) / 1000.0);
   		    pw.flush(); 
            writer.close();


		}catch(Exception e){
		    e.printStackTrace();           
		}
		

		
		
		
		
		
    }
}
