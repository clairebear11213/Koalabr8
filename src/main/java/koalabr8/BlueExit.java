package koalabr8;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BlueExit extends Exit {
    int x, y;
    BufferedImage exitImage;
    Rectangle hitBox;

    public BlueExit(int x, int y, BufferedImage exitImage) {
        this.x = x;
        this.y = y;
        this.exitImage = exitImage;
        this.hitBox = new Rectangle(x, y, this.exitImage.getWidth(), this.exitImage.getHeight());
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
    public String getType() {
        return "blue";
    }

    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(this.exitImage, x, y, null);
        g2.setColor(Color.GREEN);
        g2.drawRect(x, y, this.exitImage.getWidth(), this.exitImage.getHeight());
    }
}
