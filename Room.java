import java.util.Set;
import java.util.HashMap;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "Potato journey" application. 
 * "Potato journey" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * Author: Bartosz Glowacki
 * K-number: 23010447
 */

public class Room 
{
    //--------------- Attributes
    private String description;
    private String name;
    private HashMap<String, Room> exits;   // Stores exits of this room.
    private String prefix; // How to call this room 'in the' 'on the' 'at'
    
    //--------------- Methods
    /**
     * Create a room giving its name (e.g. 'kitchen'), its description
     * (e.g. 'So many items in here. Let's look around') and prefix 
     * (e.g. 'in the').
     */
    public Room(String name, String description, String prefix) 
    {
        this.description = description;
        this.name = name;
        this.prefix = prefix;
        exits = new HashMap<>();
    }

    /**
     * Define an exit from this room.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }
    
    /**
     * Display all avaiable exits from this room with with a name of the 
     * room that the exit leads into.
     */
    public void showExits() {
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            System.out.println(exit + ": " + getExit(exit).getName());
        }
    }
    
    //--------------- Get Methods
    /**
     * Return the name of the room. 
     */
    public String getName() {
        return name;
    }

    /**
     * Return a description of the room in the form:
     *     So many items in here. Let's look around.
     *     Exits: north west
     */
    public String getLongDescription()
    {
        return description + "\n" + getExitString();
    }

    /**
     * Return a string describing the room's exits, for example:
     * "Exits: north west".
     */
    public String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
    /**
     * Return the prefix of the room given when it was created, like:
     * "in the"
     */
    public String getPrefix() {
        return prefix;
    }
}   

