package game;

import java.util.HashMap;
import java.util.Set;

public class Player
{
    private HashMap<String, Item> inventory;
    public Player(){
        
        
        inventory = new HashMap<>();
        
        
    }
    public void setItem(String name, Item item){
        
         inventory.put(name, item);
    }
    public Item getItem(String key){
        
         return inventory.get(key);
    }
    public Item removeItem(String key){
        inventory.remove(key);
        return new Item("A","B");
    }
    public String getInventoryString(){
        
        String returnString = "Your inventory: ";
        Set<String> keys = inventory.keySet();
        for(String item: keys) {
             
            returnString += " " + item;
        }

        return returnString;
    }
    }