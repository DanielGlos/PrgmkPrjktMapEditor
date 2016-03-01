package Panely;

import java.awt.*;
import java.awt.image.*;

public class Blok {
	
	BufferedImage image;
	int posX;
	int posY;
	
	public Blok(BufferedImage image) {
		this.image = image;
	}
	
	public void setPosition(int x, int y) {
		posX = x;
		posY = y;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(image, posX, posY, null);
	}
	
	public void draw(Graphics2D g, int scale) {
		Image scaledImage = image.getScaledInstance(image.getWidth() * scale, image.getHeight() * scale, Image.SCALE_SMOOTH);
		g.drawImage(scaledImage, posX, posY, null);
		g.setColor(Color.BLUE);
		g.drawRect(posX, posY, image.getWidth() * scale, image.getHeight() * scale);
	}
	
}
