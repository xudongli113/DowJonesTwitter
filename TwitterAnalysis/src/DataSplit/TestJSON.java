package DataSplit;
import net.sf.json.JSONArray; 
import net.sf.json.JSONObject; 
import net.sf.json.xml.XMLSerializer; 

import java.util.ArrayList; 
import java.util.HashMap; 
import java.util.List; 
import java.util.Map; 

/** 
* JSON for Java测试代码 
* 
* @author leizhimin 2009-12-28 13:15:25 
*/ 
public class TestJSON { 
        public static void main(String[] args) { 
                test1(); 
                test2(); 
                test3(); 
                test4(); 
                test5(); 
        } 


        /** 
         * 数组或集合-->JSON串 
         */ 
        public static void test1() { 
                System.out.println("------------数组或集合-->JSON串----------"); 
                boolean[] boolArray = new boolean[]{true, false, true}; 
                JSONArray jsonArray1 = JSONArray.fromObject(boolArray); 
                System.out.println(jsonArray1); 
//[true,false,true] 
                List list = new ArrayList(); 
                list.add("first"); 
                list.add("second"); 
                JSONArray jsonArray2 = JSONArray.fromObject(list); 
                System.out.println(jsonArray2); 
//["first","second"] 
                JSONArray jsonArray3 = JSONArray.fromObject("['json','is','easy']"); 
                System.out.println(jsonArray3); 
// ["json","is","easy"]        
        } 

        /** 
         * Object|Map-->JSON串 
         */ 
        public static void test2() { 
                System.out.println("------------Object|Map-->JSON串----------"); 
                Map map = new HashMap(); 
                map.put("name", "json"); 
                map.put("bool", Boolean.TRUE); 
                map.put("int", new Integer(1)); 
                map.put("arr", new String[]{"a", "b"}); 
                map.put("func", "function(i){ return this.arr[i]; }"); 
                JSONObject jsonObject1 = JSONObject.fromObject(map); 
                System.out.println(jsonObject1); 
//{"func":function(i){ return this.arr[i]; },"arr":["a","b"],"int":1,"bool":true,"name":"json"} 

                JSONObject jsonObject2 = JSONObject.fromObject(new MyBean()); 
                System.out.println(jsonObject2); 
//{"func1":function(i){ return this.options[i]; },"func2":function(i){ return this.options[i]; },"name":"json","options":["a","f"],"pojoId":1} 
        } 

        /** 
         * JSON串-->Object 
         */ 
        public static void test3() { 
                System.out.println("------------JSON串-->Object----------"); 
                String json1 = "{name=\"json\",bool:true,int:1,double:2.2,func:function(a){ return a; },array:[1,2]}"; 
                JSONObject jsonObject1 = JSONObject.fromObject(json1); 
                Object bean1 = JSONObject.toBean(jsonObject1); 
                System.out.println(bean1); 
//net.sf.ezmorph.bean.MorphDynaBean@10dd1f7[ 
//    {double=2.2, func=function(a){ return a; }, int=1, name=json, bool=true, array=[1, 2]} 
//] 
                String json2 = "{bool:true,integer:1,string:\"json\"}"; 
                JSONObject jsonObject2 = JSONObject.fromObject(json2); 
                BeanA bean2 = (BeanA) JSONObject.toBean(jsonObject2, BeanA.class); 
                System.out.println(bean2); 
// BeanA{bool=true, integer=1, string='json'} 
        } 

        /** 
         * JSON串-->XML 
         */ 
        public static void test4() { 
                System.out.println("------------JSON串-->XML----------"); 
                JSONObject json = new JSONObject(true); 
                String xml = new XMLSerializer().write(json); 
                System.out.println(xml); 

                JSONObject json1 = JSONObject.fromObject("{\"name\":\"json\",\"bool\":true,\"int\":1}"); 
                String xml1 = new XMLSerializer().write(json1); 
                System.out.println(xml1); 

                JSONArray json2 = JSONArray.fromObject("[1,2,3]"); 
                String xml2 = new XMLSerializer().write(json2); 
                System.out.println(xml2); 
        } 

        /** 
         * XML-->JSON串 
         */ 
        public static void test5() { 
                System.out.println("------------XML-->JSON串----------"); 
                String xml = "" + 
                                "<a class=\"array\">\n" + 
                                "    <e type=\"function\" params=\"i,j\">\n" + 
                                "            return matrix[i][j];\n" + 
                                "    </e>\n" + 
                                "</a>"; 
                JSONArray json = (JSONArray) new XMLSerializer().read(xml); 
                System.out.println(json); 
        } 
}
