import java.awt.Color;
import info.gridworld.actor.Actor;
import info.gridworld.grid.Location;
import info.gridworld.gui.GUIController;

public class NetworkCar  extends Actor
{
	   private boolean ender;
	   private boolean firstMove;
	   private int colorChanger;
	   private Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE};
	   private Object[][] map;
	   private Object [][] surrounding; 
	   private Trainer forward;
	   private Trainer left;
	   private Trainer right;
	   private Trainer turn;
	   private static boolean update;
	   private boolean[] networkOutput = new boolean[4];
	   
	   //The Neural Network Animations
	   private static NetView forwardAnimation;
	   private static NetView leftAnimation;
	   private static NetView rightAnimation;
	   
	   public NetworkCar() {
		   super();
		   ender = false;
		   firstMove = true;
		   colorChanger = 0;
		   makeJaggedArrays();
		   trainNetworks();
	   }  
	   
	   /**
	    * Initializes the jagged arrays for the map and surrounding locations. 
	    */
		private void makeJaggedArrays() {
			this.map = new Location[][]{
			   	   {null,null,null},
				   {null,null,null},
				   {null,null,null},
				   {null}
		   };
		   this.surrounding = new Actor[][] {
			   {null,null,null},
			   {null,null,null},
			   {null,null,null},
			   {null}
		   }; 
	}
		
		/**
		 * Instantiates the training networks.
		 * Two arrays of training data are fed to the networks to train the weights on the networks.
		 * Trainer forward will be used to determine if the car should proceed forward.
		 * Trainer turn can be applied to either a left or right turn informing the car if it is safe to turn. 
		 */
	    private void trainNetworks() {
			this.forward = new Trainer(NetworkCarTrainer.forwardData);
			this.left = new Trainer(NetworkCarTrainer.laneChangeData);
			this.right = new Trainer(NetworkCarTrainer.laneChangeData);
			this.turn = new Trainer(NetworkCarTrainer.turnData); 
	}

	    /**
	     * The method called on each step of the grid. 
	     */
		public void act()
	    {
	    	 if (getGrid() == null)
		            return;
	    	if(this.firstMove) 
	    			firstRun();
	    	flasher();
	    	boolean [] dirs = setDirs(); 
	    	Location next = getLocation().getAdjacentLocation(getDirection());
	    	if(!dirs[3]) {
	    		turn();
	    		next = getLocation().getAdjacentLocation(getDirection());
	    	}
	    	else if(!dirs[1]) {
	    	}
	    	else if(!dirs[0]) {
	    		next = this.getLocation().getAdjacentLocation(getDirection() - 45);
	    	}
	    	else if(!dirs[2]) {
	    		next =this.getLocation().getAdjacentLocation(getDirection() + 45);
	    	}
	    	else {
	    		next = getLocation(); 
	    	}
	    	if(getGrid().isValid(next)) {
		    	Actor a = getGrid().get(next); 
		    	if(a == null) {
		    		moveTo(next);
		    	}
		    	else if(a.equals(this)) {
		    	}
		    	else
		    		ender = true;
	    	}
	    	else 
	    		ender = true;  
	    	populateMap();
	    	populateSurrounder();
	    }
		
	    /**
	     * Create an array of neural network outputs.
	     * The index corresponds with a possible decision for the network. 
	     * @return boolean array of neural network decisions. [left, right, stop, turn, forward] 
	     */
	    private boolean[] setDirs() {
	    	populateMap();
	    	populateSurrounder();
	    	 
	    	int[] locs = setUpLocs(surrounding);  
	    	int[] leftDir = new int[3];
	    	int[] forwardDir = new int[3];
	    	int[] rightDir = new int[3];
	    	int[] turnDir = new int[9];
	    	for(int i = 0, index =0; i < locs.length-1; i++) {
	    		if(i < 3) {
	    			leftDir[index] = locs[i];
	    		}
	    		else if( i < 6) {
	    			forwardDir[index] = locs[i];
	    		}
	    		else {
	    			rightDir[index] = locs[i];
	    		}
	    		turnDir[i] = locs[i]; 
	    		index++;
	    		if(index == 3) {
	    			index = 0;
	    		}
	    	}
	    	networkOutput[0] = left.useTrainer(leftDir);
	    	networkOutput[1] = forward.useTrainer(forwardDir);
	    	networkOutput[2] = right.useTrainer(rightDir);
	    	networkOutput[3] = turn.useTrainer(turnDir);
	    	
	    	//CAllS TO ANIMATE
	    	leftTrainAnimation(leftDir);
	    	forwardTrainAnimation(forwardDir);
	    	rightTrainAnimation(rightDir);
	    	
	    	return networkOutput;
		}
	    
		public void populateSurrounder() {
	    	for(int i = 0; i < map.length; i++)
	    		for (int z = 0; z < map[i].length; z++)
	    			if(getGrid().isValid((Location)map[i][z]))
	    				surrounding[i][z]= getGrid().get((Location)map[i][z]);
	    			else {
	    				surrounding[i][z] = new Actor();
	    			}
	    }
		
	    /**
	     * Figures out if the vehicle has crashed.
	     * @return Should the vehicle be replaced with a crash.
	     */
	    public boolean getEnder() {
	    	return this.ender;
	    }
	    
	    /**
	     * sets the direction to east on first run.
	     */
	    private void firstRun() {
	    	this.firstMove = false;
		    this.setDirection(Location.EAST);
	   }
	    
	    /**
	     * Changes the car color every turn. 
	     */	
	    private void flasher() {
	    	colorChanger++;
	    	if(colorChanger == colors.length)
	    		colorChanger = 0;
		    this.setColor(colors[colorChanger]);
	    }
	    
	    /**
	     * turns the car right.
	     */
	    public void turn()
	    {
	        setDirection(getDirection() + Location.RIGHT );
	    }
	    
	    /**
	     * Populates an array of Locations
	     * @param first The start location
	     * @return An array containing the start location and the two locations in front of it. 
	     */
	    public Location[] directionLocs(Location first) {
	    	Location[] locs = new Location[3];
	    	Location temp = first;
	    	for(int i = 0; i < locs.length; i++) {
	    		locs[i] = temp;
	    		temp = temp.getAdjacentLocation(getDirection());
	    	}
	    	return locs; 
	    }
	    
	    /**
	     * Used to get a matrix of locations.
	     * The order is (Left, Forward, Right, Backward)
	     * Note: only the first value in backward is useful. 
	     * @return Location matrix around the car.
	     */
	    public Object[][] getMap(){
	    	return this.map;
	    }
	    
	    /**
	     * populates the map matrix with the Location objects that the car uses for predictive behavior.
	     */
	    public void populateMap() {
	    	map[0] = directionLocs(this.getLocation().getAdjacentLocation(getDirection() - 90));
	    	map[1] = directionLocs(this.getLocation().getAdjacentLocation(getDirection()));
	    	map[2] = directionLocs(this.getLocation().getAdjacentLocation(getDirection() + 90));
	    	map[3][0] = this.getLocation().getAdjacentLocation(getDirection() - 180);
	    }    
	 
	    public int[] setUpLocs(Object[][] objects) {
	    	int[] data = new int[10]; 
			for(int i = 0, index = 0; i < objects.length; i++) {
				for(int z = 0; z < objects[i].length;z++, index++) {
					if(objects[i][z] != null) {
						data[index] = 1;
					}
					else {
						data[index] = 0; 
					}
				}
			}
			return data;
		}
	    
	    public Object[][] getSurrounding() {
	    	return this.surrounding; 
	    }
	    
	    //JONS STUFF
	    static public void displayAnimationWindow()
		   {
			   if(GUIController.disNet)
		        {
				   leftAnimation = new NetView ("Left",555, 77);
				   forwardAnimation = new NetView ("Forward",805, 277);  
				   rightAnimation = new NetView ("Right",555, 477);
				   update = true; //only allow update of colors if object has been initialized
		        }
			  
		   }
	    
	    private void leftTrainAnimation(int [] leftDir)
	    {
	    	if(update)
	    	{
	          leftAnimation.updateColors(left.getWeightOne(),left.getWeightTwo(),left.getWeightThree(),networkOutput[0],GUIController.disNet);
	    	}
	    }
	    
	    private void forwardTrainAnimation(int [] forwardDir)
	    {
	    	if(update)
	    	{
	          forwardAnimation.updateColors(forward.getWeightOne(),forward.getWeightTwo(),forward.getWeightThree(),networkOutput[1],GUIController.disNet);
	    	}
	    }
	    
	    private void rightTrainAnimation(int [] rightDir)
	    {
	    	if(update)
	    	{
	          rightAnimation.updateColors(right.getWeightOne(),right.getWeightTwo(),right.getWeightThree(),networkOutput[2],GUIController.disNet);
	    	}
	    }
	  
}