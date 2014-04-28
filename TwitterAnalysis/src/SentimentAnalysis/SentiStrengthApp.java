package SentimentAnalysis;
import uk.ac.wlv.sentistrength.*;

public class SentiStrengthApp {
    public static void main(String[] args) {

       SentiStrength sentiStrength = new SentiStrength(); 
       String ssthInitialisation[] = {"sentidata", "C:/Users/Ronchy/Desktop/Twitter/Sentiment_Tools/SentiStrength/Java_version/SentStrength_Data_Sept2011/", "explain"};
       sentiStrength.initialise(ssthInitialisation); //Initialise
       System.out.println(sentiStrength.computeSentimentScores(" AustinMahone But I really wanted to know what show you were watching on Disney Channel lol")); 
       System.out.println(sentiStrength.computeSentimentScores("Mitchell Johnson vows more intimidation of England cricket")); 
    }
}
