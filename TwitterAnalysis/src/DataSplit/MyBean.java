package DataSplit;

import net.sf.json.JSONFunction; 

/** 
* ≤‚ ‘Bean 
* 
* @author leizhimin 2009-12-28 13:55:09 
*/ 
public class MyBean { 
        private String name = "json"; 
        private int pojoId = 1; 
        private char[] options = new char[]{'a', 'f'}; 
        private String func1 = "function(i){ return this.options[i]; }"; 
        private JSONFunction func2 = new JSONFunction(new String[]{"i"}, "return this.options[i];"); 

        public String getName() { 
                return name; 
        } 

        public void setName(String name) { 
                this.name = name; 
        } 

        public int getPojoId() { 
                return pojoId; 
        } 

        public void setPojoId(int pojoId) { 
                this.pojoId = pojoId; 
        } 

        public char[] getOptions() { 
                return options; 
        } 

        public void setOptions(char[] options) { 
                this.options = options; 
        } 

        public String getFunc1() { 
                return func1; 
        } 

        public void setFunc1(String func1) { 
                this.func1 = func1; 
        } 

        public JSONFunction getFunc2() { 
                return func2; 
        } 

        public void setFunc2(JSONFunction func2) { 
                this.func2 = func2; 
        } 
}
 


