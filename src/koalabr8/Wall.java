package koalabr8;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;

public class Wall {

    int x, y;
    BufferedImage wallImage;
    Rectangle hitBox;

    public Wall(int x, int y, BufferedImage wallImage) {
        this.x = x;
        this.y = y;
        this.wallImage = wallImage;
        this.hitBox = new Rectangle(x, y, this.wallImage.getWidth(), this.wallImage.getHeight());
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
        g2.drawImage(this.wallImage, x, y, null);
        g2.setColor(Color.GREEN);
        g2.drawRect(x, y, this.wallImage.getWidth(), this.wallImage.getHeight());
    }

}
