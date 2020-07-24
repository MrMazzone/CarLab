import java.util.Random;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Road;
import info.gridworld.actor.Car;
import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Location;

public class CarRunner {


    public static void main(String[] args)
    {
    	Random rand = new Random(); 
        
    	//CarWorld world = new CarWorld(new BoundedGrid(rand.nextInt(10) + 15,rand.nextInt(10) + 15));
    	//CarWorld world = new CarWorld(new BoundedGrid( 10,10)); Presentation Size
    	
    	CarWorld world = new CarWorld(new BoundedGrid( 19,19)); //Jon Standard size fits with views
        
    	 for(int row = 0, gridRows = world.getGrid().getNumRows() ; row < gridRows;row++) {
        	for(int col = 0, gridCols = world.getGrid().getNumCols(); col < gridCols; col++) {
        		if(row == 0 || col == 0 || row == gridRows-1 || col == gridCols -1 )
        			world.add(new Location(row,col), new Road());
        		if( (row >= 3 && col >= 3 ) && (row <= gridRows-4 && col <= gridCols - 4) )
        			world.add(new Location(row,col), new Road());
        		if( (row >= 4 && col >= 4 ) && (row <= gridRows - 5 && col <= gridCols - 5) ) {
        			Actor a = world.getGrid().get(new Location(row,col));
        			a.removeSelfFromGrid();
        		}
        	}
        	
        }
        for(int i =0; i < rand.nextInt(5)  +1; i++) {
        	world.add(new Location(2,rand.nextInt(world.getGrid().getNumCols()-5) + 3), new Car());
        }
        for(int i =0; i < rand.nextInt(5)  +1; i++) {
        	world.add(new Location(1,rand.nextInt(world.getGrid().getNumCols()-5) + 3), new Car());
        }
        world.add(new Location(1,rand.nextInt(world.getGrid().getNumCols()-5) + 3), new NetworkCar());
        world.show();
    }

}
