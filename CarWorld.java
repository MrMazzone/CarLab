import java.util.ArrayList;
import java.util.Random;
import javax.swing.SwingUtilities;
import info.gridworld.actor.*;
import info.gridworld.grid.*;
import info.gridworld.gui.CarViewGUI;;




public class CarWorld extends ActorWorld{
	private CarViewGUI carView;
	private Random rand;

	public CarWorld(Grid boundedGrid) {
		super(boundedGrid);
		carView = new CarViewGUI(); 
		rand = new Random(); 
	}

	@Override
	public void step() {
		 Grid<Actor> gr = getGrid();
	        ArrayList<Actor> actors = new ArrayList<Actor>();
	        ArrayList<Actor> myCars = new ArrayList<Actor>();
	        ArrayList<Actor> following = new ArrayList<Actor>();
	        for (Location loc : gr.getOccupiedLocations())
	            actors.add(gr.get(loc));

	        for (int i= 0; i < actors.size();i++) 
	        {
	        	Actor a = actors.get(i);
	            if (a.getGrid() == gr && !(a instanceof Rock)) {
   
	            		        if(a instanceof NetworkCar)
	            		        	myCars.add(a);
	            		        else if(gr.get(a.getLocation().getAdjacentLocation(a.getDirection())) instanceof NetworkCar)
	            		        	following.add(a);
	            		        else
	            		        	a.act();

	            	if(a.getGrid() == gr && (a instanceof Car && ((Car) a).getEnder()) || a instanceof NetworkCar && ((NetworkCar) a).getEnder())  {
	            		new Debrise().putSelfInGrid(getGrid(), a.getLocation());
	            	}
	            }
	            if (a instanceof Debrise)
	            	a.act(); 
	        }
	        fixer(myCars);
	        fixer(following);
	        mapUpdate(myCars);

	}

	public void fixer(ArrayList<Actor> acts) {
		for(int i = 0; i < acts.size();i++)
			acts.get(i).act();
	}
	
	
	
	
	public void mapUpdate(ArrayList<Actor> acts) {
		if(acts.size()> 0)
			carView.updateCarView(((NetworkCar) acts.get(0)).getSurrounding());
		else
	        this.add(new Location(1,rand.nextInt(this.getGrid().getNumCols()-5) + 3), new NetworkCar());
	}

}
