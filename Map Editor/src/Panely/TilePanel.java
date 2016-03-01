package Panely;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

import java.awt.Graphics2D;

@SuppressWarnings("serial")
public class TilePanel extends Panel{
	
	private static final String adresa = "/testtileset.gif";
	private static final int scale = 2;
	
	public TilePanel() {
		super();
		init();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
	}
	
	@Override
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			thread.start();
		}
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
	
	/**
	 * Nacita obrazok do tile setu a nasledne ho rozparsuje na jednotlive tiles
	 * taktiez inicializuje bloky
	 * @param adresa - tileSet obrazok
	 */
	private void loadTileSet(String adresa) {
		try{
			tileSet = ImageIO.read(getClass().getResourceAsStream(adresa));
			//tu sa da vypocitat rozmer dlazdice ale nie je potrebne ak ho zadame hned na zaciatku
			//tileSize = tileSet.getHeight() / 2;
			int numTilesAcross = tileSet.getWidth() / tileSize;
			numTiles = numTilesAcross * numRowsTiles;
			tiles = new BufferedImage[numRowsTiles][numTilesAcross];
			for(int i = 0; i < numTilesAcross; i++) {
				tiles[0][i] = tileSet.getSubimage(tileSize * i, 0, tileSize, tileSize);
				tiles[1][i] = tileSet.getSubimage(tileSize * i, tileSize, tileSize, tileSize);
			}
			
			
			//skontrolovat ci numTilesAcross == width blocks
			blocks = new Blok[numTiles];
			for(int i = 0; i < numTilesAcross ; i++) {
				blocks[i] = new Blok(tiles[0][i]);
				blocks[i].setPosition(i * tileSize,  HEIGHT - 2 * tileSize);
				blocks[i + numTilesAcross] = new Blok(tiles[1][i]);
				blocks[i + numTilesAcross].setPosition(i * tileSize, HEIGHT - tileSize);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void init() {
		loadTileSet(adresa);
		
		WIDTH = 301;
		HEIGHT = 601;
		
		mainImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_BGR);
		mainGraphics = (Graphics2D) mainImage.getGraphics();
		
		blocks = new Blok[numTiles];
		int width = numTiles / numRowsTiles;
		
		FPS = 1;
		targetTime = 1000/FPS;
		
		int col = 0;
		int row = 0;
		
		for(int i = 0; i < width; i++) {
			if(col == 5) {
				col = 0;
				row++;
			}
			blocks[i] = new Blok(tiles[0][i]);
			blocks[i].setPosition(col * tileSize * 2,row * tileSize * 2);
			blocks[i + width] = new Blok(tiles[1][i]);
			blocks[i + width].setPosition(col * tileSize * scale, row * tileSize * scale + 300);
			col++;
		}
		
		
		
		
		
		
		
		
	}
	
	@Override
	protected void render() {
		mainGraphics.setColor(new Color(245,245,220));
		mainGraphics.fillRect(0, 0, WIDTH, HEIGHT);
		
		
		//Nakresli tile bloky
		for(int i = 0; i < numTiles; i++) {
			blocks[i].draw(mainGraphics, scale);
		}
		
	}

	@Override
	protected void draw() {
		Graphics g = getGraphics();
		g.drawImage(mainImage, 0, 0, WIDTH, HEIGHT, null);
		g.dispose();
	}
	

	@Override
	public void mouseClicked(MouseEvent me) {}

	@Override
	public void mouseEntered(MouseEvent me) {}

	@Override
	public void mouseExited(MouseEvent me) {}

	@Override
	public void mousePressed(MouseEvent me) {
		if(SwingUtilities.isLeftMouseButton(me)) {
			int x = me.getX();
			int y = me.getY();
			if((x < 60 && y < 300)|| y < 240) {
			currBlock = ((x/60) + ((y/60) * 5));
			currBlockImage = blocks[currBlock].getImage();
			}else if(y > 300 && y < 360 ){
				currBlock = ((x/60) + ((y/60) * 5)) - 4;
				currBlockImage = blocks[(currBlock)].getImage();
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent me) {}

	@Override
	public void mouseDragged(MouseEvent me) {}

	@Override
	public void mouseMoved(MouseEvent me) {
		
	}

}
