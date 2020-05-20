package koalabr8;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Koala {

    private int x;
    private int y;
    private int move = 2;

    private int XSIZE = 50;
    private int YSIZE = 50;
    private Rectangle hitBox;

    static long upCount = 0;
    static long downCount = 0;
    static long leftCount = 0;
    static long rightCount = 0;
    private int clickSize = 17;
    //static long t = Koalabr8.tickCount;

    private BufferedImage koalaImage;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;

    public boolean alive = true;

    Koala(int x, int y, BufferedImage koalaImage) {
        this.x = x;
        this.y = y;
        this.koalaImage = koalaImage;
        this.hitBox = new Rectangle(x, y, this.koalaImage.getWidth(), this.koalaImage.getHeight());
    }

    //hitbox
    public Rectangle getHitBox() {
        return hitBox.getBounds();
    }

    //press button
    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    //unpress button
    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    //press button boolean
    public boolean isUpPressed() {
        return UpPressed;
    }

    public boolean isDownPressed() {
        return DownPressed;
    }

    public boolean isLeftPressed() {
        return LeftPressed;
    }

    public boolean isRightPressed() {
        return RightPressed;
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

    //update
    public void update() {
        if (this.UpPressed) {
            this.moveUp();
        }
        if (this.DownPressed) {
            this.moveDown();
        }
        if (this.LeftPressed) {
            this.moveLeft();
        }
        if (this.RightPressed) {
            this.moveRight();
        }
    }

    //move
    private void moveUp() {
        y -= move;
//        upCount++;
//        if (upCount % clickSize == 0) {
//            y -= YSIZE;
//            upCount = 0;
//        }
        //y -= vy;
        //y -= YSIZE;
        checkBorder();
        this.hitBox.setLocation(x, y);
    }

    private void moveDown() {
        y += move;
//        downCount++;
//        if (downCount % clickSize == 0) {
//            y += YSIZE;
//            downCount = 0;
//        }
        //y += vy;
        //y += YSIZE;
        checkBorder();
        this.hitBox.setLocation(x, y);
    }

    private void moveLeft() {
        x -= move;
//        leftCount++;
//        if (leftCount % clickSize == 0) {
//            x -= XSIZE;
//            leftCount = 0;
//        }
        //x -= vx;
        //x -= XSIZE;
        checkBorder();
        this.hitBox.setLocation(x, y);
    }

    private void moveRight() {
        x += move;
//        rightCount++;
//        if (rightCount % clickSize == 0) {
//            x += XSIZE;
//            rightCount = 0;
//        }
        //x += vx;
        //x += XSIZE;
        checkBorder();
        this.hitBox.setLocation(x, y);
    }

    //check border
    private void checkBorder() {
        if (x < 50) {
            x = 50;
        }
        if (x >= Koalabr8.WORLD_WIDTH - 100) {
            x = Koalabr8.WORLD_WIDTH - 100;
        }
        if (y < 50) {
            y = 50;
        }
        if (y >= Koalabr8.WORLD_HEIGHT - 100) {
            y = Koalabr8.WORLD_HEIGHT - 100;
        }
    }

    //toString
    @Override
    public String toString() {
        return "x=" + x + ", y=" + y;
    }

    //draw
    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.koalaImage, rotation, null);
        g2d.setColor(Color.CYAN);
        g2d.drawRect(x, y, this.koalaImage.getWidth(), this.koalaImage.getHeight());
    }
}
