package koalabr8;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PressureLock extends Lock {
    int x, y;
    BufferedImage lockImage;
    Rectangle hitBox;

    public PressureLock(int x, int y, BufferedImage lockImage) {
        this.x = x;
        this.y = y;
        this.lockImage = lockImage;
        this.hitBox = new Rectangle(x, y, this.lockImage.getWidth(), this.lockImage.getHeight());
    }

    @Override
    public int getX() {
        return x;
    }
    @Override
    public int getY() {
        return y;
    }

    @Override
    public Rectangle getHitBox() {
        return hitBox.getBounds();
    }

    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(this.lockImage, x, y, null);
        g2.setColor(Color.GREEN);
        g2.drawRect(x, y, this.lockImage.getWidth(), this.lockImage.getHeight());
    }
}