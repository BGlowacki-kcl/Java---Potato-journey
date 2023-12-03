/**
 * This class is part of the "Potato journey" application. 
 * "Potato journey" is a very simple, text based adventure game. 
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * Author: Bartosz Glowacki
 * K-Number: 23010447
 */

public class CommandWords
{
    //--------------- Attributes
    // a constant array that holds all valid command words
    private static final String[] validCommands = {
        "go", "quit", "help", "pick", "put", "show", "do", "move", "back"
    };
    
    //--------------- Methods
    // Use default Java constructor
    /**
     * Check whether a given String is a valid command word. 
     */
    public boolean isCommand(String aString)
    {
        for(int i = 0; i < validCommands.length; i++) {
            if(validCommands[i].equals(aString))
                return true; // It is a valid command
        }
        // if we get here, the string was not found in the commands
        return false;
    }

    /**
     * Print all valid commands to System.out.
     */
    public void showAll() 
    {
        for(String command: validCommands) {
            System.out.print(command + "  ");
        }
        System.out.println();
    }
}
