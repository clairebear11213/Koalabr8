package koalabr8;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;

public class TNT {

    int x, y;
    BufferedImage tntImage;
    Rectangle hitBox;

    public TNT(int x, int y, BufferedImage tntImage) {
        this.x = x;
        this.y = y;
        this.tntImage = tntImage;
        this.hitBox = new Rectangle(x, y, this.tntImage.getWidth(), this.tntImage.getHeight());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rectangle getHitBox() {
        return hitBox.getBounds();
    }

    public void drawImage(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(this.tntImage, x, y, null);
        g2.setColor(Color.GREEN);
        g2.drawRect(x, y, this.tntImage.getWidth(), this.tntImage.getHeight());
    }

}
