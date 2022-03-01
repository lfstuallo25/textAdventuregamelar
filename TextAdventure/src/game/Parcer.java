package game;

import java.util.Scanner;
public class Parcer
{
    // instance variables - replace the example below with your own
    private Scanner kb;

    public Parcer()
    {
       kb = new Scanner(System.in);
       
    }

   
    public Command getCommand(){
        
        
        
        
        String inputLine;
        String word1 = null;
        String word2 = null;
        String line = null;
        inputLine = kb.nextLine();
        
        Scanner tokenizer = new Scanner(inputLine);
        if(tokenizer.hasNext()) { 
            word1 = tokenizer.next();
            if(tokenizer.hasNext()) {
                
                word2 = tokenizer.next();
            }
            if(tokenizer.hasNext()) {
                
                line = tokenizer.nextLine();
            }
        }
        return new Command(word1, word2, line);
    }
}
