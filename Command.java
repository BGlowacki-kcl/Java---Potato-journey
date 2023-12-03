/**
 * This class is part of the "Potato journey" application. 
 * "Potato journey" is a very simple, text based adventure game.  
 *
 * This class holds information about a command that was issued by the user.
 * A command consists of one, two or three strings: a command word, a 
 * second word and a third word.
 * 
 * Author: Bartosz Glowacki
 * K-number: 23010447
 */

public class Command
{
    //--------------- Attributes
    private String commandWord;
    private String secondWord;
    private String thirdWord;

    //--------------- Methods
    /**
     * Create a command object. First, second and third word must be 
     * supplied, but either one, two or all can be null.
     */
    public Command(String firstWord, String secondWord, String thirdWord)
    {
        commandWord = firstWord;
        this.secondWord = secondWord;
        this.thirdWord = thirdWord;
    }

    //--------------- Get Methods
    /**
     * Return the command word (the first word) of this command or null if
     * it was not understood.
     */
    public String getCommandWord()
    {
        return commandWord;
    }

    /**
     * Return the second word of the command or null if there isn't any.
     */
    public String getSecondWord()
    {
        return secondWord;
    }
    
    /**
     * Return the third word of the command or null if there isn't any. 
     * Applies only to 'move' command.
     */
    public String getThirdWord() {
        return thirdWord;
    }
    
    //--------------- Check Methods
    /**
     * Return true if the command word was not understood.
     */
    public boolean isUnknown()
    {
        return (commandWord == null);
    }

    /**
     * Return true if the command has a second word.
     */
    public boolean hasSecondWord()
    {
        return (secondWord != null);
    }
    
    /**
     * Return true if the command has a third word.
     */
    public boolean hasThirdWord()
    {
        return (thirdWord != null);
    }
}

