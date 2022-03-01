package game;
import java.util.HashMap;
public class Item {
	// instance variables - replace the example below with your own
    private String name;
    private HashMap<String, Item> inventory;
    private String description;
    public Item(String name, String description)
    {
        this.name = name;
        this.description = name;
        inventory = new HashMap<>();
    }
    public void setItem(String name, Item item){
        
         inventory.put(name, item);
    }
    
    public Item removeItem(String key){
        return inventory.remove(key);
        
    }
    public String getName(){
        
        return name;
    }
    public Item getItem(String key){
        
         return inventory.get(key);
    }
    public String getDesc(){
        
        return description;
    }
    
}
