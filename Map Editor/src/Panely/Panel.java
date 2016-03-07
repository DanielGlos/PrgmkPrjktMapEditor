package Panely;

import java.awt.Graphics2D;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;



/**
 * Hlavny panel od ktoreho dedia panely na zobrazovanie tile setu a na vykreslovanie mapy
 * @author Daniel
 *
 */
@SuppressWarnings("serial")
public abstract class Panel extends JPanel implements Runnable, MouseListener, MouseMotionListener{
	
	protected int WIDTH;
	protected int HEIGHT;
	
	protected static final int tileSize = 60;
	protected static final int numRowsTiles = 2;;
	
	
	protected static BufferedImage tileSet;
	protected static BufferedImage[][] tiles;
	protected static int numTiles;
	
	protected static Blok[] blocks;
	protected static BufferedImage currBlockImage;
	protected static int currBlock;
	
	protected Thread thread;
	protected int FPS;
	protected long targetTime;
	
	
	protected BufferedImage mainImage;
	protected Graphics2D mainGraphics;
	
	/*
	private int mapWidth;
	private int mapHeight;
	private int[][] map;
	private int xMap;
	private int yMap;
	*/
	
	
	
	public Panel() {
		super();
	}
	
	protected abstract void init();
	
	protected abstract void draw();
	
	protected abstract void render();
	
	
}
