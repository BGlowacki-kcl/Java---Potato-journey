/**
 * Class Item - an item in the game.
 * 
 * This class is part of the "Potato journey" application. 
 * "Potato journey" is a very simple, text based adventure game. 
 *
 * This class takes care of creating items. Each item has assigned name, 
 * weight and a current room it is in. Methods allow a player to pick and
 * put items to and from the backpack.
 *
 * Author: Bartosz Glowacki
 * K-number: 23010447
 */
public class Item
{
    //--------------- Attributes
    private int weight;
    private String name;  
    private Room currentRoom;
    
    //--------------- Methods
    /**
     * Constructor - responsible for creating an item with assigned name,
     * weight and room.
     */
    public Item(String nameGiven, int weightGiven, Room currentRoomGiven) {
        weight = weightGiven;
        name = nameGiven;
        currentRoom = currentRoomGiven;
    }
    
    /**
     * Pick item (change its current room to the 'room' called backpack).
     * Backpack is not a room on the map, it just helps locating an item.
     */
    public void pickItem(Room backpack) { 
            currentRoom = backpack; 
    }
    
    /**
     * Take an item out of the backpack. It changes the items current room
     * to the room in which the player is currently in.
     */
    public void putItem(Room currentRoomPut) {
            currentRoom = currentRoomPut;
    } 
    
    //--------------- Get Methods
    /**
     * Return a room in which the item is currently in.
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }
    
    /**
     * Return the name of the item
     */
    public String getName() {
        return name;
    }
    
    /**
     * Return the weight of the item
     */
    public int getWeight() {
        return weight;
    }      
}
