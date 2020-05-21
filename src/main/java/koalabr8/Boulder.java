package koalabr8;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;

public class Boulder {

    int x, y;
    BufferedImage boulderImage;
    Rectangle hitBox;

    public Boulder(int x, int y, BufferedImage boulderImage) {
        this.x = x;
        this.y = y;
        this.boulderImage = boulderImage;
        this.hitBox = new Rectangle(x, y, this.boulderImage.getWidth(), this.boulderImage.getHeight());
    }

    //get and set x and y
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void updateHitBox(int newX, int newY) {
        this.hitBox.x = newX;
        this.hitBox.y = newY;
    }

    public Rectangle getHitBox() {
        return hitBox.getBounds();
    }

    public void drawImage(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(this.boulderImage, x, y, null);
        g2.setColor(Color.GREEN);
        g2.drawRect(x, y, this.boulderImage.getWidth(), this.boulderImage.getHeight());
    }

}
