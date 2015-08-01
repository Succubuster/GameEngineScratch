package window;

public class SpawnHandler {
	
	private static SpawnHandler instance = new SpawnHandler();
	private boolean playerSet = false;
	private SpawnHandler() {
		// TODO Auto-generated constructor stub
	}
	
	public void tick() {
		
	} // reading not working...
	public void mapReader() {
		MapHandler.getInstance().generateMap(40,40);
		
		for (int x = 0; x < MapHandler.WIDTH; x++) {
			for (int y = 0; y < MapHandler.HEIGHT; y++) {
				if (MapHandler.getInstance().isClosed(x,y)) {
					Handler.getInstance().addObject(new Block(x*32,y*32,32,32,Id.Block,0));				
				}  
//				else if (!playerSet) {
//					Handler.getInstance().addObject(new Player(x*32,y*32, Id.Player, 0));
//					//
//					playerSet = true;
//				}
//					System.out.print("A ");	
			} 
		} 
		
		MapHandler.getInstance().randomOpenSpawn();
		Handler.getInstance().addObject(new Player(MapHandler.getInstance().openSpace.getX()*32, MapHandler.getInstance().openSpace.getY()*32, Id.Player, 0));
	}
	public void initSpawn() {
//		for (int i = NUM_OF_PLAYERS-1; i >= 0; i--) { // check for max players supported || allow for dynamic key setting
//			Player p = new Player((i+1)*100d,(i+1)*150d,Id.Player,i);
			//			System.out.println(p.getType());
			//			p.setWidth(16);
			//			p.setHeight(16);
//			Handler.getInstance().addObject(p);
//		}
		//		Handler.getInstance().addObject(new Block(100-32,150+64,Id.Block,0));
		//		Handler.getInstance().addObject(new Block(100-32,150-64,Id.Block,0));
		//		Handler.getInstance().addObject(new Block(100-32,150,Id.Block,0));
		//		Handler.getInstance().addObject(new Block(100-32,150+32,Id.Block,0));
		//		Handler.getInstance().addObject(new Block(100-32,150-32,Id.Block,0));
		//		Handler.getInstance().addObject(new Block(100,150-64,Id.Block,0));
		//		Handler.getInstance().addObject(new Block(100+32,150-64,Id.Block,0));
		//		Handler.getInstance().addObject(new Block(100+64,150-64,Id.Block,0));
		//		
		//		Handler.getInstance().addObject(new Block(100+96,150-64,Id.Block,0));
		//		Handler.getInstance().addObject(new Block(100+96,150+64,Id.Block,0));
		//		Handler.getInstance().addObject(new Block(100+96,150,Id.Block,0));
		//		Handler.getInstance().addObject(new Block(100+96,150+32,Id.Block,0));
		//		Handler.getInstance().addObject(new Block(100+96,150-32,Id.Block,0));
		//		Handler.getInstance().addObject(new Block(100,150+64,Id.Block,0));
		//		Handler.getInstance().addObject(new Block(100+32,150+64,Id.Block,0));
		//		Handler.getInstance().addObject(new Block(100+64,150+64,Id.Block,0));

		//		handler.addObject(new Block(100+32,150,Id.Block,0));

		
		
		
		
//		Handler.getInstance().addObject(new Player(100,100, Id.Player, 0));
		
		
		mapReader();
		
//		Handler.getInstance().addObject(new Block(400, 150, Id.Block, 0));
	}
	
	public static SpawnHandler getInstance() {
		return instance;
	}

}
