/**
 * Class Character - a character in the game.
 * 
 * This class is part of the "Potato journey" application. 
 * "Potato journey" is a very simple, text based adventure game. 
 * 
 * This class specifies the character object. Assigns a name and current
 * room to it. Method allows to move the character on the map.
 * 
 * Author: Bartosz Glowacki
 * K-number: 23010447
 */
public class Character
{
    //--------------- Attributes
    private String name;
    private Room currentRoom;
    private boolean ifForward; // If we move forward the array of rooms (true), or backward (false)
    private boolean ifAbleToMove; // After moving the character to any room it cannot move by itself anymore
    private Room path[];
    
    //--------------- Methods
    /**
     * Constructor - assigns name and current room to the object
     */
    public Character(String name, Room currentRoom, Room path[]) {
        this.name = name;
        this.currentRoom = currentRoom;
        ifForward = true;
        ifAbleToMove = true;
        this.path = path;
    }

    /**
     * Method moves the character to the given room after 'move'
     * command and makes it stay in place
     */
    public void changeLocation(Room room){    
        currentRoom = room;
        setImmovable();
    }
    
    /**
     * Method is responsible for making movements by characters themselves
     */
    public void moveInDirection() {
        if(!ifAbleToMove) 
            return;     
        currentRoom = getNextRoom();
    }
    
    /**
     * After moving the character, do not allow it to move by itself
     */
    private void setImmovable() {
        ifAbleToMove = false;
    }
    
    //--------------- Get Methods
    /**
     * Return the name of the character.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Return the room in which the character currently is.
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }
    
    /**
     * Find the next room, the character should be going into
     * following the path specified in the path array
     */
    public Room getNextRoom() {
        if(!ifAbleToMove) {
            return null;
        }
        Room nextRoom;
        // Find index of current room in the path
        int index = 0;
        for(int i = 0; i < path.length; i++) {
            if(path[i] == currentRoom) {
                index = i;
                break;
            }
        }
        // If it is last index or the character is moving backward the path, we want him to go into the previous room in the path. Otherwise we want it to take the next room in the path.
        if((index == path.length - 1)) {
            // The last room in the array
            nextRoom = path[index - 1];
            ifForward = false;
        } else if(index == 0){
            // The first room in the array
            nextRoom = path[1];
            ifForward = true;
        } else if(ifForward) {
            nextRoom = path[index + 1];
        } else {
            nextRoom = path[index - 1];
        }
        return nextRoom;
    }
}
