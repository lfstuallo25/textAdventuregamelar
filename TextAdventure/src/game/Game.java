package game;


public class Game
{

    private Parcer parser;
    private Location freezeA;
    private boolean AttackLogEvent = false; // these are my booleans for whether not certain events have occurred.
    private boolean fixed = false; // fixing time machine
    
    private boolean RunState = false; // for when volcano is about to explode
    private Location VolcanoGate;
    private Player player; 
    private int deathWait = 2;
    private Location currentLocation;
    private boolean EndReady = false; // game's check for if we have a way to get in the volcano or not.
    private boolean InDanger = false;
    private String currentArea;
    private boolean canFreeze = false;
    private CLS cls_var;
    private int freezeCharge = 0;
    public Game()
    {
        parser = new Parcer();
        player = new Player();
    }

    public static void main(String[] arg){
        Game game = new Game();
        game.setUpGame();
        game.play();
    }

    public void printInformation(){
        System.out.println(currentLocation.getExitString());
        System.out.println(currentLocation.getShortDescription());
        System.out.println(currentLocation.getInventoryString());
        System.out.println(player.getInventoryString());

        System.out.println("freeze gun charge: " + freezeCharge);
    }

    public void setUpGame() {

        Location startingLocation = new Location(" This is where your time machine landed, albeit damaged.", "This is a small, lightly wooded area. Your time machine lies here in a mess, and your toolbox is nowhere to be found. High pitched squeaking is heard from the east, and nothing is heard from the west.", false, false);
        Location shallowLake = new Location("this is a shallow lake. some lystrosaurues are slowly shufflling around.", "Its a shallow lake, about 2ft deep. Herbivorous Lystrosauruses can be seen walking around. They are about the size of a small dog.", true, false);
        Location postCliff = new Location("you see the freeze ray and your tool box not far at the cliff base. However, several sleeping postsuchus are also present. Best not to wake them.", "you see the freeze ray and your tool box not far at the cliff base. However, several sleeping postsuchus are also present. Best not to wake them.", true,true);
        Location CliffTop = new Location(" This is the top of the cliff. Some calm postosuchuses are present, with a old tree on one edge of the cliff.", "At the top of the steep cliff, you see some postosuchuses, however these 3.5 meter long carnivore seem to have been calmed down by you. A very old and tall tree is at the edge of the cliff, and the postosuchus look at you funny when you go near it. ", true,false);
        Location Volcano = new Location("There it is, the volcano. However, a shiringasaurus is also right there as well.", "You've reached the main objective, to freeze this volcano so it doesen't go super in the future. Hope you brought your freezeray.", true, true);
        Location HeavyForest = new Location(" This is a small clearing, with a giant tree log blocking the path to the volcano. A lisowacia is also there, but sleeping.", "e", false, false);
        Item freezeGun = new Item("your freeze gun", "long description");   
        Item rockDepositChunk = new Item("large chunk of pumice rock that floats.", "long description");   
        Item tools = new Item("a toolbox", "long description");
        Item lystrosaurus = new Item("the lystrosaurus you grabbed", "long description");  
        startingLocation.SetExit("shallow lake", shallowLake);
        startingLocation.SetExit("cliff edge", postCliff);
        postCliff.SetExit("climb up", CliffTop);
        postCliff.SetExit("start location", startingLocation);
        shallowLake.SetExit("forest area", HeavyForest);
        shallowLake.SetExit("start location", startingLocation);
        VolcanoGate = Volcano;
        CliffTop.SetExit("climb up", postCliff); // weird but functional
        CliffTop.SetExit("use log", HeavyForest);        
        HeavyForest.SetExit("shallow lake", shallowLake);
        currentLocation = startingLocation;
        HeavyForest.SetExit("volcano path", Volcano);
        shallowLake.setItem("lystrosaurus", lystrosaurus);
        postCliff.setItem("freezegun", freezeGun);
        postCliff.setItem("PumiceChunk", rockDepositChunk);
        postCliff.setItem("toolbox", tools);
        currentArea = "start location";
        freezeA = CliffTop;
        try {
            cls_var.main(); 
        }catch(Exception e) {
            System.out.println(e); 
        }
        printInformation();
        play();
    }

    public void play(){
        System.out.println("Note: some items work differently in this game. Simply type a action the item will use, and it will do whatever task was needed for the room.");
        System.out.println("Also, you die in this game by remaning in a dangerous area for more then 2 commands. It resets when you leave.");
        while(true){

            Command command = parser.getCommand();
            try {
                cls_var.main(); 
            }catch(Exception e) {
                System.out.println(e); 
            }
            processCommand(command);
        }

    }

    public void processCommand(Command command){
        String commandWord = command.getCommandWord().toLowerCase();
        switch(commandWord) {

            case "speak":
                System.out.println("you wanted me to say this word: " + command.getSecWord());
                 if(deathWait <= 0){
                try {
                    cls_var.main(); 
                }catch(Exception e) {
                    System.out.println(e); 
                }
                System.out.println("Unfortunately you died, probably because you didn't notice the aggresive creature right next to you. Restart the game.");                
            }
            else if(currentLocation.getDanger() == true){
                System.out.println("You are in a lot of danger here. Either solve the problem or leave.");
                deathWait -= 1;
            }   else{
                deathWait = 2;

            }

                break;   
            case "info":
                System.out.println(currentLocation.getLongDescription());
                System.out.println("hope you got that.");
                 if(deathWait <= 0){
                try {
                    cls_var.main(); 
                }catch(Exception e) {
                    System.out.println(e); 
                }
                System.out.println("Unfortunately you died, probably because you didn't notice the aggresive creature right next to you. Restart the game.");                
            }
            else if(currentLocation.getDanger() == true){
                System.out.println("You are in a lot of danger here. Either solve the problem or leave.");
                deathWait -= 1;
            }   else{
                deathWait = 2;

            }

                break;   
            case "freeze":

                freezeRoom(command);
                 if(deathWait <= 0){
                try {
                    cls_var.main(); 
                }catch(Exception e) {
                    System.out.println(e); 
                }
                System.out.println("Unfortunately you died, probably because you didn't notice the aggresive creature right next to you. Restart the game.");                
            }
            else if(currentLocation.getDanger() == true){
                System.out.println("You are in a lot of danger here. Either solve the problem or leave.");
                deathWait -= 1;
            }   else{
                deathWait = 2;

            }

                break;
            case "feed":
                feed();
                 if(deathWait <= 0){
                try {
                    cls_var.main(); 
                }catch(Exception e) {
                    System.out.println(e); 
                }
                System.out.println("Unfortunately you died, probably because you didn't notice the aggresive creature right next to you. Restart the game.");                
            }
            else if(currentLocation.getDanger() == true){
                System.out.println("You are in a lot of danger here. Either solve the problem or leave.");
                deathWait -= 1;
            }   else{
                deathWait = 2;

            }
            case "fix":
                fix(command);
                if(deathWait <= 0){
                    try {
                        cls_var.main(); 
                    }catch(Exception e) {
                        System.out.println(e); 
                    }
                    System.out.println("Unfortunately you died, probably because you didn't notice the aggresive creature right next to you. Restart the game.");                
                }
                else if(currentLocation.getDanger() == true){
                    System.out.println("You are in a lot of danger here. Either solve the problem or leave.");
                    deathWait -= 1;
                }   else{
                    deathWait = 2;

                }

                break;
            case "go":
                goRoom(command);
                break;
           
            case "inv":
                player.getInventoryString();
                 if(deathWait <= 0){
                try {
                    cls_var.main(); 
                }catch(Exception e) {
                    System.out.println(e); 
                }
                System.out.println("Unfortunately you died, probably because you didn't notice the aggresive creature right next to you. Restart the game.");                
            }
            else if(currentLocation.getDanger() == true){
                System.out.println("You are in a lot of danger here. Either solve the problem or leave.");
                deathWait -= 1;
            }   else{
                deathWait = 2;

            }

                break;
            case "grab":
                grab(command);
                 if(deathWait <= 0){
                try {
                    cls_var.main(); 
                }catch(Exception e) {
                    System.out.println(e); 
                }
                System.out.println("Unfortunately you died, probably because you didn't notice the aggresive creature right next to you. Restart the game.");                
            }
            else if(currentLocation.getDanger() == true){
                System.out.println("You are in a lot of danger here. Either solve the problem or leave.");
                deathWait -= 1;
            }   else{
                deathWait = 2;

            }

                break;
            case "drop":
                drop(command);
                 if(deathWait <= 0){
                try {
                    cls_var.main(); 
                }catch(Exception e) {
                    System.out.println(e); 
                }
                System.out.println("Unfortunately you died, probably because you didn't notice the aggresive creature right next to you. Restart the game.");                
            }
            else if(currentLocation.getDanger() == true){
                System.out.println("You are in a lot of danger here. Either solve the problem or leave.");
                deathWait -= 1;
            }   else{
                deathWait = 2;

            }

            break;

        }
       
    }
    public void fix(Command command){
        
         Location currentRoom = currentLocation;
         String brokenThing = command.getSecWord();

        if(brokenThing == null || fixed == true){
            System.out.println("fix what idiot");
            if(player.getItem("toolbox") == null){
                System.out.println("futhermore, you don't even have your toolbox.");
            }
        } else{
           

            switch(brokenThing) { // the exit is used instead of the actual location leaving some errors possible...

                case "timeMachine":
                	if(currentArea.equals("start location")) {
                		System.out.println("Quickly, you reconnect the battery to the warp drive, and tighten the bolts on the shield generator. Now you can go back when you need to.");
                        fixed = true;
                                       	}
                	 else {
                     	
                 		System.out.println("you are not near the time machine.");

                     }

                    break;  
                case "PumiceChunk":
                	if(player.getItem("PumiceChunk")) {
                		System.out.println("Quickly, you reconnect the battery to the warp drive, and tighten the bolts on the shield generator. Now you can go back when you need to.");
                        fixed = true;
                                       	}
                	 else {
                     	
                 		System.out.println("you are not near the time machine.");

                     }

                    break;  
            }
        }
        
    }
    public void freezeRoom(Command command){
    	
        Location currentRoom = currentLocation;
        if(canFreeze == false){
            System.out.println("you don't even have your freezegun.");
        }	
        if(currentRoom == null || currentRoom.getFreeze() == false && freezeCharge > 0){
            System.out.println("There is nothing of value that needs to be freezed.");
            
        } else if (freezeCharge > 0){
            currentRoom.Freeze();

            printInformation();
            switch(currentArea) { // the exit is used instead of the actual location leaving some errors possible...

                case "start location":
                    System.out.println("Don't freeze your time machine..");
                    break;  
                case "shallow lake":
                    System.out.println("With a quick blast, the lake is frozen over. You probably just wasted a shot.");
                    freezeCharge -= 1;     
                    break;
                case "cliff edge":
                    System.out.println("you point it straight at the postosuchus, but hold off when you realize that there are 5 others.");

                    break;   
                case "climb up":
                    System.out.println("You shoot the tree. It freezes and slowly tips over, creating a log bridge.");
                    freezeCharge -= 1; 
                    break; 
                case "volcano path":
                    System.out.println("The volcano slowly freezes over, and seems to calm down... but starts to rumble. GET OUT OF THERE");
                    RunState = true;
                    freezeCharge -= 1; 
                    break; 

            }
        }
    }

    public void feed(){
        Location currentRoom = currentLocation;
        if(player.getItem("lystrosaurus") == null)
        {
            System.out.println("You don't have something for a animal to eat.");
        } else{

            printInformation();
            switch(currentArea) {// the exit is used instead of the actual location leaving some errors possible...

                case "start location":
                    System.out.println("Nothing looks hungry here.");
                    break;  
                case "shallow lake":
                    System.out.println("You can't feed a lystrosaurus lystrosaurus!");
                    freezeCharge -= 1;     

                    break;
                case "cliff edge":
                    System.out.println("you put the lystrosaurus down and within secounds the postosuchuses tear it to shreds. you are not only a terrible person, but you have also made the postosuchuses calm down.");
                    player.removeItem("lystrosaurus");  
                    break;   
                case "climb up":
                    System.out.println("Cheater.");
                    player.removeItem("lystrosaurus");  
                    break; 
                case "volcano path":
                    System.out.println("Cheater.");
                    player.removeItem("lystrosaurus");  
                    break; 
                case "forest area":
                    System.out.println("That giant thing is a herbivore idiot");
                    player.removeItem("lystrosaurus");  
                    break; 

            }
        }
    }

    public void grab(Command command){
        if(!command.ifsecWord()){
            System.out.println("specify what to grab");

        }
        String item = command.getSecWord();
        Item nextItem = currentLocation.removeItem(item);
        if(nextItem == null){
            System.out.println("you cannot grab it.");

        } else{
            if (item.equals("freezegun")){
                freezeCharge = 2;
                System.out.println("freeze gun grabbed and fully charged"); 

            }
            player.setItem(item, nextItem);

            printInformation();
        }

    }

    public void drop(Command command){
        if(!command.ifsecWord()){
            System.out.println("specify what to drop");
        }
        String item = command.getSecWord();
        Item nextItem = player.removeItem(item);
        if(nextItem == null){
            System.out.println("there is nothing to drop.");

        } else{

            currentLocation.setItem(item, nextItem);
            printInformation();
        }

    }

    public void goRoom(Command command){
        if(!command.ifsecWord()){
            System.out.println("go WHERE?!");
        }
        String direction = command.getSecWord()+command.getline();
        currentArea = command.getSecWord() + "" + command.getline();

        Location nextRoom = currentLocation.getExit(direction);

        if(direction.equals("use log") && currentLocation == freezeA && currentLocation.getFreeze() == true){
            nextRoom = null;    
            System.out.print("The log is standing tall, and jumping would not work. Needless to say, ");
        }
         if(direction.equals("volcano path") && currentLocation == VolcanoGate && AttackLogEvent == false){
            nextRoom = null;    
            System.out.print("That is blocked by a heavy log.");
        }
        if(nextRoom == null){
            System.out.println("you cannot go there.");

        } else{
            if(RunState = true){
                currentLocation.setDanger(true); // we are running away from the volcano
            }
            currentLocation = nextRoom;
            if(direction.equals("use log")){
                AttackLogEvent = true;
                System.out.println("Before you go across the log, a ton of the postosuchuses storm across the log you created. A loud noise also is heard from directly ahead.");
                currentLocation.setShortDescription("This is crazy town, as there are postosuchuses attacking the lisowacia as a group. In the chaos, the large log blocking the volcano has been removed.");
            }
            printInformation();
        }
    }
}
