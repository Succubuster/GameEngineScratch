package window;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Vector;



public class MapHandler {
	
	private static Id MAP[][];
	
	public static final Id OPEN = Id.Air, CLOSED = Id.Block; 
	
	public String seed = "henry";
	public boolean useRandSeed = true;
	public int randomFillPercent;
	public Random prng = new Random();
	
	public static int WIDTH, HEIGHT; // idk if static or nah?

	private int smoothFactor = 5;

	public Coordinate openSpace;
	private static MapHandler instance = new MapHandler();
	private MapHandler() {
		
	}
	
	
	public void mapReader() {
		//generateMap(99,99);

		//readAloud();
	}
	
	
	public void generateMap(int w, int h) {
		WIDTH = w;
		HEIGHT = h;
		
		MAP = new Id[WIDTH][HEIGHT];
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				open(x,y);
			}
		}
		
		fillRandomly();
//		readAloud();
		for (int i = 0; i < smoothFactor ; i++) {
			smoothMap();
//			readAloud();
		}
//		processMap();	}
	
	private static void clearMap(Id[][] arr) {
		arr = new Id[WIDTH][HEIGHT];
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				arr[x][y] = OPEN;
			}
		}
	}
	
	public void fillRandomly() {
		if (useRandSeed) {
			seed = Long.toString(System.currentTimeMillis());
			System.out.println("Seed: " + seed);
		}
		setSeed(seed);
		randomFillPercent = 55; //prng.nextInt(101);
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				if (x == 0 || x == WIDTH - 1 || y == 0 || y == HEIGHT - 1) {
					
				}
				if (prng.nextInt(100) > randomFillPercent) { // may fix to better formatted
					close(x,y);
				}
			}
		}
	}
	
	public void smoothMap() { //maybe add a queue
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				int nearWallsCount = getSurroundingWallCount(x,y);
				if (nearWallsCount > 4)
					close(x,y);
				else if (nearWallsCount < 4)
					open(x,y); 
			}
		}
	}

	private int getSurroundingWallCount(int x, int y) {
		int wallCount = 0;
		
		for (int nx = x - 1; nx <= x + 1; nx++) {
			for (int ny = y - 1; ny <= y + 1; ny++) {
				if (isInMapRange(nx, ny)) {
					if (nx != x || ny != y) {
						if (isClosed(nx,ny)) wallCount++;
					}
				} else 
					wallCount++;
			}
		}		
		return wallCount;
	}

	public Vector<Coordinate> getOpenSpaces(int sx, int sy) {
		Vector<Coordinate> open = new Vector<Coordinate>();
		
		Id mapFlags[][] = new Id[WIDTH][HEIGHT];
		mapFlags = new Id[WIDTH][HEIGHT];
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				mapFlags[x][y] = OPEN;
			}
		}
		
		Id startType = getState(sx,sy);
//		int startType = MAP[sx,sy];
		
		Queue<Coordinate> queue = new LinkedList<Coordinate>();
		queue.add(new Coordinate(sx,sy));
		mapFlags[sx][sy] = CLOSED;
		
		while (queue.peek() != null) {
			Coordinate space = queue.poll();
			open.add(space);
			
			for (int x = space.getX() - 1; x < space.getX() + 1; x++) {
				for (int y = space.getY() - 1; y < space.getY() + 1; y++) {
					if (isInMapRange(x,y)) {
						if (y == space.getY() || x == space.getY()) {
							if (mapFlags[x][y] == OPEN && MAP[x][y] == startType) {
								mapFlags[x][y] = CLOSED;
								queue.add(new Coordinate(x,y));
							}
						}
						
					}
				}
			}
		} return open;
	}

	public Vector<Vector<Coordinate>> getRegions(Id type) {
		Vector<Vector<Coordinate>> regions = new Vector<Vector<Coordinate>>();
		
		Id mapFlags[][] = new Id[WIDTH][HEIGHT];
		mapFlags = new Id[WIDTH][HEIGHT];
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				mapFlags[x][y] = OPEN;
			}
		}
		
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				if (isInMapRange(x,y) && mapFlags[x][y] == OPEN && MAP[x][y] == type) {
					Vector<Coordinate> newRegion = getOpenSpaces(x,y);
					regions.add(newRegion);
					
					for (Coordinate space: newRegion) {
						mapFlags[space.getX()][space.getY()] = CLOSED;
					}
				}
			}
		}
		
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				if (mapFlags[x][y] == CLOSED) {
					System.out.print("d ");
				} else if (mapFlags[x][y] == OPEN) {
					System.out.print("u ");
				}
			} System.out.println();
		}
		
		return regions;
	}
	
	public void processMap() {
		Vector<Vector<Coordinate>> wallRegions = getRegions(Id.Block);
		int wallThreshold = 5; // can be modded for desires
		Vector<Room> survivingRooms = new Vector<Room> ();
		for (Vector<Coordinate> wallRegion: wallRegions) {
			if (wallRegion.size() < wallThreshold) {
				for (Coordinate space: wallRegion) {
					open(space.getX(),space.getY()); //play around with the open and close
				}
			} 
		}
		
		Vector<Vector<Coordinate>> airRegions = getRegions(OPEN);
		int spaceThreshold = 10; // can be modded for desires
		Vector<Room> open = new Vector<Room> ();
		for (Vector<Coordinate> airRegion: airRegions) {
			if (airRegion.size() < spaceThreshold) {
				for (Coordinate space: airRegion) {
					close(space.getX(),space.getY()); //play around with the open and close
				}
			} else 
				open.add(new Room(airRegion,MAP));
		}
		Collections.sort(open);
		for (Room r: open) {
			System.out.println(r.roomSize);
		}
	}
	
	public void connectClosestRooms(Vector<Room> rooms) {
		double bestDistance = 0;
		Coordinate bestSpaceA = null;
		Coordinate bestSpaceB = null;
		Room bestRoomA = new Room(); // not sure of the benefit
		Room bestRoomB = new Room();
		boolean possibleConnectionFound = false;
		
		for (Room roomA: rooms) {
			possibleConnectionFound = false;
			for (Room roomB: rooms) {
				if (roomA == roomB) continue; // should break?
				if (roomA.isConnectedTo(roomB)) {
					possibleConnectionFound = false; continue; // should continue? // was a break
				}
				for (int indexA = 0; indexA < roomA.edges.size(); indexA++) {
					for (int indexB = 0; indexB < roomB.edges.size(); indexB++) {
						Coordinate spaceA = roomA.edges.get(indexA);
						Coordinate spaceB = roomB.edges.get(indexB);
						double distanceBetweenRooms = Math.pow(spaceA.x - spaceB.x, 2) + Math.pow(spaceA.y - spaceB.y, 2);
						
						if (distanceBetweenRooms < bestDistance || !possibleConnectionFound) {
							bestDistance = distanceBetweenRooms;
							possibleConnectionFound = true;
							bestSpaceA = spaceA;
							bestSpaceB = spaceB;
							bestRoomA = roomA;
							bestRoomB = roomB;
							
						}
					}
				}
			}
			if (possibleConnectionFound) {
				
			}
		}
	}
	
	public void createPassage(Room roomA, Room roomB, Coordinate archwayA, Coordinate archwayB) {
		roomA.connectRoom(roomB); //may mess up
		
		
	}
	
	
	public double[] getRealCoordinates(Coordinate c) { // could also use Point2D
		double pointLoc[] = new double[2]; // only 2dimensional for now
		pointLoc[0] = c.getX()*32;
		pointLoc[1] = c.getY()*32;
		return pointLoc;
	}
		
	public void randomOpenSpawn() {
		Vector<Vector<Coordinate>> areas = getRegions(OPEN);
		int largest = 0;
		Vector<Coordinate> largestArea = null;
		
		for (Vector<Coordinate> area: areas) {
			if (area.size() > largest) {
				largest = area.size();
				largestArea = area;
			}
		}
		
		openSpace = largestArea.get(prng.nextInt(largest));
	}
	public void readAloud() {
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				if (isClosed(x,y)) {
					System.out.print("B ");					
				} else if (isOpen(x,y))
					System.out.print("A ");	
			} System.out.println();
		} System.out.println();
	}
	

	
	public String getSeed() {
		return seed;
	}
	
	public void setSeed(String s) {
		prng.setSeed(s.hashCode());
	}
	
	public boolean isInMapRange(int x, int y) {
		return x > 0 && x < WIDTH-1 && y > 0 && y < HEIGHT-1;
	}
	
	public static MapHandler getInstance() {
		return instance;
	}
	
	private void close(int x, int y) {
		MAP[x][y] = CLOSED;
	}
	
	private void open(int x, int y) {
		MAP[x][y] = OPEN;
	}
	
	public boolean isClosed(int x, int y) {
		return MAP[x][y] == CLOSED;
	}
	
	public boolean isOpen(int x, int y) {
		// TODO Auto-generated method stub
		return MAP[x][y] == OPEN;
	}
	
	public Id getState(int x, int y) {
		// TODO Auto-generated method stub
		return MAP[x][y];
	}
	class Room implements Comparable<Room>{
		public Vector<Coordinate> spaces;
		public Vector<Coordinate> edges;
		public Vector<Room> connectedRooms;
		public int roomSize;
		
		public Room() {
			
		}
		
		public Room(Vector<Coordinate> roomSpaces, Id map[][]) {
			spaces = roomSpaces;
			roomSize = roomSpaces.size();
			connectedRooms = new Vector<Room>();
			
			edges = new  Vector<Coordinate>();
			for (Coordinate space : spaces) {
				for (int x = space.getX() - 1; x < space.getX() + 1; x++) {
					for (int y = space.getY() - 1; y < space.getY() + 1; y++) {
						if (x == space.getX() || y == space.getY()) {
							if (map[x][y] == CLOSED) { // might need to be tweaked
								edges.add(space);
							}
						}
					}
				}
			}
		}
		
		public void connectRoom(Room otherRoom) { // cannot be static??
			this.connectedRooms.add(otherRoom);
			otherRoom.connectedRooms.add(this);
		}
		public boolean isConnectedTo(Room otherRoom) {
			return connectedRooms.contains(otherRoom);
		}
		public int compareTo(Room o) {
			Room otherRoom;
			
			if (o instanceof Room) otherRoom = (Room) o;
			else return 0;
			
			if (otherRoom.roomSize > roomSize) return -1;
			else if (otherRoom.roomSize < roomSize) return 1;
			else return 0;
//			return Integer.compare(otherRoom.roomSize, roomSize);
		}
	}
	class Coordinate {
		int x,y;
//		Id contents = null;
		Coordinate(int x, int y) {
			this.x = x;
			this.y = y;
		}
		public int getX() {
			return x;
		}
		public int getY() {
			return y;
		}
//		public Id getContents() {
//			return contents;
//		}
//		public void setContents(Id contents) {
//			this.contents = contents;
//		}
	}

//	private Vector<Coordinate> spawnRoom(int w, int h, Coordinate start) {
//	Id filler = Id.Block;
//	Vector<Coordinate> walls = new Vector<Coordinate>();
//	int startingX = start.getX()+1, startingY = start.getY()+1;
//	
//	// clamping
//	Game.clamp(startingX, 0, WIDTH);
//	
//	for (int x = startingX; x < w+startingX; x++) {
//		Coordinate top = get(x,start.getY());
//		Coordinate bottom = get(x,start.getY()+h+1);
//		
//		top.setContents(filler);
//		bottom.setContents(filler);
//		
//		walls.add(top);
//		walls.add(bottom);
//	}
//	for (int y = startingY; y < h+startingY; y++) {
//		Coordinate left = get(start.getX(),y);
//		Coordinate right = get(start.getX()+w+1,y);
//		
//		left.setContents(filler);
//		right.setContents(filler);
//		
//		walls.add(left);
//		walls.add(right);
//	}
//	return walls;
//}
}
