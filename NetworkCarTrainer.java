public class NetworkCarTrainer {
	

	 
	public static int[][][] forwardData =    // 1 = don't go forward 
		{
			{{0,0,0},{0}},
			{{0,0,1},{0}},
			{{0,1,1},{1}},
			{{1,0,0},{1}}
		};
	
	public static int[][][] laneChangeData =   //  1  = Don't change lanes this way
		{
			{{0,0,0},{0}}
		};
	
	//9 inputs either 1 or 0 => 2^9 = 512
	public static int[][][] turnData =    // 0 = turn    
		{
			{{0,0,1,0,1,1,0,0,1},{0}},
			{{1,1,1,0,1,1,0,0,1},{0}},
			{{1,1,1,1,1,1,1,1,1},{1}}
		};
}
