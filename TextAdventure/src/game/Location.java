package game;
import java.util.HashMap;
import java.util.Set;
public class Location
{
    // instance variables - replace the example below with your own
    private String shortDesc;
    private boolean canFreeze;
    private boolean IsDanger;
    private String longDesc;
    private String escapeItem;
    private HashMap<String, Location> exits;
    private HashMap<String, Item> inventory;
    public Location(String shortDescript, String longDescript, boolean canFreeze, boolean IsDanger)
    {
        this.shortDesc = shortDescript;
         this.IsDanger = IsDanger;
         this.longDesc = longDescript;
           this.canFreeze = canFreeze; 
        // initialise instance variables'
         inventory = new HashMap<>();
           exits = new HashMap<>();
    }
    public void setShortDescription(String newDesc){
        
        shortDesc = newDesc;
        
    }
    
    public void setItem(String name, Item item){
        
         inventory.put(name,  item);
    }
    public Item getItem(String key){
        
         return inventory.get(key);
    }
    public boolean getDanger(){
        
         return IsDanger;
    }
    public void setDanger(boolean choice){
        
         IsDanger = choice;
    }
    public Item removeItem(String name){
        
        return inventory.remove(name);
    }
    public String getShortDescription(){
        
        return shortDesc;
        
    }
    public boolean getFreeze(){
        
        return canFreeze;
        
    }
    public void Freeze(){
        
        canFreeze = false;
        
    }
    public String getLongDescription(){
        
        return longDesc;
        
    }
    public void SetExit(String direction, Location neighbor){
        
        exits.put(direction, neighbor);        
        
        
        
        
    }
    public String getInventoryString(){
        
        String returnString = "Room inventory: ";
        Set<String> keys = inventory.keySet();
        for(String item: keys) {
     
            returnString += " " + item;
        }

        return returnString;
    }
    public Location getExit(String direction){
               return exits.get(direction);
    }
     
    public String getExitString(){
        
        String returnString = "Exits: ";
        Set<String> keys = exits.keySet();
        for(String exit: keys) {
     
            returnString += " " + exit;
        }

        return returnString;
    }
}