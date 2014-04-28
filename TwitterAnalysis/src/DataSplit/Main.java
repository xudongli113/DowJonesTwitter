package DataSplit;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class Main {
	    public static void main(String[] args){
	           String jsonString = "{\"address\":\"chian\",\"birthday\":{\"birthday\":\"2010-11-22\"},"+
	           "\"email\":\"email@123.com\",\"id\":22,\"name\":\"tom\", \"employees\": [{ \"firstName\":\"John\" , \"lastName\":\"Doe\" },{ \"firstName\":\"Anna\" , \"lastName\":\"Smith\" },{ \"firstName\":\"Peter\" , \"lastName\":\"Jones\" }]}";
	           
	           String jsonString1 = "{\"address\":\"chian\",\"no\":{\"birthday\":\"2010-11-22\"},"+
	           "\"email\":\"email@123.com\",\"id\":22,\"name\":\"tom\", \"employees\": [{ \"firstName\":\"John\" , \"lastName\":\"Doe\" },{ \"firstName\":\"Anna\" , \"lastName\":\"Smith\" },{ \"firstName\":\"Peter\" , \"lastName\":\"Jones\" }]}";
	           JSON jsonObject = JSONSerializer.toJSON(jsonString);
	           if(jsonObject.isArray()){
	               JSONArray jsonArray = (JSONArray)jsonObject;
	               System.out.println("first element is:" + jsonArray.getString(0));
	           }
	           else{
	               JSONObject jsonObj = (JSONObject)jsonObject;
	               System.out.println("address value is :");
	           
	              //try { 
	               if (!jsonObj.has("no")){
	            	 // jsonObj.getString("no");
	              //}catch (Exception e){
	            	  System.out.println("Hi");
	            	  }
	            	  //}
	           
	           
	           
	           
	           }

	    }
	
}
