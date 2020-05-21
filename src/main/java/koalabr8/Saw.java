package koalabr8;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Saw {

    int x, y, angle, startpt, endpt;
    BufferedImage sawImage;
    Rectangle hitBox;

    public Saw(int x, int y, int angle, int startpt, int endpt, BufferedImage sawImage) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.startpt = startpt;
        this.endpt = endpt;
        this.sawImage = sawImage;
        this.hitBox = new Rectangle(x, y, this.sawImage.getWidth(), this.sawImage.getHeight());
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

    public void updateHitBox(int newX, int newY) {
        this.hitBox.x = newX;
        this.hitBox.y = newY;
    }

    public void move() {
        if (angle == 90 || angle == 270) {   //horizontal
            if (x == endpt) {    //at end
                int temp = startpt;
                startpt = endpt;
                endpt = temp;
            } else {    //at beginning or somewhere in the middle
                if (startpt < endpt) {          // --->
                    x++;
                } else if (startpt > endpt) {   // <---
                    x--;
                }
            }
//                for (int d = startpt; d <= endpt; d++) {
//                    x++;
//                }
//                int temp = startpt;
//                startpt = endpt;
//                endpt = temp;
//            }
//                for (int d = startpt; d >= endpt; d--) {
//                    x--;
//                }
//                int temp = startpt;
//                startpt = endpt;
//                endpt = temp;
//            }
        } else if (angle == 0 || angle == 180) {   //vertical
            if (y == endpt) {    //at end
                int temp = startpt;
                startpt = endpt;
                endpt = temp;
            } else {    //at beginning or somewhere in the middle
                if (startpt < endpt) {          // --->
                    y++;
                } else if (startpt > endpt) {   // <---
                    y--;
                }
            }
//            if (startpt < endpt) {          // --->
//                for (int d = startpt; d <= endpt; d++) {
//                    y++;
//                }
//                int temp = startpt;
//                startpt = endpt;
//                endpt = temp;
//            } else if (startpt > endpt) {   // <---
//                for (int d = startpt; d >= endpt; d--) {
//                    y--;
//                }
//                int temp = startpt;
//                startpt = endpt;
//                endpt = temp;
//            }
        }
    }

    public void drawImage(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(this.sawImage, x, y, null);
        g2.setColor(Color.GREEN);
        g2.drawRect(x, y, this.sawImage.getWidth(), this.sawImage.getHeight());
    }
}
