package game;

public class Command {
	private String commandWord;
    private String secoundWord;
    private String lineWord;
    
    public Command(String commandWord, String secoundWord, String line)
    {
        this.commandWord = commandWord;
          this.secoundWord = secoundWord;
          this.lineWord = line;
    }
    public String getCommandWord(){
        
        return commandWord;
        
    }
    public String getSecWord(){
        return secoundWord;
     
        
    }
    public boolean ifsecWord(){
        return (secoundWord != null);
    
    }
    public String getline(){
        return lineWord;
    
    }
    public boolean ifline(){
        return (lineWord != null);
    
    }
}
