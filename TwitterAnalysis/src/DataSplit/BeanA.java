package DataSplit;

/** 
* ≤‚ ‘Bean 
* 
* @author leizhimin 2009-12-28 14:18:18 
*/ 
public class BeanA { 
        //                 String json2 = "{bool:true,integer:1,string:\"json\"}"; 
        private boolean bool; 
        private Integer integer; 
        private String string; 

        public boolean isBool() { 
                return bool; 
        } 

        public void setBool(boolean bool) { 
                this.bool = bool; 
        } 

        public Integer getInteger() { 
                return integer; 
        } 

        public void setInteger(Integer integer) { 
                this.integer = integer; 
        } 

        public String getString() { 
                return string; 
        } 

        public void setString(String string) { 
                this.string = string; 
        } 

        @Override 
        public String toString() { 
                return "BeanA{" + 
                                "bool=" + bool + 
                                ", integer=" + integer + 
                                ", string='" + string + '\'' + 
                                '}'; 
        } 
}