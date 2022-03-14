package game;


public class Game
{

    private Parcer parser;
    private Location freezeA;
    private boolean AttackLogEvent = false; // these are my booleans for whether not certain events have occurred.
    private boolean Timefixed = false; // fixing time machine
    private boolean HappyPostosuchusEvent = false; // for when the dangerous animals have calmed down and you are able to progress further.
    private boolean RunState = false; // for when volcano is about to explode. sets a location to dangerous as soon as you enter it.
    private Location VolcanoGate; // the other locked door.
    private Player player; // you
    private int deathWait = 2; // if this reaches zero(usually due to wasted commands), you will die.
    private Location currentLocation; // very important variable that tells us the current location.
    private Location feedLocation; // helps with displaying certain locked things correctly
    private Location endLocation; // where to end the game
    private String currentArea; // current area, via exit string
    private boolean canFreeze = false; // whether or not we have a freeze gun
    private CLS cls_var; // copied stuff
    private int freezeCharge = 0; // charge of the freeze ray. you have 2 charges that can be used on 4 different targets.
    private boolean gaming = true; // determines if we are playing or not
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
        if(canFreeze == true){
        	System.out.println("freeze gun charge: " + freezeCharge);
        }
        
        if(currentLocation.getDanger() == true) {
        	System.out.println("Commands until death(not including movement) = " + deathWait);
        }
        
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
        Item tools = new Item("a toolbox", "red toolbox with lots of cool stuff.");
        Item lystrosaurus = new Item("the lystrosaurus you grabbed", "A small animal from the Trissaic period. It was one of the only animals to survive the Permian extinction.");  
        startingLocation.SetExit("shallow lake", shallowLake);
        startingLocation.SetExit("cliff edge", postCliff);
        postCliff.SetExit("climb up", CliffTop);
        postCliff.SetExit("start location", startingLocation);
        shallowLake.SetExit("forest area", HeavyForest);
        shallowLake.SetExit("start location", startingLocation);
        VolcanoGate = HeavyForest;
        CliffTop.SetExit("climb up", postCliff); // weird but functional
        CliffTop.SetExit("use log", HeavyForest);        
        HeavyForest.SetExit("shallow lake", shallowLake);
        currentLocation = startingLocation;
        feedLocation = postCliff;
        endLocation = startingLocation;
        HeavyForest.SetExit("volcano path", Volcano);
        Volcano.SetExit("volcano path", HeavyForest);
        shallowLake.setItem("lystrosaurus", lystrosaurus);
        postCliff.setItem("freezegun", freezeGun);
        HeavyForest.setItem("PumiceChunk", rockDepositChunk);
        postCliff.setItem("toolbox", tools);
        currentArea = "start location";
        freezeA = CliffTop;
        try {
            cls_var.main(); 
        }catch(Exception e) {
            System.out.println(e); 
        }
       
        play();
      
    }

    public void play(){
    	if(gaming == true) {
    		System.out.println("Welcome to..");
        	System.out.print("\r\n"
        			+ "   _____                                  _                       \r\n"
        			+ "  / ____|                                | |                      \r\n"
        			+ " | (___  _   _ _ __   ___ _ ____   _____ | | ___ __ _ _ __   ___  \r\n"
        			+ "  \\___ \\| | | | '_ \\ / _ \\ '__\\ \\ / / _ \\| |/ __/ _` | '_ \\ / _ \\ \r\n"
        			+ "  ____) | |_| | |_) |  __/ |   \\ V / (_) | | (_| (_| | | | | (_) |\r\n"
        			+ " |_____/ \\__,_| .__/ \\___|_|    \\_/ \\___/|_|\\___\\__,_|_| |_|\\___/ \r\n"
        			+ "              | |                                                 \r\n"
        			+ "              |_|                                                 \r\n"
        			+ "");
        	System.out.println("Ok so the year is 2050 and a supervolcano is on the brink of erupting. luckily the government has a time machine, so they sent you back in time to destroy the volcano. HOWEVER, the time period is the Trissaic period, a strange time after trilobites and giant bugs but before dinosaurs.");
            System.out.println("Note: some items(freezegun) work differently in this game. Simply type a action the item will use, and it will do whatever task was needed for the room.");
            
            System.out.println("Also, you die in this game by remaning in a dangerous area for more then 2 commands(including typos). It resets when you leave. Now onto the game.");
            printInformation();
    		
    	}
    
        while(gaming == true){

            Command command = parser.getCommand();
            try {
                cls_var.main(); 
            }catch(Exception e) {
                System.out.println(e); 
            }
            processCommand(command);
        }
        return;

    }

    public void processCommand(Command command){
        String commandWord = command.getCommandWord().toLowerCase();
        if(commandWord != null && command.getCommandWord() != null) {
        	
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
                gaming = false;
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
                gaming = false;
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
                gaming = false;
            }
            else if(currentLocation.getDanger() == true){
                System.out.println("You are in a lot of danger here. Either solve the problem or leave.");
                deathWait -= 1;
            }   else{
                deathWait = 2;

            }
                 
                break;
            case "larsenssecretdebugcommand":
            	
            	System.out.println("debug activated");
            	deathWait = 999;
        		System.out.println("warping away now!");
        		System.out.println("........");
        		System.out.println("Mission accomplished! You were able to freeze the volcano and cause it to erupt prematurely.");
        		gaming = false;
            	break;
            case "larsenssecretdeathdebugcommand":
            	   try {
                       cls_var.main(); 
                   }catch(Exception e) {
                       System.out.println(e); 
                   }
                   System.out.println("you died because you are debugging the game.");         
                   gaming = false;
            	break;
            case "feed":
                feed(command);
                 if(deathWait <= 0){
                try {
                    cls_var.main(); 
                }catch(Exception e) {
                    System.out.println(e); 
                }
                System.out.println("Unfortunately you died, probably because you didn't notice the aggresive creature right next to you. Restart the game.");         
                gaming = false;
            }
            else if(currentLocation.getDanger() == true){
                System.out.println("You are in a lot of danger here. Either solve the problem or leave.");
                deathWait -= 1;
            }   else{
                deathWait = 2;

            }
                 break;
            case "help":
                help(command);
                
                 break;
            case "fix":
            	
                fix(command);
                if(deathWait <= 0){
                    try {
                        cls_var.main(); 
                    }catch(Exception e) {
                        System.out.println(e); 
                    }
                    System.out.println("Unfortunately you died, probably because you didn't notice the aggresive creature right next to you. Restart the game.");  
                    gaming = false;
                }
                else if(currentLocation.getDanger() == true){
                    System.out.println("You are in a lot of danger here. Either solve the problem or leave.");
                    deathWait -= 1;
                }   else{
                    deathWait = 2;

                }


                break;
       
        
            case "warp":
            	if(currentLocation == endLocation && Timefixed == true && RunState == true) {
            		deathWait = 999;
            		System.out.println("warping away now!");
            		System.out.println("........");
            		System.out.println("Mission accomplished! You were able to freeze the volcano and cause it to erupt prematurely.");
            		gaming = false;
            	} else {
            		
            		System.out.println("Warp failed. Try fixing your time machine or being in the same location as it.");
            	}
            	if(deathWait <= 0){
                    try {
                        cls_var.main(); 
                    }catch(Exception e) {
                        System.out.println(e); 
                    }
                    System.out.println("Unfortunately you died, probably because you didn't notice the aggresive creature right next to you. Restart the game.");           
                    gaming = false;
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
            	
            case "inv":
                player.getInventoryString();
                 if(deathWait <= 0){
                try {
                    cls_var.main(); 
                }catch(Exception e) {
                    System.out.println(e); 
                }
                System.out.println("Unfortunately you died, probably because you didn't notice the aggresive creature right next to you. Restart the game.");          
                gaming = false;
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
                gaming = false;
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
                gaming = false;
            }
            else if(currentLocation.getDanger() == true){
                System.out.println("You are in a lot of danger here. Either solve the problem or leave.");
                deathWait -= 1;
            }   else{
                deathWait = 2;

            }

            break;

            default:
             	 
            	System.out.println("Not a recognized command.");
            	System.out.println("Need help? type help cmds and press enter");
           	 break;
        	}
        }
       
    }
    public void fix(Command command){
        
         Location currentRoom = currentLocation;
         String brokenThing = command.getSecWord();

        if(brokenThing == null){
            System.out.println("fix what idiot");
            if(player.getItem("toolbox") == null){
                System.out.println("futhermore, you don't even have your toolbox.");
            }
        } else{
           

            switch(brokenThing) { // the item is now is used instead of the actual location leaving some errors possible...

                case "timeMachine":
                	if(Timefixed == false) {
                		if(currentArea.equals("start location")) {
                    		System.out.println("Quickly, you reconnect the battery to the warp drive, and tighten the bolts on the shield generator. Now you can go back when you need to.");
                    		Timefixed = true;
                                           	}
                    	 else {
                         	
                     		System.out.println("you are not near the time machine.");

                         }
                	}
                	

                    break;  
                case "PumiceChunk":
                	if(player.getItem("PumiceChunk") != null) {
                		System.out.println("Utilizing the angle grinder, you cut the pumice rock into a large circle that is big enough for you to stand on, but easy enough to carry");
                      
                        player.removeItem("PumiceChunk");
                        Item CarvedPumice = new Item("A carved piece of pumice large enough to stand on", "long description");  
                       player.setItem("CarvedPumice", CarvedPumice);             
                	}
                	 else {
                     	
                 		System.out.println("You don't have one of those.");

                     }

                    break;  
            }
        }
        
    }
    public void freezeRoom(Command command){
    	
        Location currentRoom = currentLocation;
        if(canFreeze == false){
            System.out.println("you don't even have your freezegun.");
            return;
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
                	if(player.getItem("CarvedPumice") == null) {
                		 System.out.println("You are unable to freeze the volcano from that distance. You need to somehow get in the middle.");
                        
                		return;
                	}
               	 	System.out.println("The volcano slowly freezes over, and seems to calm down... but starts to rumble. GET OUT OF THERE");
                	RunState = true;
                    freezeCharge -= 1; 
                    break; 

            }
        }
    }

    public void feed(Command command){
    	   String hungryThing = command.getSecWord();

           if(hungryThing == null){
               System.out.println("feed what");
           }
               else if(player.getItem("lystrosaurus") == null)
               {
            	   
            System.out.println("You don't have something for a animal to eat.");
        } else{
        	
            
            switch(hungryThing) {// the exit is used instead of the actual location leaving some errors possible...

                case "postosuchus":
                	if(currentArea.equals("cliff edge") && HappyPostosuchusEvent == false) {
                		 System.out.println("you feed the postosuchuses. they are happy now");
                		 HappyPostosuchusEvent = true;
                		 player.removeItem("lystrosaurus");
                		 currentLocation.setDanger(false);	
                		 deathWait = 2;
                	}
                    break;  
                case "lystrosaurus":
                	
                	System.out.println("bruh");
                    break;  
                default:
                	 System.out.println("Cant feed that.");
                	break;
              
                	

            }
        }
        
            
        }
    
    public void help(Command command){
        
        
        String subject = command.getSecWord();
        if(subject == null) {
    		System.out.println("help with what? (try the word danger or items).");

    		System.out.println("hint: use the word go to go to another location(the location is always 2 words). Most other commands are what you would expect(fix for toolbox, freeze for freezegun, feed for the lystro)");

        }
        else {
        	switch(subject) {
        	case "cmds":
        		System.out.println("feed, freeze, go, help, grab, drop(not going to be used), help, warp, fix");
                break; 
        	case "danger":
        		System.out.println("There is a way to avoid all the dangerous objects in the game. You die after wasting more then 3 turns in a room.");
                break;  
        	case "items":
        		System.out.println("4 items exist in this game. You need all of them to win.");
                break;  
        	case "toolbox":
        		System.out.println("This can fix anything, but it could probably shape something as well.");
                break; 
        	case "freezegun":
        		System.out.println("A freeze gun of this type causes frozen objects to lose their tensile strength.");
                break; 
        	case "location":
        		switch(currentArea) { // the exit is used instead of the actual location leaving some errors possible...

                case "start location":
                    System.out.println("You can go back home once you fixed your time machine.");
                    break;  
                case "shallow lake":
                    System.out.println("Those small lizards look like they would be food for any large animal.");
                    break;
                case "cliff edge":
                    System.out.println("Like all animals, these postosuchuses near the cliff are angry when they are hungry.");

                    break;   
                case "climb up":
                    System.out.println("The tree is old enough that a rapid change of energy could knock it down.");
                    break; 
                case "volcano path":
                	
               	 	System.out.println("To be able to freeze the volcano, you need to be in the center of the volcano. Most floatation devices burn, but is there a sort of floating rock you can use?");
                	
                    break; 
                
                
                default:
                  	 
                	System.out.println("I do not know what this is, and cannot help you with it.");
               	 break;
        		}
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
                freezeCharge = 3;
                System.out.println("freeze gun grabbed and fully charged"); 
                canFreeze = true;
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
       

        Location nextRoom = currentLocation.getExit(direction);

        if(direction.equals("use log") && currentLocation == freezeA && currentLocation.getFreeze() == true){
        	nextRoom = currentLocation;  
            System.out.print("The log is standing tall, and jumping would not work. Needless to say, you can't go there.");
           
        }
        else if(direction.equals("volcano path") && currentLocation == VolcanoGate && AttackLogEvent == false){
        	nextRoom = currentLocation;   
            System.out.print("That is blocked by a heavy log.");
            
        }
        else if(direction.equals("climb up") && currentLocation == feedLocation && HappyPostosuchusEvent == false){
             nextRoom = currentLocation;    
             System.out.print("It's too dangerous to climb up.");
             deathWait -= 1;
             System.out.println(deathWait);
             if(deathWait <= 0){
                 try {
                     cls_var.main(); 
                 }catch(Exception e) {
                     System.out.println(e); 
                 }
                 System.out.println("Unfortunately you died, probably because you didn't notice the aggresive creature right next to you. Restart the game.");            
                 gaming = false;
             }
         }
        else if(nextRoom == null){
            System.out.println("you cannot go there.");
            nextRoom = currentLocation; 
            
        } else{
            if(RunState == true){
                currentLocation.setDanger(true); // we are running away from the volcano
                System.out.println("Keep running, the lava is right behind you!");
                
            }
            currentArea = command.getSecWord() + "" + command.getline();
            currentLocation = nextRoom;
            deathWait = 2;
            if(direction.equals("use log") || (direction.equals("forest area") && AttackLogEvent == true)){
                AttackLogEvent = true;
                System.out.println("Before you go across the log, a ton of the postosuchuses storm across the log you created. A loud noise also is heard from directly ahead.");
                currentLocation.setShortDescription("This is crazy town, as there are postosuchuses attacking the lisowacia as a group. In the chaos, the large log blocking the volcano has been removed.");
                currentLocation.setDanger(true);
            }
            printInformation();
        }
    }
}
