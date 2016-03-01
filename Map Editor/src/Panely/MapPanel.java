package Panely;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.SwingUtilities;

import Panely.updateMap.UpdateMap;

@SuppressWarnings("serial")
public class MapPanel extends Panel implements KeyListener{
	
	private int mapWidth;
	private int mapHeight;
	private int[][] map;
	private int xMap;
	private int yMap;
	
	private int middleX;
	private int middleY;
	
	private int FPS = 30;
	private long targetTime = 1000/FPS;
	
	
	public MapPanel() {
		super();
		init();
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		setFocusable(true);
		requestFocus();
	}
	
	
	@Override
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			thread.start();
		}
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	@Override
	public void run() {
		//init();
		
		long start;
		long elapsed;
		long wait;
		
		
		while(true) {
			start = System.nanoTime();
			
			update();
			render();
			draw();
			
			elapsed = (System.nanoTime() - start) / 1000000;
			wait = targetTime - elapsed;
			if(wait < 0) wait = 5;
			try{
				Thread.sleep(wait);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	@Override
	protected void init() {
		WIDTH = 1024;
		HEIGHT = 768;
		
		mainImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_BGR);
		mainGraphics = (Graphics2D) mainImage.getGraphics();
		
		mapWidth = 30;
		mapHeight = 30;
		map = new int[mapWidth][mapHeight];
		xMap = (WIDTH - (mapWidth * tileSize)) / 2;
		yMap = (HEIGHT  - (mapHeight * tileSize)) / 2;
		
		FPS = 30;
		targetTime = 1000/FPS;
		
	}

	@Override
	protected void render() {
		mainGraphics.setColor(new Color(245,245,220));
		mainGraphics.fillRect(0, 0, WIDTH, HEIGHT);
		
		
		//draws map
		for(int row=0;row< mapHeight;row++) {
			for(int col=0;col<mapWidth;col++) {
				try{
					mainGraphics.drawImage(blocks[map[row][col]].getImage(),col * tileSize + xMap, row * tileSize + yMap, null);
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		
		//Nakresli ram mapy
		mainGraphics.setColor(Color.RED);
		mainGraphics.drawRect(xMap, yMap, mapWidth * tileSize, mapHeight * tileSize);
		
		
		//Nakresli mriezku, ak mapWidth != mapHeight je potrebne robit v dvoch cykloch
		mainGraphics.setColor(Color.GRAY);
		for(int i=1; i < mapWidth;i++) {
			mainGraphics.drawLine(xMap + i * tileSize, yMap, xMap + i * tileSize, yMap + mapHeight * tileSize);
		}
		for(int j=1; j < mapHeight;j++) {
			mainGraphics.drawLine(xMap, yMap + j * tileSize, xMap + mapWidth * tileSize, yMap + j * tileSize);
		}
		/*
		//Nakresli zorne pole okna
		mainGraphics.setColor(new Color(152,251,152));
		mainGraphics.drawRect(xMap + (((mapWidth * tileSize) - (int)mapView.getWidth()) / 2), yMap + (((mapHeight * tileSize) - (int) mapView.getHeight() ) / 2), (int)mapView.width, (int)mapView.height);
			
			*/
		
		
	}
	
	@Override
	protected void draw() {
		Graphics g2 = getGraphics();
		g2.drawImage(mainImage, 0, 0, WIDTH, HEIGHT, null);
		g2.dispose();
	}

	
	private void update() {
		//clearMap();
	}
	
	
	public void clearMap() {
		for(int i=0;i<mapWidth;i++) {
			for(int j=0;j<mapHeight;j++) {
				map[i][j] = 8;
			}
		}
	}
	
	public void updateMap(UpdateMap update) {
		int[][] temp;
		switch(update) {
		case PLUS_WIDTH:
			mapWidth++;
			temp = new int[mapHeight][mapWidth];
			for(int row = 0; row < mapHeight; row++) {
				for(int col = 0; col < mapWidth-1; col++) {
					temp[row][col] = map[row][col];
				}
			}
			map = temp;
			break;
		case PLUS_HEIGHT:
			mapHeight++;
			temp = new int[mapHeight][mapWidth];
			for(int row = 0; row < mapHeight-1; row++) {
				for(int col = 0; col < mapWidth; col++) {
					temp[row][col] = map[row][col];
				}
			}
			map = temp;
			break;
		case MINUS_WIDTH:
			mapWidth--;
			temp = new int[mapHeight][mapWidth];
			for(int row = 0; row < mapHeight; row++) {
				for(int col = 0; col < mapWidth; col++) {
					temp[row][col] = map[row][col];
				}
			}
			map = temp;
			break;
		case MINUS_HEIGHT:
			mapHeight--;
			temp = new int[mapHeight][mapWidth];
			for(int row = 0; row < mapHeight; row++) {
				for(int col = 0; col < mapWidth; col++) {
					temp[row][col] = map[row][col];
				}
			}
			map = temp;
			break;
		}
	}
	
	
	public int getMapWidth() { return mapWidth; }
	public int getMapHeight() { return mapHeight; }
	
	public void setMapWidth(int w) { mapWidth = w; }
	public void setMapHeight(int h) { mapHeight = h; }
	
	public int[][] getMap() { return map; }
	
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent me) {
		if(SwingUtilities.isRightMouseButton(me)) {
			middleX = me.getX();
			middleY = me.getY();
		}else if(SwingUtilities.isLeftMouseButton(me)) {
			int y = me.getY() - yMap;
			int x = me.getX() - xMap;
			if(x > 0 && x < mapWidth * tileSize &&
					y > 0 && y < mapHeight * tileSize) {
				map[y / tileSize][x / tileSize] = currBlock;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent me) {
	}

	@Override
	public void mouseDragged(MouseEvent me) {
		int x;
		int y;
		if(SwingUtilities.isRightMouseButton(me)) {
			x = me.getX();
			y = me.getY();
			int dX = (x - middleX) / tileSize;
			int dY = (y - middleY) / tileSize;
			if(x > 0 && x < mapWidth * tileSize &&
					y > 0 && y < mapHeight * tileSize)
				if(dX != 0 || dY != 0) {
					middleX = me.getX();
					middleY = me.getY();
					xMap += dX * tileSize;
					yMap += dY * tileSize;
				}
		}else if(SwingUtilities.isLeftMouseButton(me)) {
			x = me.getX() - xMap;
			y = me.getY() - yMap;
			map[y / tileSize][x / tileSize] = currBlock;
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyPressed(KeyEvent e) {
		int k = e.getKeyCode();
		switch(k) {
		case(KeyEvent.VK_C):
			clearMap();
			break;
		case(KeyEvent.VK_LEFT):
			xMap += tileSize;
			break;
		}
	}


	@Override
	public void keyReleased(KeyEvent e) {
		
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	
}
