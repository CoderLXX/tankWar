import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

class Tank extends GameObject implements KeyListener {

    private static final int XSPEED = 5, YSPEED = 5;

    private static boolean isEnemy = false;

    private static int lifeValue = 100;

    private static Direction direction = Direction.Stop;

    private static Direction gunDir = Direction.Stop;

    private static Image[] tankImages = null;
    private static Map<String, Image> tkImgs = new HashMap<String, Image>();

    private static Toolkit tk = Toolkit.getDefaultToolkit();

    private boolean kL, kU, kR, kD;

    static {
        tankImages = new Image[] {
                tk.getImage(Tank.class.getClassLoader().getResource("images/tankL.gif")),
                tk.getImage(Tank.class.getClassLoader().getResource("images/tankLU.gif")),
                tk.getImage(Tank.class.getClassLoader().getResource("images/tankU.gif")),
                tk.getImage(Tank.class.getClassLoader().getResource("images/tankRU.gif")),
                tk.getImage(Tank.class.getClassLoader().getResource("images/tankR.gif")),
                tk.getImage(Tank.class.getClassLoader().getResource("images/tankRD.gif")),
                tk.getImage(Tank.class.getClassLoader().getResource("images/tankD.gif")),
                tk.getImage(Tank.class.getClassLoader().getResource("images/tankLD.gif")),

        };

        tkImgs.put("L", tankImages[0]);
        tkImgs.put("LU", tankImages[1]);
        tkImgs.put("U", tankImages[2]);
        tkImgs.put("RU", tankImages[3]);
        tkImgs.put("R", tankImages[4]);
        tkImgs.put("RD", tankImages[5]);
        tkImgs.put("D", tankImages[6]);
        tkImgs.put("LD", tankImages[7]);

    }


    Tank(int x, int y) {
        this(x, y, false);
    }

    Tank(int x, int y, boolean isEnemy) {
        this.x = x;
        this.y = y;
        this.isEnemy = isEnemy;
    }

    public void initDirection(Direction dir) {
        direction = dir;
    }

    @Override
    public void draw(Graphics g) {
        if (!isLive()) {
            return;
        }

        Image tankImg = tkImgs.get(gunDir.abbrev);
        g.drawImage(tankImg, x, y, null);
        move();



    }

    private void changeTankDirection() {
        if(kL && !kU && !kR && !kD) direction = Direction.Left;
        else if(kL && kU && !kR && !kD) direction = Direction.LeftUp;
        else if(!kL && kU && !kR && !kD) direction =Direction.Up;
        else if(!kL && kU && kR && !kD) direction =Direction.RightUp;
        else if(!kL && !kU && kR && !kD) direction =Direction.Right;
        else if(!kL && !kU && kR && kD) direction =Direction.RightDown;
        else if(!kL && !kU && !kR && kD) direction =Direction.Down;
        else if(kL && !kU && !kR && kD) direction =Direction.LeftDown;
        else if(!kL && !kU && !kR && !kD) direction =Direction.Stop;
    }

    private void move() {
        switch (direction) {
            case Left:
                x -= XSPEED;
                break;
            case LeftUp:
                x -= XSPEED;
                y -= YSPEED;
                break;
            case Up:
                y -= YSPEED;
                break;
            case RightUp:
                x += XSPEED;
                y -= YSPEED;
                break;
            case Right:
                x += XSPEED;
                break;
            case RightDown:
                x += XSPEED;
                y += YSPEED;
                break;
            case Down:
                y += YSPEED;
                break;
            case LeftDown:
                x -= XSPEED;
                y += YSPEED;
                break;
            case Stop:
                break;

        }
        if (direction != Direction.Stop) {
            gunDir.abbrev = direction.abbrev;
            System.out.println(gunDir.abbrev);
        }

    }

    private void fire() {

    }



    @Override
    public void keyTyped(KeyEvent e) {
        // -> Ignore
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_E:
                if (!isLive()) {
                    setLive(true);
                }
                break;
            case KeyEvent.VK_LEFT:
                kL = true;
                break;
            case KeyEvent.VK_UP:
                kU = true;
                break;
            case KeyEvent.VK_RIGHT:
                kR = true;
                break;
            case KeyEvent.VK_DOWN:
                kD = true;
                break;
        }
        changeTankDirection();

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_1:
                fire();
                break;
            case KeyEvent.VK_LEFT:
                kL = false;
                break;
            case KeyEvent.VK_UP:
                kU = false;
                break;
            case KeyEvent.VK_RIGHT:
                kR = false;
                break;
            case KeyEvent.VK_DOWN:
                kD = false;
                break;
        }
        changeTankDirection();

    }

    @Override
    Rectangle getRectangle() {
        return null;
    }
}
