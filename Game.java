import java.util.ArrayList;
import java.util.Random;
import java.util.stream.*;
import java.util.*;

/**
 *  This class is the main class of the "Potato journey" application. 
 *  "Potato journey" is a very simple, text based adventure game. Users 
 *  can walk around, collect items, move characters and complete tasks to 
 *  finally become a pack of chips and be eaten.
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, items, characters creates the parser and starts the game.  
 *  It also evaluates and executes the commands that the parser returns.
 * 
 *  Author: Bartosz Glowacki
 *  K-number: 23010447
 *  
 *  Based on the program of:
 *    Michael Kölling and David J. Barnes
 */

public class Game 
{
    //--------------- Attributes
    // Utilities in game
    private Parser parser;
    private Room currentRoom;
    
    // Sample objects, help not to leave empty parameters in functions
    private Room backpackRoom = new Room("backpack", "Room for items in backpack", "");
    private Command emptyCommand = new Command(null, null, null);
    
    // Arraylists   
    private ArrayList<Item> items = new ArrayList<Item>();
    private ArrayList<Item> backpack = new ArrayList<Item>();
    private ArrayList<Room> rooms = new ArrayList<Room>();
    private ArrayList<Room> roomOrder = new ArrayList<Room>();
    private ArrayList<Character> characters = new ArrayList<Character>();
    
    // Array
    private static final String TO_DO[] = new String[] {"wash yourself", "repair the road", "peel yourself", "cut yourself", "fry yourself", "go to the canteen"};
    
    // Different variables
    private static final int MAX_BACKPACK_WEIGHT = 5;
    private int backpackWeight = 0;
    private int doDone = 1; // Counting how many activities have been done   
    private boolean ifWon = false;
    
    //--------------- Methods
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together. Create items 
     * and characters, locate them in the existing rooms.
     */
    private void createRooms()
    {
        // Declare all the objects on the map
        Room farm, garage, workshop, cleaningArea, road, kitchen, peelingRoom, home, cutter, cooker, canteen, teleport;
        Item asphalt, knife, water, oil, blade, car, tree, sink, cupboard, table, chair, map, map2;
        Character worker, cleaner, kitchenPorter, chef;
        
        // Create the rooms
        farm = new Room("farm", "Your history started here.", "at the");
        garage = new Room("garage", "It looks so old. A place where our worker used to be everyday.", "in the");
        workshop = new Room("workshop", "Can you smell it? This is how a fresh asphalt smells. ", "in the");
        cleaningArea = new Room("cleaningArea", "So many sinks. You can wash yourself here!", "in the");
        road = new Room("road", "Look at this beautiful road and all these trees nearby.", "on the");
        kitchen = new Room("kitchen", "So many items in here. Let's look around", "in the");
        peelingRoom = new Room("peelingRoom", "Here you can get rid of the peel and get one step closer to become a pack of crisps.", "in the");
        home = new Room("home", "Kitchen porter lives here, she might be helpful now and later.", "at");
        cutter = new Room("cutter", "Watch out! These blades are so sharp, you don't want to cut yourself incorrectly.", "in the");
        cooker = new Room("cooker", "It is extremaly hot in here, let's get out of this place.", "in the");
        canteen = new Room("canteen", "Look at all these hungry boys. They are going to eat you from one side to another. Just become a pack of chips", "in the");
        teleport = new Room("teleport", "What a strange-looking room. You can teleport to any random room if you wish.", "in the");
        
        // Add rooms to an ArrayList
        rooms.add(farm);
        rooms.add(garage);
        rooms.add(workshop);
        rooms.add(cleaningArea);
        rooms.add(road);
        rooms.add(kitchen);
        rooms.add(peelingRoom);
        rooms.add(home);
        rooms.add(cutter);
        rooms.add(cooker);
        rooms.add(canteen);
        
        // Initialise room exits
        farm.setExit("left", garage);
        farm.setExit("up", cleaningArea);
        farm.setExit("right", road);

        garage.setExit("right", farm);
        garage.setExit("up", workshop);
    
        workshop.setExit("down", garage);
        
        cleaningArea.setExit("down", farm);
        
        road.setExit("up", kitchen);
        road.setExit("left", farm);

        kitchen.setExit("down", road);
        kitchen.setExit("up", peelingRoom);
        kitchen.setExit("left", cutter);
        kitchen.setExit("right", teleport);
        
        teleport.setExit("left", kitchen);
        
        peelingRoom.setExit("down", kitchen);
        peelingRoom.setExit("left", home);
        
        home.setExit("right", peelingRoom);
        
        cutter.setExit("right", kitchen);
        cutter.setExit("left", cooker);
        
        cooker.setExit("right", cutter);
        cooker.setExit("up", canteen);
        
        canteen.setExit("down", cooker);
        
        // Create items
        asphalt = new Item("asphalt", 4, workshop);
        knife = new Item("knife", 3, kitchen);
        water = new Item("water", 2, farm);
        oil = new Item("oil", 3, kitchen);
        blade = new Item("blade", 2, workshop);
        car = new Item("car", 0, garage);
        tree = new Item("tree", 0, farm);
        sink = new Item("sink", 0, cleaningArea);
        cupboard = new Item("cupboard", 0, kitchen);
        table = new Item("table", 0, home);
        chair = new Item("chair", 0, home);
        map = new Item("map", 0, workshop);
        map2 = new Item("map", 0, home);
        
        // Add items to the ArrayList
        items.add(asphalt); 
        items.add(knife);
        items.add(water);
        items.add(oil);
        items.add(blade);
        items.add(car);
        items.add(tree);
        items.add(sink);
        items.add(cupboard);
        items.add(table);
        items.add(chair);
        items.add(map);
        items.add(map2);
        
        // Create paths for characters
        Room cleanerRooms[] = new Room[] {cleaningArea, farm, road};  
        Room workerRooms[] = new Room[] {workshop, garage, farm, cleaningArea};
        Room kitchenPorterRooms[] = new Room[] {home, peelingRoom, kitchen, teleport};
        Room chefRooms[] = new Room[] {teleport, kitchen, cutter, cooker, canteen}; 
        
        // Create characters
        worker = new Character("worker", workshop, workerRooms);
        cleaner = new Character("cleaner", farm, cleanerRooms);
        kitchenPorter = new Character("kitchenPorter", home, kitchenPorterRooms);
        chef = new Character("chef", kitchen, chefRooms);
        
        // Add characters to an ArrayList
        characters.add(worker);
        characters.add(cleaner);
        characters.add(kitchenPorter);
        characters.add(chef);
        
        // Set initial values
        currentRoom = farm;  // start game at the farm
        roomOrder.add(farm);
    }

    /**
     *  Main play routine. Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the Potato journey!");
        System.out.println("Potato journey is a new, incredibly interesting adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println("Farm. The beginning of our journey. Here you have been growing for months to finally escape this place and become a chip.");
        System.out.println(currentRoom.getExitString());
    }

    /**
     * Given a command, process (that is: execute) the command.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("Invalid command. Write 'help' to see which you can use.");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help"))
            printHelp(command);
        else if (commandWord.equals("go")) {
            goRoom(command);
            wantToQuit = ifWon;
        } else if (commandWord.equals("quit"))
            wantToQuit = quit(command);
        else if (commandWord.equals("pick")) 
            pickItem(command);
        else if (commandWord.equals("put"))
            putItem(command);
        else if (commandWord.equals("show")) 
            show(command);
        else if (commandWord.equals("move"))
            move(command);
        else if (commandWord.equals("do"))
            doSomething(command);
        else if (commandWord.equals("back"))
            back(command);
        // else command not recognised.
        return wantToQuit;
    }

    //--------------- Implementations of user commands:

    /**
     * Print out some help information.
     * Some short text and commands that can be used.
     * Or print description of a command that was provided 
     * as a second word
     */
    private void printHelp(Command command) 
    {
        if(!command.hasSecondWord()) {
            System.out.println("You are lost. You are a lonely potatoe which doesn't know what do!");
            System.out.println();
            System.out.println("Your command words are:");
            parser.showCommands();
            System.out.println("If you want to know what the specific command means type help 'command'");
            System.out.println();
            System.out.println("Right now you should " + TO_DO[doDone - 1]);
        } else if(command.hasThirdWord()) {
            System.out.println("help " + command.getSecondWord() + " what?");
        } else {
            if(command.getSecondWord().equals("go"))
                System.out.println("This command moves you to the room in direction specified in secodnd word (up, right, down, left)");
            else if(command.getSecondWord().equals("back"))
                System.out.println("This command moves you to the previous room. Typed multiple times takes to to the begining 'farm'");
            else if(command.getSecondWord().equals("pick"))
                System.out.println("This command puts the specified item into the backpack, if you are in the same room");
            else if(command.getSecondWord().equals("put"))
                System.out.println("This command takes a specified item out of the backpack to the room you are currently in");
            else if(command.getSecondWord().equals("quit"))
                System.out.println("This command quits the game");
            else if(command.getSecondWord().equals("help"))
                System.out.println("This command shows you possible commands and task you are required to do");
            else if(command.getSecondWord().equals("show")) {
                System.out.println("This command shows you useful information like:");
                System.out.println("  items: items in a current room");
                System.out.println("  characters: characters in a current room");
                System.out.println("  map: map (if there is one in the room)");
                System.out.println("  backpack: items currently in a backpack");
                System.out.println("  weight: current weight of the backpack");
                System.out.println("  'character's name': current location the character is in and room it is heading into");
                System.out.println("  exits: rooms' names behind the possible exits");
            } else if(command.getSecondWord().equals("do"))
                System.out.println("This command does teleportation (do teleport) or does the task specified (wash, repair, peel, cut, fry)");
            else if(command.getSecondWord().equals("move"))
                System.out.println("This command moves the specified character into the specified room");
            else 
                System.out.println("Invalid command!");
        }
    }

    /** 
     * Go to another room in specified direction if the
     * exit like this exists.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();
        
        // You cannot access kitchen unless you have repaired the road
        if(currentRoom.getName() == "road" && direction.equals("up") && doDone <= 2) {
            System.out.println("Firstly you need to repair the road!");
            return;
        }

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null)
            System.out.println("There is no door!");
        else {
            currentRoom = nextRoom;
            roomOrder.add(currentRoom);
            System.out.println("You are " + currentRoom.getPrefix() + " " + currentRoom.getName());
            if(doDone == 6 && currentRoom.getName().equals("canteen")) { // Did 6 tasks and is in the canteen? Then, won.
                System.out.println("Congratulations!!! You just won the Potao journey."); 
                System.out.println("From growing at a farm you became a pack of chips.");
                ifWon = true; // will quit the game
                return;
            }
            System.out.println(currentRoom.getLongDescription());
            for(Character character : characters) {
                character.moveInDirection();
            }
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    
    /**
     * Method checks if the item we want to pick is in the same room, 
     * is movable, isn't too heavy or isn't already taken and 'picks' it.
     */
    private void pickItem(Command command) {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what item to pick.
            System.out.println("Pick what?");
            return;
        }
        Item itemToPick = findItemByName(command.getSecondWord());
        if(itemToPick == null)
            System.out.println(command.getSecondWord() + " does not exist"); // Wrong item name
        else {  
            if(backpack.contains(itemToPick))
                System.out.println(command.getSecondWord() + " is already in backpack!"); // Item already picken
            else if(itemToPick.getWeight() == 0)
                System.out.println("This item is immovable. You cannot pick the " + itemToPick.getName()); // One of immovable items
            else if(itemToPick.getCurrentRoom() != currentRoom)
                System.out.println(command.getSecondWord() + " is not in the room!"); // Item in different room
            else if (backpackWeight + itemToPick.getWeight() > MAX_BACKPACK_WEIGHT)
                System.out.println(command.getSecondWord() + " is to heavy for the backpack!"); // Item is too heavy for the backpack
            else {
                // Picking the right item
                backpack.add(itemToPick);
                itemToPick.pickItem(backpackRoom);
                backpackWeight += itemToPick.getWeight();
                System.out.println(itemToPick.getName() + " picked!");
            }
        }
    }
    
    /**
     * Method checks if the item we want to take out from the backpack 
     * exists and is in the backpack. Then, takes it out.
     */
    private void putItem(Command command) {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to take out of the backpack
            System.out.println("Put what?");
            return;
        }
        Item itemToPut = findItemByName(command.getSecondWord());
        if(itemToPut == null)
            System.out.println(command.getSecondWord() + " does not exist"); // Name given is wrong
        else {  
            if(!backpack.contains(itemToPut))
                System.out.println(command.getSecondWord() + " is not in the backpack!"); // Item is not in the backpack
            else {
                // Take out the item given 
                backpack.remove(itemToPut);
                itemToPut.putItem(currentRoom);
                backpackWeight -= itemToPut.getWeight();
                System.out.println(itemToPut.getName() + " put in the " + currentRoom.getName());
            }
        }
    }
    
    /**
     * Method shows a choosen property at a given time. Properties are:
     * backpack (items inside), items (loceted in the current room), 
     * characters (loceted in the current room), weight (of the backpack),
     * exits (directions with rooms there).
     */
    private void show(Command command) {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to show
            System.out.println("Show what?");
            return;
        }
        boolean ifAnything = false;
        if(command.getSecondWord().equals("backpack")) {
            if(backpack.size() >= 1) {
                // Show items in a backpack
                System.out.println("Your backpack has: ");
                for(Item item : backpack) {
                    System.out.println("  " + item.getName() + ": " + item.getWeight());
                }
            } else
                System.out.println("Your backpack is empty!");
        } else if (command.getSecondWord().equals("items")) {
            // Check if there are any items in the current room
            for(Item item : items) {
                if(item.getCurrentRoom().equals(currentRoom))
                    ifAnything = true;         
            }
            if(ifAnything) {               
                // Write down all the items and weights in the current room 
                System.out.println("Items here:");         
                for(Item item : items) {
                    if(item.getCurrentRoom().equals(currentRoom)) {
                        if(item.getWeight() == 0)
                            System.out.println("  " + item.getName());
                        else
                            System.out.println("  " + item.getName() + ": " + item.getWeight());
                    }
                }
            }  else
                System.out.println("No items found here!");
        } else if (command.getSecondWord().equals("characters")) {
            ifAnything = false;
            // Check if there are any characters in the current room
            for(Character character : characters) {
                if(character.getCurrentRoom().equals(currentRoom))
                    ifAnything = true;          
            }
            if(ifAnything) {               
                // Write down the characters in the current room
                System.out.println("Characters here:");         
                for(Character character : characters) {
                    if(character.getCurrentRoom().equals(currentRoom)) 
                            System.out.println("  " + character.getName());
                }
            }  else
                System.out.println("No characters found here!");
        } else if (command.getSecondWord().equals("weight"))
            System.out.println("backpack weight: " + backpackWeight);
        else if (command.getSecondWord().equals("exits"))
            currentRoom.showExits();
        else if (command.getSecondWord().equals("map")) {
            // Creating a stream that will collect all items named map in the current room
            List<Item> stream 
              = items.stream()
                .filter((n) -> n.getCurrentRoom() == currentRoom)
                .filter((n) -> n.getName().equals("map"))
                .collect(Collectors.toList());
            // If stream is empty (no map is in the room), don't show it
            if(stream.size() == 0) {
                System.out.println("You ned to find a room with a map");
                return;   
            } 
            showMap();
        } else if(characters.contains(findCharacterByName(command.getSecondWord()))) {
            // Show the current location of a given character
            String name = command.getSecondWord();
            Character characterToDisplay = findCharacterByName(name);   
            Room nextRoom = characterToDisplay.getNextRoom();
            if(nextRoom == null)
                System.out.println(name + " is currently currently in " + characterToDisplay.getCurrentRoom().getName() + " and isn't moving anymore!");
            else 
                System.out.println(name + " is currently in " + characterToDisplay.getCurrentRoom().getName() + " and is heading to " + characterToDisplay.getNextRoom().getName());
        } else // Invalid command
            System.out.println("I cannot show " + command.getSecondWord() + "!");
    }
    
    /**
     * This method moves characters on the map. The invoke of this method
     * requires two parameters: name of the character to move, and the
     * direction where to move it. E.g. 'move chef right'.
     */
    private void move(Command command) {
        if(!command.hasSecondWord()) {
            // Character not specified
            System.out.println("Move who?");
            return;
        }       
        if(!command.hasThirdWord()) {
            // Direction not specified
            System.out.println("Move where?");
            return;
        }
        
        String nameToMove = command.getSecondWord();
        String roomNameToMove = command.getThirdWord();
        Character characterToMove = findCharacterByName(nameToMove);
        Room roomToMove = findRoomByName(roomNameToMove); 
        
        if(characterToMove == null) {
            // Wrong name of the character
            System.out.println(nameToMove + " does not exist!");
            return; 
        } 
        if(characterToMove.getCurrentRoom() != currentRoom) {
            // Character is not in the current room
            System.out.println(nameToMove + " is not in this room!");
            return;
        } 
        if(roomToMove == null) {
            // Room does not exist
            System.out.println("Room " + roomNameToMove + " does not exist!");
            return;
        }
        // Moving the character to the specified room
        characterToMove.changeLocation(roomToMove);
        System.out.println(nameToMove + " moved to " + roomNameToMove);
    }
    
    /**
     * Method is responible for completing special tasks, keeping track 
     * of tasks done and teleporting.
     */
    private void doSomething(Command command) {
        if(!command.hasSecondWord()) {
            // Method requires a specified activity
            System.out.println("Do what?");
            return;
        }
        String secondWord = command.getSecondWord();
        if(secondWord.equals("peel")) 
            checkDoConditions("knife", 3, "kitchenPorter", "peelingRoom", "be in the peelingRoom to peel yourself", "Peeling", "peeled");
        else if(secondWord.equals("wash")) 
            checkDoConditions("water", 1, "cleaner", "cleaningArea", "", "Washing", "washed");
        else if(secondWord.equals("repair")) 
            checkDoConditions("asphalt", 2, "worker", "road", "washed to put the road", "Repairing", "done with the road");
        else if(secondWord.equals("fry")) 
            checkDoConditions("oil", 5, "chef", "cooker", "cut to fry yourself", "Frying", "fried");
        else if(secondWord.equals("cut")) 
            checkDoConditions("blade", 4, "kitchenPorter", "cutter", "peeled to cut yourself", "Cutting", "cut");
        else if(secondWord.equals("teleport"))
            teleport();
        else
            System.out.println("Unknown command!");
    }
           
    /**
     * Method goes to the previously visited room. Executed many times 
     * makes the player come back to the initial room.
     */
    private void back(Command command) {
        if(command.hasSecondWord()) {
            // Command requires only one word
            System.out.println("back what?");
            return;
        }
        // Check if we have visited any room before
        if(roomOrder.size() == 1) {
            System.out.println("This is the first room!");
            return;
        }        
        // Move to the previous room
        Room previousRoom = roomOrder.get(roomOrder.size() - 2);
        currentRoom = previousRoom;
        roomOrder.remove(roomOrder.size() - 1);
        System.out.println("You are " + currentRoom.getPrefix() + " " + currentRoom.getName());
        System.out.println(currentRoom.getLongDescription());
        for(Character character : characters) {
            character.moveInDirection();
        }
    }
    
    //--------------- Supplementary Methods 
    /**
     * Method used in doSomething function. It creates a random value and
     * sets the random room from ArrayList as a currentRoom, simulating
     * teleportation.
     */
    private void teleport() {
        if(!currentRoom.getName().equals("teleport")) {
            // Player is out of the teleporter room
            System.out.println("You need to be in a teleport room!");
            return;
        }
        // Get random index
        Random rand = new Random();
        int roomIndex = rand.nextInt(rooms.size());
        
        // Set random room as a current one
        currentRoom = rooms.get(roomIndex);
        roomOrder.add(currentRoom);
        System.out.println("You are " + currentRoom.getPrefix() + " " + currentRoom.getName());
        System.out.println(currentRoom.getLongDescription());
    }
    
    /**
     * Method used by doSomething function. Befor doing the task it checks
     * if all prvious tasks are done, if the player is in the right room, 
     * if he/she has a neccessary item in the backpack and if the needed
     * character is in the same room.
     */
    private void checkDoConditions(String itemName, int doDoneOrder, String characterName, String roomName, String youNeedToBe, String doingWhat, String didWhat) {
        boolean ifCondition = false;
        // Check if the item neede is in the backpack
        for(Item item : backpack) {
            if(item.getName() == itemName)
                ifCondition = true;
        }
        if(doDone > doDoneOrder) // If the task is already done
            System.out.println("You are already " + didWhat);
        else if(currentRoom.getName() != roomName) // If player is in the wrong room
            System.out.println("I guess we are in a wrong room. Find " + roomName + " instead!");
        else if(doDone < doDoneOrder) // If previous task is not done
            System.out.println("You need to be " + youNeedToBe);
        else if(!ifCondition) // If needed item is not in the backpack
            System.out.println("Firstly, let's find " + itemName + "!");    
        else if(findCharacterByName(characterName).getCurrentRoom() != currentRoom) // If needed character is not in the right room
            System.out.println("We need "+ characterName + "!");
        else {
            // Do the task
            System.out.println(doingWhat + "!");
            doDone++;
        }
    }
    
    /**
     * Method displays map and is invoked after typing show map command 
     * while being in the smae room as map item (workshop, home).
     */
    private void showMap() {
        System.out.println("---------------------------------------------------");
        System.out.println("|                |                |               |");
        System.out.println("|                |                |               |");
        System.out.println("|    CANTEEN     |      HOME         PEELING ROOM |");
        System.out.println("|                |                |               |");
        System.out.println("|                |                |               |");
        System.out.println("-------| |-------------------------------| |-----------------------");
        System.out.println("|                |                |               |               |");
        System.out.println("|                |                |               |               |");
        System.out.println("|     COOKER           CUTTER          KITCHEN         TELEPORT   |");
        System.out.println("|                |                |               |               |");
        System.out.println("|                |                |               |               |");
        if(doDone <= 2) {
            System.out.println("------------------------------------------|-|----------------------");
            System.out.println("|                |                |       |-|     |");
            System.out.println("|                |                |       |-|     |");
            System.out.println("|    WORKSHOP    |  CLEANING AREA |       |-|     |");
            System.out.println("|                |                |       |-|     |");
            System.out.println("|                |                |       |-|     |");
            System.out.println("-------| |---------------| |--------------|-|------");
        } else {
            System.out.println("------------------------------------------| |----------------------");
            System.out.println("|                |                |       | |     |");
            System.out.println("|                |                |       | |     |");
            System.out.println("|    WORKSHOP    |  CLEANING AREA |       | |     |");
            System.out.println("|                |                |       | |     |");
            System.out.println("|                |                |       | |     |");
            System.out.println("-------| |---------------| |--------------| |------");
        }
        System.out.println("|                |                |               |");
        System.out.println("|                |                |               |");
        System.out.println("|    GARAGE             FARM             ROAD     |");
        System.out.println("|                |                |               |");
        System.out.println("|                |                |               |");
        System.out.println("---------------------------------------------------");
        
    }
    
    //--------------- Finding Methods    
    /**
     * Returns character that has a given name
     */
    private Character findCharacterByName(String name) {
        for(Character character : characters) {
            if(character.getName().equals(name))
                return character;
        }
        return null;
    }
    
    /**
     * Returns an object of class Item that has a given 
     * name or null if it didn't find one. 
     */
    private Item findItemByName(String itemName) {
        for(Item item : items) {
            if(item.getName().equals(itemName))
                return item;
        }
        return null;
    }
    
    /** 
     * returns room that has a given name or assigns null
     * value if it does not exist.
     */
    private Room findRoomByName(String name) {
        for(Room room : rooms) {
            if(room.getName().equals(name))
                return room;
        }
        return null;
    }
}