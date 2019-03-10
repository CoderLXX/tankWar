import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Tank extends GameObject implements KeyListener {

    private static final int XSPEED = 5, YSPEED = 5;

    private int WIDTH = 35, HEIGHT= 35;

    private int oldX, oldY;

    private final boolean enemy;

    private static int lifeValue = 100;

    private Direction direction;

    private Direction pDirection = Direction.Down;

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

    Tank(int x, int y, boolean enemy) {
        this.x = x;
        this.y = y;
        this.enemy = enemy;
        this.oldX = x;
        this.oldY = y;
    }

    boolean isEnemy() {
        return this.enemy;
    }

    @Override
    public void setLive(boolean live) {
        super.setLive(live);
    }

    public void initDirection(Direction dir) {
        direction = dir;
        pDirection = dir;
        Image image = tkImgs.get(pDirection.abbrev);
        WIDTH = image.getWidth(null);
        HEIGHT = image.getHeight(null);
    }

    @Override
    public void draw(Graphics g) {
        if (!isLive()) {
            return;
        }
        Image tankImg = tkImgs.get(pDirection.abbrev);

        WIDTH = tankImg.getWidth(null);
        HEIGHT = tankImg.getHeight(null);


        g.drawImage(tankImg, x, y, null);
        if (direction != null) {
            pDirection = direction;
            move();
        }


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
        else if(!kL && !kU && !kR && !kD) direction = null;

    }

    private void move() {
        oldX = x;
        oldY = y;
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

        }

        if (x < 0) x = 0;
        else if (y < 0) y = 0;
        else if (x > TankWar.WIDTH - WIDTH) x = TankWar.WIDTH - WIDTH;
        else if (y > TankWar.HEIGHT - HEIGHT - 25) y = TankWar.HEIGHT - HEIGHT - 25;

    }

    private void fire() {
        if (!this.isLive()) return;
        int mx = x + WIDTH / 2 - Missile.WIDTH / 2;
        int my = y + HEIGHT / 2 - Missile.HEIGHT / 2;
        Missile m = new Missile(mx, my, pDirection, enemy);
        TankWar.getInstance().addMissile(m);
    }


    private void stay() {
        x = oldX;
        y = oldY;
    }

    private int step = Tools.nextInt(12) + 3;

    void directionRandom() {
        Direction[] dirs = Direction.values();
        if (step == 0) {
            step = Tools.nextInt(12) + 3;
            int rn = Tools.nextInt(dirs.length);
            pDirection = direction = dirs[rn];
            if (Tools.nextBoolean()) {
                this.fire();
            }
        }
        step --;
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
            case KeyEvent.VK_R:
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
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }

    boolean collidesWithWalls(List<Wall> walls) {
        for (Wall w: walls) {
            if (this.isLive() && this.getRectangle().intersects(w.getRectangle())) {
                stay();
                return true;
            }
        }
        return true;
    }

    public void collidesWithTanks(List<Tank> tanks) {
        for (Tank t: tanks) {
            if (this == t) continue;

            if (this.isLive() && t.isLive() && this.getRectangle().intersects(t.getRectangle())) {
                if (this.enemy) {
                    this.stay();
                }
                t.stay();
                return;
            }
        }
    }


}
