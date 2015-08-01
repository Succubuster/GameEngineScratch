package window;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import javax.swing.event.MouseInputAdapter;
// add GameMaster and make Game dependent on it
public class Game extends Canvas implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7316508010004193915L;

	public static final int WIDTH = 640, HEIGHT = WIDTH/12*9, NUM_OF_PLAYERS = 1; // 16:9 aspect ratio // final will be an issue for cmd line args
	private Thread thread;
	private boolean running = false, spawnIgnored = true;
	
	private HUD hud = new HUD(); 
	
	private static Game instance = new Game();
	
	private Menu menu = new Menu();
//	private Handler handler;
	
	public enum STATE {
		Menu,
		Game;
	};
	
	public STATE gameState = STATE.Game;

	public boolean playerPresent = false;
	
	public void switchToGame() {
		gameState = STATE.Game;
	}
	public void switchToMenu() {
		gameState = STATE.Menu;
	}
	
	private Game () {
		new Window (WIDTH,HEIGHT,"Test",this);
		
		
		
		if (gameState == STATE.Game) {
			SpawnHandler.getInstance().initSpawn();
			spawnIgnored = false;
		}
		
		this.addKeyListener(new KeyListener());
		MouseInputAdapter curs = new CursorListener();
		this.addMouseListener(curs);
		this.addMouseMotionListener(curs);
		this.addMouseListener(menu);
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;		
	}
	
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void run() {
		// Not mine below, still a mystery
		this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running){
                long now = System.nanoTime();
                delta += (now - lastTime) / ns;
                lastTime = now;
                while(delta >= 1){
                        tick();
                        delta--;
                }
                if(running)
                        render();
                frames++;
                if(System.currentTimeMillis() - timer > 1000){
                        timer += 1000;
//                        System.out.println("FPS: " + frames);
                        frames = 0;
                }
        }
        stop();
	}
	
	public void tick() {
		Handler.getInstance().tick();
		for (GameObject o: Handler.getInstance().getChain()) {
			if (o.getId() == Id.Player)
				playerPresent = true;
		}
		if (playerPresent) {
			Camera.getInstance().tick();
		}
		if (gameState == STATE.Game) {
			if (spawnIgnored) {
				SpawnHandler.getInstance().initSpawn();
				spawnIgnored = false;
			}
			hud.tick();
		} else if (gameState == STATE.Menu) {
			menu.tick();
		}
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		// DRAW WITHIN BOUNDS //
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g2d.translate(Camera.getInstance().getX(), Camera.getInstance().getY());
		
		Handler.getInstance().render(g);
		
		g2d.translate(-Camera.getInstance().getX(), -Camera.getInstance().getY());
		if (gameState == STATE.Game) {
			hud.render(g);
		} else if (gameState == STATE.Menu) {
			menu.render(g);
		}
		
		
		//                    //
		g.dispose();
		bs.show();
		
	}
	// forces a varible to be bound between min and max
	public static int clamp(int var, int min, int max) {
		if (var >= max) return var = max;
		else if (var <= min) return var = min;
		else return var;
	}
	public static Game getInstance() {
		return instance;
	}
	
	public static void main (String[] args) {
//		if (args.length == 2)
//			new Game(args[0],args[1]);
//		else
//			new Game(600,600);
//		Game g = new Game();
//		g.start();
		Game.getInstance().start();
	}

	
}
