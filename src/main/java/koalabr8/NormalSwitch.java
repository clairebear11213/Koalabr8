package koalabr8;

import java.awt.*;
import java.awt.image.BufferedImage;

public class NormalSwitch extends Switch {

    int x, y;
    BufferedImage switchImage;
    Rectangle hitBox;

    public NormalSwitch(int x, int y, BufferedImage switchImage) {
        this.x = x;
        this.y = y;
        this.switchImage = switchImage;
        this.hitBox = new Rectangle(x, y, this.switchImage.getWidth(), this.switchImage.getHeight());
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
    public int getType() {
        return 1;
    }

    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(this.switchImage, x, y, null);
        g2.setColor(Color.GREEN);
        g2.drawRect(x, y, this.switchImage.getWidth(), this.switchImage.getHeight());
    }
}
