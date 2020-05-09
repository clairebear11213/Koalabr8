package tankrotationexample;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author anthony-pc
 */
public class Tank {


    private int x;
    private int y;
    private int vx;
    private int vy;
    private int angle;

    private int R = 2;
    private int ROTATION_SPEED = 4;
    private Rectangle hitBox;
    private ArrayList<Bullet> ammo;


    private BufferedImage tankImage;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;

    public int life = 300;

    Tank(int x, int y, int vx, int vy, int angle, BufferedImage tankImage) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.tankImage = tankImage;
        this.angle = angle;
        this.hitBox = new Rectangle(x, y, this.tankImage.getWidth(), this.tankImage.getHeight());
        this.ammo = new ArrayList<>();

    }

    public Rectangle getHitBox() {
        return hitBox.getBounds();
    }


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

    void toggleShootPressed() {
        this.ShootPressed = true;
    }

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

    void unToggleShootPressed() {
        this.ShootPressed = false;
    }

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

    public int getVx() {
        return vx;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public int getVy() {
        return vy;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    public boolean isUpPressed() {
        return UpPressed;
    }

    public boolean isDownPressed() {
        return DownPressed;
    }

    public void update() {
        if (this.UpPressed) {
            this.moveForwards();
        }
        if (this.DownPressed) {
            this.moveBackwards();
        }
        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }
        if (this.ShootPressed && (TankRotationExample.tickCount % 20 == 0)) {
            Bullet b = new Bullet(x, y, angle, TankRotationExample.bulletImage);
            this.ammo.add(b);
        }
        this.ammo.forEach(bullet -> {
            bullet.update();
            if(this.getHitBox().intersects(bullet.getHitBox())) {
                life--;
                System.out.println("You've been shot! HEALTH: " + life);
            }
        });

        /*for (int i = 0; i < this.ammo.size(); i++) {
            this.ammo.get(i).update();
        }*/



    }

    private void rotateLeft() {
        this.angle -= this.ROTATION_SPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATION_SPEED;
    }

    private void moveBackwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
        checkBorder();
        this.hitBox.setLocation(x, y);
    }

    private void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        this.hitBox.setLocation(x, y);
    }




    private void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= TankRotationExample.WORLD_WIDTH - 80) {
            x = TankRotationExample.WORLD_WIDTH - 80;
        }
        if (y < 30) {
            y = 30;
        }
        if (y >= TankRotationExample.WORLD_HEIGHT - 80) {
            y = TankRotationExample.WORLD_HEIGHT - 80;
        }
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }


    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.tankImage.getWidth() / 2.0, this.tankImage.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.tankImage, rotation, null);
        //b.drawImage(g);
        this.ammo.forEach(bullet -> bullet.drawImage(g));
        /*for (int i = 0; i < this.ammo.size(); i++) {
            this.ammo.get(i).drawImage(g);
        }*/
        g2d.setColor(Color.CYAN);
        g2d.drawRect(x, y, this.tankImage.getWidth(), this.tankImage.getHeight());
    }



}
